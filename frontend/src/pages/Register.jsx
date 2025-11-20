
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

const Register = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    name: "",
    email: "",
    phone: "",
    bloodGroup: "",
    password: "",
    city: "",
    isDonor: false,
    latitude: null,
    longitude: null,
  });

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleUseMyLocation = () => {
    if (!navigator.geolocation) {
      alert("Geolocation is not supported by your browser");
      return;
    }

    navigator.geolocation.getCurrentPosition(
      (pos) => {
        const { latitude, longitude } = pos.coords;
        setForm((prev) => ({
          ...prev,
          latitude,
          longitude,
        }));
        alert("Location captured successfully");
      },
      (err) => {
        console.error(err);
        alert("Unable to get your location. Please allow location access.");
      }
    );
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    try {
      const response = await api.post("/auth/register", form);

      setSuccess("Registration successful! Redirecting to login...");

      localStorage.setItem("user", JSON.stringify(response.data));

      setTimeout(() => {
        navigate("/login");
      }, 2000);
    } catch (err) {
      console.error(err);
      setError(
        err.response?.data?.message ||
          "Registration failed. Email may already be in use."
      );
    }
  };

  return (
    <div
      style={{
        maxWidth: "400px",
        margin: "50px auto",
        padding: "20px",
        border: "1px solid #ccc",
        borderRadius: "8px",
      }}
    >
      <h2 style={{ textAlign: "center" }}>Register</h2>

      {error && <p style={{ color: "red", textAlign: "center" }}>{error}</p>}
      {success && (
        <p style={{ color: "green", textAlign: "center" }}>{success}</p>
      )}

      <form onSubmit={handleRegister}>
        {/* Existing fields */}
        {["name", "email", "phone", "bloodGroup", "password"].map((field) => (
          <div key={field} style={{ marginBottom: "15px" }}>
            <label style={{ textTransform: "capitalize" }}>{field}:</label>
            <input
              type={field === "password" ? "password" : "text"}
              name={field}
              value={form[field]}
              onChange={handleChange}
              required
              style={{ width: "100%", padding: "8px" }}
              placeholder={
                field === "bloodGroup" ? "e.g., A+, B-, O+" : ""
              }
            />
          </div>
        ))}

        {/* New: City */}
        <div style={{ marginBottom: "15px" }}>
          <label>City:</label>
          <input
            type="text"
            name="city"
            value={form.city}
            onChange={handleChange}
            required
            style={{ width: "100%", padding: "8px" }}
            placeholder="e.g., Hyderabad"
          />
        </div>

        {/* New: Donor toggle */}
        <div style={{ marginBottom: "15px" }}>
          <label>
            <input
              type="checkbox"
              name="isDonor"
              checked={form.isDonor}
              onChange={handleChange}
              style={{ marginRight: "8px" }}
            />
            Register as Blood Donor
          </label>
        </div>

        {/* New: Location capture */}
        <div style={{ marginBottom: "15px" }}>
          <button
            type="button"
            onClick={handleUseMyLocation}
            style={{
              padding: "8px",
              background: "#555",
              color: "#fff",
              border: "none",
              borderRadius: "4px",
              width: "100%",
            }}
          >
            Use My Current Location
          </button>
          {form.latitude && form.longitude && (
            <p style={{ fontSize: "0.8rem", marginTop: "8px" }}>
              Location captured: {form.latitude.toFixed(4)},{" "}
              {form.longitude.toFixed(4)}
            </p>
          )}
        </div>

        <button
          type="submit"
          style={{
            width: "100%",
            padding: "10px",
            background: "#1976d2",
            color: "white",
            border: "none",
            borderRadius: "4px",
          }}
        >
          Register
        </button>
      </form>
    </div>
  );
};

export default Register;


