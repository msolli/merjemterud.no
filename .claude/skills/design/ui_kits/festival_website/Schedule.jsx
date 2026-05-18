/* global React, Tape */

const SCHEDULE = {
  FRE: { date: '4 SEP', sets: [
    { time: '16:00', name: 'Dører / natursti opnar', stage: 'UTE', kind: 'open' },
    { time: '17:30', name: 'Partikkelfysikkens ABC — Anders & Are', stage: 'BRYGGA', kind: 'talk' },
    { time: '19:00', name: 'Arnfinn Nesset', stage: 'STORSAL', kind: 'set' },
    { time: '20:30', name: 'Higgs Boson', stage: 'BRYGGA', kind: 'set' },
    { time: '22:00', name: 'Dromedar', stage: 'STORSAL', kind: 'set' },
    { time: '23:30', name: 'Muru', stage: 'KJELLER', kind: 'set' },
  ]},
  'LØR': { date: '5 SEP', sets: [
    { time: '13:00', name: 'Tesla-streiken i Sverige (foredrag)', stage: 'BRYGGA', kind: 'talk' },
    { time: '15:00', name: 'Moderne dans — på plenen', stage: 'UTE', kind: 'open' },
    { time: '17:30', name: 'Pokerskinn', stage: 'BRYGGA', kind: 'set' },
    { time: '19:30', name: 'Det Forflutna', stage: 'BRYGGA', kind: 'set' },
    { time: '21:00', name: 'Tusmørke', stage: 'STORSAL', kind: 'set', highlight: true },
    { time: '23:00', name: 'OKKULTOKRATI — BURSDAG', stage: 'STORSAL', kind: 'set', highlight: true },
    { time: '01:00', name: 'Moaners', stage: 'KJELLER', kind: 'set' },
    { time: '02:30', name: 'Sysselmann', stage: 'KJELLER', kind: 'set' },
    { time: '03:30', name: 'Oslo Skrotum', stage: 'KJELLER', kind: 'set' },
  ]},
  'SØN': { date: '6 SEP', sets: [
    { time: '11:00', name: 'Frokost + natursti runde 2', stage: 'UTE', kind: 'open' },
    { time: '13:00', name: 'Blikkstilt', stage: 'BRYGGA', kind: 'set' },
    { time: '14:00', name: 'Enda flere gleder med skjeden — Ellen Støkken Dahl', stage: 'STORSAL', kind: 'talk' },
    { time: '15:00', name: 'Svartskog Spelemannslag', stage: 'BRYGGA', kind: 'set', highlight: true },
    { time: '17:00', name: 'Avslutning / rydding', stage: 'ALLE', kind: 'open' },
  ]},
};

function Schedule() {
  const [day, setDay] = React.useState('LØR');
  const items = SCHEDULE[day].sets;
  return (
    <section style={{
      background: '#ecead9', padding: '80px 56px',
      position: 'relative', overflow: 'hidden',
      borderBottom: '1.5px solid #0a0a0a',
    }}>
      <div style={{ display: 'flex', alignItems: 'baseline', gap: 24, marginBottom: 32 }}>
        <h2 style={{
          fontFamily: "'Permanent Marker', cursive",
          fontSize: 80, margin: 0, textTransform: 'uppercase',
          color: '#0a0a0a', lineHeight: 0.9, transform: 'rotate(-2deg)',
        }}>Program</h2>
        <span style={{
          fontFamily: "'Permanent Marker', cursive",
          fontSize: 28, color: '#0a0a0a', transform: 'rotate(1deg)',
          textTransform: 'uppercase',
        }}>siste ord teller</span>
      </div>

      {/* Day tabs */}
      <div style={{ display: 'flex', gap: 14, marginBottom: 28 }}>
        {Object.keys(SCHEDULE).map(d => (
          <button key={d} onClick={() => setDay(d)} style={{
            background: day === d ? '#999999' : 'transparent',
            color: '#0a0a0a',
            border: '1.5px solid #0a0a0a',
            padding: '10px 22px',
            fontFamily: "'Open Sans', sans-serif", fontWeight: 800,
            fontSize: 14, letterSpacing: '0.06em', textTransform: 'uppercase',
            cursor: 'pointer', borderRadius: 2,
            boxShadow: day === d ? '4px 4px 0 #0a0a0a' : 'none',
          }}>{d} · {SCHEDULE[d].date}</button>
        ))}
      </div>

      {/* List */}
      <div style={{
        background: '#f6f4ef', color: '#0a0a0a',
        border: '1.5px solid #0a0a0a',
        boxShadow: '6px 6px 0 #999999',
        padding: '20px 28px',
        maxWidth: 900,
      }}>
        {items.map((s, i) => (
          <div key={i} style={{
            display: 'grid',
            gridTemplateColumns: '110px 1fr 140px',
            alignItems: 'center', gap: 16,
            padding: '14px 0',
            borderBottom: i < items.length - 1 ? '1px dashed rgba(10,10,10,0.2)' : 'none',
            fontFamily: "'Open Sans', sans-serif",
          }}>
            <span style={{
              fontFamily: "'Open Sans', sans-serif", fontWeight: 800,
              fontSize: 22, fontVariantNumeric: 'tabular-nums',
              color: '#0a0a0a',
              background: s.highlight ? '#999999' : 'transparent',
              padding: s.highlight ? '2px 8px' : 0,
            }}>{s.time}</span>
            <span style={{
              fontWeight: s.highlight ? 800 : 600,
              fontSize: s.highlight ? 22 : 17,
              textTransform: s.highlight ? 'uppercase' : 'none',
              letterSpacing: s.highlight ? '0.02em' : 0,
              color: '#0a0a0a',
            }}>{s.name}</span>
            <span style={{
              fontSize: 11, letterSpacing: '0.12em', textTransform: 'uppercase',
              fontWeight: 700, color: '#666', textAlign: 'right',
            }}>{s.kind === 'talk' ? '◇ talk · ' : s.kind === 'open' ? '○ ' : '● '}{s.stage}</span>
          </div>
        ))}
      </div>

      <p style={{
        fontFamily: "'Open Sans', sans-serif", fontSize: 13, color: '#0a0a0a',
        marginTop: 20, maxWidth: 600, fontStyle: 'italic',
      }}>Tider kan endre seg. Det går som det går.</p>
    </section>
  );
}

Object.assign(window, { Schedule });
