/* global React, Tape, MarkerScrawl, Btn */

function InfoBlock() {
  const items = [
    { title: 'Sted', body: 'Alcatraz — gammelt sluseanlegg ved sjøen. Rostad, et stykke ut av byen.\nTog + buss + 15 min gåtur. Vi henter på stasjonen om du sier ifra.', tape: 'KART →' },
    { title: 'Billetter', body: 'Helgepass 850 kr · dagspass 350 kr · students/honnør 600 kr · radio gratis.\nKjøp på døra eller på forhånd.', tape: 'KJØP' },
    { title: 'Mat & drikke', body: 'Bar i foajéen. Pølser fra 19. Bring egne snacks om du vil.', tape: 'PØLSE' },
    { title: 'Tilgjengelighet', body: 'Storsal og Brygga er flate. Kjeller har trapper. Det er grus mellom byggene. Ring oss om du trenger hjelp.', tape: 'KONTAKT' },
  ];
  return (
    <section style={{ padding: '80px 56px', background: '#f6f4ef' }}>
      <h2 style={{
        fontFamily: "'Permanent Marker', cursive",
        fontSize: 72, margin: '0 0 36px', textTransform: 'uppercase',
        color: '#0a0a0a', lineHeight: 0.9,
      }}>Praktisk info</h2>
      <div style={{
        display: 'grid', gridTemplateColumns: 'repeat(2, 1fr)', gap: 36,
      }}>
        {items.map((it, i) => (
          <div key={it.title} style={{ position: 'relative', paddingTop: 12 }}>
            <div style={{ position: 'absolute', top: -8, left: -8 }}>
              <Tape rotate={i % 2 ? 2 : -3} size="sm">{it.tape}</Tape>
            </div>
            <h3 style={{
              fontFamily: "'Open Sans', sans-serif", fontWeight: 800,
              fontSize: 22, textTransform: 'uppercase', letterSpacing: '0.02em',
              margin: '24px 0 10px', color: '#0a0a0a',
            }}>{it.title}</h3>
            <p style={{
              fontFamily: "'Open Sans', sans-serif", fontSize: 15, lineHeight: 1.6,
              color: '#181818', margin: 0, whiteSpace: 'pre-line', maxWidth: 460,
            }}>{it.body}</p>
          </div>
        ))}
      </div>
    </section>
  );
}

function FAQ() {
  const [open, setOpen] = React.useState(0);
  const qs = [
    { q: 'Hva er dette egentlig?', a: 'En tre-dagers festival for eksperimentell musikk + radio + en bursdag. Vi inviterte folk vi liker, og folk vi liker tok med folk de liker.' },
    { q: 'Må jeg kjenne Knut?', a: 'Nei. Det er fint om du gjør det, men det er ikke et krav. Det er ikke et bryllup.' },
    { q: 'Kan jeg sende inn et set?', a: 'Lineup er ganske satt, men hvis du har noe rart vi bør høre — send en mail. Vi har én slot vi ikke har bestemt.' },
    { q: 'Kringkastes det?', a: 'Ja. 97.4 FM (lokalt) + nettstream gjennom hele helga. Også opptak etterpå.' },
    { q: 'Hvorfor "i monitor plis"?', a: 'Det er det Knut sier på hver eneste soundcheck. I 30 år. Det føltes riktig.' },
  ];
  return (
    <section style={{ padding: '80px 56px', background: '#ecead9' }}>
      <h2 style={{
        fontFamily: "'Permanent Marker', cursive",
        fontSize: 64, margin: '0 0 32px', textTransform: 'uppercase',
        color: '#0a0a0a', lineHeight: 0.9,
      }}>Ofte stilte spørsmål</h2>
      <div style={{ maxWidth: 880 }}>
        {qs.map((it, i) => (
          <div key={i} style={{ borderBottom: '1.5px solid #0a0a0a' }}>
            <button onClick={() => setOpen(open === i ? -1 : i)} style={{
              width: '100%', textAlign: 'left',
              background: 'transparent', border: 'none', cursor: 'pointer',
              padding: '20px 0', display: 'flex', justifyContent: 'space-between', alignItems: 'center',
              fontFamily: "'Open Sans', sans-serif", fontWeight: 700,
              fontSize: 19, color: '#0a0a0a',
            }}>
              <span>{it.q}</span>
              <span style={{
                fontFamily: "'Permanent Marker', cursive", fontSize: 32,
                transform: open === i ? 'rotate(45deg)' : 'rotate(0deg)',
                transition: 'transform 120ms ease-out', display: 'inline-block',
                lineHeight: 1,
              }}>+</span>
            </button>
            {open === i && (
              <p style={{
                fontFamily: "'Open Sans', sans-serif", fontSize: 16, lineHeight: 1.6,
                color: '#181818', margin: 0, padding: '0 0 24px', maxWidth: 660,
              }}>{it.a}</p>
            )}
          </div>
        ))}
      </div>
    </section>
  );
}

