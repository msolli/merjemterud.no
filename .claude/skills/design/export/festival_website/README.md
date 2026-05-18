# Festival Website — UI kit

A hi-fi recreation of the Jemterud 50 festival marketing site. Single-page, scroll-based, built from the same handful of brand atoms.

## Run

Open `index.html` in a browser. Designed at **1280px** width.

## Components

| File | Exports | What it is |
|---|---|---|
| `Atoms.jsx` | `Tape`, `OnAirPip`, `MarkerScrawl`, `Btn` | Brand primitives — the tape strip, the live-radio pip, raw marker text, the standard button |
| `TopNav.jsx` | `TopNav` | Sticky dark nav bar with marker brand + live indicator + CTA |
| `Hero.jsx` | `Hero` | Title section, dark with halftone backdrop, tape-stack title |
| `LineupGrid.jsx` | `LineupGrid` | 3-col grid of artist cards with tape-label time/day |
| `Schedule.jsx` | `Schedule` | Day-tab program board on grey "wall" |
| `Sections.jsx` | `InfoBlock`, `FAQ`, `Footer`, `RadioBar` | Practical info, expanding FAQ, dark footer, fixed-bottom radio strip |

## Interactions implemented

- Day tabs in **Schedule** swap which day's set list is shown
- **FAQ** accordion — click question to expand
- Nav links update the active state
- **RadioBar** is a fixed-bottom always-on "now playing on FM" strip with a blinking ON AIR dot

## What's intentionally not real

- The "BILLETT" CTA doesn't go anywhere
- The radio player is decorative — no audio attached
- Artist names beyond Knut are real Norwegian experimental musicians used as placeholder lineup

## Caveats

- Lineup is plausible-but-invented — confirm with client before treating as truth
- Hero title is a hand-composed approximation of the logo. For the real thing, use `assets/logo.png` directly.
