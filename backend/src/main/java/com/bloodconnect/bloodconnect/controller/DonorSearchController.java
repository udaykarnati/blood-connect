package com.bloodconnect.bloodconnect.controller;

import com.bloodconnect.bloodconnect.dto.DonorDistanceDto;
import com.bloodconnect.bloodconnect.model.User;
import com.bloodconnect.bloodconnect.service.DonorSearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/donors")
@CrossOrigin(origins = "http://localhost:3000") 
public class DonorSearchController {

    private final DonorSearchService donorSearchService;

    public DonorSearchController(DonorSearchService donorSearchService) {
        this.donorSearchService = donorSearchService;
    }

 
    @GetMapping("/search/city")
    public List<User> searchByCity(@RequestParam String city) {
        return donorSearchService.searchDonorsByCity(city);
    }

    
    @GetMapping("/search/nearby")
    public List<DonorDistanceDto> searchNearby(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam double radiusKm) {
        return donorSearchService.searchNearbyDonors(lat, lng, radiusKm);
    }
}
