package com.example.backend.dto;

import org.springframework.stereotype.Component;

@Component
public class AdminDTO {
    private int id;
    private String email;
    private String password;

    public AdminDTO(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
