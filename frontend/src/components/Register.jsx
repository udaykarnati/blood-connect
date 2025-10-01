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
  });

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    try {
      const response = await api.post("/auth/register", form);

      // Show success message
      setSuccess("Registration successful! Redirecting to login...");

      // Optional: save user info in localStorage for demo
      localStorage.setItem("user", JSON.stringify(response.data));

      setTimeout(() => {
        navigate("/login");
      }, 2000);
    } catch (err) {
      console.error(err);
      setError(err.response?.data?.message || "Registration failed. Email may already be in use.");
    }
  };

  return (
    <div style={{ maxWidth: "400px", margin: "50px auto", padding: "20px", border: "1px solid #ccc", borderRadius: "8px" }}>
      <h2 style={{ textAlign: "center" }}>Register</h2>

      {error && <p style={{ color: "red", textAlign: "center" }}>{error}</p>}
      {success && <p style={{ color: "green", textAlign: "center" }}>{success}</p>}

      <form onSubmit={handleRegister}>
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
              placeholder={field === "bloodGroup" ? "e.g., A+, B-, O+" : ""}
            />
          </div>
        ))}

        <button
          type="submit"
          style={{ width: "100%", padding: "10px", background: "#1976d2", color: "white", border: "none", borderRadius: "4px" }}
        >
          Register
        </button>
      </form>
    </div>
  );
};

export default Register;

