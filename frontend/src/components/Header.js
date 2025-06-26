import React from 'react';
import { Link } from 'react-router-dom';
import './styles.css'
import './custom.js'

export default function Header() {
  return (
    <div>
      <div className="hero_area">
        <header className="header_section">
          <div className="header_bottom">
            <div className="container-fluid">
              <nav className="navbar navbar-expand-lg custom_nav-container">
                <Link className="navbar-brand" to="/">
                  <span>QuickTix</span>
                </Link>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                  <span></span>
                </button>
    
                <div className="collapse navbar-collapse ml-auto" id="navbarSupportedContent">
                  <ul className="navbar-nav">
                    <li className="nav-item active">
                      <Link className="nav-link" to="/">Home</Link>
                    </li>
                    <li className="nav-item">
                      <Link className="nav-link" to="/vendorlogin">Login As Vendor</Link>
                    </li>
                    <li className="nav-item">
                      <Link className="nav-link" to="/adminlogin">Login As Admin</Link>
                    </li>
                  </ul>
                </div>
              </nav>
            </div>
          </div>
        </header>
      </div>
      
      
    </div>
  );
}
