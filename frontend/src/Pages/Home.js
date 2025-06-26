import React from 'react'
import Header from '../components/Header'
import Admin from '../components/Admin'
import Vendor from '../components/Vendor'
import Customer from '../components/Customer'
import Footer from '../components/Footer'
import { Link } from 'react-router-dom'

export default function Home() {
  return (
    <div>
        
      <Header />
      <Customer />
      <Footer />
    </div>
  )
}

