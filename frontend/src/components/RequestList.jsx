import React, { useEffect, useState } from "react";
import api from "../api/axios";

const RequestList = () => {
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);

  // Get logged-in user from localStorage
  const currentUser = JSON.parse(localStorage.getItem("user"));

  useEffect(() => {
    const fetchRequests = async () => {
      try {
        // Call the backend endpoint to get requests created by this user
        const response = await api.get(`/requests/user/${currentUser.email}`);
        setRequests(response.data);
      } catch (err) {
        console.error("Error fetching requests:", err);
      } finally {
        setLoading(false);
      }
    };

    if (currentUser?.email) {
      fetchRequests();
    }
  }, [currentUser?.email]);

  if (loading) {
    return <p style={{ textAlign: "center", marginTop: "20px" }}>Loading your requests...</p>;
  }

  if (requests.length === 0) {
    return <p style={{ textAlign: "center", color: "#d32f2f" }}>You have not created any requests yet.</p>;
  }

  return (
    <div style={{ maxWidth: "800px", margin: "20px auto" }}>
      {requests.map((req) => (
        <div
          key={req.id}
          style={{
            padding: "15px",
            marginBottom: "15px",
            border: "1px solid #ccc",
            borderRadius: "10px",
            background: "#fff0f0",
          }}
        >
          <p><strong>Recipient Name:</strong> {req.recipientName}</p>
          <p><strong>Recipient Phone:</strong> {req.recipientPhone}</p>

          <p><strong>Guardian Name:</strong> {req.creatorName}</p>
          <p><strong>Contact:</strong> {req.creatorPhone}</p>

          <p><strong>Blood Group:</strong> {req.bloodGroup}</p>
          <p><strong>Quantity:</strong> {req.quantity}</p>
          <p style={{ color: "#d32f2f", fontWeight: "bold" }}>
            Status: Request created successfully. Alerts sent to matching donors!
          </p>
        </div>
      ))}
    </div>
  );
};

export default RequestList;

