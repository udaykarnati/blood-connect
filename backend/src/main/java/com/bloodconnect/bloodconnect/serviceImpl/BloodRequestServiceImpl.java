package com.bloodconnect.bloodconnect.serviceImpl;

import com.bloodconnect.bloodconnect.model.BloodRequest;
import com.bloodconnect.bloodconnect.model.User;
import com.bloodconnect.bloodconnect.repository.BloodRequestRepository;
import com.bloodconnect.bloodconnect.service.BloodRequestService;
import com.bloodconnect.bloodconnect.service.UserService;
import com.bloodconnect.bloodconnect.service.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodRequestServiceImpl implements BloodRequestService {
    private final BloodRequestRepository bloodRequestRepository;
    private final UserService userService;
    private final EmailService emailService;

    public BloodRequestServiceImpl(BloodRequestRepository bloodRequestRepository,
                                   UserService userService,
                                   EmailService emailService) {
        this.bloodRequestRepository = bloodRequestRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public BloodRequest createRequest(BloodRequest request) {
        // Fetch creator (logged-in user) from DB using email
        User creator = userService.getUserByEmail(request.getCreatorEmail());
        if (creator != null) {
            request.setCreatorName(creator.getName());
            request.setCreatorPhone(creator.getPhone());
        }

        BloodRequest savedRequest = bloodRequestRepository.save(request);

        // Find matching donors by blood group
        List<User> matchingUsers = userService.getUsersByBloodGroup(request.getBloodGroup());

        // Notify matching donors (excluding the creator)
        for (User user : matchingUsers) {
            if (user.getEmail().equals(request.getCreatorEmail())) continue; // skip creator

            emailService.sendEmail(
                    user.getEmail(),
                    "Urgent Blood Request - " + request.getBloodGroup(),
                    "Dear " + user.getName() + ",\n\n" +
                            "An urgent request for blood group " + request.getBloodGroup() + " has been made.\n\n" +

                            "📌 Recipient Details:\n" +
                            "   Name: " + request.getRecipientName() + "\n" +
                            "   Phone: " + request.getRecipientPhone() + "\n\n" +

                            "📌 Guardian Details:\n" +
                            "   Name: " + request.getCreatorName() + "\n" +
                            "   Contact: " + request.getCreatorPhone() + "\n\n" +

                            "👉 Please reach out to the recipient’s guardian for coordination.\n\n" +
                            "Thank you for being a lifesaver!\n" +
                            "BloodConnect"
            );
        }

        return savedRequest;
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



