import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export default function NewReviewPage() {
  const navigate = useNavigate();
  const [step, setStep] = useState(1);

  // Dropdown options loaded from backend
  const [salesPeople, setSalesPeople] = useState([]);
  const [projects, setProjects] = useState([]);
  const [consultants, setConsultants] = useState([]);

  // Loading states
  const [loadingProjects, setLoadingProjects] = useState(false);
  const [loadingConsultants, setLoadingConsultants] = useState(false);

  const [formData, setFormData] = useState({
    salesPerson: '',
    customer: '',
    consultant: '',
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

  // 1. Initial Load: Fetch all sales people
  useEffect(() => {
    fetch('/api/sales')
      .then((res) => res.json())
      .then((data) => setSalesPeople(data))
      .catch((err) => console.error('Kunde inte hämta säljare:', err));
  }, []);

  // 2. Cascade: When a salesperson changes, fetch their filtered projects & consultants
  const handleSalesPersonChange = (e) => {
    const selectedSalesId = e.target.value;
    const selectedPerson = salesPeople.find((sp) => String(sp.id) === selectedSalesId);

    // Reset downstream selections
    setFormData((prev) => ({
      ...prev,
      salesPerson: selectedPerson ? selectedPerson.name : '',
      customer: '',
      consultant: ''
    }));

    if (!selectedSalesId) {
      setProjects([]);
      setConsultants([]);
      return;
    }

    // Fetch projects belonging to this salesperson
    setLoadingProjects(true);
    fetch(`/api/projects?salesPersonId=${selectedSalesId}`)
      .then((res) => res.json())
      .then((data) => {
        setProjects(data);
        setLoadingProjects(false);
      })
      .catch((err) => {
        console.error('Kunde inte hämta projekt:', err);
        setLoadingProjects(false);
      });

    // Fetch consultants managed by this salesperson
    setLoadingConsultants(true);
    fetch(`/api/consultants?salesPersonId=${selectedSalesId}`)
      .then((res) => res.json())
      .then((data) => {
        setConsultants(data);
        setLoadingConsultants(false);
      })
      .catch((err) => {
        console.error('Kunde inte hämta konsulter:', err);
        setLoadingConsultants(false);
      });
  };

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
      setError('Vänligen välj alla obligatoriska fält.');
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

      {/* STEP 1: Cascading Dropdowns */}
      {step === 1 && (
        <form onSubmit={handleNextStep} style={{ display: 'flex', flexDirection: 'column', gap: '1.2rem' }}>
          
          {/* 1. Säljare */}
          <div>
            <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '4px' }}>Säljare:</label>
            <select
              onChange={handleSalesPersonChange}
              required
              defaultValue=""
              style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e1' }}
            >
              <option value="" disabled>-- Välj ansvarig säljare --</option>
              {salesPeople.map((sp) => (
                <option key={sp.id} value={sp.id}>{sp.name}</option>
              ))}
            </select>
          </div>

          {/* 2. Kund / Projekt (Filtered) */}
          <div>
            <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '4px' }}>Kund / Projekt:</label>
            <select
              name="customer"
              value={formData.customer}
              onChange={handleChange}
              disabled={!formData.salesPerson || loadingProjects}
              required
              style={{
                width: '100%',
                padding: '8px',
                borderRadius: '4px',
                border: '1px solid #cbd5e1',
                background: !formData.salesPerson ? '#f1f5f9' : '#fff'
              }}
            >
              <option value="" disabled>
                {!formData.salesPerson
                  ? '-- Välj säljare först --'
                  : loadingProjects
                  ? 'Hämtar projekt...'
                  : projects.length === 0
                  ? 'Inga projekt kopplade till säljaren'
                  : '-- Välj kund --'}
              </option>
              {projects.map((proj) => (
                <option key={proj.id} value={proj.customer}>{proj.customer}</option>
              ))}
            </select>
          </div>

          {/* 3. Konsult (Filtered) */}
          <div>
            <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '4px' }}>Konsult:</label>
            <select
              name="consultant"
              value={formData.consultant}
              onChange={handleChange}
              disabled={!formData.salesPerson || loadingConsultants}
              required
              style={{
                width: '100%',
                padding: '8px',
                borderRadius: '4px',
                border: '1px solid #cbd5e1',
                background: !formData.salesPerson ? '#f1f5f9' : '#fff'
              }}
            >
              <option value="" disabled>
                {!formData.salesPerson
                  ? '-- Välj säljare först --'
                  : loadingConsultants
                  ? 'Hämtar konsulter...'
                  : consultants.length === 0
                  ? 'Inga konsulter kopplade till säljaren'
                  : '-- Välj konsult --'}
              </option>
              {consultants.map((c) => (
                <option key={c.id} value={c.name}>{c.name}</option>
              ))}
            </select>
          </div>

          {/* 4. Datum & Informerad */}
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

          <button
            type="submit"
            disabled={!formData.salesPerson || !formData.customer || !formData.consultant}
            style={{
              marginTop: '0.8rem',
              padding: '10px 16px',
              background: (!formData.salesPerson || !formData.customer || !formData.consultant) ? '#94a3b8' : '#2563eb',
              color: '#fff',
              border: 'none',
              borderRadius: '4px',
              fontWeight: '600',
              cursor: (!formData.salesPerson || !formData.customer || !formData.consultant) ? 'not-allowed' : 'pointer'
            }}
          >
            Fortsätt till Betyg &rarr;
          </button>
        </form>
      )}

      {/* STEP 2: Ratings */}
      {step === 2 && (
        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
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