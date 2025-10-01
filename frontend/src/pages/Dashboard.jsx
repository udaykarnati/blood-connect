import React, { useEffect, useState } from "react";
import api from "../api/axios";

const Dashboard = () => {
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const user = JSON.parse(localStorage.getItem("user"));

  useEffect(() => {
    const fetchRequests = async () => {
      if (!user) return;

      try {
        // Fetch requests created by the logged-in user's email
        const response = await api.get(`/requests/user/${user.email}`);
        setRequests(response.data);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchRequests();
  }, [user.email]);

  if (loading) {
    return <p style={{ textAlign: "center", marginTop: "20px" }}>Loading your requests...</p>;
  }

  return (
    <div
      style={{
        padding: "20px",
        background: "linear-gradient(135deg, #ffe5e0 0%, #f5f5f5 100%)",
        minHeight: "80vh",
      }}
    >
      <h2 style={{ textAlign: "center", color: "#E53935", marginBottom: "20px" }}>
        Your Blood Requests
      </h2>

      {requests.length === 0 ? (
        <p style={{ textAlign: "center" }}>You have not created any requests yet.</p>
      ) : (
        requests.map((req) => (
          <div
            key={req.id}
            style={{
              backgroundColor: "#fff",
              padding: "15px",
              borderRadius: "8px",
              boxShadow: "0px 3px 6px rgba(0,0,0,0.1)",
              marginBottom: "15px",
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
        ))
      )}
    </div>
  );
};

export default Dashboard;
