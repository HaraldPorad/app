import { Link } from 'react-router-dom';

export default function SuccessPage() {
  return (
    <div style={{ textAlign: 'center', marginTop: '3rem' }}>
      <h2>Tack för din feedback!</h2>
      <p>Utvärderingen har sparats i databasen.</p>
      <div style={{ marginTop: '1.5rem' }}>
        <Link to="/reviews" style={{ marginRight: '1rem', color: '#2563eb' }}>Visa alla utvärderingar</Link>
        <Link to="/new" style={{ color: '#2563eb' }}>Lägg till ny</Link>
      </div>
    </div>
  );
}