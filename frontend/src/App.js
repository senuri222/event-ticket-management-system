import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import Home from './Pages/Home';  // Import the Home component here
import 'font-awesome/css/font-awesome.min.css';  // Import the Font Awesome CSS file
import Vendor from './components/Vendor';
import Admin from './components/Admin';
import AdminPage from './Pages/AdminPage';
import VendorPage from './Pages/VendorPage';
import Form from './components/Form';
import AdminLogin from './components/AdminLogin';
import VendorLogin from './components/VendorLogin';

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
        </Routes>
        <Routes>
          <Route path="/vendor" element={<VendorPage />} />
        </Routes>
        <Routes>
          <Route path="/admin" element={<AdminPage />} />
        </Routes>
        <Routes>
          <Route path="/form/:eventId" element={<Form />} />
        </Routes>
        <Routes>
          <Route path="/adminlogin" element={<AdminLogin />} />
        </Routes>
        <Routes>
          <Route path="/vendorlogin" element={<VendorLogin />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
