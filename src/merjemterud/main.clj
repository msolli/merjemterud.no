(ns merjemterud.main
  (:require [clojure.string :as str]
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

(defn render-page
  [_context page]
  [:html {:lang "no"}
   [:head
    [:link {:rel "preconnect" :href "https://fonts.googleapis.com"}]
    [:link {:rel "preconnect" :href "https://fonts.gstatic.com" :crossorigin true}]
    [:link {:rel "stylesheet"
            :href "https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600;700;800&family=Permanent+Marker&display=swap"}]]
   [:body
    [:main.site
     ;; ── Hero ───────────────────────────────────────────────
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
       [:span.stamp__est "est. 1976"]]]

     ;; ── Program / intro ────────────────────────────────────
     [:section.section.section--paper
      [:div.section__inner
       [:div.eyebrow-row [:span.tape.tape--yellow "Program"]]
       (:intro/body page)
       [:ul.facts
        [:li.tape.tape--grey "3 scener"]
        [:li.tape.tape--grey "2 serveringssteder"]
        [:li.tape.tape--grey "vandrerhjem"]
        [:li.tape.tape--grey "festivalcamp"]
        [:li.tape.tape--grey "kanal + innsjø"]]]]

     ;; ── Bursdagspresang ────────────────────────────────────
     [:section.section.section--wall
      [:div.section__inner
       [:h2.section__title (:gift/title page)]
       [:div.card
        [:div.card__tapes
         [:span.tape.tape--yellow "blakk"]
         [:span.tape.tape--yellow "hvermannsen"]
         [:span.tape.tape--yellow "lyx"]]
        (prose (:gift/body page))]]]

     ;; ── Call to action ─────────────────────────────────────
     [:section.cta-band
      [:p.cta-band__label "Det jeg ønsker meg i bursdag er festival"]
      [:a.btn.btn--dark.btn--lg {:href (:cta/url page)} (:cta/label page)]]]

    ;; ── Footer ──────────────────────────────────────────────
    [:footer.footer
     [:p.footer__mark "Jemterud 50"]
     [:p.footer__meta "4.–6. september 2026 · Dalslands kanal, Sverige"]
     [:p.footer__fine "Enda mer Jemterud i monitor plis"]]]])

(def config
  {:site/title            "Enda mer Jemterud i monitor plis"
   :powerpack/render-page #'render-page
   :optimus/bundles       {"app.css"
                           {:public-dir "public"
                            :paths      ["/styles.css"]}}})
