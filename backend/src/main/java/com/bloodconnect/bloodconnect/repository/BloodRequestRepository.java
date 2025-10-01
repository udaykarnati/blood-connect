package com.bloodconnect.bloodconnect.repository;

import com.bloodconnect.bloodconnect.model.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloodRequestRepository extends JpaRepository<BloodRequest,Long> {
    List<BloodRequest> findByBloodGroup(String bloodGroup);
    List<BloodRequest> findByCreatorEmail(String email);

}
