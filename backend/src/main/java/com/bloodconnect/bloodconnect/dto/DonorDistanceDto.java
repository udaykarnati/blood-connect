package com.bloodconnect.bloodconnect.dto;

public class DonorDistanceDto {

    private Long id;
    private String name;
    private String bloodGroup;
    private String city;
    private Double latitude;
    private Double longitude;
    private Double distance; // in km

    public DonorDistanceDto() {}

    public DonorDistanceDto(Long id, String name, String bloodGroup, String city,
                            Double latitude, Double longitude, Double distance) {
        this.id = id;
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getDistance() { return distance; }
    public void setDistance(Double distance) { this.distance = distance; }
}

