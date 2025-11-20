/*package com.bloodconnect.bloodconnect.repository;

import com.bloodconnect.bloodconnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByBloodGroup(String bloodGroup);

} */

package com.bloodconnect.bloodconnect.repository;

import com.bloodconnect.bloodconnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // existing methods
    Optional<User> findByEmail(String email);
    List<User> findByBloodGroup(String bloodGroup);

    // 🔹 1) City-based donor search
    List<User> findByCityIgnoreCaseAndIsDonorTrueAndAvailableTrue(String city);

    // 🔹 2) Distance-based donor search (Haversine formula)
    @Query(value = """
        SELECT u.id, u.name, u.blood_group, u.city,
               u.latitude, u.longitude,
               (6371 * acos(
                    cos(radians(:lat)) * cos(radians(u.latitude)) *
                    cos(radians(u.longitude) - radians(:lng)) +
                    sin(radians(:lat)) * sin(radians(u.latitude))
               )) AS distance
        FROM users u
        WHERE u.is_donor = true
          AND u.available = true
          AND u.latitude IS NOT NULL
          AND u.longitude IS NOT NULL
        HAVING distance <= :radiusKm
        ORDER BY distance ASC
        """, nativeQuery = true)
    List<Object[]> findNearbyDonors(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("radiusKm") double radiusKm
    );
}

