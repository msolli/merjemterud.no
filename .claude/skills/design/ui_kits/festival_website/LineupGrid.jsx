/* global React, Tape */

const ARTISTS = [
  { name: 'Okkultokrati', tag: 'okkult punk · noise rock', day: 'LØR', time: '23:00', stage: 'STORSAL', tags: ['punk','noise','okkult'] },
  { name: 'Tusmørke', tag: 'prog · psych · norrønt', day: 'LØR', time: '21:00', stage: 'STORSAL', tags: ['prog','psych','folk'], photo: '../../assets/photos/band-tusmorke.jpg' },
  { name: 'Svartskog Spelemannslag', tag: 'hardingfele · drone folk', day: 'SØN', time: '15:00', stage: 'BRYGGA', tags: ['folk','drone'], photo: '../../assets/photos/band-spelemann.jpg' },
  { name: 'Dromedar', tag: 'støy + visdom', day: 'FRE', time: '22:00', stage: 'STORSAL', tags: ['noise','støy'] },
  { name: 'Muru', tag: 'eksperimentell elektronikk', day: 'FRE', time: '23:30', stage: 'KJELLER', tags: ['electronic','experimental'] },
  { name: 'Moaners', tag: 'sludge / heavy', day: 'LØR', time: '01:00', stage: 'KJELLER', tags: ['sludge','heavy'] },
  { name: 'Higgs Boson', tag: 'partikkel-pop', day: 'FRE', time: '20:30', stage: 'BRYGGA', tags: ['indie','science'] },
  { name: 'Det Forflutna', tag: 'kraut · post-rock', day: 'LØR', time: '19:30', stage: 'BRYGGA', tags: ['kraut','post-rock'] },
  { name: 'Pokerskinn', tag: 'noir-rock', day: 'LØR', time: '17:30', stage: 'BRYGGA', tags: ['rock','noir'] },
  { name: 'Blikkstilt', tag: 'roleg, men ikkje stille', day: 'SØN', time: '13:00', stage: 'BRYGGA', tags: ['ambient','roleg'] },
  { name: 'Sysselmann', tag: 'kald nordisk støy', day: 'LØR', time: '02:30', stage: 'KJELLER', tags: ['noise','nordisk'] },
  { name: 'Arnfinn Nesset', tag: 'mystisk ny tilskudd', day: 'FRE', time: '19:00', stage: 'STORSAL', tags: ['?','mystery'], photo: '../../assets/photos/band-arnfinn-nesset.jpg' },
  { name: 'Oslo Skrotum', tag: 'navnet sier det meste', day: 'LØR', time: '03:30', stage: 'KJELLER', tags: ['punk','støy'] },
];

function Card({ a, rotateTape }) {
  return (
    <article style={{
      position: 'relative',
      background: '#f6f4ef',
      border: '1.5px solid #0a0a0a',
      boxShadow: '4px 4px 0 #0a0a0a',
      padding: '24px 22px 20px',
      minHeight: 220,
      display: 'flex', flexDirection: 'column', justifyContent: 'space-between',
    }}>
      <div style={{ position: 'absolute', top: -14, left: 14, zIndex: 2 }}>
        <Tape rotate={rotateTape} size="sm">{a.day} · {a.time}</Tape>
      </div>
      {a.photo && (
        <div style={{
          margin: '-24px -22px 14px', height: 140, overflow: 'hidden',
          position: 'relative', borderBottom: '1.5px solid #0a0a0a',
        }}>
          <img src={a.photo} alt="" style={{
            width: '100%', height: '100%', objectFit: 'cover',
            filter: 'grayscale(1) contrast(1.25) brightness(0.95)',
          }}/>
          <div style={{
            position: 'absolute', inset: 0,
            backgroundImage: 'repeating-linear-gradient(0deg,rgba(0,0,0,0.16) 0 1px,transparent 1px 3px)',
            mixBlendMode: 'multiply', pointerEvents: 'none',
          }}/>
        </div>
      )}
      <div>
        <h3 style={{
          fontFamily: "'Permanent Marker', cursive",
          fontSize: 32, lineHeight: 0.95,
          margin: '8px 0 6px',
          textTransform: 'uppercase',
          color: '#0a0a0a',
        }}>{a.name}</h3>
        <p style={{
          fontFamily: "'Open Sans', sans-serif",
          fontSize: 13, color: '#666', margin: 0, fontStyle: 'italic',
        }}>{a.tag}</p>
      </div>
      <div style={{ display: 'flex', flexWrap: 'wrap', gap: 6, marginTop: 14 }}>
        {a.tags.map(t => (
          <span key={t} style={{
            fontFamily: "'Open Sans', sans-serif", fontWeight: 700,
            fontSize: 10, letterSpacing: '0.1em', textTransform: 'uppercase',
            border: '1.5px solid #0a0a0a', padding: '3px 8px', borderRadius: 999,
            color: '#0a0a0a',
          }}>{t}</span>
        ))}
      </div>
      <div style={{
        marginTop: 14, paddingTop: 12, borderTop: '1.5px solid #0a0a0a',
        fontFamily: "'Open Sans', sans-serif", fontWeight: 700, fontSize: 11,
        letterSpacing: '0.1em', textTransform: 'uppercase',
        display: 'flex', justifyContent: 'space-between',
      }}>
        <span>{a.stage}</span>
        <span style={{ color: '#666' }}>→ DETALJER</span>
      </div>
    </article>
  );
}

function LineupGrid() {
  return (
    <section style={{ padding: '80px 56px', background: '#f6f4ef' }}>
      <div style={{ display: 'flex', alignItems: 'baseline', gap: 18, marginBottom: 40 }}>
        <h2 style={{
          fontFamily: "'Permanent Marker', cursive",
          fontSize: 72, margin: 0, textTransform: 'uppercase',
          color: '#0a0a0a', lineHeight: 0.9,
        }}>Lineup</h2>
        <span style={{
          fontFamily: "'Open Sans', sans-serif", fontWeight: 700, fontSize: 13,
          letterSpacing: '0.14em', textTransform: 'uppercase', color: '#666',
        }}>· 13 band · 3 foredrag · 2 ekstra</span>
      </div>
      <div style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(3, 1fr)',
        gap: 28,
      }}>
        {ARTISTS.map((a, i) => <Card key={a.name} a={a} rotateTape={i % 2 ? 2.5 : -3} />)}
      </div>
    </section>
  );
}

Object.assign(window, { LineupGrid });
