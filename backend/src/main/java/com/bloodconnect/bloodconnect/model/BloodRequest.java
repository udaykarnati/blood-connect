package com.bloodconnect.bloodconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="blood_requests")
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bloodGroup;

    @Column(nullable = false)
    private int quantity;

    private String recipientName;
    private String recipientEmail;
    private String recipientPhone;

    @Column(nullable = false)
    private String creatorName;

    @Column(nullable = false)
    private String creatorEmail;

    @Column(nullable = false)
    private String creatorPhone;

    private LocalDateTime requestTime;

    // 🔥 NEW: request status (PENDING, ACCEPTED, etc.)
    @Column(nullable = false)
    private String status = "PENDING";

    // 🔥 NEW: accepted donor info (set when a donor accepts this request)
    private Long acceptedDonorId;
    private String acceptedDonorName;
    private String acceptedDonorPhone;

    // 🔥 EXISTING FIELDS FOR DONOR MATCHING

    @Column(nullable = false)
    private String mode;  // "CITY" or "DISTANCE"

    private String city;  // used only for CITY mode

    private Double latitude;  // used only for DISTANCE mode
    private Double longitude; // used only for DISTANCE mode

    private Double radiusKm;  // used only for DISTANCE mode


    public BloodRequest() {
        this.requestTime = LocalDateTime.now();
        this.status = "PENDING";
    }

    // Constructor (optional)
    public BloodRequest(String bloodGroup, int quantity, String recipientName, String recipientEmail, String recipientPhone,
                        String creatorName, String creatorEmail, String creatorPhone,
                        String mode, String city, Double latitude, Double longitude, Double radiusKm) {

        this.bloodGroup = bloodGroup;
        this.quantity = quantity;
        this.recipientName = recipientName;
        this.recipientEmail = recipientEmail;
        this.recipientPhone = recipientPhone;

        this.creatorName = creatorName;
        this.creatorEmail = creatorEmail;
        this.creatorPhone = creatorPhone;

        this.mode = mode;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radiusKm = radiusKm;

        this.requestTime = LocalDateTime.now();
        this.status = "PENDING";
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    public String getRecipientEmail() { return recipientEmail; }
    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }

    public String getRecipientPhone() { return recipientPhone; }
    public void setRecipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; }

    public String getCreatorName() { return creatorName; }
    public void setCreatorName(String creatorName) { this.creatorName = creatorName; }

    public String getCreatorEmail() { return creatorEmail; }
    public void setCreatorEmail(String creatorEmail) { this.creatorEmail = creatorEmail; }

    public String getCreatorPhone() { return creatorPhone; }
    public void setCreatorPhone(String creatorPhone) { this.creatorPhone = creatorPhone; }

    public LocalDateTime getRequestTime() { return requestTime; }
    public void setRequestTime(LocalDateTime requestTime) { this.requestTime = requestTime; }

    // NEW: status
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // NEW: accepted donor fields
    public Long getAcceptedDonorId() { return acceptedDonorId; }
    public void setAcceptedDonorId(Long acceptedDonorId) { this.acceptedDonorId = acceptedDonorId; }

    public String getAcceptedDonorName() { return acceptedDonorName; }
    public void setAcceptedDonorName(String acceptedDonorName) { this.acceptedDonorName = acceptedDonorName; }

    public String getAcceptedDonorPhone() { return acceptedDonorPhone; }
    public void setAcceptedDonorPhone(String acceptedDonorPhone) { this.acceptedDonorPhone = acceptedDonorPhone; }

    // EXISTING donor-matching fields
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getRadiusKm() { return radiusKm; }
    public void setRadiusKm(Double radiusKm) { this.radiusKm = radiusKm; }
}


