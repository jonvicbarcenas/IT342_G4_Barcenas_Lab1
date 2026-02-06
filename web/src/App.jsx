import { useState, useEffect } from 'react';
import Login from './pages/Login';
import Signup from './pages/Signup';
import Profile from './pages/Profile';
import Dashboard from './pages/Dashboard';
import './App.css';

function App() {
  const [currentPage, setCurrentPage] = useState('login');
  const [user, setUser] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const email = localStorage.getItem('email');
    const userId = localStorage.getItem('userId');
    const firstName = localStorage.getItem('firstName');
    const lastName = localStorage.getItem('lastName');
    
    if (token && email) {
      setUser({ token, email, userId, firstName, lastName });
      setCurrentPage('dashboard');
    }
  }, []);

  const handleLoginSuccess = (userData) => {
    setUser(userData);
    localStorage.setItem('token', userData.token);
    localStorage.setItem('email', userData.email);
    localStorage.setItem('userId', userData.userId);
    localStorage.setItem('firstName', userData.firstName);
    localStorage.setItem('lastName', userData.lastName);
    setCurrentPage('dashboard');
  };

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem('token');
    localStorage.removeItem('email');
    localStorage.removeItem('userId');
    localStorage.removeItem('firstName');
    localStorage.removeItem('lastName');
    setCurrentPage('login');
  };

  return (
    <div className="app">
      {currentPage === 'login' && (
        <Login 
          onLoginSuccess={handleLoginSuccess}
          onSwitchToSignup={() => setCurrentPage('signup')}
        />
      )}
      {currentPage === 'signup' && (
        <Signup 
          onSignupSuccess={handleLoginSuccess}
          onSwitchToLogin={() => setCurrentPage('login')}
        />
      )}
      {currentPage === 'dashboard' && user && (
        <Dashboard 
          user={user}
          onLogout={handleLogout}
          onNavigateToProfile={() => setCurrentPage('profile')}
        />
      )}
      {currentPage === 'profile' && user && (
        <Profile 
          user={user}
          onLogout={handleLogout}
          onNavigateToDashboard={() => setCurrentPage('dashboard')}
        />
      )}
    </div>
  );
}

export default App;
