import React, { useState, useEffect } from 'react';
import './custom.js';
import './styles.css';

export default function Admin() {
  
  const [vendors, setVendors] = useState([]);
  const [formData, setFormData] = useState({
    vendid: "",
    vendname: "",
    vendemail: "",
    vendpassword: ""
  });
  const [formErrors, setFormErrors] = useState({}); // state to hold form validation errors

  // handle input change
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  // Validate form data
  const validateForm = () => {
    const errors = {};
    const { vendname, vendemail, vendpassword } = formData;

    // Check if vendor name is empty
    if (!vendname) {
      errors.vendname = "Vendor name is required";
    }

    // Check if email is valid
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!vendemail) {
      errors.vendemail = "Email is required";
    } else if (!emailRegex.test(vendemail)) {
      errors.vendemail = "Invalid email format";
    }

    // Check if password is valid (min 6 characters)
    if (!vendpassword) {
      errors.vendpassword = "Password is required";
    } else if (vendpassword.length < 6) {
      errors.vendpassword = "Password must be at least 6 characters long";
    }

    setFormErrors(errors);
    return Object.keys(errors).length === 0; // Return true if no errors
  };

  // Save vendor function
  const saveVendor = (e) => {
    e.preventDefault();
    
    if (!validateForm()) return; // Prevent submission if validation fails

    const { vendname, vendemail, vendpassword } = formData;

    fetch("http://localhost:8080/api/v2/vendor/saveVendor", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        id: "", // Empty ID for new vendor
        name: vendname,
        email: vendemail,
        password: vendpassword
      })
    })
    .then(response => response.json())
    .then(data => {
      alert("Vendor saved successfully");
      getAllVendors();
    })
    .catch(error => {
      alert("Error saving vendor");
      console.error(error);
    });
  };

  // Update vendor function
  const updateVendor = () => {
    if (!validateForm()) return; // Prevent submission if validation fails
    
    const { vendid, vendname, vendemail, vendpassword } = formData;

    fetch("http://localhost:8080/api/v2/vendor/updateVendor", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        id: vendid,
        name: vendname,
        email: vendemail,
        password: vendpassword
      })
    })
    .then(response => response.json())
    .then(data => {
      alert("Vendor updated successfully");
      getAllVendors();
    })
    .catch(error => {
      alert("Error updating vendor");
      console.error(error);
    });
  };

  const deleteVendor = (vendId) => {
    fetch(`http://localhost:8080/api/v2/vendor/deleteVendor/${vendId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then(response => response.json())
    .then(data => {
      alert('Vendor deleted successfully');
      getAllVendors(); // Call the function to reload vendors
    })
    .catch(error => {
      alert('Error deleting vendor');
      console.error(error);
    });
  };

  // Get all vendors function
  const getAllVendors = () => {
    fetch("http://localhost:8080/api/v2/vendor/getVendors", {
      method: "GET", // GET method for fetching data
      headers: {
        "Content-Type": "application/json"
      }
    })
    .then(response => response.json())
    .then(data => {
      if (data.code === "00") {
        setVendors(data.content); // Update state with the fetched vendors
      } else {
        alert("Error loading vendors");
      }
    })
    .catch(error => {
      alert("Error loading vendors");
    });
  };

  // Handle row click (populate form fields)
  const handleRowClick = (vendId, name, email, password) => {
    setFormData({
      vendid: vendId,
      vendname: name,
      vendemail: email,
      vendpassword: password
    });
  };

  // useEffect hook to fetch vendors on component mount
  useEffect(() => {
    getAllVendors();
  }, []);

  return (
    <div>
      <section className="project-section">
        <div className="text-content">
          <h1 className="project-title">Empowering Control, Simplifying Management</h1>
          <p className="project-description">
            Welcome to the Admin Dashboard — your ultimate control center. Seamlessly manage users, monitor activities, and optimize system performance all in one place.
          </p>
        </div>
        <div className="image-content">
          <img src="ticket.jpg" className="ticket-image" style={{ opacity: '75%' }} alt="Ticket" />
        </div>
      </section>

      <div className="formbold-main-wrapper" style={{ backgroundColor: '#fbf4ec' }}>
        <div className="formbold-form-wrapper" style={{ backgroundColor: 'rgb(241, 228, 199)' }}>
          <div className="centered-heading">
            <h1 className="heading">Enter Vendor Details</h1>
          </div>
          <form style={{ margin: '40px' }}>
            <div className="formbold-input-flex">
              <div>
                <input
                  disabled
                  type="text"
                  name="vendid"
                  value={formData.vendid}
                  onChange={handleInputChange}
                  id="vendid"
                  placeholder="Enter ID"
                  className="formbold-form-input"
                />
                <label htmlFor="id" className="formbold-form-label">Vendor ID</label>
              </div>
              <div>
                <input
                  type="text"
                  name="vendname"
                  value={formData.vendname}
                  onChange={handleInputChange}
                  id="vendorname"
                  placeholder="Enter Name"
                  className="formbold-form-input"
                />
                <label htmlFor="firstname" className="formbold-form-label">Vendor Name</label>
                {formErrors.vendname && <div className="error">{formErrors.vendname}</div>}
              </div>
            </div>

            <div className="formbold-input-flex">
              <div>
                <input
                  type="email"
                  name="vendemail"
                  value={formData.vendemail}
                  onChange={handleInputChange}
                  id="vendoremail"
                  placeholder="Enter Email"
                  className="formbold-form-input"
                />
                <label htmlFor="email" className="formbold-form-label">Email</label>
                {formErrors.vendemail && <div className="error">{formErrors.vendemail}</div>}
              </div>
              <div>
                <input
                  type="password"
                  name="vendpassword"
                  id="vendpassword"
                  placeholder="Enter Password"
                  className="formbold-form-input"
                />
                <label htmlFor="phone" className="formbold-form-label">Password</label>
                {formErrors.vendpassword && <div className="error">{formErrors.vendpassword}</div>}
              </div>
            </div>

            <div style={{ display: 'flex', justifyContent: 'space-between' }}>
              <button className="formbold-btn" onClick={saveVendor}>Insert</button>
              <button className="formbold-btn" onClick={updateVendor}>Update</button>
              <button className="formbold-btn" onClick={() => deleteVendor(formData.vendid)}>Delete</button>
            </div>
          </form>
        </div>
      </div>

      <div className="main-table-container">
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Vendor ID</th>
                <th>Name</th>
                <th>Email</th> 
              </tr>
            </thead>
            <tbody>
              {vendors.map(vendor => (
                <tr key={vendor.id} onClick={() => handleRowClick(vendor.id, vendor.name, vendor.email, vendor.password)}>
                  <td>{vendor.id}</td>
                  <td>{vendor.name}</td>
                  <td>{vendor.email}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
