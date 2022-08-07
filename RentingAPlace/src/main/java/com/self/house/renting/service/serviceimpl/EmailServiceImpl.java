package com.self.house.renting.service.serviceimpl;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.model.dto.request.ReservationRequest;
import com.self.house.renting.model.entity.Email;
import com.self.house.renting.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {


    private JavaMailSender sender;

    @Autowired
    public EmailServiceImpl(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendEmail(Email email) {
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(email.getSubject());
            helper.setFrom(email.getFrom());
            helper.setTo(email.getTo());
            helper.setText(email.getContent());
            sender.send(helper.getMimeMessage());
        } catch(Exception e) {
//            throw new InvalidDateRangeException(Constants.SENDING_EMAIL_FAILED);
        }
    }

    public Email initializeEmail(String appEmail, ReservationRequest request) {
        return Email.builder().from(appEmail).to(request.getEmail()).subject(Constants.RESERVATION_SUBJECT_TITLE).content(Constants.RESERVE_SUCCESS).build();
    }
}
