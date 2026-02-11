import { useNavigate } from 'react-router-dom';

function Profile({ user, onLogout,  }) {
  const navigate = useNavigate();

  const getInitials = (email) => {
    return email.charAt(0).toUpperCase();
  };

  return (
    <div className="profile-container">
      <div className="profile-nav">
        <button className="nav-btn" onClick={() => navigate('/dashboard')}>
          ← Back to Dashboard
        </button>
      </div>
      
      <div className="profile-header">
        <div className="profile-avatar">
          {getInitials(user.email)}
        </div>
        <h2>Your Profile</h2>
      </div>
      
      <div className="profile-info">
        <div className="info-row">
          <span className="info-label">User ID:</span>
          <span className="info-value">{user.userId}</span>
        </div>
        <div className="info-row">
          <span className="info-label">First Name:</span>
          <span className="info-value">{user.firstName}</span>
        </div>
        <div className="info-row">
          <span className="info-label">Last Name:</span>
          <span className="info-value">{user.lastName}</span>
        </div>
        <div className="info-row">
          <span className="info-label">Email:</span>
          <span className="info-value">{user.email}</span>
        </div>
      </div>

      <button className="logout-btn" onClick={onLogout}>
        Logout
      </button>
    </div>
  );
}

export default Profile;
