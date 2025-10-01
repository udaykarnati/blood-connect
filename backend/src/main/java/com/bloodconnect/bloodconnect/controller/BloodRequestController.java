package com.bloodconnect.bloodconnect.controller;

import com.bloodconnect.bloodconnect.model.BloodRequest;
import com.bloodconnect.bloodconnect.service.BloodRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/requests")
public class BloodRequestController {

    private final BloodRequestService bloodRequestService;

    public BloodRequestController(BloodRequestService bloodRequestService){
        this.bloodRequestService=bloodRequestService;
    }

    @PostMapping("/create")
    public ResponseEntity<BloodRequest> createRequest(@RequestBody BloodRequest request){
        BloodRequest savedRequest = bloodRequestService.createRequest(request);
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


    // Get requests by blood group
    @GetMapping("/blood-group/{bloodGroup}")
    public ResponseEntity<List<BloodRequest>> getRequestsByBloodGroup(@PathVariable String bloodGroup) {
        List<BloodRequest> requests = bloodRequestService.getRequestsByBloodGroup(bloodGroup);
        return ResponseEntity.ok(requests);
    }


}
