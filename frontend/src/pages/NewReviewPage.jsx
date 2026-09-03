import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function NewReviewPage() {
  const navigate = useNavigate();
  const [step, setStep] = useState(1);

  const [formData, setFormData] = useState({
    customer: '',
    consultant: '',
    salesPerson: '',
    date: new Date().toISOString().split('T')[0],
    consultantInformed: false,
    resultScore: 3,
    resultComment: '',
    responsibilityScore: 3,
    responsibilityComment: '',
    simplicityScore: 3,
    simplicityComment: '',
    joyScore: 3,
    joyComment: ''
  });

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleScoreChange = (scoreField, score) => {
    setFormData((prev) => ({
      ...prev,
      [scoreField]: Number(score)
    }));
  };

  const handleNextStep = (e) => {
    e.preventDefault();
    if (!formData.customer || !formData.consultant || !formData.salesPerson || !formData.date) {
      setError('Vänligen fyll i alla obligatoriska fält.');
      return;
    }
    setError(null);
    setStep(2);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    setError(null);

    try {
      const response = await fetch('/api/reviews', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData)
      });

      if (!response.ok) {
        throw new Error(`Kunde inte spara utvärderingen (HTTP ${response.status})`);
      }

      navigate('/success');
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  const renderStarInput = (field, currentVal) => (
    <div style={{ display: 'flex', gap: '8px', margin: '6px 0 10px 0' }}>
      {[1, 2, 3, 4, 5].map((val) => (
        <label key={val} style={{ cursor: 'pointer', fontSize: '1.5rem', userSelect: 'none' }}>
          <input
            type="radio"
            name={field}
            value={val}
            checked={currentVal === val}
            onChange={() => handleScoreChange(field, val)}
            style={{ display: 'none' }}
          />
          <span style={{ color: val <= currentVal ? '#eab308' : '#cbd5e1' }}>★</span>
        </label>
      ))}
    </div>
  );

  return (
    <div style={{ maxWidth: '600px', margin: '0 auto', fontFamily: 'system-ui, sans-serif' }}>
      <h2>{step === 1 ? 'Lägg till projektutvärdering (Steg 1 av 2)' : 'Kvalitetsuppföljning (Steg 2 av 2)'}</h2>

      {error && (
        <div style={{ background: '#fee2e2', color: '#b91c1c', padding: '10px 14px', borderRadius: '6px', marginBottom: '1.2rem' }}>
          {error}
        </div>
      )}

      {/* STEP 1: Basic Info */}
      {step === 1 && (
        <form onSubmit={handleNextStep} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
          <div>
            <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '4px' }}>Kund:</label>
            <input
              type="text"
              name="customer"
              value={formData.customer}
              onChange={handleChange}
              placeholder="T.ex. Spotify"
              required
              style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e1' }}
            />
          </div>

          <div>
            <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '4px' }}>Konsult:</label>
            <input
              type="text"
              name="consultant"
              value={formData.consultant}
              onChange={handleChange}
              placeholder="T.ex. Erik Sandlöv"
              required
              style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e1' }}
            />
          </div>

          <div>
            <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '4px' }}>Säljare:</label>
            <input
              type="text"
              name="salesPerson"
              value={formData.salesPerson}
              onChange={handleChange}
              placeholder="T.ex. Anna Lind"
              required
              style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e1' }}
            />
          </div>

          <div>
            <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '4px' }}>Datum:</label>
            <input
              type="date"
              name="date"
              value={formData.date}
              onChange={handleChange}
              required
              style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e1' }}
            />
          </div>

          <label style={{ display: 'flex', alignItems: 'center', gap: '8px', cursor: 'pointer', margin: '4px 0' }}>
            <input
              type="checkbox"
              name="consultantInformed"
              checked={formData.consultantInformed}
              onChange={handleChange}
            />
            Konsult informerad
          </label>

          <button
            type="submit"
            style={{
              marginTop: '1rem',
              padding: '10px 16px',
              background: '#2563eb',
              color: '#fff',
              border: 'none',
              borderRadius: '4px',
              fontWeight: '600',
              cursor: 'pointer'
            }}
          >
            Fortsätt till Betyg &rarr;
          </button>
        </form>
      )}

      {/* STEP 2: Ratings & Feedback */}
      {step === 2 && (
        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
          
          {/* Resultat */}
          <div>
            <label><strong>Resultat:</strong> <i>(Kompetens, levererar, kvalitet, tid)</i></label>
            {renderStarInput('resultScore', formData.resultScore)}
            <textarea
              name="resultComment"
              placeholder="Övrig kommentar..."
              value={formData.resultComment}
              onChange={handleChange}
              rows="2"
              style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e1' }}
            />
          </div>

          {/* Ansvar */}
          <div>
            <label><strong>Ansvar:</strong> <i>(Samarbete, hjälper & frågar, står för åtaganden, flaggar)</i></label>
            {renderStarInput('responsibilityScore', formData.responsibilityScore)}
            <textarea
              name="responsibilityComment"
              placeholder="Övrig kommentar..."
              value={formData.responsibilityComment}
              onChange={handleChange}
              rows="2"
              style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e1' }}
            />
          </div>

          {/* Enkelhet */}
          <div>
            <label><strong>Enkelhet:</strong> <i>(Gör det svåra enkelt, enkel kommunikation)</i></label>
            {renderStarInput('simplicityScore', formData.simplicityScore)}
            <textarea
              name="simplicityComment"
              placeholder="Övrig kommentar..."
              value={formData.simplicityComment}
              onChange={handleChange}
              rows="2"
              style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e1' }}
            />
          </div>

          {/* Glädje */}
          <div>
            <label><strong>Glädje:</strong> <i>(Tillför energi, kul att jobba med)</i></label>
            {renderStarInput('joyScore', formData.joyScore)}
            <textarea
              name="joyComment"
              placeholder="Övrig kommentar..."
              value={formData.joyComment}
              onChange={handleChange}
              rows="2"
              style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e1' }}
            />
          </div>

          <div style={{ display: 'flex', gap: '10px', marginTop: '0.5rem', marginBottom: '2rem' }}>
            <button
              type="button"
              onClick={() => setStep(1)}
              style={{
                padding: '10px 16px',
                background: '#e2e8f0',
                color: '#1e293b',
                border: 'none',
                borderRadius: '4px',
                fontWeight: '500',
                cursor: 'pointer'
              }}
            >
              &larr; Tillbaka
            </button>

            <button
              type="submit"
              disabled={isSubmitting}
              style={{
                flex: 1,
                padding: '10px 16px',
                background: isSubmitting ? '#94a3b8' : '#16a34a',
                color: '#fff',
                border: 'none',
                borderRadius: '4px',
                fontWeight: '600',
                cursor: isSubmitting ? 'not-allowed' : 'pointer'
              }}
            >
              {isSubmitting ? 'Sparar...' : 'Skicka Formulär'}
            </button>
          </div>
        </form>
      )}
    </div>
  );
}