package com.example.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Vendor")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    private int ticketsPerBatch;
    private int intervalMinutes;

    public Vendor() {}

    public Vendor(int ticketsPerBatch, int intervalMinutes) {
        this.ticketsPerBatch = ticketsPerBatch;
        this.intervalMinutes = intervalMinutes;
    }

    public int getTicketsPerBatch() {
        return ticketsPerBatch;
    }

    public void setTicketsPerBatch(int ticketsPerBatch) {
        this.ticketsPerBatch = ticketsPerBatch;
    }

    public int getIntervalMinutes() {
        return intervalMinutes;
    }

    public void setIntervalMinutes(int intervalMinutes) {
        this.intervalMinutes = intervalMinutes;
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
