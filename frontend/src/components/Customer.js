import React, { useState, useEffect } from 'react';   
import './custom.js';
import './styles.css';
import { Link } from 'react-router-dom';

export default function Customer() {
  const [events, setEvents] = useState([]); 

  // fetch events from the API
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

  return (
    <div>
      <section className="project-section">
        <div className="text-content">
          <h1 className="project-title">Empowering Control, Simplifying Management</h1>
          <p className="project-description" style={{ fontSize: "14px" }}>
            Welcome to the Admin Dashboard — your ultimate control center. Seamlessly manage users, monitor activities, and optimize system performance all in one place.
          </p>
          <br/>
        </div>
        
        <div className="image-content">
          <img src="ticket3.jpg" className="ticket-image" style={{ opacity: "0.8" }} />
        </div>
      </section>

      <section className="ticket_section layout_padding">
        <div className="container">
          <div className="heading_container heading_center">
            <h2 style={{ color: "black" }}>Events</h2>
          </div>
          <div className="row">
            {Array.isArray(events) && events.length > 0 ? (
              events
                .filter(event => event.totalTickets >= 5) // Filter out events with less than 5 tickets
                .map((event) => {
                  console.log("Event ID:", event.id);  // Debugging line to check event.id
                  console.log("Event object:", event);
                  return (
                    <div className="col-md-4" key={event.id}>
                      <div className="box" style={{ backgroundColor: "rgb(241, 228, 199)" }}>
                        <div className="img-box"></div>
                        <div className="detail-box">
                          <h6>{event.eventName}</h6>
                          <p>{event.eventDescription}</p>
                          <h1 className="project-title" style={{fontSize: "20px"}}>Rs. {event.ticketPrice}</h1>

                          <button className="button-buy">
                            <Link 
                              to={`/form/${event.eventid}`} 
                              style={{
                                textDecoration: "none", 
                                color: "inherit", 
                                background: "none", 
                                border: "none", 
                                padding: "0", 
                                paddingBottom: "10px",
                                font: "inherit"
                              }}
                            >
                              Buy Tickets
                            </Link>
                          </button>
                        </div>
                      </div>
                    </div>
                  );
                })
            ) : (
              <p>No events available</p>  // Message if there are no events
            )}
          </div>
        </div>
      </section>
    </div>
  );
}
