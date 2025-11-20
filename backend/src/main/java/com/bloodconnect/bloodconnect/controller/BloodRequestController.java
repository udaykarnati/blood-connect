package com.bloodconnect.bloodconnect.controller;

import com.bloodconnect.bloodconnect.dto.DonorDistanceDto;
import com.bloodconnect.bloodconnect.model.BloodRequest;
import com.bloodconnect.bloodconnect.model.User;
import com.bloodconnect.bloodconnect.service.BloodRequestService;
import com.bloodconnect.bloodconnect.service.DonorSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/requests")
@CrossOrigin(origins = "http://localhost:3000")
public class BloodRequestController {

    private final BloodRequestService bloodRequestService;
    private final DonorSearchService donorSearchService;

    public BloodRequestController(BloodRequestService bloodRequestService,
                                  DonorSearchService donorSearchService) {
        this.bloodRequestService = bloodRequestService;
        this.donorSearchService = donorSearchService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRequest(@RequestBody BloodRequest request){

        // Save request to DB + send alerts (handled inside service)
        BloodRequest savedRequest = bloodRequestService.createRequest(request);

        // Optional extra logging here (not sending alerts again)
        if ("CITY".equalsIgnoreCase(request.getMode())) {
            List<User> donors = donorSearchService.searchDonorsByCity(request.getCity());
            System.out.println("Matched donors by CITY (controller log): " + donors.size());
        }
        else if ("DISTANCE".equalsIgnoreCase(request.getMode())) {
            List<DonorDistanceDto> donors = donorSearchService.searchNearbyDonors(
                    request.getLatitude(),
                    request.getLongitude(),
                    request.getRadiusKm()
            );
            System.out.println("Matched donors by DISTANCE (controller log): " + donors.size());
        }

        return ResponseEntity.ok(savedRequest);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BloodRequest>> getAllRequests(){
        return ResponseEntity.ok(bloodRequestService.getAllRequests());
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<BloodRequest>> getRequestsByCreator(@PathVariable String email) {
        return ResponseEntity.ok(bloodRequestService.getRequestsByCreator(email));
    }

    @GetMapping("/blood-group/{bloodGroup}")
    public ResponseEntity<List<BloodRequest>> getRequestsByBloodGroup(@PathVariable String bloodGroup) {
        List<BloodRequest> requests = bloodRequestService.getRequestsByBloodGroup(bloodGroup);
        return ResponseEntity.ok(requests);
    }

    // 🔥 NEW: Donor accepts a request
    @PostMapping("/{id}/accept")
    public ResponseEntity<BloodRequest> acceptRequest(
            @PathVariable Long id,
            @RequestParam String donorEmail
    ) {
        BloodRequest updated = bloodRequestService.acceptRequest(id, donorEmail);
        return ResponseEntity.ok(updated);
    }
}


