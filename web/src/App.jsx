import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { useState, useEffect } from "react";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Profile from "./pages/Profile";
import Dashboard from "./pages/Dashboard";
import "./App.css";

function App() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("jwt");
    const email = localStorage.getItem("email");
    const userId = localStorage.getItem("userId");
    const firstName = localStorage.getItem("firstName");
    const lastName = localStorage.getItem("lastName");

    if (token && email) {
      setUser({ token, email, userId, firstName, lastName });
    }
  }, []);

  const handleLoginSuccess = (userData) => {
    setUser(userData);
    localStorage.setItem("jwt", userData.token);
    localStorage.setItem("email", userData.email);
    localStorage.setItem("userId", userData.userId);
    localStorage.setItem("firstName", userData.firstName);
    localStorage.setItem("lastName", userData.lastName);
  };

  const handleLogout = () => {
    setUser(null);
    localStorage.clear();
  };

  return (
    <Router>
      <div className="app">
        <Routes>

          <Route 
            path="/login" 
            element={
              user 
                ? <Navigate to="/dashboard" />
                : <Login onLoginSuccess={handleLoginSuccess} />
            } 
          />

          <Route 
            path="/signup" 
            element={
              user 
                ? <Navigate to="/dashboard" />
                : <Signup onSignupSuccess={handleLoginSuccess} />
            } 
          />

          <Route 
            path="/dashboard" 
            element={
              user 
                ? <Dashboard user={user} onLogout={handleLogout} />
                : <Navigate to="/login" />
            } 
          />

          <Route 
            path="/profile" 
            element={
              user 
                ? <Profile user={user} onLogout={handleLogout} />
                : <Navigate to="/login" />
            } 
          />

          <Route path="*" element={<Navigate to="/login" />} />


        </Routes>
      </div>
    </Router>
  );
}

export default App;
