import { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [reviews, setReviews] = useState([]);
  const [customer, setCustomer] = useState('');
  const [consultant, setConsultant] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Function to execute the GET request
  const fetchReviews = async (customerFilter = '', consultantFilter = '') => {
    setLoading(true);
    setError(null);

    try {
      const params = new URLSearchParams();
      if (customerFilter.trim()) params.append('customer', customerFilter.trim());
      if (consultantFilter.trim()) params.append('consultant', consultantFilter.trim());

      const response = await fetch(`/api/reviews/search?${params.toString()}`);
      if (!response.ok) {
        throw new Error(`Server responded with status ${response.status}`);
      }

      const data = await response.json();
      setReviews(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  // Load all initial reviews when the page first opens
  useEffect(() => {
    fetchReviews();
  }, []);

  const handleSearch = (e) => {
    e.preventDefault();
    fetchReviews(customer, consultant);
  };

  const handleClear = () => {
    setCustomer('');
    setConsultant('');
    fetchReviews('', '');
  };

  const renderStars = (score) => {
    const validScore = Math.max(0, Math.min(5, score || 0));
    return '★'.repeat(validScore) + '☆'.repeat(5 - validScore);
  };

  return (
    <div style={{ maxWidth: '1000px', margin: '2rem auto', padding: '0 1rem', fontFamily: 'system-ui, sans-serif' }}>
      <h1>Sök och filtrera projekt</h1>

      {/* Filter Form */}
      <form onSubmit={handleSearch} style={{ display: 'flex', gap: '10px', marginBottom: '1.5rem', flexWrap: 'wrap' }}>
        <input
          type="text"
          placeholder="Sök kund..."
          value={customer}
          onChange={(e) => setCustomer(e.target.value)}
          style={{ padding: '8px 12px', border: '1px solid #ccc', borderRadius: '4px', flex: '1' }}
        />
        <input
          type="text"
          placeholder="Sök konsult..."
          value={consultant}
          onChange={(e) => setConsultant(e.target.value)}
          style={{ padding: '8px 12px', border: '1px solid #ccc', borderRadius: '4px', flex: '1' }}
        />
        <button type="submit" style={{ padding: '8px 16px', background: '#2563eb', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
          Filtrera
        </button>
        <button type="button" onClick={handleClear} style={{ padding: '8px 16px', background: '#e2e8f0', color: '#1e293b', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
          Rensa
        </button>
      </form>

      {/* State Indicators */}
      {loading && <p>Laddar data...</p>}
      {error && <p style={{ color: '#dc2626' }}>Fel vid hämtning: {error}</p>}

      {/* Empty State */}
      {!loading && !error && reviews.length === 0 && (
        <p style={{ color: '#64748b' }}>Inga projekt matchade din sökning.</p>
      )}

      {/* Results Table */}
      {!loading && !error && reviews.length > 0 && (
        <div>
          <h3>Resultat ({reviews.length})</h3>
          <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
            <thead>
              <tr style={{ borderBottom: '2px solid #cbd5e1', background: '#f8fafc' }}>
                <th style={{ padding: '8px' }}>ID</th>
                <th style={{ padding: '8px' }}>Kund</th>
                <th style={{ padding: '8px' }}>Konsult</th>
                <th style={{ padding: '8px' }}>Datum</th>
                <th style={{ padding: '8px' }}>Resultat</th>
                <th style={{ padding: '8px' }}>Ansvar</th>
                <th style={{ padding: '8px' }}>Enkelhet</th>
                <th style={{ padding: '8px' }}>Glädje</th>
              </tr>
            </thead>
            <tbody>
              {reviews.map((r) => (
                <tr key={r.id} style={{ borderBottom: '1px solid #e2e8f0' }}>
                  <td style={{ padding: '8px' }}>{r.id}</td>
                  <td style={{ padding: '8px' }}>{r.customer || '-'}</td>
                  <td style={{ padding: '8px' }}>{r.consultantName || '-'}</td>
                  <td style={{ padding: '8px' }}>{r.date || '-'}</td>
                  <td style={{ padding: '8px', color: '#eab308' }}>{renderStars(r.resultScore)}</td>
                  <td style={{ padding: '8px', color: '#eab308' }}>{renderStars(r.responsibilityScore)}</td>
                  <td style={{ padding: '8px', color: '#eab308' }}>{renderStars(r.simplicityScore)}</td>
                  <td style={{ padding: '8px', color: '#eab308' }}>{renderStars(r.joyScore)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default App;