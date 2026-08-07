(ns merjemterud.program
  "Parses the program blocks in content/program.md into runsheet data.

   A block is a stage header line followed by pipe-delimited event lines:

     Hovedscena, Pubscena

     17:30 | Hovedscena | Blikstilt | Nøkkelharpe og piano.
     18:00 | Pubscena   | Performance |

   Bad lines abort the build rather than rendering an empty cell."
  (:require [clojure.string :as str]))

(def ^:private time-re #"\d{2}:\d{2}")

(defn- fail
  [block line message]
  (throw (ex-info (str "Bad program line in " block ": " message "\n  " line)
                  {:block block :line line})))

(defn- parse-event
  [block stages line]
  (let [fields (mapv str/trim (str/split line #"\|" 4))]
    (when (< (count fields) 3)
      (fail block line "expected `HH:MM | Stage | Title | Blurb`"))
    (let [[time stage title blurb] fields
          canonical (first (filter #(.equalsIgnoreCase ^String % stage) stages))]
      (when-not (re-matches time-re time)
        (fail block line (str "bad time " (pr-str time))))
      (when-not canonical
        (fail block line (str "unknown stage " (pr-str stage)
                              " — expected one of " (str/join ", " stages))))
      (when (str/blank? title)
        (fail block line "missing title"))
      {:time time :stage canonical :title title :blurb (not-empty (str blurb))})))

(defn parse-block
  "Turns one program block into {:stages [name] :rows [{:time _ :cells {stage [event]}}]}.

   Rows are sorted by time, one per distinct time. A cell holds a vector because
   several things can start at once on the same stage."
  [block s]
  (let [[header & lines] (->> (str/split-lines (str s))
                              (map str/trim)
                              (remove str/blank?)
                              (remove #(str/starts-with? % "#")))
        stages (mapv str/trim (str/split (str header) #","))]
    (when (str/blank? (str header))
      (fail block "" "missing stage header line"))
    (let [events (mapv #(parse-event block stages %) lines)]
      (when (empty? events)
        (fail block "" "no events"))
      {:stages stages
       :rows   (->> (group-by :time events)
                    (sort-by key)
                    (mapv (fn [[time evs]]
                            {:time  time
                             :cells (into {} (for [stage stages]
                                               [stage (filterv #(= stage (:stage %)) evs)]))})))})))

(defn time-span
  "First to last start time of a parsed block, for the block heading."
  [{:keys [rows]}]
  (str (:time (first rows)) "–" (:time (last rows))))
