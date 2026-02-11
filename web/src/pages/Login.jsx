import { useState } from 'react';
import { AUTH_BASE_URL, apiRequest } from '../api/client';

function Login({ onLoginSuccess, onSwitchToSignup }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const { response, data } = await apiRequest(`${AUTH_BASE_URL}/login`, {
        method: 'POST',
        body: JSON.stringify({ email, password }),
      });

      if (response.ok && data.token) {
        onLoginSuccess({
          token: data.token,
          email: data.email,
          userId: data.userId,
          firstName: data.firstName,
          lastName: data.lastName,
        });
      } else {
        setError(data.message || 'Login failed');
      }
    } catch (err) {
      setError('Unable to connect to server');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">
      <h2>Login</h2>
      {error && <div className="error-message">{error}</div>}
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Email</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            placeholder="Enter your email"
          />
        </div>
        <div className="form-group">
          <label>Password</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            placeholder="Enter your password"
          />
        </div>
        <button type="submit" className="btn" disabled={loading}>
          {loading ? 'Logging in...' : 'Login'}
        </button>
      </form>
      <div className="switch-link">
        Don't have an account?{' '}
        <button onClick={onSwitchToSignup}>Sign up</button>
      </div>
    </div>
  );
}

export default Login;
