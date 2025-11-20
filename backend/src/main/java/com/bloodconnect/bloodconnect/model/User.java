package com.bloodconnect.bloodconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;
    private String phone;

    @Column(nullable = false)
    private String bloodGroup;

    // NEW FIELDS -----------------------------

    // For city-based search
    private String city;

    // For distance-based search (auto-filled via Google Maps / Geolocation)
    private Double latitude;
    private Double longitude;

    // To identify if the user registered as a donor
    private Boolean isDonor = false;

    // To check if donor is available to donate
    private Boolean available = true;

    // ----------------------------------------

    public User() {}

    public User(String email, String password, String name, String phone, String bloodGroup) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
    }

    // GETTERS & SETTERS ----------------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Boolean getIsDonor() { return isDonor; }
    public void setIsDonor(Boolean isDonor) { this.isDonor = isDonor; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
}
