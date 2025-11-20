import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const Navbar = () => {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const user = JSON.parse(localStorage.getItem("user"));
  const [showProfile, setShowProfile] = useState(false);

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    navigate("/login");
  };

  return (
    <nav
      style={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        padding: "12px 25px",
        backgroundColor: "#E53935",
        color: "#fff",
        position: "relative",
      }}
    >
      <Link
        to="/"
        style={{
          color: "#fff",
          textDecoration: "none",
          fontWeight: "bold",
          fontSize: "1.5rem",
        }}
      >
        BloodConnect
      </Link>

      <div>
        {!token ? (
          <>
            <Link
              to="/login"
              style={{ marginRight: "10px", color: "#fff", fontWeight: "bold" }}
            >
              Login
            </Link>
            <Link
              to="/register"
              style={{ color: "#fff", fontWeight: "bold" }}
            >
              Register
            </Link>
          </>
        ) : (
          <>
            <Link
              to="/dashboard"
              style={{ marginRight: "10px", color: "#fff", fontWeight: "bold" }}
            >
              Dashboard
            </Link>

            <Link
              to="/requestblood"
              style={{ marginRight: "10px", color: "#fff", fontWeight: "bold" }}
            >
              Create Request
            </Link>

            {/* 🔹 Only show for donors */}
            {user?.isDonor && (
              <Link
                to="/browserequests"
                style={{
                  marginRight: "10px",
                  color: "#fff",
                  fontWeight: "bold",
                }}
              >
                Browse Requests
              </Link>
            )}

            <button
              onClick={() => setShowProfile(!showProfile)}
              style={{
                marginRight: "10px",
                backgroundColor: "#fff",
                color: "#E53935",
                border: "none",
                padding: "5px 12px",
                borderRadius: "5px",
                cursor: "pointer",
                fontWeight: "bold",
              }}
            >
              Profile
            </button>
            <button
              onClick={handleLogout}
              style={{
                backgroundColor: "#fff",
                color: "#E53935",
                border: "none",
                padding: "5px 12px",
                borderRadius: "5px",
                cursor: "pointer",
                fontWeight: "bold",
              }}
            >
              Logout
            </button>

            {showProfile && user && (
              <div
                style={{
                  position: "absolute",
                  top: "50px",
                  right: "20px",
                  background: "#fff",
                  border: "1px solid #ccc",
                  padding: "15px",
                  borderRadius: "10px",
                  color: "#333",
                  width: "220px",
                  textAlign: "left",
                  boxShadow: "0px 3px 6px rgba(0,0,0,0.1)",
                }}
              >
                <p>
                  <strong>Name:</strong> {user.name}
                </p>
                <p>
                  <strong>Email:</strong> {user.email}
                </p>
                <p>
                  <strong>Phone:</strong> {user.phone}
                </p>
                <p>
                  <strong>Blood Group:</strong> {user.bloodGroup}
                </p>
              </div>
            )}
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;





