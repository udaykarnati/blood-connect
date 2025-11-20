import React, { useState } from "react";
import axios from "axios";

const API_BASE = "http://localhost:8080/api/users/donors";

const DonorSearch = () => {
  const [mode, setMode] = useState("city"); // 'city' or 'distance'
  const [city, setCity] = useState("");
  const [radius, setRadius] = useState(20); // km
  const [position, setPosition] = useState(null); // { lat, lng }
  const [donors, setDonors] = useState([]);
  const [loading, setLoading] = useState(false);

  const handleCitySearch = async () => {
    if (!city.trim()) {
      alert("Please enter a city");
      return;
    }
    try {
      setLoading(true);
      const res = await axios.get(`${API_BASE}/search/city`, {
        params: { city },
      });
      setDonors(res.data);
    } catch (err) {
      console.error(err);
      alert("Error searching by city");
    } finally {
      setLoading(false);
    }
  };

  const handleNearbySearch = async () => {
    if (!position) {
      alert("Please click 'Use my location' first");
      return;
    }
    try {
      setLoading(true);
      const res = await axios.get(`${API_BASE}/search/nearby`, {
        params: {
          lat: position.lat,
          lng: position.lng,
          radiusKm: radius,
        },
      });
      setDonors(res.data);
    } catch (err) {
      console.error(err);
      alert("Error searching nearby donors");
    } finally {
      setLoading(false);
    }
  };

  const handleUseMyLocation = () => {
    if (!navigator.geolocation) {
      alert("Geolocation is not supported by your browser");
      return;
    }

    navigator.geolocation.getCurrentPosition(
      (pos) => {
        const { latitude, longitude } = pos.coords;
        setPosition({ lat: latitude, lng: longitude });
        alert("Location captured successfully");
      },
      (err) => {
        console.error(err);
        alert("Unable to get your location. Please allow location access.");
      }
    );
  };

  return (
    <div style={{ maxWidth: "900px", margin: "2rem auto", padding: "1rem" }}>
      <h2>Find Blood Donors</h2>

      {/* Mode Selector */}
      <div style={{ marginBottom: "1rem" }}>
        <label style={{ marginRight: "1rem" }}>
          <input
            type="radio"
            value="city"
            checked={mode === "city"}
            onChange={() => setMode("city")}
          />{" "}
          Search by City
        </label>
        <label>
          <input
            type="radio"
            value="distance"
            checked={mode === "distance"}
            onChange={() => setMode("distance")}
          />{" "}
          Search by Distance (Near Me)
        </label>
      </div>

      {/* City-based UI */}
      {mode === "city" && (
        <div style={{ marginBottom: "1.5rem" }}>
          <input
            type="text"
            placeholder="Enter city (e.g., Hyderabad)"
            value={city}
            onChange={(e) => setCity(e.target.value)}
            style={{ padding: "0.5rem", width: "60%", marginRight: "0.5rem" }}
          />
          <button onClick={handleCitySearch}>Search</button>
        </div>
      )}

      {/* Distance-based UI */}
      {mode === "distance" && (
        <div style={{ marginBottom: "1.5rem" }}>
          <div style={{ marginBottom: "0.5rem" }}>
            <button onClick={handleUseMyLocation}>Use My Current Location</button>
            {position && (
              <span style={{ marginLeft: "1rem", fontSize: "0.9rem" }}>
                Selected: {position.lat.toFixed(4)}, {position.lng.toFixed(4)}
              </span>
            )}
          </div>

          <div style={{ marginBottom: "0.5rem" }}>
            <label>
              Radius (km):{" "}
              <input
                type="number"
                min="1"
                max="60"
                value={radius}
                onChange={(e) => setRadius(Number(e.target.value))}
                style={{ width: "80px", marginRight: "0.5rem" }}
              />
            </label>
            <small>(Max 60 km)</small>
          </div>

          <button onClick={handleNearbySearch}>Search Nearby Donors</button>
      </div>
      )}

      {/* Loading */}
      {loading && <p>Searching donors...</p>}

      {/* Results */}
      <div style={{ marginTop: "2rem" }}>
        <h3>Results ({donors.length})</h3>
        {donors.length === 0 && !loading && <p>No donors found.</p>}

        {donors.map((d) => (
          <div
            key={d.id}
            style={{
              border: "1px solid #ccc",
              padding: "0.75rem",
              marginBottom: "0.75rem",
              borderRadius: "4px",
            }}
          >
            <strong>{d.name}</strong> – {d.bloodGroup}
            <div>City: {d.city}</div>
            {d.distance !== undefined && d.distance !== null && (
              <div>Distance: {d.distance.toFixed(1)} km</div>
            )}
            {d.phone && <div>Phone: {d.phone}</div>}
            {d.email && <div>Email: {d.email}</div>}
          </div>
        ))}
      </div>
    </div>
  );
};

export default DonorSearch;