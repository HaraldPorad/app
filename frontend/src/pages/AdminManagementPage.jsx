import { useState, useEffect } from 'react';

export default function AdminManagementPage() {
  const [activeTab, setActiveTab] = useState('consultant');
  const [salesPeople, setSalesPeople] = useState([]);
  const [projects, setProjects] = useState([]);

  const [salesName, setSalesName] = useState('');
  const [projectForm, setProjectForm] = useState({ customer: '', salesPersonId: '' });
  const [consultantForm, setConsultantForm] = useState({ name: '', salesPersonId: '', projectId: '' });

  const [statusMessage, setStatusMessage] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);

  const loadData = () => {
    fetch('/api/sales')
      .then((res) => res.json())
      .then(setSalesPeople)
      .catch(console.error);

    fetch('/api/projects')
      .then((res) => res.json())
      .then(setProjects)
      .catch(console.error);
  };

  useEffect(() => {
    loadData();
  }, []);

  const resetNotifs = () => {
    setStatusMessage(null);
    setErrorMessage(null);
  };

  const handleCreateSales = async (e) => {
    e.preventDefault();
    resetNotifs();
    try {
      const res = await fetch('/api/sales', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: salesName })
      });
      if (!res.ok) throw new Error('Kunde inte skapa säljare');
      setStatusMessage(`Säljare "${salesName}" har lagts till!`);
      setSalesName('');
      loadData();
    } catch (err) {
      setErrorMessage(err.message);
    }
  };

  const handleCreateProject = async (e) => {
    e.preventDefault();
    resetNotifs();
    try {
      const res = await fetch('/api/projects', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          customer: projectForm.customer,
          salesPersonId: Number(projectForm.salesPersonId)
        })
      });
      if (!res.ok) throw new Error('Kunde inte skapa projekt');
      setStatusMessage(`Projekt "${projectForm.customer}" har lagts till!`);
      setProjectForm({ customer: '', salesPersonId: '' });
      loadData();
    } catch (err) {
      setErrorMessage(err.message);
    }
  };

  const handleCreateConsultant = async (e) => {
    e.preventDefault();
    resetNotifs();
    try {
      const res = await fetch('/api/consultants', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          name: consultantForm.name,
          salesPersonId: consultantForm.salesPersonId ? Number(consultantForm.salesPersonId) : null,
          projectId: consultantForm.projectId ? Number(consultantForm.projectId) : null
        })
      });
      if (!res.ok) throw new Error('Kunde inte skapa konsult');
      setStatusMessage(`Konsult "${consultantForm.name}" har lagts till!`);
      setConsultantForm({ name: '', salesPersonId: '', projectId: '' });
      loadData();
    } catch (err) {
      setErrorMessage(err.message);
    }
  };

  return (
    <div style={{ maxWidth: '600px', margin: '0 auto' }}>
      <h2>Hantera Entiteter</h2>

      {/* Tabs */}
      <div style={{ display: 'flex', gap: '8px', marginBottom: '1.5rem', borderBottom: '2px solid #e2e8f0' }}>
        <button
          onClick={() => { setActiveTab('consultant'); resetNotifs(); }}
          style={{
            padding: '8px 16px',
            border: 'none',
            borderBottom: activeTab === 'consultant' ? '2px solid #2563eb' : 'none',
            background: 'none',
            fontWeight: activeTab === 'consultant' ? 'bold' : 'normal',
            cursor: 'pointer'
          }}
        >
          Konsult
        </button>
        <button
          onClick={() => { setActiveTab('project'); resetNotifs(); }}
          style={{
            padding: '8px 16px',
            border: 'none',
            borderBottom: activeTab === 'project' ? '2px solid #2563eb' : 'none',
            background: 'none',
            fontWeight: activeTab === 'project' ? 'bold' : 'normal',
            cursor: 'pointer'
          }}
        >
          Projekt
        </button>
        <button
          onClick={() => { setActiveTab('sales'); resetNotifs(); }}
          style={{
            padding: '8px 16px',
            border: 'none',
            borderBottom: activeTab === 'sales' ? '2px solid #2563eb' : 'none',
            background: 'none',
            fontWeight: activeTab === 'sales' ? 'bold' : 'normal',
            cursor: 'pointer'
          }}
        >
          Säljare
        </button>
      </div>

      {statusMessage && <p style={{ color: '#15803d', background: '#dcfce7', padding: '10px', borderRadius: '4px' }}>{statusMessage}</p>}
      {errorMessage && <p style={{ color: '#b91c1c', background: '#fee2e2', padding: '10px', borderRadius: '4px' }}>{errorMessage}</p>}

      {/* Form: Konsult */}
      {activeTab === 'consultant' && (
        <form onSubmit={handleCreateConsultant} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
          <div>
            <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '4px' }}>Namn:</label>
            <input
              type="text"
              value={consultantForm.name}
              onChange={(e) => setConsultantForm({ ...consultantForm, name: e.target.value })}
              required
              style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e1' }}
            />
          </div>
          <div>
            <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '4px' }}>Ansvarig säljare:</label>
            <select
              value={consultantForm.salesPersonId}
              onChange={(e) => setConsultantForm({ ...consultantForm, salesPersonId: e.target.value })}
              style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e1' }}
            >
              <option value="">-- Ingen säljare vald --</option>
              {salesPeople.map((sp) => (
                <option key={sp.id} value={sp.id}>{sp.name}</option>
              ))}
            </select>
          </div>
          <div>
            <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '4px' }}>Tilldelat projekt:</label>
            <select
              value={consultantForm.projectId}
              onChange={(e) => setConsultantForm({ ...consultantForm, projectId: e.target.value })}
              style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e1' }}
            >
              <option value="">-- Inget projekt valt --</option>
              {projects.map((proj) => (
                <option key={proj.id} value={proj.id}>{proj.customer}</option>
              ))}
            </select>
          </div>
          <button type="submit" style={{ padding: '10px', background: '#2563eb', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
            Skapa Konsult
          </button>
        </form>
      )}

      {/* Form: Projekt */}
      {activeTab === 'project' && (
        <form onSubmit={handleCreateProject} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
          <div>
            <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '4px' }}>Kund / Projekt:</label>
            <input
              type="text"
              value={projectForm.customer}
              onChange={(e) => setProjectForm({ ...projectForm, customer: e.target.value })}
              required
              style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e1' }}
            />
          </div>
          <div>
            <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '4px' }}>Ansvarig säljare:</label>
            <select
              value={projectForm.salesPersonId}
              onChange={(e) => setProjectForm({ ...projectForm, salesPersonId: e.target.value })}
              required
              style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e1' }}
            >
              <option value="" disabled>-- Välj ansvarig säljare --</option>
              {salesPeople.map((sp) => (
                <option key={sp.id} value={sp.id}>{sp.name}</option>
              ))}
            </select>
          </div>
          <button type="submit" style={{ padding: '10px', background: '#2563eb', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
            Skapa Projekt
          </button>
        </form>
      )}

      {/* Form: Säljare */}
      {activeTab === 'sales' && (
        <form onSubmit={handleCreateSales} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
          <div>
            <label style={{ display: 'block', fontWeight: 'bold', marginBottom: '4px' }}>Säljarens Namn:</label>
            <input
              type="text"
              value={salesName}
              onChange={(e) => setSalesName(e.target.value)}
              required
              style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #cbd5e1' }}
            />
          </div>
          <button type="submit" style={{ padding: '10px', background: '#2563eb', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
            Skapa Säljare
          </button>
        </form>
      )}
    </div>
  );
}