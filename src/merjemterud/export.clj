(ns merjemterud.export
  (:require
    [clojure.java.io :as io]
    [clojure.string :as str]
    [merjemterud.main :as main]
    [powerpack.export :as export]))

(def build-dir "docs")

(defn write-cname!
  "GitHub Pages needs docs/CNAME verbatim. Powerpack wipes the build dir on
   every export and only writes files it knows as Optimus assets — and those it
   writes under cache-busted paths — so the domain file is copied in by hand."
  []
  (->> (str/trim (slurp (io/resource "public/CNAME")))
       (spit (io/file build-dir "CNAME"))))

(defn ^:export export! [& args]
  (-> main/config
      (assoc :site/base-url "https://merjemterud.no"
             :powerpack/build-dir build-dir)
      export/export!)
  (write-cname!))
