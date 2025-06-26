import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

export default function Form() {
  const { eventId } = useParams(); // Get eventId from the URL
  const [noOfTickets, setNoOfTickets] = useState('');
  const [customerName, setCustomerName] = useState('');
  const [mobile, setMobile] = useState('');
  const [email, setEmail] = useState('');
  const [cardName, setCardName] = useState('');
  const [cardNumber, setCardNumber] = useState('');

  useEffect(() => {
    console.log("Event ID from URL:", eventId); // Log the eventId to ensure it's correct
  }, [eventId]);

  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevent form from reloading the page on submit
  
    // Send email request
    await fetch("http://localhost:8080/api/v2/mail/send", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        toMail: email,  // Use the email provided by the user
        message: `Customer Name: ${customerName}\nMobile: ${mobile}\nNo of Tickets: ${noOfTickets}`, // Include relevant data in the message
        subject: "Youre Purchase Successfully."  
      })
    })
      .then(response => response.json())
      .then(data => {
        console.log("Email sent successfully", data);
      })
      .catch(error => {
        console.error("Error sending email", error);
      });
  
    // Send buy ticket request
    await fetch(`http://localhost:8080/api/v2/event/buyTickets/${eventId}?numberOfTickets=${noOfTickets}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
    })
      .then(response => response.json())
      .then(data => {
        console.log("Tickets purchased successfully", data);
      })
      .catch(error => {
        console.error("Error purchasing tickets", error);
      });
  };
  
  return (
    <div className="body">
      <div className="cstm-container">
        <h1>Event Booking</h1>

        <form id="bookingForm" onSubmit={handleSubmit}>
          <div className="cstm-billing-section">
            <h3>Billing Information</h3>
            <div className="cstm-form-group">
              <input
                type="number"
                name="nooftickets"
                placeholder="Number of Tickets"
                className="cstm-input"
                value={noOfTickets}
                onChange={(e) => setNoOfTickets(e.target.value)}
                required
              />
            </div>
            <div className="cstm-form-group">
              <input
                type="text"
                name="customerName"
                className="cstm-input"
                placeholder="Customer Name"
                value={customerName}
                onChange={(e) => setCustomerName(e.target.value)}
                required
              />
            </div>
            <div className="cstm-form-group">
              <input
                type="text"
                name="mobile"
                className="cstm-input"
                placeholder="Mobile Number"
                value={mobile}
                onChange={(e) => setMobile(e.target.value)}
                required
              />
            </div>
            <div className="cstm-form-group">
              <input
                type="email"
                name="email"
                className="cstm-input"
                placeholder="Email Address"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            <div className="cstm-form-group">
              <input
                type="text"
                name="cardName"
                className="cstm-input"
                placeholder="Name On Card"
                value={cardName}
                onChange={(e) => setCardName(e.target.value)}
                required
              />
            </div>
            <div className="cstm-form-group">
              <input
                type="text"
                name="cardNumber"
                className="cstm-input"
                placeholder="Card Number"
                value={cardNumber}
                onChange={(e) => setCardNumber(e.target.value)}
                required
              />
            </div>

            <button type="submit" className="cstm-button">
              Pay Now
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
