import React from "react";
import RequestForm from "../components/RequestForm"; // your existing form component

const RequestBlood = () => {
  return (
    <div
      style={{
        minHeight: "80vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "flex-start",
        padding: "40px 20px",
        background: "linear-gradient(135deg, #ffe5e0 0%, #f5f5f5 100%)",
      }}
    >
      <div
        style={{
          maxWidth: "600px",
          width: "100%",
          backgroundColor: "#fff",
          padding: "30px",
          borderRadius: "10px",
          boxShadow: "0px 5px 15px rgba(0,0,0,0.1)",
        }}
      >
        <h1
          style={{
            textAlign: "center",
            color: "#E53935",
            marginBottom: "25px",
            fontSize: "1.8rem",
          }}
        >
          Create Blood Request
        </h1>
        <RequestForm />
      </div>
    </div>
  );
};

export default RequestBlood;



