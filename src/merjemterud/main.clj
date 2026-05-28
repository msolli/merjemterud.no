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

;; ── Shared chrome ──────────────────────────────────────────

(def nav-items
  [["/" "Festivalen"]
   ["/program/" "Program"]
   ["/praktisk/" "Praktisk"]])

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
             [:a.btn.btn--ghost {:href "/program/"} "Se hele programmet →"]]]]

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

(defn program-page
  [page]
  (layout page
          [:section.section.section--paper
           [:div.section__inner
            [:div.eyebrow-row [:span.tape.tape--yellow "Program"]]
            [:h1.section__title "Foreløpig program"]
            (prose (:program/note page))
            (content-block "Konserter fredag" (:program/fredag page))
            (content-block "Konserter lørdag" (:program/lordag page))
            (content-block "Andre aktiviteter" (:program/aktiviteter page))
            (content-block "Foredrag og debatter" (:program/foredrag page))]]
          (cta-band page)))

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
    "/program/" (program-page page)
    "/praktisk/" (praktisk-page page)
    (landing page)))

(def config
  {:site/title            "Enda mer Jemterud i monitor plis"
   :powerpack/render-page #'render-page
   :optimus/bundles       {"app.css"
                           {:public-dir "public"
                            :paths      ["/styles.css"]}}})
