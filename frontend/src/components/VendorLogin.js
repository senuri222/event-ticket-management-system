import { useState } from 'react';  
import { useNavigate } from 'react-router-dom'; 
import { Link } from 'react-router-dom';

export default function VendorLogin() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();
  
    const handleLogin = async (e) => {
      e.preventDefault();
  
      // Prepare the request data
      const loginData = {
        email: email,
        password: password,
      };
  
      try {
        // Send POST request to login API
        const response = await fetch('http://localhost:8080/api/v2/vendor/loginVendor', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(loginData),
        });
  
        const data = await response.json();
  
        // Check if login was successful
        if (data.content === 1) {
          navigate('/vendor');
        } else {
          // If failed, display error message
          setErrorMessage('Invalid email or password');
        }
      } catch (error) {
        console.error('Error during login:', error);
        setErrorMessage('An error occurred. Please try again.');
      }
    };
  return (
    <div>
        <div>
        <div class="login-body">
        <div class="dark-background">
            <div class="smoke-effect">
            <div class="lock-icon">
            <p class="login-text" style={{color: "white"}}>
                <span class="fa-stack fa-2x"> 
                    <i class="fa fa-circle fa-stack-2x"></i>
                    <i class="fa fa-lock fa-stack-1x" style={{color: "black"}}></i>
                </span>
            </p>

            </div>
            <form class="hidden-login-form">
                <input type="email" placeholder="Email" class="dimmed-input"/>
                <input type="password" placeholder="Password" class="dimmed-input"/>
                <br/>
                <button type="submit" class="ghost-button">
                <Link className="nav-link" to="/vendor">Login</Link></button>
            </form>
            <br/>
            {errorMessage && <p className="error-message" style={{ color: 'red' }}>{errorMessage}</p>}
            </div>
        </div>
    </div>
    </div>
    </div>
  )
}
