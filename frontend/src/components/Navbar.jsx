import { Link } from 'react-router-dom';

export default function Navbar() {
  return (
    <nav style={{ display: 'flex', gap: '1.5rem', padding: '1rem 0', borderBottom: '2px solid #e2e8f0', marginBottom: '2rem' }}>
      <Link to="/new" style={{ fontWeight: 'bold', textDecoration: 'none', color: '#2563eb' }}>Lägg till projekt</Link>
      <Link to="/search" style={{ fontWeight: 'bold', textDecoration: 'none', color: '#2563eb' }}>Sök</Link>
      <Link to="/reviews" style={{ fontWeight: 'bold', textDecoration: 'none', color: '#2563eb' }}>Alla utvärderingar</Link>
    </nav>
  );
}