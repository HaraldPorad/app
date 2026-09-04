import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import SearchPage from './pages/SearchPage';
import OverviewPage from './pages/OverviewPage';
import NewReviewPage from './pages/NewReviewPage';
import SuccessPage from './pages/SuccessPage';
import AdminManagementPage from './pages/AdminManagementPage';

export default function App() {
  return (
    <BrowserRouter>
      <div style={{ maxWidth: '1100px', margin: '0 auto', padding: '0 1rem', fontFamily: 'system-ui, sans-serif' }}>
        <Navbar />
        <Routes>
          <Route path="/" element={<Navigate to="/new" replace />} />
          <Route path="/new" element={<NewReviewPage />} />
          <Route path="/search" element={<SearchPage />} />
          <Route path="/reviews" element={<OverviewPage />} />
          <Route path="/success" element={<SuccessPage />} />
          <Route path="/admin" element={<AdminManagementPage />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}