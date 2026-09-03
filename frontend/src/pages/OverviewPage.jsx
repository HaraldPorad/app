import { useState, useEffect } from 'react';
import StarRating from '../components/StarRating';

export default function OverviewPage() {
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch('/api/reviews/search')
      .then((res) => res.json())
      .then((data) => {
        setReviews(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error(err);
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Laddar utvärderingar...</p>;

  return (
    <div>
      <h1>Alla utvärderingar</h1>
      <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left', fontSize: '0.9rem' }}>
        <thead>
          <tr style={{ borderBottom: '2px solid #cbd5e1', background: '#f8fafc' }}>
            <th style={{ padding: '8px' }}>ID</th>
            <th style={{ padding: '8px' }}>Kund</th>
            <th style={{ padding: '8px' }}>Konsult</th>
            <th style={{ padding: '8px' }}>Datum</th>
            <th style={{ padding: '8px' }}>Informerad</th>
            <th style={{ padding: '8px' }}>Resultat</th>
            <th style={{ padding: '8px' }}>Ansvar</th>
            <th style={{ padding: '8px' }}>Enkelhet</th>
            <th style={{ padding: '8px' }}>Glädje</th>
            <th style={{ padding: '8px' }}>Kommentarer</th>
          </tr>
        </thead>
        <tbody>
          {reviews.map((r) => (
            <tr key={r.id} style={{ borderBottom: '1px solid #e2e8f0' }}>
              <td style={{ padding: '8px' }}>{r.id}</td>
              <td style={{ padding: '8px' }}>{r.customer}</td>
              <td style={{ padding: '8px' }}>{r.consultantName}</td>
              <td style={{ padding: '8px' }}>{r.date}</td>
              <td style={{ padding: '8px' }}>
                <span style={{
                  padding: '2px 8px',
                  borderRadius: '12px',
                  fontSize: '0.75rem',
                  fontWeight: 'bold',
                  background: r.consultantInformed ? '#dcfce7' : '#fee2e2',
                  color: r.consultantInformed ? '#166534' : '#991b1b'
                }}>
                  {r.consultantInformed ? 'Ja' : 'Nej'}
                </span>
              </td>
              <td style={{ padding: '8px' }}><StarRating score={r.resultScore} /></td>
              <td style={{ padding: '8px' }}><StarRating score={r.responsibilityScore} /></td>
              <td style={{ padding: '8px' }}><StarRating score={r.simplicityScore} /></td>
              <td style={{ padding: '8px' }}><StarRating score={r.joyScore} /></td>
              <td style={{ padding: '8px', maxWidth: '200px', fontSize: '0.8rem', color: '#475569' }}>
                {r.resultComment && <div><strong>Res:</strong> {r.resultComment}</div>}
                {r.responsibilityComment && <div><strong>Ans:</strong> {r.responsibilityComment}</div>}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}