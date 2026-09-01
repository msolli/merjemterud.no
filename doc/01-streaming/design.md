# Radio Jemterud — streaming design

Recorded 2026-08-31. Festival 4–6 September 2026.

Goal: broadcast analog stereo from the venue mixer to listeners, and link to it from merjemterud.no.

The site does not play audio. It links out to the AzuraCast public page in a new tab.

---

## 1. Architecture

```
mixer (analog stereo)
  → Focusrite Scarlett 2i2        A/D, 48 kHz
  → BUTT (laptop)                 Ogg/Opus 96–128 kbps
  → 5G / venue wifi               manual failover
  → AzuraCast @ Hetzner Helsinki  Liquidsoap input.harbor → Icecast
  → listener browser              merjemterud.torshov.club/public/merjemterud
```

merjemterud.no carries only a status pip and a link.

---

## 2. Verified facts

Probed 2026-08-31 against the live install.

| Fact | Value |
|---|---|
| Station shortcode | `merjemterud` |
| Station name | "Enda mer Jemterud i monitor, pliz" |
| Frontend / backend | Icecast / Liquidsoap |
| Public page | `https://merjemterud.torshov.club/public/merjemterud` |
| Listen URL | `https://merjemterud.torshov.club/listen/merjemterud/radio.mp3`, 192 kbps MP3, default mount |
| HLS | disabled (`hls_enabled: false`) |
| API CORS | `access-control-allow-origin: *` |
| API cache | `cache-control: max-age=15` |
| Host | `62.238.104.244` — netname `CLOUD-HEL1`, HETZNER-DC (Hetzner Cloud Helsinki) |
| Public page `X-Frame-Options` | absent (the API sends `DENY`) |

State at time of recording, both unresolved:

- Station backend not running — the mount returns 404, `/api/nowplaying` returns `[]`.
- Ports 8000 / 8005 / 8010 closed or filtered from outside. Cause undetermined between "nothing listening" and "firewall".

From AzuraCast source (`backend/src/Radio/Backend/Liquidsoap.php:65-77`):

- DJ port = `backend_config.dj_port`, else frontend port + 5. Station id 1 → default **8005**.

From `azuracast.liq:666` and `FallbackFile.php`:

- The chain ends in `fallback(id="safe_fallback", track_sensitive=false, [radio, error_file])`. `error_file` is a `single()` on `settings.azuracast.fallback_path`, so *something* always plays while the backend runs.
- Default path `/usr/local/share/icecast/web/error.mp3` — the AzuraCast jingle.
- `station.fallback_path` overrides it per station, set from **Station → Broadcasting → Custom Fallback File**. Changing it sets `needs_restart`.
- `ConfigWriter.php` writes the `settings.azuracast.fallback_path :=` assignment *after* the `CUSTOM_TOP` injection point, so overriding the setting in the Liquidsoap config editor is clobbered. Use the upload page.

From `ConfigWriter.php:547-663`:

- The whole harbor block, live recording included, is gated on `enable_streamers`.
- `input.harbor` is passed no format restriction, so Liquidsoap auto-detects the incoming codec.

From AzuraCast documentation:

- BUTT in Icecast mode maps "Address" → Server and "Icecast mountpoint" → Mount Name, taken from the Streamer/DJ page sidebar.
- Reverse proxies often block broadcaster ports; the documented workaround is to connect by raw IP.
- Ogg Vorbis from a DJ client is explicitly supported and transcoded by Liquidsoap.

Now-playing schema carries two distinct signals (verified against `demo.azuracast.com`, which returns `is_online: true` with `live.is_live: false`):

- `is_online` — the Icecast mount is up, true even when AutoDJ plays.
- `live.is_live` — a streamer is connected.

---

## 3. Decisions

### Broadcast chain

| Id | Decision | Rationale |
|---|---|---|
| B1 | Liquidsoap DJ/streamer input, not direct Icecast source | Documented AzuraCast path; required by B6. Direct source would conflict with Liquidsoap, which is also a source on that mount. |
| B2 | BUTT sends Ogg/Opus 96–128 kbps | The mount re-encodes to 192 kbps MP3. A different codec family minimises generation loss versus MP3→MP3. Bandwidth is irrelevant — every candidate is under 0.25 Mbps. |
| B3 | 48 kHz | Opus operates internally at 48 kHz; 44.1 forces a resample for no benefit. The 2i2 is the A/D converter, so there is no upstream digital rate to match. |
| B4 | No AutoDJ — nothing is scheduled when nobody broadcasts | Radio will not run continuously. The mount does **not** go down, though: Liquidsoap stays up and plays the station fallback file, so off-air is signalled by `live.is_live`, not by a 404. See B7. |
| B5 | Single uplink with manual failover between 5G and venue wifi | The feed is attended and non-continuous; dual-WAN hardware is not warranted. BUTT auto-reconnect turns a 5G blip into a gap rather than an ending. |
| B6 | Server-side recording of live broadcasts | Enables listen-back after the festival. Requires B1. |
| B7 | Custom Fallback File — ~30 s of silence, uploaded per station | Without it the built-in `error.mp3` ("The station you're listening to is powered by AzuraCast…") loops whenever no streamer is connected, which under B4 is most of the time. Silence is the intended sound of off-air. |

