/* global React */
/* Shared bits: tape strip, ON AIR pip, marker scrawl */

function Tape({ children, rotate = -3, size = 'md', color = 'grey', style = {}, className = '' }) {
  const palette = {
    grey:   { bg: '#999999', fg: '#0a0a0a' },
    yellow: { bg: '#ffe850', fg: '#0a0a0a' },
    black:  { bg: '#0a0a0a', fg: '#f6f4ef' },
  };
  const p = palette[color] || palette.grey;
  const sizes = {
    sm: { fontSize: 13, padX: 10, padY: '4px 10px 3px' },
    md: { fontSize: 18, padX: 14, padY: '6px 14px 5px' },
    lg: { fontSize: 28, padX: 18, padY: '8px 20px 6px' },
    xl: { fontSize: 44, padX: 24, padY: '12px 26px 10px' },
  };
  const s = sizes[size] || sizes.md;
  const clips = [
    'polygon(2% 8%, 99% 0%, 98% 92%, 1% 100%)',
    'polygon(0% 12%, 100% 4%, 99% 96%, 2% 100%)',
    'polygon(3% 4%, 98% 10%, 99% 100%, 1% 92%)',
  ];
  const clip = clips[Math.abs(Math.round(rotate)) % clips.length];
  return (
    <span
      className={`tape ${className}`}
      style={{
        display: 'inline-block',
        background: p.bg,
        color: p.fg,
        fontFamily: "'Permanent Marker', cursive",
        textTransform: 'uppercase',
        letterSpacing: '0.01em',
        padding: s.padY,
        fontSize: s.fontSize,
        lineHeight: 1,
        transform: `rotate(${rotate}deg)`,
        clipPath: clip,
        boxShadow: '1px 1px 0 rgba(0,0,0,0.1)',
        whiteSpace: 'nowrap',
        ...style,
      }}
    >
      {children}
    </span>
  );
}

function OnAirPip({ label = 'ON AIR · 97.4 FM', live = true }) {
  return (
    <span className="onair-pip" style={{
      display: 'inline-flex', alignItems: 'center', gap: 8,
      fontFamily: "'Open Sans', sans-serif", fontWeight: 700,
      fontSize: 11, letterSpacing: '0.12em', textTransform: 'uppercase',
      color: live ? '#e63946' : '#666',
      border: `1.5px solid ${live ? '#e63946' : '#666'}`,
      padding: '5px 10px 4px',
    }}>
      <span style={{
        width: 8, height: 8, borderRadius: '50%',
        background: live ? '#e63946' : '#666',
        animation: live ? 'pip-blink 1s steps(2) infinite' : 'none',
      }}/>
      {label}
    </span>
  );
}

function MarkerScrawl({ children, size = 80, rotate = -3, color = '#0a0a0a', style = {} }) {
  return (
    <span style={{
      fontFamily: "'Permanent Marker', cursive",
      fontSize: size,
      lineHeight: 0.95,
      color,
      textTransform: 'uppercase',
      transform: `rotate(${rotate}deg)`,
      display: 'inline-block',
      ...style,
    }}>{children}</span>
  );
}

function Btn({ children, variant = 'primary', size = 'md', as = 'button', ...rest }) {
  const sizes = {
    sm: { padding: '8px 14px', fontSize: 11 },
    md: { padding: '12px 22px', fontSize: 13 },
    lg: { padding: '16px 32px', fontSize: 15 },
  };
  const variants = {
    primary: { background: '#0a0a0a', color: '#f6f4ef', border: '1.5px solid #0a0a0a', boxShadow: '4px 4px 0 #999999' },
    dark:    { background: '#0a0a0a', color: '#ffe850', border: '1.5px solid #0a0a0a', boxShadow: '4px 4px 0 #ffe850' },
    ghost:   { background: 'transparent', color: '#0a0a0a', border: '1.5px solid #0a0a0a', boxShadow: 'none' },
    onDark:  { background: 'transparent', color: '#f6f4ef', border: '1.5px solid #f6f4ef', boxShadow: 'none' },
  };
  const Tag = as;
  return (
    <Tag
      {...rest}
      className={`btn ${rest.className || ''}`}
      style={{
        fontFamily: "'Open Sans', sans-serif",
        fontWeight: 700,
        textTransform: 'uppercase',
        letterSpacing: '0.06em',
        cursor: 'pointer',
        borderRadius: 2,
        ...sizes[size],
        ...variants[variant],
        ...rest.style,
      }}
    >{children}</Tag>
  );
}

Object.assign(window, { Tape, OnAirPip, MarkerScrawl, Btn });
