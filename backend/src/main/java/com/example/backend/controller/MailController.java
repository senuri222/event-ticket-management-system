package com.example.backend.controller;

import com.example.backend.dto.MailDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/mail")
@CrossOrigin
public class MailController {
    @Autowired
    JavaMailSender javaMailSender;

    @PostMapping("/send")
    public String sendEmail(@RequestBody MailDetailsDTO mailDetailsDTO) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(mailDetailsDTO.getSubject());
            message.setTo(mailDetailsDTO.getToMail());
            message.setFrom("quicktix@gmail.com");
            message.setText(mailDetailsDTO.getMessage());

            javaMailSender.send(message);
            return "Success";
        } catch (Exception e) {
            return e.getMessage();
        }

    }
}
