(ns merjemterud.main
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [merjemterud.program :as program]
            [powerpack.markdown :as md]))

(defn prose
  [s]
  (when (not-empty s)
    [:div.prose (md/render-html s)]))

(defn tape-words
  "Split a title into one yellow-tape-grey strip per word."
  [title]
  (for [word (str/split (str title) #"\s+")]
    [:span.tape.tape--grey.hero__word word]))

;; ── Shared chrome ──────────────────────────────────────────

(def nav-items
  [["/" "Festivalen"]
   ["/program.html" "Program"]
   ["/praktisk.html" "Praktisk"]
   ["/match.html" "Match"]])

(defn site-nav
  [page]
  (let [uri (:page/uri page)]
    [:header.topnav
     [:a.topnav__brand {:href "/"}
      [:span.topnav__name "Jemterud 50"]
      [:small.topnav__sub "Festival · Alcatraz · 4.–6. sep 2026"]]
     [:nav.topnav__links
      (for [[href label] nav-items]
        [:a {:href  href
             :class (str "topnav__link"
                         (when (= href uri) " topnav__link--active"))}
         label])]
     [:a.btn.btn--primary.topnav__cta {:href (:cta/url page)} "Billett →"]]))

(defn site-footer
  []
  [:footer.footer
   [:p.footer__mark "Jemterud 50"]
   [:p.footer__meta "4.–6. september 2026 · Dalslands kanal, Sverige"]
   [:p.footer__fine "Enda mer Jemterud i monitor plis"]])

(defn cta-band
  [page]
  [:section.cta-band
   [:p.cta-band__label "Det jeg ønsker meg i bursdag er festival"]
   [:a.btn.btn--dark.btn--lg {:href (:cta/url page)} (:cta/label page)]])

(defn layout
  [page & body]
  [:html {:lang "no"}
   [:head
    [:link {:rel "preconnect" :href "https://fonts.googleapis.com"}]
    [:link {:rel "preconnect" :href "https://fonts.gstatic.com" :crossorigin true}]
    [:link {:rel "stylesheet"
            :href "https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600;700;800&family=Permanent+Marker&display=swap"}]]
   [:body
    (site-nav page)
    (into [:main.site] body)
    (site-footer)]])

(defn content-block
  "Subtitle + rendered markdown, one stop on a Program/Praktisk page."
  [title body]
  [:div.block
   [:h2.block__title title]
   (prose body)])

;; ── Front page ─────────────────────────────────────────────

(defn hero
  [page]
  [:header.hero
   [:div.hero__inner
    [:p.kicker (:hero/kicker page)]
    [:h1.hero__title (tape-words (:hero/title page))]
    [:p.hero__tagline (:hero/tagline page)]
    [:p.hero__lead (:hero/lead page)]
    [:div.hero__actions
     [:a.btn.btn--primary.btn--lg {:href (:cta/url page)} (:cta/label page)]]]
   [:div.stamp
    [:span.stamp__big "50"]
    [:span.stamp__sub "år"]
    [:span.stamp__est "est. 1976"]]])

(defn landing
  [page]
  (layout page
          (hero page)

          ;; ── Program / intro ──
          [:section.section.section--paper
           [:div.section__inner
            [:div.eyebrow-row [:span.tape.tape--yellow "Program"]]
            (:intro/body page)
            [:ul.facts
             [:li.tape.tape--grey "3 scener"]
             [:li.tape.tape--grey "2 serveringssteder"]
             [:li.tape.tape--grey "vandrerhjem"]
             [:li.tape.tape--grey "festivalcamp"]
             [:li.tape.tape--grey "kanal + innsjø"]]
            [:div.teaser
             (prose (:teaser/body page))
             [:a.btn.btn--ghost {:href "/program.html"} "Se hele programmet →"]]]]

          ;; ── Bursdagspresang ──
          [:section.section.section--wall
           [:div.section__inner
            [:h2.section__title (:gift/title page)]
            [:div.card
             [:div.card__tapes
              [:span.tape.tape--yellow "blakk"]
              [:span.tape.tape--yellow "hvermannsen"]
              [:span.tape.tape--yellow "lyx"]]
             (prose (:gift/body page))]]]

          ;; ── Hvem kan melde seg på? ──
          [:section.section.section--paper
           [:div.section__inner
            [:h2.section__title (:who/title page)]
            (prose (:who/body page))]]

          (cta-band page)))

;; ── Program page ───────────────────────────────────────────

(defn radio-strip
  "Radio Jemterud isn't on any stage — it gets a banner above the grids."
  [text]
  (when (not-empty text)
    [:div.radio
     [:span.onair [:span.onair__pip] "On air"]
     [:p.radio__text text]]))

(defn slot
  "One event. The time/stage line is hidden on wide screens, where the grid
   already says both, and shown when the grid collapses to a list."
  [{:keys [time stage title blurb]}]
  [:div.slot
   [:p.slot__meta
    [:span.slot__time time]
    [:span.slot__stage stage]]
   [:h3.slot__title title]
   (when blurb [:p.slot__blurb blurb])])

(defn runsheet
  "Stage columns × time rows. Every cell is placed explicitly, so document
   order is free to be chronological — that's what the phone layout reads."
  [{:keys [stages rows]}]
  (let [column (zipmap stages (map #(+ % 2) (range)))]
    [:div.runsheet
     {:style (str "grid-template-columns: var(--runsheet-gutter) repeat("
                  (count stages) ", minmax(0, 1fr))")}
     ;; Opaque bar behind the sticky stage labels, so rows scroll under
     ;; the header instead of showing through it.
     [:div.runsheet__stagebar {:style "grid-column:1/-1;grid-row:1"}]
     (for [stage stages]
       [:div.runsheet__stage {:style (str "grid-column:" (column stage) ";grid-row:1")}
        [:span.tape.tape--yellow stage]])
     (for [[i {:keys [time cells]}] (map-indexed vector rows)
           :let [row (+ i 2)]]
       (list
        [:div.runsheet__rule {:style (str "grid-column:1/-1;grid-row:" row)}]
        [:div.runsheet__time {:style (str "grid-column:1;grid-row:" row)} time]
        (for [stage stages
              :let [events (get cells stage)]
              :when (seq events)]
          [:div.runsheet__cell {:style (str "grid-column:" (column stage) ";grid-row:" row)}
           (map slot events)])))]))

(defn runsheet-block
  [title source]
  (when (not-empty source)
    (let [data (program/parse-block title source)]
      [:div.runsheet-block
       [:h2.block__title title]
       [:p.runsheet-block__span (program/time-span data)]
       (runsheet data)])))

(defn chip-strip
  "Comma-separated things that run all weekend, with no time of their own."
  [title source]
  (when (not-empty source)
    [:div.runsheet-block
     [:h2.block__title title]
     [:ul.facts
      (for [item (map str/trim (str/split source #","))]
        [:li.tape.tape--grey item])]]))

(defn program-page
  [page]
  (layout page
          [:section.section.section--paper
           [:div.section__inner
            [:div.eyebrow-row [:span.tape.tape--yellow "Program"]]
            [:h1.section__title "Program"]
            (prose (:program/note page))
            (radio-strip (:program/radio page))
            (runsheet-block "Fredag" (:program/fredag-kveld page))
            (runsheet-block "Lørdag dag" (:program/lordag-dag page))
            (runsheet-block "Lørdag kveld" (:program/lordag-kveld page))
            (chip-strip "Andre aktiviteter" (:program/andre page))]]
          (cta-band page)))

;; ── Match page ─────────────────────────────────────────────

(defn match-page
  "A self-contained HTML document with its own head, styling and scripts.
   Served verbatim — a string body skips Powerpack's hiccup rendering.

   Powerpack resolves every img[src] as an asset and chokes on inline data:
   URIs. Those placeholders only keep the browser from drawing a broken image
   before the JS fills them in, so strip them on the way out."
  [_page]
  (-> (slurp (io/resource "match.html"))
      (str/replace #"\s+src=\"data:[^\"]*\"" "")))

;; ── Praktisk page ──────────────────────────────────────────

(defn praktisk-page
  [page]
  (layout page
          [:section.section.section--paper
           [:div.section__inner
            [:div.eyebrow-row [:span.tape.tape--yellow "Praktisk"]]
            [:h1.section__title "Praktisk info"]
            (content-block "Hvor er dette?" (:sted/body page))
            (content-block "Overnatting" (:overnatting/body page))
            (content-block "Mat og drikke" (:mat/body page))
            (content-block "Transport" (:transport/body page))]]
          (cta-band page)))

(defn render-page
  [_context page]
  (case (:page/uri page)
    "/program.html" (program-page page)
    "/praktisk.html" (praktisk-page page)
    "/match.html" (match-page page)
    (landing page)))

(def config
  {:site/title            "Enda mer Jemterud i monitor plis"
   :powerpack/render-page #'render-page
   :optimus/bundles       {"app.css"
                           {:public-dir "public"
                            :paths      ["/styles.css"]}}})
