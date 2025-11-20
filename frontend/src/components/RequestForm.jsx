import React, { useState } from "react";
import api from "../api/axios";

const RequestForm = () => {
  const [recipientName, setRecipientName] = useState("");
  const [recipientEmail, setRecipientEmail] = useState("");
  const [recipientPhone, setRecipientPhone] = useState("");

  const [guardianName, setGuardianName] = useState("");
  const [guardianEmail, setGuardianEmail] = useState("");
  const [guardianPhone, setGuardianPhone] = useState("");

  const [bloodGroup, setBloodGroup] = useState("");
  const [quantity, setQuantity] = useState("");

  // 🔹 New: search mode + location info for matching
  const [mode, setMode] = useState("CITY"); // "CITY" or "DISTANCE"
  const [city, setCity] = useState("");
  const [radiusKm, setRadiusKm] = useState(20);
  const [latitude, setLatitude] = useState(null);
  const [longitude, setLongitude] = useState(null);

  const [statusMessage, setStatusMessage] = useState("");

  const handleUseMyLocation = () => {
    if (!navigator.geolocation) {
      alert("Geolocation is not supported by your browser");
      return;
    }

    navigator.geolocation.getCurrentPosition(
      (pos) => {
        const { latitude, longitude } = pos.coords;
        setLatitude(latitude);
        setLongitude(longitude);
        alert("Location captured successfully for this request.");
      },
      (err) => {
        console.error(err);
        alert("Unable to get your location. Please allow location access.");
      }
    );
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setStatusMessage("");

    // basic validation for mode-specific fields
    if (mode === "CITY" && !city.trim()) {
      alert("Please enter a city for city-based matching.");
      return;
    }
    if (mode === "DISTANCE" && (latitude == null || longitude == null)) {
      alert("Please click 'Use My Location' for distance-based matching.");
      return;
    }

    const requestData = {
      recipientName,
      recipientEmail,
      recipientPhone,
      creatorName: guardianName,
      creatorEmail: guardianEmail,
      creatorPhone: guardianPhone,
      bloodGroup,
      quantity,

      // 🔹 New fields sent to backend
      mode,                               // "CITY" or "DISTANCE"
      city: mode === "CITY" ? city : null,
      latitude: mode === "DISTANCE" ? latitude : null,
      longitude: mode === "DISTANCE" ? longitude : null,
      radiusKm: mode === "DISTANCE" ? radiusKm : null,
    };

    try {
      await api.post("/requests/create", requestData);
      setStatusMessage(
        "Request created successfully! Alerts will be sent to matching donors."
      );

      // Clear form
      setRecipientName("");
      setRecipientEmail("");
      setRecipientPhone("");
      setGuardianName("");
      setGuardianEmail("");
      setGuardianPhone("");
      setBloodGroup("");
      setQuantity("");
      setCity("");
      setLatitude(null);
      setLongitude(null);
      setRadiusKm(20);
      setMode("CITY");
    } catch (err) {
      console.error(err);
      setStatusMessage("Failed to create request.");
    }
  };

  return (
    <div
      style={{
        maxWidth: "600px",
        margin: "20px auto",
        padding: "20px",
        border: "1px solid #ccc",
        borderRadius: "10px",
        background: "#ffe5e5",
      }}
    >
      <h2 style={{ textAlign: "center", color: "#d32f2f" }}>
        Create Blood Request
      </h2>

      {statusMessage && (
        <p
          style={{
            textAlign: "center",
            color: "#d32f2f",
            fontWeight: "bold",
          }}
        >
          {statusMessage}
        </p>
      )}

      <form
        onSubmit={handleSubmit}
        style={{ display: "flex", flexDirection: "column", gap: "15px" }}
      >
        <h3>Recipient Details</h3>
        <input
          type="text"
          placeholder="Recipient Name"
          value={recipientName}
          onChange={(e) => setRecipientName(e.target.value)}
          required
        />
        <input
          type="email"
          placeholder="Recipient Email"
          value={recipientEmail}
          onChange={(e) => setRecipientEmail(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Recipient Phone"
          value={recipientPhone}
          onChange={(e) => setRecipientPhone(e.target.value)}
          required
        />

        <h3>Guardian Details</h3>
        <input
          type="text"
          placeholder="Guardian Name"
          value={guardianName}
          onChange={(e) => setGuardianName(e.target.value)}
          required
        />
        <input
          type="email"
          placeholder="Guardian Email"
          value={guardianEmail}
          onChange={(e) => setGuardianEmail(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Guardian Phone"
          value={guardianPhone}
          onChange={(e) => setGuardianPhone(e.target.value)}
          required
        />

        <h3>Blood Request</h3>
        <input
          type="text"
          placeholder="Blood Group"
          value={bloodGroup}
          onChange={(e) => setBloodGroup(e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Quantity (in units)"
          value={quantity}
          onChange={(e) => setQuantity(e.target.value)}
          required
        />

        {/* 🔹 New: Search Mode */}
        <h3>Donor Matching Mode</h3>
        <div>
          <label style={{ marginRight: "10px" }}>
            <input
              type="radio"
              value="CITY"
              checked={mode === "CITY"}
              onChange={(e) => setMode(e.target.value)}
            />{" "}
            City-based
          </label>
          <label>
            <input
              type="radio"
              value="DISTANCE"
              checked={mode === "DISTANCE"}
              onChange={(e) => setMode(e.target.value)}
            />{" "}
            Distance-based (Near Me)
          </label>
        </div>

        {/* 🔹 If CITY mode: show city input */}
        {mode === "CITY" && (
          <div>
            <input
              type="text"
              placeholder="City for matching (e.g., Hyderabad)"
              value={city}
              onChange={(e) => setCity(e.target.value)}
              required
            />
          </div>
        )}

        {/* 🔹 If DISTANCE mode: show radius + location */}
        {mode === "DISTANCE" && (
          <div
            style={{
              padding: "10px",
              borderRadius: "8px",
              background: "#ffd6d6",
            }}
          >
            <button
              type="button"
              onClick={handleUseMyLocation}
              style={{
                background: "#555",
                color: "#fff",
                padding: "8px",
                border: "none",
                borderRadius: "4px",
                cursor: "pointer",
                width: "100%",
                marginBottom: "8px",
              }}
            >
              Use My Current Location
            </button>
            {latitude && longitude && (
              <p style={{ fontSize: "0.8rem" }}>
                Location captured: {latitude.toFixed(4)}, {longitude.toFixed(4)}
              </p>
            )}

            <label>
              Radius (in km, max 60):
              <input
                type="number"
                min="1"
                max="60"
                value={radiusKm}
                onChange={(e) => setRadiusKm(Number(e.target.value))}
                style={{ width: "100%", padding: "6px", marginTop: "4px" }}
              />
            </label>
          </div>
        )}

        <button
          type="submit"
          style={{
            background: "#d32f2f",
            color: "#fff",
            padding: "10px",
            border: "none",
            borderRadius: "5px",
            cursor: "pointer",
          }}
        >
          Create Request
        </button>
      </form>
    </div>
  );
};

export default RequestForm;



