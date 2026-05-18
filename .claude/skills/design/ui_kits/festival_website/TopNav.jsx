/* global React, OnAirPip */

function TopNav({ current = 'lineup', onNav = () => {} }) {
  const items = [
    { id: 'lineup', label: 'Lineup' },
    { id: 'program', label: 'Program' },
    { id: 'radio', label: 'Radio' },
    { id: 'info', label: 'Info' },
    { id: 'faq', label: 'FAQ' },
  ];
  return (
    <header style={{
      background: '#f6f4ef', color: '#0a0a0a',
      padding: '18px 56px',
      display: 'flex', alignItems: 'center', gap: 36,
      borderBottom: '1.5px solid #0a0a0a',
      position: 'sticky', top: 0, zIndex: 10,
    }}>
      <a href="#" onClick={(e)=>{e.preventDefault();onNav('lineup');}} style={{
        fontFamily: "'Permanent Marker', cursive",
        color: '#0a0a0a', fontSize: 28, lineHeight: 1,
        textTransform: 'uppercase', textDecoration: 'none',
      }}>
        Jemterud 50
        <small style={{
          display: 'block', fontFamily: "'Open Sans', sans-serif",
          color: '#666', fontSize: 9, letterSpacing: '0.2em',
          fontWeight: 700, marginTop: 4,
        }}>FESTIVAL · ALCATRAZ · 4—6 SEP 2026</small>
      </a>
      <nav style={{
        display: 'flex', gap: 24, marginLeft: 'auto',
        fontFamily: "'Open Sans', sans-serif", fontWeight: 700,
        textTransform: 'uppercase', letterSpacing: '0.06em', fontSize: 12,
      }}>
        {items.map(it => (
          <a key={it.id} href="#" onClick={(e)=>{e.preventDefault();onNav(it.id);}} style={{
            color: '#0a0a0a',
            textDecoration: 'none',
            padding: '6px 4px',
            background: current === it.id ? '#999999' : 'transparent',
            borderBottom: `2px solid ${current === it.id ? '#0a0a0a' : 'transparent'}`,
          }}>{it.label}</a>
        ))}
      </nav>
      <OnAirPip />
      <a href="#" onClick={(e)=>e.preventDefault()} style={{
        fontFamily: "'Open Sans', sans-serif", fontWeight: 700,
        textTransform: 'uppercase', letterSpacing: '0.06em', fontSize: 12,
        padding: '9px 18px', background: '#0a0a0a', color: '#f6f4ef',
        textDecoration: 'none', border: '1.5px solid #0a0a0a',
        boxShadow: '4px 4px 0 #999999',
      }}>BILLETT →</a>
    </header>
  );
}

Object.assign(window, { TopNav });
