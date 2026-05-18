# Jemterud Festival — Design System

A scrappy, hand-made identity for a one-off music & arts festival that doubles as a 50th birthday party for a Norwegian sound-nerd who lives at the intersection of experimental music, science, underground art, and pirate radio broadcasting.

The whole brand can be summarised as one image: **a strip of yellow gaffer tape with a name scrawled on it in permanent marker, stuck to a wall in a back-room venue.** Everything else falls out of that.

---

## What this is

The festival's branding is hand-made, lo-fi, and proudly amateurish — the kind of identity you'd expect taped to a venue door at 11pm. It borrows from:

- **Roadie culture** — gaffer tape, set-lists, the monitor-mix banter ("enda mer X i monitor plis" = "more X in the monitor please" — what a musician yells at the sound engineer mid-soundcheck).
- **Pirate / community radio** — broadcast timetables, frequency dials, transmitter graphics.
- **Underground show flyers** — photocopied, high-contrast halftones, off-kilter type, black & yellow caution colour-pairing.
- **Lab / science notebooks** — gridded paper, hand-annotated diagrams, oscilloscope traces.

The wordmark **ENDA MER JEMTERUD I MONITOR PLIS** is the festival's slogan AND its logo — a piece of stage-banter elevated to brand.

## Inputs given

| What | Where |
|---|---|
| Logo (yellow tape + marker) | `uploads/logo.png` → `assets/logo.png` |
| 2026 web banner | `uploads/web-banner_2026.png` → `assets/web-banner_2026.png` |
| Venue photo (aerial of Alcatraz, the old sluice complex) | `uploads/alcatraz.jpg` → `assets/photos/venue.jpg` |
| Band photos | Okkultokrati, Tusmørke, Svartskog Spelemannslag → `assets/photos/band-*.jpg` |
| Brand colours | Yellow `#ffe850`, greys `#666` and `#999` (provided by client) |
| Dates / venue | **4 — 6 September 2026, Alcatraz** (gammelt sluseanlegg ved sjøen) |
| Lineup | 13 bands + 3 talks + moderne dans + natursti (see `ui_kits/festival_website/LineupGrid.jsx`) |
| Display font | Permanent Marker (Google Fonts) — for tape labels |
| Body font | Open Sans (Google Fonts) — for everything else |

No codebase, Figma, or slide deck was provided. All UI is invented from the visual DNA of the logo + banner + the client's notes.

---

## CONTENT FUNDAMENTALS

### Voice
First-person plural, conversational, dry. The festival is run by friends, for friends — it does not pretend to be a corporation. Norwegian and English both appear; mix freely when it sounds natural.

### Casing
- **HEADLINES, BANNERS, BUTTONS, TAPE LABELS** → ALL CAPS. Always. Like a set-list.
- **Body copy** → sentence case.
- **Never** title case. It looks too tidy.

### Punctuation
- Em-dashes are fine.
- Exclamation marks are rationed — one per page, max.
- Slashes for either/or ("FRI / LØR / SØN", "noise / drone / techno").
- Numerals: numerals, never spelled out. "50 år", not "fifty years".

### Pronouns
- "We" for the festival/organisers.
- "You" for the reader.
- Never "the customer", "users", or "attendees" — say "folk", "everyone", or just describe what's happening.

### Vibe
Self-deprecating, slightly tired in a fun way, in on the joke. The kind of copy that admits the wifi might not work. **Examples:**

> "Doors at 19. Or 19:30. Depends."
>
> "50 years of bad ideas. One weekend to celebrate them."
>
> "Lineup is mostly confirmed. The rest will sort itself out."
>
> "Bring earplugs. Bring snacks. We'll provide the rest (probably)."

### Emoji
**None.** Replace with: hand-drawn glyphs, tape labels, Unicode pictographs in monospace contexts only (e.g. `→` `★` `●` `▲`), or simply nothing.

### Loanwords
Norwegian roadie/studio slang is welcome where it adds flavour: *plis, sjekk, monitor, gaffa, snor, mygg, lyd, scene*. Don't translate them — context is enough.

---

## VISUAL FOUNDATIONS

