---
name: jemterud-festival-design
description: Use this skill to generate well-branded interfaces and assets for the Jemterud 50 festival — a Norwegian one-off music & arts festival that doubles as a 50th birthday party for an experimental-music / pirate-radio nerd. Use for production work, prototypes, posters, flyers, slides, social cards, or any visual artifact that needs the festival's lo-fi handmade aesthetic. Contains brand guidelines, colors, type, fonts, asset files, and a UI kit recreating the marketing site.
user-invocable: true
---

# Jemterud 50 — Festival Design Skill

Read `README.md` in this skill folder first — it contains the full brand definition, content fundamentals, visual foundations, and iconography rules.

## Core idea, in one line
**Yellow gaffer tape (`#ffe850`) with hand-scrawled black permanent marker, stuck to a back-room venue wall.** Everything else falls out of that.

## What's in this skill

- `README.md` — full brand definition. Start here.
- `colors_and_type.css` — drop-in CSS custom properties + base type styles. Import at the top of any HTML you make.
- `assets/` — real visual assets: logo, web banner. Copy these out — never redraw them.
- `preview/` — small reference cards showing every brand decision (colors, type, tape strips, components). Read these to learn the system; reproduce their patterns in new work.
- `ui_kits/festival_website/` — a hi-fi React recreation of the festival's marketing site. Steal the JSX components and patterns wholesale.

## How to use this skill

If creating **visual artifacts** (slides, mocks, throwaway prototypes, posters, social cards):
1. Copy `assets/logo.png` and `assets/web-banner_2026.png` out — they are the source of truth for the brand.
2. Link `colors_and_type.css` and rely on the CSS variables (`--tape-yellow`, `--marker-black`, etc).
3. Reuse the `Tape`, `OnAirPip`, `MarkerScrawl`, `Btn` atoms from `ui_kits/festival_website/Atoms.jsx`.
4. Output static HTML files for the user to view.

If working on **production code**:
1. Read `README.md` cover-to-cover, especially CONTENT FUNDAMENTALS, VISUAL FOUNDATIONS, and ICONOGRAPHY.
2. Lift the design tokens from `colors_and_type.css` into the codebase's own design-token system.
3. Treat the JSX in `ui_kits/festival_website/` as a visual reference, not as importable code — re-implement with the production stack.

## When the user invokes this skill with no other guidance

Ask:
- What are they making? (poster · slide · web page · social post · email · something else)
- For what? (announcement · ticket promo · schedule release · post-festival thanks)
- Norwegian, English, or both?
- Do they have real photography / a lineup to use, or should you use placeholders?

Then act as an expert designer for this brand and output an HTML artifact (default) or production code (if asked).

## Hard rules

- **Never use emoji.** Marker glyphs (`★ ● ✕ → ▲`) or tape labels instead.
- **No soft drop-shadows, no gradients, no glassmorphism, no rounded cards.** Hard offset shadows, flat colours, brutalist corners.
- **Permanent Marker is for tape labels and big headlines only.** Body text is always Open Sans.
- **Never put yellow text on white.** It fails contrast. Yellow goes on black, or it's a fill behind black text.
- **Voice is dry, self-deprecating, lowercase Norwegian + English mix.** Never corporate. Never `we're excited to announce`.
