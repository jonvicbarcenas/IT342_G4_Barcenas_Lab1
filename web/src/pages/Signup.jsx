import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AUTH_BASE_URL, apiRequest } from '../api/client';

function Signup({ onSignupSuccess }) {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  });

  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setLoading(true);

    try {
      const { response, data } = await apiRequest(
        `${AUTH_BASE_URL}/register`,
        {
          method: 'POST',
          body: JSON.stringify(formData),
        }
      );

      if (response.ok) {
        // If backend returns token (auto-login after register)
        if (data.token) {
          onSignupSuccess({
            token: data.token,
            email: data.email,
            userId: data.userId,
            firstName: data.firstName,
            lastName: data.lastName,
          });

          navigate('/dashboard');
        } else {
          // If backend requires manual login
          setSuccess(data.message || 'Registration successful');

          setTimeout(() => {
            navigate('/login');
          }, 1500);
        }
      } else {
        setError(data.message || 'Registration failed');
      }
    } catch (err) {
      setError('Unable to connect to server');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">
      <h2>Sign Up</h2>

      {error && <div className="error-message">{error}</div>}
      {success && <div className="success-message">{success}</div>}

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>First Name</label>
          <input
            type="text"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
            required
            placeholder="Enter your first name"
          />
        </div>

        <div className="form-group">
          <label>Last Name</label>
          <input
            type="text"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
            required
            placeholder="Enter your last name"
          />
        </div>

        <div className="form-group">
          <label>Email</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
            placeholder="Enter your email"
          />
        </div>

        <div className="form-group">
          <label>Password</label>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
            placeholder="Enter your password"
          />
        </div>

        <button type="submit" className="btn" disabled={loading}>
          {loading ? 'Creating account...' : 'Sign Up'}
        </button>
      </form>

      <div className="switch-link">
        Already have an account?{' '}
        <button onClick={() => navigate('/login')}>Login</button>
      </div>
    </div>
  );
}

export default Signup;
