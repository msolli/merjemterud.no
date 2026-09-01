(ns merjemterud.main
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [merjemterud.program :as program]
            [powerpack.hiccup :as hiccup]
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

;; ── Radio Jemterud ─────────────────────────────────────────
;;
;; The station is off air most of the time, so both the pip and the button are
;; rendered in the OFF state and only lifted to LIVE by radio-status-script.
;; A reader without JavaScript sees a working link and an honest "not on air".

(def radio-live-label "På lufta")
(def radio-off-label "Ikke på lufta")

(defn onair-pip
  "Status pip and its label. `bare?` drops the bordered box, for the hero, where
   a second framed element would compete with the stamp and the tape title."
  [bare?]
  [:span {:class (str "onair" (when bare? " onair--bare"))
          :data-radio-pip true}
   [:span.onair__pip]
   [:span {:data-radio-label true} radio-off-label]])

(defn radio-button
  [page]
  (when-let [url (:radio/url page)]
    [:a.btn.btn--radio.is-off {:href url
                               :target "_blank"
                               :rel "noopener"
                               :data-radio-btn true}
     "Hør Radio Jemterud →"]))

(def radio-status-script
  "Poll AzuraCast for on-air state and mirror it onto every pip and button.

   LIVE is HTTP 200 and live.is_live true. Everything else — 404 while the
   station is down, a network error, malformed JSON — reads OFF, because from a
   listener's seat unreachable is off air.

   Polling stops while the tab is hidden and re-fetches on the way back. The API
   caches for 15 s, so a 30 s period loses nothing."
  (str "(function () {
  var api = 'https://merjemterud.torshov.club/api/nowplaying/merjemterud';
  var period = 30000;
  var timer = null;
  var liveLabel = '" radio-live-label "';
  var offLabel = '" radio-off-label "';

  function paint(live) {
    var pips = document.querySelectorAll('[data-radio-pip]');
    for (var i = 0; i < pips.length; i++) {
      pips[i].classList.toggle('onair--live', live);
      var label = pips[i].querySelector('[data-radio-label]');
      if (label) label.textContent = live ? liveLabel : offLabel;
    }
    var btns = document.querySelectorAll('[data-radio-btn]');
    for (var j = 0; j < btns.length; j++) btns[j].classList.toggle('is-off', !live);
  }

  function poll() {
    fetch(api, { cache: 'no-store' })
      .then(function (res) { return res.ok ? res.json() : null; })
      .then(function (data) { paint(!!(data && data.live && data.live.is_live)); })
      .catch(function () { paint(false); });
  }

  function start() { poll(); timer = setInterval(poll, period); }
  function stop() { clearInterval(timer); timer = null; }

  document.addEventListener('visibilitychange', function () {
    if (document.hidden) { stop(); } else if (!timer) { start(); }
  });

  // A tab opened in the background waits for its first visibilitychange, so the
  // interval only ever runs while someone can see the pip.
  if (!document.hidden) { start(); }
})();"))

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
    (site-footer)
    [:script {:type "text/javascript"}
     (hiccup/unescape radio-status-script)]]])

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
     [:a.btn.btn--primary.btn--lg {:href (:cta/url page)} (:cta/label page)]
     (radio-button page)
     (onair-pip true)]]
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
  [page]
  (when-let [text (not-empty (:program/radio page))]
    [:div.radio
     (onair-pip false)
     [:p.radio__text text]
     (radio-button page)]))

(defn forjemterud-note
  "The Friday reception, before the first grid starts. Not on a stage, so it
   sits between the radio banner and the Fredag runsheet."
  [body]
  (when (not-empty body)
    [:div.forjemterud (prose body)]))

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

(defn program-page
  [page]
  (layout page
          [:section.section.section--paper
           [:div.section__inner
            [:div.eyebrow-row [:span.tape.tape--yellow "Program"]]
            [:h1.section__title "Program"]
            (prose (:program/note page))
            (radio-strip page)
            (forjemterud-note (:program/forjemterud page))
            (runsheet-block "Fredag" (:program/fredag-kveld page))
            (runsheet-block "Lørdag dag" (:program/lordag-dag page))
            (runsheet-block "Lørdag kveld" (:program/lordag-kveld page))
            (content-block "Hele helga, andre steder" (:program/andre page))
            (content-block "Søndag formiddag" (:program/sondag page))]]
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
