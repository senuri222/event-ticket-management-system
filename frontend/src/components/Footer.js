import React from 'react'
import './custom.js'
import './styles.css'

export default function Footer() {
  return (
    <div>
        <section className="info_section">
        <div className="container">
          <div className="row">
            <div className="col-md-3">
              <div className="info_logo">
                
                  <span>Get in Touch!</span>
               
                <p>
                  Have questions or need assistance? We're here to help! Reach out to us anytime, and we'll ensure your ticketing experience is smooth and hassle-free.
                </p>
              </div>
            </div>
            <div className="col-md-3">
              <div className="info_links">
                <h5>We’re Here to Help!</h5>
                <ul>
                  <li>Help Center</li>
                  <li>Email us</li>
                  <li>Call us</li>
                  <li>Live Chat</li>
                </ul>
              </div>
            </div>
            <div className="col-md-3">
              <div className="info_info">
                <h5>Contact Us</h5>
              </div>
              <div className="info_contact">
                <a href="https://www.google.com/maps?q=123+Street,+Colombo,+Sri+Lanka">
                  <i className="fa fa-map-marker" aria-hidden="true"></i>
                  <span>123 Street, Colombo, Sri Lanka</span>
                </a>
                <a href="tel:+0761231234">
                  <i className="fa fa-phone" aria-hidden="true"></i>
                  <span>Call : +076 123 1234</span>
                </a>
                <a href="mailto:quicktix@gmail.com">
                  <i className="fa fa-envelope" aria-hidden="true"></i>
                  <span>quicktix@gmail.com</span>
                </a>
              </div>
            </div>
            <div className="col-md-3">
              <div className="info_form">
                <h5>Newsletter</h5>
                <form action="">
                  <input type="email" placeholder="Enter your email" />
                  <button>Subscribe</button>
                </form>
                <div className="social_box">
                  <a href="https://www.facebook.com">
                    <i className="fa fa-facebook" aria-hidden="true"></i>
                  </a>
                  <a href="https://www.twitter.com">
                    <i className="fa fa-twitter" aria-hidden="true"></i>
                  </a>
                  <a href="https://www.youtube.com">
                    <i className="fa fa-youtube" aria-hidden="true"></i>
                  </a>
                  <a href="https://www.instagram.com">
                    <i className="fa fa-instagram" aria-hidden="true"></i>
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  )
}
