/* global React, Tape, MarkerScrawl, Btn */

function Hero() {
  return (
    <section style={{
      position: 'relative',
      background: '#f6f4ef',
      color: '#0a0a0a',
      padding: '64px 56px 96px',
      overflow: 'hidden',
      minHeight: 640,
      borderBottom: '1.5px solid #0a0a0a',
    }}>
      {/* Notebook-grid backdrop */}
      <div style={{
        position: 'absolute', inset: 0,
        backgroundImage: `
          repeating-linear-gradient(0deg, rgba(10,10,10,0.05) 0 1px, transparent 1px 28px),
          repeating-linear-gradient(90deg, rgba(10,10,10,0.05) 0 1px, transparent 1px 28px)
        `,
        opacity: 0.7,
      }}/>

      {/* eyebrow */}
      <div style={{ position: 'relative', display: 'flex', alignItems: 'center', gap: 16, marginBottom: 24 }}>
        <span style={{
          fontFamily: "'Open Sans', sans-serif", fontWeight: 700,
          fontSize: 12, letterSpacing: '0.2em', textTransform: 'uppercase',
          color: '#666',
        }}>4 — 6 SEPTEMBER 2026 · ALCATRAZ, ROSTAD</span>
        <span style={{ height: 1, width: 60, background: '#999' }}/>
        <span style={{
          fontFamily: "'Open Sans', sans-serif", fontWeight: 700,
          fontSize: 12, letterSpacing: '0.2em', textTransform: 'uppercase',
          color: '#666',
        }}>EDITION 01</span>
      </div>

      {/* Title — concrete-grey tape stack */}
      <div style={{ position: 'relative', display: 'inline-block', maxWidth: 1100, marginBottom: 40 }}>
        <Tape color="grey" rotate={-4} size="lg" style={{ fontSize: 36, position: 'absolute', top: -16, left: -8 }}>ENDA</Tape>
        <div style={{ marginTop: 32 }}>
          <Tape color="grey" rotate={-2} size="xl" style={{ fontSize: 96, padding: '10px 30px 8px', display: 'inline-block' }}>MER&nbsp;JEMTERUD</Tape>
        </div>
        <div style={{ marginTop: 8 }}>
          <Tape color="grey" rotate={-3} size="xl" style={{ fontSize: 70, padding: '10px 28px 8px', display: 'inline-block' }}>I&nbsp;MONITOR&nbsp;PLIS</Tape>
        </div>
      </div>

      {/* Sub */}
      <p style={{
        position: 'relative', maxWidth: 560,
        fontFamily: "'Open Sans', sans-serif", fontSize: 18,
        lineHeight: 1.5, color: '#181818', marginTop: 32,
      }}>
        En helg med eksperimentell musikk, kortbølge, lødende skarp lyd og dårlig dansing.
        Også er det bursdag. <strong>50 år.</strong> Bring earplugs.
      </p>

      <div style={{ position: 'relative', display: 'flex', gap: 16, marginTop: 32, alignItems: 'center' }}>
        <Btn variant="primary" size="lg">KJØP BILLETT</Btn>
        <Btn variant="ghost" size="lg">SE LINEUP →</Btn>
        <span style={{
          fontFamily: "'Open Sans', sans-serif", fontSize: 13, color: '#666',
          marginLeft: 12, fontStyle: 'italic',
        }}>helgepass 850 kr · students 600 kr · radio gratis</span>
      </div>

      {/* corner stamp */}
      <div style={{
        position: 'absolute', right: 56, top: 80, transform: 'rotate(8deg)',
      }}>
        <div style={{
          width: 140, height: 140, borderRadius: '50%',
          border: '3px solid #0a0a0a', display: 'flex',
          alignItems: 'center', justifyContent: 'center',
          textAlign: 'center', color: '#0a0a0a',
          fontFamily: "'Permanent Marker', cursive",
          fontSize: 22, lineHeight: 1, textTransform: 'uppercase',
        }}>50<br/><span style={{ fontSize: 14 }}>år</span><br/><span style={{ fontSize: 10, letterSpacing: '0.1em' }}>EST. 1976</span></div>
      </div>
    </section>
  );
}

Object.assign(window, { Hero });
