import { useState, useEffect } from 'react';
import StarRating from '../components/StarRating';

export default function SearchPage() {
  const [reviews, setReviews] = useState([]);
  const [customer, setCustomer] = useState('');
  const [consultant, setConsultant] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [searched, setSearched] = useState(false);

  const fetchReviews = async (customerFilter = '', consultantFilter = '') => {
    setLoading(true);
    setError(null);

    try {
      const params = new URLSearchParams();
      if (customerFilter.trim()) params.append('customer', customerFilter.trim());
      if (consultantFilter.trim()) params.append('consultant', consultantFilter.trim());

      const response = await fetch(`/api/reviews/search?${params.toString()}`);
      if (!response.ok) {
        throw new Error(`HTTP-fel: ${response.status}`);
      }

      const data = await response.json();
      setReviews(data);
      setSearched(true);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  //// Initial fetch on page mount
  //useEffect(() => {
  //  fetchReviews();
  //}, []);

  const handleSearch = (e) => {
    e.preventDefault();
    fetchReviews(customer, consultant);
  };

  const handleClear = () => {
    setCustomer('');
    setConsultant('');
    fetchReviews('', '');
  };

  return (
    <div>
      <h2>Sök och filtrera projekt</h2>

      {/* Filter Bar */}
      <form onSubmit={handleSearch} style={{ display: 'flex', gap: '10px', marginBottom: '1.5rem', flexWrap: 'wrap' }}>
        <input
          type="text"
          placeholder="Sök kund..."
          value={customer}
          onChange={(e) => setCustomer(e.target.value)}
          style={{ padding: '8px 12px', border: '1px solid #cbd5e1', borderRadius: '4px', flex: '1', minWidth: '180px' }}
        />
        <input
          type="text"
          placeholder="Sök konsult..."
          value={consultant}
          onChange={(e) => setConsultant(e.target.value)}
          style={{ padding: '8px 12px', border: '1px solid #cbd5e1', borderRadius: '4px', flex: '1', minWidth: '180px' }}
        />
        <button
          type="submit"
          style={{ padding: '8px 16px', background: '#2563eb', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: '500' }}
        >
          Filtrera
        </button>
        <button
          type="button"
          onClick={handleClear}
          style={{ padding: '8px 16px', background: '#e2e8f0', color: '#1e293b', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
        >
          Rensa
        </button>
      </form>

      {/* Status Indicators */}
      {loading && <p style={{ color: '#64748b' }}>Laddar projekt...</p>}
      {error && <p style={{ color: '#dc2626' }}>Fel vid sökning: {error}</p>}

      {/* Empty State */}
      {!loading && !error && searched && reviews.length === 0 && (
        <p style={{ color: '#64748b' }}>Inga projekt matchade din sökning.</p>
      )}

      {/* Results Table */}
      {!loading && !error && reviews.length > 0 && (
        <div>
          <h3 style={{ marginBottom: '0.75rem' }}>Resultat ({reviews.length})</h3>
          <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left', fontSize: '0.95rem' }}>
            <thead>
              <tr style={{ borderBottom: '2px solid #cbd5e1', background: '#f8fafc' }}>
                <th style={{ padding: '10px 8px' }}>ID</th>
                <th style={{ padding: '10px 8px' }}>Kund</th>
                <th style={{ padding: '10px 8px' }}>Konsult</th>
                <th style={{ padding: '10px 8px' }}>Datum</th>
                <th style={{ padding: '10px 8px' }}>Resultat</th>
                <th style={{ padding: '10px 8px' }}>Ansvar</th>
                <th style={{ padding: '10px 8px' }}>Enkelhet</th>
                <th style={{ padding: '10px 8px' }}>Glädje</th>
              </tr>
            </thead>
            <tbody>
              {reviews.map((review) => (
                <tr key={review.id} style={{ borderBottom: '1px solid #e2e8f0' }}>
                  <td style={{ padding: '10px 8px' }}>{review.id}</td>
                  <td style={{ padding: '10px 8px' }}>{review.customer || '-'}</td>
                  <td style={{ padding: '10px 8px' }}>{review.consultantName || '-'}</td>
                  <td style={{ padding: '10px 8px' }}>{review.date || '-'}</td>
                  <td style={{ padding: '10px 8px' }}><StarRating score={review.resultScore} /></td>
                  <td style={{ padding: '10px 8px' }}><StarRating score={review.responsibilityScore} /></td>
                  <td style={{ padding: '10px 8px' }}><StarRating score={review.simplicityScore} /></td>
                  <td style={{ padding: '10px 8px' }}><StarRating score={review.joyScore} /></td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}