Explicitly rejected: TLS on the DJ connection. The password crosses the network in the clear over plain Icecast, accepted for a festival-duration credential, rotated afterwards.

### Website

| Id | Decision | Rationale |
|---|---|---|
| A1 | Live on-air status polled from the AzuraCast API | The existing pip blinks unconditionally while the radio will not run continuously — it asserts something false. CORS is open, so a static page can read the real state. |
| A2 | Program page and front page | The front page is where people land. |
| A3 | `.btn` affordance | Existing button styles carry the weight the action needs. |
| A4 | `:radio/url` key duplicated in `index.md` and `program.md` | `render-page` receives one page's data, so cross-page values must be duplicated. `:cta/url` already works exactly this way. |
| C1 | Two states, LIVE / OFF | Any of {non-200, `is_live` false, network error, malformed JSON} reads OFF. From a listener's seat, unreachable is off air. |
| C2 | Button stays visible and clickable when off air, visually muted | Signals state without a layout shift or a removed link. |
| C3 | Poll ~30 s, paused on `visibilitychange` | The API caches 15 s, so faster gains nothing. Pausing avoids background waste on a long-open tab. |
| C4 | Front-page placement in the hero | Highest prominence. |
| C5 | Inline `<script>` in `layout` | First JavaScript on the site, roughly 20 lines. A bundle entry is not worth the build change. |
| D1 | Hero gets button plus a bare pip, no `.radio` box | The hero is title and actions; a bordered box there would compete with `.stamp` and the tape title. |
| D2 | New purpose-built `.btn` variant | Two `.btn--primary` would tie against the ticket CTA, `.btn--dark` already means "buy ticket" on this site, and `.btn--ghost` has no room left to mute further. |

LIVE is defined as HTTP 200 **and** `live.is_live === true`. Under B4 this nearly coincides with `is_online`, but `is_live` is the precise "Torkild is broadcasting" signal and stays correct if a playlist is ever added.

Out of scope: audio playback on merjemterud.no, iframe embed, HLS, now-playing metadata, a player that survives page navigation.

---

## 4. Work packages

### 4.1 Server — AzuraCast

| # | Item | Level | Done when |
|---|---|---|---|
| 1.1 | Diagnose why the station is not running | MUST | The listen URL returns 200, not 404 |
| 1.2.1 | Docker publishes 8005 | MUST | — |
| 1.2.2 | Host firewall allows 8005 | MUST | — |
| 1.2.3 | Hetzner Cloud Firewall allows 8005 inbound | MUST | `nc -z merjemterud.torshov.club 8005` succeeds off-venue |
| 1.3.1 | Enable Streamers on the station | MUST | Streamers/DJs page renders its sidebar values |
| 1.3.2 | DJ mount point left at default `/` | SHOULD | — |
| 1.3.3 | DJ buffer left at default | SHOULD | Raise only if the rehearsal shows dropouts |
| 1.3.4 | Set DJ port explicitly rather than rely on +5 | MAY | Removes ambiguity |
| 1.4 | Create the streamer account | MUST | Account listed and active |
| 1.5 | Enable Record Live Broadcasts | MUST | A test broadcast yields a downloadable file |
| 1.5.1 | Verify free disk on the Hetzner volume | SHOULD | Headroom for a weekend of recordings |
| 1.6 | No AutoDJ playlist | WONT | Chosen, per B4 |
| 1.7 | Upload a silent Custom Fallback File | MUST | Disconnecting BUTT yields silence on the mount, not the AzuraCast jingle |

Generating the file for 1.7, tagged because the fallback's metadata reaches listeners' players:

```bash
ffmpeg -f lavfi -i anullsrc=r=48000:cl=stereo -t 30 \
  -c:a libmp3lame -b:a 64k -metadata title="Radio Jemterud" -metadata artist=" " \
  silence.mp3
```

If port 8005 remains blocked after 1.1 succeeds, the block is network rather than "nothing listening". If nginx interferes, connect BUTT to `62.238.104.244`.

### 4.2 Local — Scarlett 2i2 and BUTT