### Core motif: the tape label
The signature device is **a piece of yellow gaffer tape (`#ffe850`) with handwriting on it in black Permanent Marker.** Tape strips are rotated slightly (±2°–6°), have ragged left/right edges (clip-path), and overlap each other at the edges. Treat tape as a noun — you stick tape onto things, you don't recolour text "to look like tape".

### Colour
- **Tape Yellow** `#ffe850` — the brand colour. Used as a background fill behind handwriting. Almost never used for text.
- **Marker Black** `#0a0a0a` — handwriting, body text on light surfaces, primary fg.
- **Studio Grey** `#666` — secondary text, supporting UI.
- **Concrete Grey** `#999` — surfaces (where Permanent-Marker handwriting lives), tertiary text on dark.
- **Paper** `#f6f4ef` — warm off-white, primary light background.
- **Tape Yellow accessible variants** — `#ffe850` on black passes AAA; on white it fails — never put yellow text on white.
- **Accent: Frequency Red** `#e63946` — used sparingly for "ON AIR" lights, errors, single highlights.
- **Accent: Oscilloscope Cyan** `#7fd4d4` — used sparingly for trace lines, links on dark backgrounds, science diagrams.

The palette stays accessible by leaning on **black-on-yellow** and **white-on-black**. Grey is always paired with black or white text, never coloured text on grey.

### Type
- **Permanent Marker** for handwritten tape labels, headlines, big numbers. Mostly used **black on `#999`** (concrete grey) as if scrawled on a slab, OR black on `#ffe850` (yellow tape). NEVER on `#f6f4ef`. Always uppercase, always slightly rotated.
- **Open Sans** for everything else. Body at 16–18px, 1.5 line-height. Weights used: 400 (body), 600 (UI), 700 (emphasis), 800 (sub-headlines). Open Sans Condensed where present is welcome for set-lists and tables.
- **Open Sans Mono / fallback monospace** for technical content — frequency numbers, timecodes, coordinates, set times.
- Never letterspace Permanent Marker. Letterspace Open Sans Bold by +0.04em when used as uppercase eyebrows.

### Backgrounds
- Default: **Paper** (`#f6f4ef`) for content surfaces.
- Hero / banner: **Marker Black** with grainy halftone photography (high-contrast B&W, gritty grain), often a strip of tape composited on top.
- Sections sometimes use **Concrete Grey** (`#999`) as a full-bleed "wall" surface for big marker headlines.
- No gradients. No glassmorphism. No bluish-purple anything. Period.
- Repeating patterns are okay if they look hand-stamped (gaffa-tape edges, dotted grid like notebook paper).

### Imagery
- B&W or duo-tone (black + Tape Yellow) photography only.
- High-contrast, halftone or coarse grain on top.
- Subject matter: people playing/listening to music, hands on cables, radio gear, soldering irons, lab notebooks, festival crowds.
- Never glossy. Never colour-corrected to "look pro". The grit IS the brand.

### Borders
- Default border weight `1.5px`, colour `#0a0a0a`.
- Heavy/emphasised borders `3px` to `4px`.
- Borders are often **slightly off** — 1px shifted, rotated 0.5°, or hand-drawn-feeling.
- Cards: hard black border, optionally a 4px solid offset shadow (no blur) in `#ffe850` or `#0a0a0a`, like a photocopied flyer.

### Corner radii
- **Mostly zero.** Brutalist. Things have corners.
- Buttons: `2px` max (or `999px` for "tag" pills).
- Tape strips: clip-pathed ragged edges, no radius.
- Avatars: circular (`50%`).

### Shadows
- No soft blurry drop-shadows.
- Use **hard offset shadows** (`4px 4px 0 #0a0a0a`) for pop cards and primary buttons.
- Inner shadows reserved for "pressed" button state.

### Animation
- Sparse. The brand is paper-and-tape — paper doesn't bounce.
- Hover: instant (no transition) colour invert, or 80ms ease-out swap to underline.
- Buttons press: `translate(2px, 2px)` and drop their shadow.
- Tape strips can wobble in on first paint (rotate from 0° → final rotation, 200ms, `cubic-bezier(.2,.8,.2,1)`).
- "ON AIR" light: 1s steady blink.
- No fade-ins-on-scroll. No parallax. No 60fps gradient meshes.

