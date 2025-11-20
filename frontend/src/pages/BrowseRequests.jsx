import React, { useEffect, useState } from "react";
import api from "../api/axios";

const BrowseRequests = () => {
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [acceptingId, setAcceptingId] = useState(null);
  const user = JSON.parse(localStorage.getItem("user"));

  useEffect(() => {
    const fetchRequests = async () => {
      if (!user || !user.email) {
        setLoading(false);
        return;
      }

      try {
        const response = await api.get("/requests/all");

        // Show only:
        // - PENDING requests
        // - Not created by this user
        const filtered = response.data.filter(
          (req) =>
            req.status === "PENDING" &&
            req.creatorEmail &&
            req.creatorEmail.toLowerCase() !== user.email.toLowerCase()
        );

        setRequests(filtered);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchRequests();
  }, [user?.email]);

  if (!user) {
    return (
      <p style={{ textAlign: "center", marginTop: "20px" }}>
        Please log in as a donor to view and accept requests.
      </p>
    );
  }

  if (!user.isDonor) {
    return (
      <p style={{ textAlign: "center", marginTop: "20px" }}>
        Only registered donors can browse and accept blood requests.
      </p>
    );
  }

  const handleAccept = async (requestId) => {
    if (!window.confirm("Are you sure you want to accept this request?")) {
      return;
    }

    try {
      setAcceptingId(requestId);

      await api.post(`/requests/${requestId}/accept`, null, {
        params: { donorEmail: user.email },
      });

      alert("You have accepted this request. The creator will be notified.");

      // Remove accepted request from UI
      setRequests((prev) => prev.filter((r) => r.id !== requestId));
    } catch (err) {
      console.error(err);
      alert(
        err.response?.data?.message ||
          "Failed to accept this request. Please try again."
      );
    } finally {
      setAcceptingId(null);
    }
  };

  if (loading) {
    return (
      <p style={{ textAlign: "center", marginTop: "20px" }}>
        Loading active requests...
      </p>
    );
  }

  return (
    <div
      style={{
        padding: "20px",
        background: "linear-gradient(135deg, #e3f2fd 0%, #f5f5f5 100%)",
        minHeight: "80vh",
      }}
    >
      <h2
        style={{
          textAlign: "center",
          color: "#1565c0",
          marginBottom: "20px",
        }}
      >
        Active Blood Requests (For Donors)
      </h2>

      {requests.length === 0 ? (
        <p style={{ textAlign: "center" }}>
          No pending requests right now, or all visible requests are created by
          you.
        </p>
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
            <p>
              <strong>Recipient Name:</strong> {req.recipientName}
            </p>
            <p>
              <strong>Recipient Phone:</strong> {req.recipientPhone}
            </p>

            <p>
              <strong>Guardian Name:</strong> {req.creatorName}
            </p>
            <p>
              <strong>Guardian Contact:</strong> {req.creatorPhone}
            </p>

            <p>
              <strong>Blood Group:</strong> {req.bloodGroup}
            </p>
            <p>
              <strong>Quantity:</strong> {req.quantity}</p>

            <button
              onClick={() => handleAccept(req.id)}
              disabled={acceptingId === req.id}
              style={{
                marginTop: "10px",
                backgroundColor: "#1565c0",
                color: "#fff",
                border: "none",
                padding: "8px 16px",
                borderRadius: "5px",
                cursor: "pointer",
                fontWeight: "bold",
              }}
            >
              {acceptingId === req.id ? "Accepting..." : "Accept Request"}
            </button>
          </div>
        ))
      )}
    </div>
  );
};

export default BrowseRequests;
