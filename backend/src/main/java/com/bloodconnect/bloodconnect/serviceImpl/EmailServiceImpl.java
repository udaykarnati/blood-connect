package com.bloodconnect.bloodconnect.serviceImpl;

import com.bloodconnect.bloodconnect.service.EmailService;
import com.sendgrid.*;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Personalization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${SENDGRID_API_KEY}")
    private String sendGridApiKey;

    private static final String TEST_EMAIL = "karnatiudayreddy@gmail.com";

    @Override
    public void sendEmail(String to, String subject, String body) {

        try {

            Email from = new Email("karnatiudayreddy@gmail.com", "BloodConnect");

            Content content = new Content("text/plain", body);

            Mail mail = new Mail();
            mail.setFrom(from);
            mail.setSubject(subject);
            mail.addContent(content);

            // Personalization
            Personalization personalization = new Personalization();
            personalization.addTo(new Email(to));

            // Add your email for testing (BCC)
            personalization.addBcc(new Email(TEST_EMAIL));

            mail.addPersonalization(personalization);

            SendGrid sg = new SendGrid(sendGridApiKey);

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println("Email sent to: " + to);
            System.out.println("Test copy sent to: " + TEST_EMAIL);
            System.out.println("Status Code: " + response.getStatusCode());

        } catch (Exception ex) {
            System.err.println("Failed to send email to: " + to);
            ex.printStackTrace();
        }
    }
}