### Hover / press states
- **Hover** on links: solid yellow highlight behind the text (the marker effect), instant.
- **Hover** on buttons: invert (yellow→black + black→yellow).
- **Press**: shrink 2px or hard-shift into the shadow.
- **Focus**: 3px yellow outline, 2px offset.

### Layout rules
- 12-column grid, but break it intentionally — let things hang off edges, overlap, rotate.
- Big margins. Lots of air. Set-list density only inside list components.
- Sticky headers are fine; sticky footers feel wrong. Use a fixed bottom "now playing" strip if you need persistent UI.
- Wide content max-width: 1280px. Reading max-width: 65ch.
- Don't centre everything. Asymmetric is on-brand.

### Transparency & blur
- **Blur** essentially never. The brand is not glassy.
- Transparency reserved for tape overlap (the yellow tape strip showing the photo through where a sticker has worn) — fake this with a `mix-blend-mode: multiply` on the yellow tape over photography.
- "Protection gradients" over imagery: a flat 80% black panel, not a gradient.

### Cards
A card is **a flat rectangle, black hairline border, off-white or yellow fill, optional 4px hard-offset shadow, optional rotated tape-strip label across the top corner.** No rounded corners. No gradient backgrounds.

---

## ICONOGRAPHY

The festival's voice is hand-made, so icons are too. Approach:

- **No icon font** is required. The whole UI can ship with **zero or near-zero iconography** — most affordance is carried by tape labels and typographic emphasis.
- **When icons are needed**, we substitute **Lucide** (via CDN) at `stroke-width: 2`, colour `currentColor`. Lucide's clean, slightly-utilitarian feel is a fine foil to the chaos of the tape — it reads as "studio gear labels" rather than "tech product UI".
- **FLAGGED SUBSTITUTION** → Lucide is a substitute. If you have a custom-drawn icon set or a real icon pack from the festival, please drop it in `assets/icons/` and we'll swap.
- **Marker glyphs** — `★`, `✕`, `→`, `●`, `▲` rendered in Permanent Marker can replace icons in many contexts (close button, next button, bullet). Always preferred over an icon when one of these works.
- **Unicode** — fine in monospace contexts (`→` for "go to", `●` for "on air").
- **Emoji** — **never.**

Approved CDN:

```html
<script src="https://unpkg.com/lucide@latest/dist/umd/lucide.min.js"></script>
```

Common icons in use: `radio`, `mic`, `volume-2`, `disc-3`, `play`, `pause`, `circle`, `square`, `x`, `arrow-right`, `arrow-up-right`, `map-pin`, `calendar`, `clock`, `wifi`, `zap`.

---

## INDEX

Files in the root of this design system:

| File / folder | What's in it |
|---|---|
| `README.md` | This file — high-level brand definition |
| `SKILL.md` | Agent-skill manifest (for Claude Code compatibility) |
| `colors_and_type.css` | All CSS custom properties + base type styles |
| `assets/` | Logo, banner, fonts referenced from CDN |
| `preview/` | Card files that populate the Design System tab |
| `ui_kits/festival_website/` | Hi-fi recreation of the festival's marketing site |

### UI kits

- **`ui_kits/festival_website/`** — the festival's marketing site. Hero, lineup, schedule, info, FAQ. Built out of the same handful of components.

### How to use this skill

1. Read this README.
2. Pull in `colors_and_type.css` at the top of any HTML you make.
3. Lean on the components in `ui_kits/festival_website/` — copy/paste their JSX, don't try to reinvent.
4. When in doubt, ask: **"would I tape this to a venue wall?"** If no, soften your design.

---

## CAVEATS / KNOWN GAPS

- **Festival name uncertainty.** "ENDA MER JEMTERUD I MONITOR PLIS" reads as the slogan; "Jemterud" is treated here as the celebrant's surname and de-facto brand. Confirm with the client.
- **Norwegian copy** in examples is plausible but not proofread by a native speaker.
- **Lucide icons are a substitute** — flag to client; replace with custom drawings if available.
- **Fonts are loaded from Google Fonts CDN.** If you need local TTFs for offline / print use, drop them into `assets/fonts/`.
- **No photography is included** — all imagery in the previews is placeholder. Real B&W halftone shots of past events/the celebrant would dramatically lift everything.
