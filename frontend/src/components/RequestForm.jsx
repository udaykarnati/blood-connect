import React, { useState } from "react";
import api from "../api/axios";

const RequestForm = () => {
  const [recipientName, setRecipientName] = useState("");
  const [recipientEmail, setRecipientEmail] = useState("");
  const [recipientPhone, setRecipientPhone] = useState("");

  const [guardianName, setGuardianName] = useState(""); // ✅ guardian's name
  const [guardianEmail, setGuardianEmail] = useState(""); // ✅ guardian's email
  const [guardianPhone, setGuardianPhone] = useState(""); // ✅ guardian's phone

  const [bloodGroup, setBloodGroup] = useState("");
  const [quantity, setQuantity] = useState("");
  const [statusMessage, setStatusMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const requestData = {
      recipientName,
      recipientEmail,
      recipientPhone,
      creatorName: guardianName,
      creatorEmail: guardianEmail,
      creatorPhone: guardianPhone,
      bloodGroup,
      quantity,
    };

    try {
      await api.post("/requests/create", requestData);
      setStatusMessage("Request created successfully! Alerts sent to matching donors.");

      // Clear form
      setRecipientName("");
      setRecipientEmail("");
      setRecipientPhone("");
      setGuardianName("");
      setGuardianEmail("");
      setGuardianPhone("");
      setBloodGroup("");
      setQuantity("");
    } catch (err) {
      console.error(err);
      setStatusMessage("Failed to create request.");
    }
  };

  return (
    <div style={{ maxWidth: "600px", margin: "20px auto", padding: "20px", border: "1px solid #ccc", borderRadius: "10px", background: "#ffe5e5" }}>
      <h2 style={{ textAlign: "center", color: "#d32f2f" }}>Create Blood Request</h2>

      {statusMessage && <p style={{ textAlign: "center", color: "#d32f2f", fontWeight: "bold" }}>{statusMessage}</p>}

      <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "15px" }}>
        <h3>Recipient Details</h3>
        <input type="text" placeholder="Recipient Name" value={recipientName} onChange={(e) => setRecipientName(e.target.value)} required />
        <input type="email" placeholder="Recipient Email" value={recipientEmail} onChange={(e) => setRecipientEmail(e.target.value)} required />
        <input type="text" placeholder="Recipient Phone" value={recipientPhone} onChange={(e) => setRecipientPhone(e.target.value)} required />

        <h3>Guardian Details</h3>
        <input type="text" placeholder="Guardian Name" value={guardianName} onChange={(e) => setGuardianName(e.target.value)} required />
        <input type="email" placeholder="Guardian Email" value={guardianEmail} onChange={(e) => setGuardianEmail(e.target.value)} required />
        <input type="text" placeholder="Guardian Phone" value={guardianPhone} onChange={(e) => setGuardianPhone(e.target.value)} required />

        <h3>Blood Request</h3>
        <input type="text" placeholder="Blood Group" value={bloodGroup} onChange={(e) => setBloodGroup(e.target.value)} required />
        <input type="number" placeholder="Quantity (in units)" value={quantity} onChange={(e) => setQuantity(e.target.value)} required />

        <button type="submit" style={{ background: "#d32f2f", color: "#fff", padding: "10px", border: "none", borderRadius: "5px", cursor: "pointer" }}>
          Create Request
        </button>
      </form>
    </div>
  );
};

export default RequestForm;



