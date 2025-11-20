import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";
import ProtectedRoute from "./components/ProtectedRoute";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import RequestBlood from "./pages/RequestBlood";
import DonorSearch from "./pages/DonorSearch";
import BrowseRequests from "./pages/BrowseRequests"; // 🔹 NEW

// Styled Landing page for BloodConnect
const Landing = () => (
  <div
    style={{
      minHeight: "80vh",
      display: "flex",
      flexDirection: "column",
      justifyContent: "center",
      alignItems: "center",
      background: "linear-gradient(135deg, #ffe5e0 0%, #f5f5f5 100%)",
      textAlign: "center",
      padding: "20px",
    }}
  >
    <h1 style={{ color: "#E53935", fontSize: "3rem", marginBottom: "20px" }}>
      Welcome to BloodConnect
    </h1>
    <p
      style={{
        color: "#333",
        fontSize: "1.2rem",
        maxWidth: "600px",
        marginBottom: "30px",
      }}
    >
      Your platform for requesting and donating blood efficiently. Connect with
      donors instantly and save lives.
    </p>
    <div>
      <a
        href="/requestblood"
        style={{
          backgroundColor: "#E53935",
          color: "#fff",
          padding: "12px 25px",
          borderRadius: "8px",
          textDecoration: "none",
          fontWeight: "bold",
          marginRight: "10px",
        }}
      >
        Create Request
      </a>
      <a
        href="/dashboard"
        style={{
          backgroundColor: "#fff",
          color: "#E53935",
          padding: "12px 25px",
          borderRadius: "8px",
          textDecoration: "none",
          fontWeight: "bold",
          border: "2px solid #E53935",
          marginRight: "10px",
        }}
      >
        View Dashboard
      </a>
      <a
        href="/finddonors"
        style={{
          backgroundColor: "#fff",
          color: "#E53935",
          padding: "12px 25px",
          borderRadius: "8px",
          textDecoration: "none",
          fontWeight: "bold",
          border: "2px solid #E53935",
        }}
      >
        Find Donors
      </a>
    </div>
  </div>
);

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<Landing />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* Protected Routes */}
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="/requestblood"
          element={
            <ProtectedRoute>
              <RequestBlood />
            </ProtectedRoute>
          }
        />
        <Route
          path="/finddonors"
          element={
            <ProtectedRoute>
              <DonorSearch />
            </ProtectedRoute>
          }
        />
        <Route
          path="/browserequests"
          element={
            <ProtectedRoute>
              <BrowseRequests />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;