function Footer() {
  return (
    <footer style={{
      background: '#999999', color: '#0a0a0a',
      padding: '60px 56px 40px',
      fontFamily: "'Open Sans', sans-serif",
      borderTop: '1.5px solid #0a0a0a',
    }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', gap: 40, flexWrap: 'wrap' }}>
        <div>
          <MarkerScrawl size={42} rotate={-2} color="#0a0a0a">Jemterud 50</MarkerScrawl>
          <p style={{ marginTop: 16, maxWidth: 360, fontSize: 13, lineHeight: 1.5 }}>
            En festival som er en bursdag. Eller en bursdag som er en festival. Vi vet ikke helt.
          </p>
        </div>
        <div style={{ display: 'flex', gap: 48 }}>
          <div>
            <h4 style={{ color: '#0a0a0a', fontSize: 11, letterSpacing: '0.16em', textTransform: 'uppercase', margin: '0 0 12px' }}>Følg</h4>
            <ul style={{ listStyle: 'none', padding: 0, margin: 0, fontSize: 13, lineHeight: 2 }}>
              <li>radio · 97.4 fm</li>
              <li>instagram · @jemterud50</li>
              <li>mail · post@jemterud50.no</li>
            </ul>
          </div>
          <div>
            <h4 style={{ color: '#0a0a0a', fontSize: 11, letterSpacing: '0.16em', textTransform: 'uppercase', margin: '0 0 12px' }}>Støttet av</h4>
            <ul style={{ listStyle: 'none', padding: 0, margin: 0, fontSize: 13, lineHeight: 2 }}>
              <li>Norsk kulturråd</li>
              <li>Oslo kommune</li>
              <li>Venner og familie</li>
            </ul>
          </div>
        </div>
      </div>
      <div style={{
        borderTop: '1px dashed rgba(10,10,10,0.25)', marginTop: 40, paddingTop: 16,
        display: 'flex', justifyContent: 'space-between', fontSize: 11, letterSpacing: '0.08em', textTransform: 'uppercase',
      }}>
        <span>© 2026 — fortsatt ikke offisielt et selskap</span>
        <span style={{ fontFamily: "'JetBrains Mono', monospace", letterSpacing: 0 }}>v0.4 · siste oppdatert mai 2026</span>
      </div>
    </footer>
  );
}

function RadioBar() {
  return (
    <div style={{
      position: 'fixed', bottom: 0, left: 0, right: 0,
      background: '#f6f4ef', borderTop: '1.5px solid #0a0a0a',
      padding: '12px 28px', display: 'flex', alignItems: 'center', gap: 20,
      color: '#0a0a0a', fontFamily: "'Open Sans', sans-serif",
      zIndex: 9,
    }}>
      <button aria-label="play" style={{
        width: 40, height: 40, borderRadius: '50%',
        background: '#0a0a0a', border: '1.5px solid #0a0a0a',
        color: '#f6f4ef', cursor: 'pointer', fontSize: 14, lineHeight: 1,
        display: 'flex', alignItems: 'center', justifyContent: 'center',
      }}>▶</button>
      <span style={{
        color: '#e63946', fontWeight: 700, fontSize: 11, letterSpacing: '0.12em', textTransform: 'uppercase',
        display: 'inline-flex', alignItems: 'center', gap: 6,
      }}>
        <span style={{ width: 8, height: 8, borderRadius: '50%', background: '#e63946', animation: 'pip-blink 1s steps(2) infinite' }}/>
        ON AIR
      </span>
      <span style={{ fontSize: 14, fontWeight: 600 }}>Maja Ratkje — &ldquo;Voice fragments&rdquo;</span>
      <span style={{ fontSize: 12, color: '#666', fontFamily: "'JetBrains Mono', monospace" }}>97.4 MHz · 64 kbps · 1241 lytter</span>
      <span style={{ marginLeft: 'auto', fontSize: 11, color: '#666', letterSpacing: '0.12em', textTransform: 'uppercase' }}>NESTE → Sysselmann 02:30</span>
    </div>
  );
}

Object.assign(window, { InfoBlock, FAQ, Footer, RadioBar });
