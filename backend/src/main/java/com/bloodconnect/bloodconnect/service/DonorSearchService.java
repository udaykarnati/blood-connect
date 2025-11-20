package com.bloodconnect.bloodconnect.service;

import com.bloodconnect.bloodconnect.dto.DonorDistanceDto;
import com.bloodconnect.bloodconnect.model.User;
import com.bloodconnect.bloodconnect.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DonorSearchService {

    private final UserRepository userRepository;

    public DonorSearchService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 🔹 City-based
    public List<User> searchDonorsByCity(String city) {
        return userRepository.findByCityIgnoreCaseAndIsDonorTrueAndAvailableTrue(city);
    }

    // 🔹 Distance-based
    public List<DonorDistanceDto> searchNearbyDonors(double lat, double lng, double radiusKm) {
        if (radiusKm > 60) radiusKm = 60; // cap at 60km

        List<Object[]> rows = userRepository.findNearbyDonors(lat, lng, radiusKm);
        List<DonorDistanceDto> result = new ArrayList<>();

        for (Object[] row : rows) {
            Long id          = ((Number) row[0]).longValue();
            String name      = (String) row[1];
            String blood     = (String) row[2];
            String city      = (String) row[3];
            Double latitude  = row[4] != null ? ((Number) row[4]).doubleValue() : null;
            Double longitude = row[5] != null ? ((Number) row[5]).doubleValue() : null;
            Double distance  = row[6] != null ? ((Number) row[6]).doubleValue() : null;

            result.add(new DonorDistanceDto(id, name, blood, city, latitude, longitude, distance));
        }

        return result;
    }
}
