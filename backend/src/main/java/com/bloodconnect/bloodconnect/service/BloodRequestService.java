package com.bloodconnect.bloodconnect.service;

import com.bloodconnect.bloodconnect.model.BloodRequest;
import java.util.List;

public interface BloodRequestService {

    BloodRequest createRequest(BloodRequest request);

    List<BloodRequest> getAllRequests();

    List<BloodRequest> getRequestsByBloodGroup(String bloodGroup);

    List<BloodRequest> getRequestsByCreator(String email);

    // 🔹 New: donor accepts a specific request
    BloodRequest acceptRequest(Long requestId, String donorEmail);
}


