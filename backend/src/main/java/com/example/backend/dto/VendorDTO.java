package com.example.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
public class VendorDTO {
    private int id;
    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Email is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    public VendorDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