| # | Item | Level | Done when |
|---|---|---|---|
| 2.1 | Mixer line out → 2i2 combo inputs via TRS, both channels | MUST | Halo rings green on programme material, never amber |
| 2.1.1 | INST switch off on both channels | MUST | Instrument mode is the wrong impedance for a line source |
| 2.1.2 | Direct Monitor off | MUST | Otherwise the room re-enters the feed |
| 2.1.3 | Air off | SHOULD | Mic voicing, wrong for a mix bus |
| 2.2 | Gain staged to peak ≈ −6 dBFS | MUST | BUTT's meter peaks near −6 with no clipping |
| 2.3 | 48 kHz in Audio MIDI Setup and in BUTT | MUST | Both read 48 000 Hz |
| 2.4 | BUTT server entry, Icecast mode | MUST | BUTT connects and AzuraCast shows the streamer live |
| 2.4.1 | Icecast mode, not Shoutcast | MUST | Shoutcast v1 cannot carry a username |
| 2.4.2 | No TLS on the DJ connection | WONT | Accepted, credential rotated after the festival |
| 2.5 | Ogg/Opus 96–128 kbps stereo | MUST | Mount carries audio, no Liquidsoap decoder error |
| 2.6 | Auto-reconnect enabled | MUST | Killing and restoring wifi resumes the mount unattended |
| 2.7 | On-site rehearsal on 5G and venue wifi | MUST | 15 minutes continuous with no listener-side dropouts, per link |

BUTT server fields, all taken verbatim from the AzuraCast Streamers/DJs sidebar:

| BUTT field | Source |
|---|---|
| Type | Icecast |
| Address | sidebar "Server" — hostname, or `62.238.104.244` if the proxy interferes |
| Port | sidebar "Port" — expect 8005 |
| Icecast user | streamer username |
| Password | streamer password |
| Icecast mountpoint | sidebar "Mount Name" — expect `/` |

Opus decoding in this install is unverified. If BUTT connects but the mount is silent or Liquidsoap logs a decoder error, fall back to Ogg/Vorbis, which AzuraCast documents as supported.

Item 2.7 cannot be verified from Oslo. Everything else should be rehearsed before travel.

### 4.3 Website

Independent of 4.1 and 4.2. Can be built, deployed and verified first — the pip reads OFF until the station runs.

| # | Item | Level | Done when |
|---|---|---|---|
| 3.1.1 | `:radio/url` in `content/program.md` | MUST | Build does not error |
| 3.1.2 | `:radio/url` in `content/index.md` | MUST | Build does not error |
| 3.2 | Hero gets a bare `.onair` pip and a radio button in `hero__actions` | MUST | One wrapping row, no overlap at 375 px and 1440 px |
| 3.3 | Button added to the `.radio` box | MUST | `/program.html` shows pip, text and button in the box |
| 3.4 | Both buttons `target="_blank"` + `rel="noopener"` | MUST | Opens a new tab, festival tab remains |
| 3.5.1 | Address pips and buttons by class or data-attribute, never id | MUST | Two pip instances exist, script is shared via `layout` |
| 3.5.2 | Poll `/api/nowplaying/merjemterud` | MUST | — |
| 3.5.3 | Interval ~30 s | MUST | — |
| 3.5.4 | Pause on `visibilitychange`, re-fetch on resume | MUST | — |
| 3.5.5 | Fetch once on load before the first interval | MUST | — |
| 3.5.6 | Two states; anything not LIVE reads OFF | MUST | Station down → OFF; broadcast running → LIVE within one interval |
| 3.6 | New `.btn` variant with normal and muted states | MUST | Two hero buttons are visually ranked |
| 3.6.2 | Muted state clickable, visible focus ring | MUST | Tabbable, ring renders |
| 3.6.3 | Muted-state contrast ≥ 4.5:1 | SHOULD | — |
| 3.7 | Bind `pip-blink` to the LIVE state only | MUST | With the station down, nothing blinks on either page |
| 3.8.3 | Update `:program/radio` body text | SHOULD | It currently says "Streames på denne sida", which the link-out design contradicts |
| 3.9 | Export to `docs/`, commit, push | MUST | merjemterud.no serves it, console clean of mixed-content and CORS errors |

Copy, decided:

- On-air label: **"På lufta"** — replaces the existing `"On air"`.
- Off-air label: **"Ikke på lufta"**.
- Button label: **"Hør Radio Jemterud →"**.

Files in scope: `content/index.md`, `content/program.md`, `src/merjemterud/main.clj`, `resources/public/styles.css`.

Relevant existing code:

- `src/merjemterud/main.clj:126` — `radio-strip`, currently a static box.
- `src/merjemterud/main.clj:129` — the hard-coded `"On air"` string.
- `resources/public/styles.css:100-117` — `.onair`, `.onair__pip`, `@keyframes pip-blink`, currently unconditional.
- `resources/public/styles.css:239` — `.hero__actions`, already `flex; flex-wrap: wrap; align-items: center; gap: 18px`, so the hero addition needs no layout work.
- `resources/public/styles.css:326` — `.radio`.
- `resources/public/styles.css:78-97` — `.btn` and its three variants; no muted variant exists.

`.stamp` is absolutely positioned and cleared by `.hero__title`'s `padding-right: 160px`, so it does not collide with the new hero row.

---

## 5. Notes

`docs/` is Powerpack build output for GitHub Pages and is overwritten by every export. This document lives in `doc/` deliberately.
