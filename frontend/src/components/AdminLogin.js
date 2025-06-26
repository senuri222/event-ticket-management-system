import { useState } from 'react';  
import { useNavigate } from 'react-router-dom'; 

export default function AdminLogin() {
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
            // Send request to login API
            const response = await fetch('http://localhost:8080/api/v2/admin/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(loginData),
            });
  
            const data = await response.json();
  
            // Check if login was successful
            if (data.content === 1) {
                navigate('/admin'); // Navigate to the admin dashboard
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
        <div className="login-body">
            <div className="dark-background">
                <div className="smoke-effect">
                    <div className="lock-icon">
                        <p className="login-text" style={{ color: "white" }}>
                            <span className="fa-stack fa-2x"> 
                                <i className="fa fa-circle fa-stack-2x"></i>
                                <i className="fa fa-lock fa-stack-1x" style={{ color: "black" }}></i>
                            </span>
                        </p>
                    </div>
                    <form onSubmit={handleLogin} className="hidden-login-form">
                        <input
                            type="email"
                            placeholder="Email"
                            className="dimmed-input"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                        <input
                            type="password"
                            placeholder="Password"
                            className="dimmed-input"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                        <br />
                        <button type="submit" className="ghost-button">
                            Login
                        </button>
                    </form>
                    <br />
                    {errorMessage && <p className="error-message" style={{ color: 'red' }}>{errorMessage}</p>}
                </div>
            </div>
        </div>
    );
}
