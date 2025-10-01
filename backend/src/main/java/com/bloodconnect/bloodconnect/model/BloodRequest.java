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

    private LocalDateTime requestTime;
    @Column(nullable = false)
    private String creatorName;
    @Column(nullable = false)
    private String creatorEmail;
    @Column(nullable = false)
    private String creatorPhone;



    public BloodRequest() {}

    public BloodRequest(String bloodGroup, int quantity, String recipientName, String recipientEmail, String recipientPhone,String creatorName,String creatorEmail,String creatorPhone) {
        this.bloodGroup = bloodGroup;
        this.quantity = quantity;
        this.recipientName = recipientName;
        this.recipientEmail = recipientEmail;
        this.recipientPhone = recipientPhone;

        this.creatorName = creatorName;
        this.creatorEmail = creatorEmail;
        this.creatorPhone=creatorPhone;
        this.requestTime = LocalDateTime.now();
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
}

