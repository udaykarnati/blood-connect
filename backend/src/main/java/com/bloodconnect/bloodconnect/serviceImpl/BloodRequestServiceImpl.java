package com.bloodconnect.bloodconnect.serviceImpl;

import com.bloodconnect.bloodconnect.dto.DonorDistanceDto;
import com.bloodconnect.bloodconnect.model.BloodRequest;
import com.bloodconnect.bloodconnect.model.User;
import com.bloodconnect.bloodconnect.repository.BloodRequestRepository;
import com.bloodconnect.bloodconnect.service.BloodRequestService;
import com.bloodconnect.bloodconnect.service.DonorSearchService;
import com.bloodconnect.bloodconnect.service.EmailService;
import com.bloodconnect.bloodconnect.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BloodRequestServiceImpl implements BloodRequestService {

    private final BloodRequestRepository bloodRequestRepository;
    private final UserService userService;
    private final EmailService emailService;
    private final DonorSearchService donorSearchService;

    public BloodRequestServiceImpl(
            BloodRequestRepository bloodRequestRepository,
            UserService userService,
            EmailService emailService,
            DonorSearchService donorSearchService
    ) {
        this.bloodRequestRepository = bloodRequestRepository;
        this.userService = userService;
        this.emailService = emailService;
        this.donorSearchService = donorSearchService;
    }

    @Override
    public BloodRequest createRequest(BloodRequest request) {

        // 🔹 1) Fetch creator from DB (by email, if provided)
        User creator = null;
        if (request.getCreatorEmail() != null && !request.getCreatorEmail().isBlank()) {
            creator = userService.getUserByEmail(request.getCreatorEmail());
            if (creator != null) {
                // overwrite with DB truth to keep it consistent
                request.setCreatorName(creator.getName());
                request.setCreatorPhone(creator.getPhone());
                request.setCreatorEmail(creator.getEmail());
            }
        }

        // 🔹 Ensure initial status
        if (request.getStatus() == null || request.getStatus().isBlank()) {
            request.setStatus("PENDING");
        }

        // 🔹 2) Save request (now has mode, city, lat, lng, radiusKm too)
        BloodRequest savedRequest = bloodRequestRepository.save(request);

        // 🔹 3) Donor matching logic
        List<User> matchedDonors = new ArrayList<>();

        String mode = request.getMode();
        String city = request.getCity();
        Double lat = request.getLatitude();
        Double lng = request.getLongitude();
        Double radiusKm = request.getRadiusKm();

        // 3.1 CITY-based matching
        if ("CITY".equalsIgnoreCase(mode)) {
            if (city != null && !city.isBlank()) {
                matchedDonors = donorSearchService.searchDonorsByCity(city);
            }
        }
        // 3.2 DISTANCE-based matching
        else if ("DISTANCE".equalsIgnoreCase(mode)) {
            if (lat != null && lng != null && radiusKm != null) {
                List<DonorDistanceDto> nearby = donorSearchService.searchNearbyDonors(lat, lng, radiusKm);
                for (DonorDistanceDto dto : nearby) {
                    User donor = userService.getUserById(dto.getId());
                    if (donor != null) {
                        matchedDonors.add(donor);
                    }
                }
            }
        }

        // 3.3 Optional fallback: if mode missing or no donors matched, fall back to blood-group-only
        if ((mode == null || mode.isBlank()) || matchedDonors.isEmpty()) {
            List<User> sameGroup = userService.getUsersByBloodGroup(request.getBloodGroup());
            matchedDonors.addAll(sameGroup);
        }

        // 🔹 4) Filter by blood group (mandatory)
        matchedDonors.removeIf(
                donor -> donor.getBloodGroup() == null ||
                        !donor.getBloodGroup().equalsIgnoreCase(request.getBloodGroup())
        );

        // 🔹 5) Strong removal: NEVER notify the creator (by id OR email)
        if (creator != null) {
            Long creatorId = creator.getId();
            String creatorEmail = creator.getEmail();

            matchedDonors.removeIf(donor ->
                    (creatorId != null && donor.getId() != null && donor.getId().equals(creatorId))
                            ||
                            (creatorEmail != null && donor.getEmail() != null &&
                                    donor.getEmail().equalsIgnoreCase(creatorEmail))
            );
        }

        System.out.println("Matched donors final count: " + matchedDonors.size());

        // 🔹 6) Send email notifications to matched donors
        for (User donor : matchedDonors) {
            emailService.sendEmail(
                    donor.getEmail(),
                    "Urgent Blood Request - " + request.getBloodGroup(),
                    "Dear " + donor.getName() + ",\n\n" +
                            "An urgent request for blood group " + request.getBloodGroup() + " has been made.\n\n" +

                            "📌 Recipient Details:\n" +
                            "   Name: " + request.getRecipientName() + "\n" +
                            "   Phone: " + request.getRecipientPhone() + "\n\n" +

                            "📌 Guardian Details:\n" +
                            "   Name: " + request.getCreatorName() + "\n" +
                            "   Contact: " + request.getCreatorPhone() + "\n\n" +

                            "👉 Please reach out to the guardian to coordinate.\n\n" +
                            "Thank you for being a lifesaver!\n" +
                            "BloodConnect"
            );
        }

        return savedRequest;
    }

    // 🔥 NEW: Donor accepts a blood request
    @Override
    public BloodRequest acceptRequest(Long requestId, String donorEmail) {

        // 1) Find the request
        BloodRequest request = bloodRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Blood request not found with id: " + requestId));

        // Optional: prevent double-accept
        if ("ACCEPTED".equalsIgnoreCase(request.getStatus())) {
            throw new RuntimeException("This request is already accepted by a donor.");
        }

        // 2) Find the donor
        User donor = userService.getUserByEmail(donorEmail);
        if (donor == null) {
            throw new RuntimeException("Donor not found for email: " + donorEmail);
        }
        if (donor.getIsDonor() == null || !donor.getIsDonor()) {
            throw new RuntimeException("This user is not registered as a donor.");
        }

        // 3) Update request with accepted donor details
        request.setAcceptedDonorId(donor.getId());
        request.setAcceptedDonorName(donor.getName());
        request.setAcceptedDonorPhone(donor.getPhone());
        request.setStatus("ACCEPTED");

        BloodRequest updated = bloodRequestRepository.save(request);

        // 4) Notify creator that a donor accepted the request
        if (request.getCreatorEmail() != null) {
            emailService.sendEmail(
                    request.getCreatorEmail(),
                    "Good news! A donor has accepted your blood request",
                    "Dear " + request.getCreatorName() + ",\n\n" +
                            "A donor has accepted your blood request for blood group " + request.getBloodGroup() + ".\n\n" +
                            "Donor Details:\n" +
                            "   Name: " + donor.getName() + "\n" +
                            "   Phone: " + donor.getPhone() + "\n" +
                            "   Email: " + donor.getEmail() + "\n\n" +
                            "Please contact the donor to coordinate the donation.\n\n" +
                            "Regards,\n" +
                            "BloodConnect"
            );
        }

        return updated;
    }

    @Override
    public List<BloodRequest> getAllRequests() {
        return bloodRequestRepository.findAll();
    }

    @Override
    public List<BloodRequest> getRequestsByBloodGroup(String bloodGroup) {
        return bloodRequestRepository.findByBloodGroup(bloodGroup);
    }

    @Override
    public List<BloodRequest> getRequestsByCreator(String email) {
        return bloodRequestRepository.findByCreatorEmail(email);
    }
}
