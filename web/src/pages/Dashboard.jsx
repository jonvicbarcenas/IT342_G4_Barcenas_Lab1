import { useNavigate } from 'react-router-dom';

function Dashboard({ user, onLogout }) {
  const navigate = useNavigate();

  return (
    <div className="dashboard-container">
      <h2>Dashboard</h2>
      <p className="dashboard-message">
        You are logged in as <strong>{user.firstName} {user.lastName}</strong>
      </p>
      
      <div className="dashboard-actions">
        <button className="btn-secondary" onClick={() => navigate('/profile')}>
          View Profile
        </button>
        <button className="btn-logout" onClick={onLogout}>
          Logout
        </button>
      </div>
    </div>
  );
} 

export default Dashboard;
