package com.example.backend.dto;

import org.springframework.stereotype.Component;

@Component
public class MailDetailsDTO {
    private String toMail;
    private String message;
    private String subject;

    public MailDetailsDTO(){}

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
