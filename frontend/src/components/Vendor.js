import React, { useState, useEffect } from 'react';
import './custom.js';
import './styles.css';

export default function Vendor() {

  // State to store events data and form input values
  const [events, setEvents] = useState([]);
  const [formData, setFormData] = useState({
    eventId: "",
    eventName: "",
    eventDate: "",
    location: "",
    eventDescription: "",
    totalTickets: 0
  });
  
  const [formErrors, setFormErrors] = useState({
    eventName: "",
    eventDate: "",
    totalTickets: ""
  });

  // Fetch events from the API
  const getAllEvents = () => {
    fetch("http://localhost:8080/api/v2/event/getEvent", {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    })
      .then(response => response.json())
      .then(data => {
        if (data.code === "00") {
          setEvents(data.content); // Update state with fetched events
        } else {
          alert("Error loading events");
        }
      })
      .catch(error => {
        alert("Error loading events");
        console.error(error);
      });
  };

  useEffect(() => {
    getAllEvents(); // Fetch events when component is mounted
  }, []);

  // Handle form input changes
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  // Validate the form before submitting
  const validateForm = () => {
    let errors = {};
    let isValid = true;

    if (!formData.eventName) {
      errors.eventName = "Event name is required.";
      isValid = false;
    }

    if (!formData.eventDate) {
      errors.eventDate = "Event date is required.";
      isValid = false;
    }

    if (formData.totalTickets <= 0) {
      errors.totalTickets = "Total tickets must be greater than zero.";
      isValid = false;
    }

    setFormErrors(errors); // Updated: Set form errors in state
      return isValid;
    };


  // Save event
  const saveEvent = (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    const { eventName, eventDate, totalTickets, eventDescription, location, ticketPrice } = formData;

    fetch("http://localhost:8080/api/v2/event/saveEvent", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        eventName,
        date: eventDate, 
        totalTickets,
        eventDescription,
        location,
        ticketPrice
      })
    })
      .then(response => response.json())
      .then(data => {
        alert("Event saved successfully");
        getAllEvents(); // Reload events list
      })
      .catch(error => {
        alert("Error saving event");
        console.error(error);
      });
  };

  // Update event
  const updateEvent = (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    const { eventId, eventName, eventDate, totalTickets, eventDescription, location, ticketPrice } = formData;

    fetch(`http://localhost:8080/api/v2/event/updateEvent/${eventId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        eventName,
        date: eventDate, 
        totalTickets,
        eventDescription,
        location,
        ticketPrice
      })
    })
      .then(response => response.json())
      .then(data => {
        alert("Event updated successfully");
        getAllEvents(); // Reload events list
      })
      .catch(error => {
        alert("Error updating event");
        console.error(error);
      });
  };

  // Delete event
  const deleteEvent = (eventId) => {
    if (!eventId) {
      alert("Invalid Event ID");
      return;
    }
  
    fetch(`http://localhost:8080/api/v2/event/deleteEvent/${eventId}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json"
      }
    })
      .then(response => {
        if (!response.ok) {
          return response.json().then((err) => {
            throw new Error(err.message || "Failed to delete the event.");
          });
        }
        return response.json();
      })
      .then(data => {
        alert("Event deleted successfully");
        getAllEvents(); // Reload events list
      });
  };
  

  // Handle row click to populate form with event details
  const handleRowClick = (event) => {
    setFormData({
      eventId: event.eventid,
      eventName: event.eventName,
      eventDate: event.date,
      location: event.location,
      eventDescription: event.eventDescription,
      totalTickets: event.totalTickets
    });
  };

  // Calculate sold tickets for each event (assuming 'tickets' array exists and each ticket has 'status' and 'ticketId')
  const calculateSoldTickets = (event) => {
    return event.tickets.filter(ticket => ticket.status === "sold").length;
  };

  return (
    <div>
        <section className="project-section">
        <div className="image-content">
          <img
            src="ticket.png"
            className="ticket-image"
            style={{ opacity: 0.77 }}
            alt="Ticket"
          />
        </div>
        <div className="text-content">
          <h1 className="project-title">Welcome, Vendors! Manage Your Events with Ease</h1>
          <p className="project-description" style={{ fontSize: '16px' }}>
            Effortlessly organize, track, and optimize your events with our comprehensive Vendor Dashboard. From creating events and managing tickets to tracking sales and analyzing performance, everything you need is at your fingertips. Take control of your events, engage with your audience, and make every event a success. Start managing your events today!
          </p>
        </div>
      </section>

      <div className="main-table-container">
        <div className="table-container" style={{ paddingTop: '40px' }}>
          <table>
            <thead>
              <tr>
                <th>Event Name</th>
                <th>Total Tickets</th>
                <th>Sold Tickets</th>
                <th>Available Tickets</th>
              </tr>
            </thead>
            <tbody>
              {events.map(event => {
                const soldTickets = calculateSoldTickets(event);
                return (
                  <tr key={event.eventId} onClick={() => handleRowClick(event)}>
                    <td>{event.eventName}</td>
                    <td>{event.totalTickets}</td>
                    <td>{soldTickets}</td>
                    <td>{event.totalTickets - soldTickets}</td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </div>

      <div style={{ marginTop: '20px' }} className="centered-heading">
        <h1
          style={{
            fontFamily: 'Georgia, "Times New Roman", Times, serif',
            fontWeight: 200,
            fontSize: '35px',
          }}
          className="heading"
        >
          Event And Ticket Management
        </h1>
      </div>

      <div className="formbold-main-wrapper">
        <div className="formbold-form-wrapper">
          <div className="centered-heading">
            <h1 className="heading">Enter Details</h1>
          </div>
          <form  style={{ margin: '40px' }}>
            <div className="formbold-input-flex">
            <div>
                <input disabled
                  type="text"
                  name="eventId"
                  value={formData.eventId}
                  onChange={handleInputChange}
                  id="eventId"
                  placeholder="Enter Event ID"
                  className="formbold-form-input"
                />
                <label htmlFor="eventId" className="formbold-form-label">
                  Event ID
                </label>
              </div>
              <div>
                <input
                  type="text"
                  name="eventName"
                  value={formData.eventName}
                  onChange={handleInputChange}
                  id="eventName"
                  placeholder="Enter Event Name"
                  className="formbold-form-input"
                />
                <label htmlFor="eventName" className="formbold-form-label">
                  Event Name
                </label>
              </div>
             
            </div>
            <div className="formbold-input-flex">
              <div>
                <input 
                  type="text"
                  name="location"
                  value={formData.location}
                  onChange={handleInputChange}
                  id="location"
                  placeholder="Enter Location"
                  className="formbold-form-input"
                />
                <label htmlFor="eventId" className="formbold-form-label">
                  Event Location
                </label>
              </div>
              <div>
                <input
                  type="number"
                  name="ticketPrice"
                  value={formData.ticketPrice}
                  onChange={handleInputChange}
                  id="ticketPrice"
                  placeholder="Enter Ticket Price"
                  className="formbold-form-input"
                />
                <label htmlFor="eventName" className="formbold-form-label">
                  Ticket Price
                </label>
              </div>
            </div>

            <div className="formbold-input-flex">
              <div>
                <input
                  type="number"
                  name="totalTickets"
                  value={formData.totalTickets}
                  onChange={handleInputChange}
                  id="totalTickets"
                  placeholder="Enter No Of Tickets"
                  className="formbold-form-input"
                />
                <label htmlFor="totalTickets" className="formbold-form-label">
                  No Of Tickets
                </label>
                {formErrors.numTickets && <div className="error">{formErrors.numTickets}</div>}

              </div>
              <div>
                <input
                  type="date"
                  name="eventDate"
                  value={formData.eventDate}
                  onChange={handleInputChange}
                  id="eventDate"
                  placeholder="Enter Event Date"
                  className="formbold-form-input"
                />
                <label htmlFor="eventDate" className="formbold-form-label">
                  Event Date
                </label>
                {formErrors.eventDate && <div className="error">{formErrors.eventDate}</div>}
              </div>
            </div>

            <div className="formbold-textarea">
              <textarea
                rows="6"
                name="eventDescription"
                value={formData.eventDescription}
                onChange={handleInputChange}
                id="eventDescription"
                placeholder="Enter Event Description..."
                className="formbold-form-input"
              ></textarea>
              <label htmlFor="message" className="formbold-form-label">
                Event Description
              </label>
            </div>

            <div style={{ display: 'flex', justifyContent: 'space-between' }}>
              <button className="formbold-btn" onClick={saveEvent}>Insert</button>
              <button className="formbold-btn" onClick={updateEvent}>Update</button>
              <button className="formbold-btn" onClick={() => deleteEvent(formData.eventId)}>Delete</button>
            </div>
          </form>
        </div>
      </div>

      <div className="main-table-container">
        <div className="table-container" style={{ paddingTop: '40px' }}>
          <table>
            <thead>
              <tr>
                <th>Event ID</th>
                <th>Event Name</th>
                <th>Location</th>
                <th>Date</th>
                <th>Ticket Price</th>
                <th>Total Tickets</th>
              </tr>
            </thead>
            <tbody>
            {events.map(event => (
                <tr key={event.eventId} onClick={() => handleRowClick(event)}>
                  <td>{event.eventid}</td>
                  <td>{event.eventName}</td>
                  <td>{event.location}</td>
                  <td>{event.date}</td>
                  <td>{event.ticketPrice}</td>
                  <td>{event.totalTickets}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
}
