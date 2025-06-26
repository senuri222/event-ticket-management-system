/*package com.example.backend.service;
import com.example.backend.entity.Ticket;
import com.example.backend.entity.TicketPool;
import com.example.backend.entity.Vendor;
import com.example.backend.repo.TicketPoolRepo;
import com.example.backend.repo.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

public class VendorSimulationService {
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private TicketPoolRepo ticketPoolRepo;

    public void startSimulation() {
        List<Vendor> vendors = List.of(
                new Vendor(5, 2), // Vendor A: 5 tickets every 2 minutes
                new Vendor(3, 3)  // Vendor B: 3 tickets every 3 minutes
        );

        // Start vendor threads
        for (Vendor vendor : vendors) {
            new Thread(() -> simulateVendor(vendor)).start();
        }
    }

    private void simulateVendor(Vendor vendor) {
        while (true) {
            try {
                // Fetch available tickets from the TicketPool (no need for parameters here)
                List<TicketPool> availableTickets = ticketPoolRepo.findAvailableTickets();

                if (!availableTickets.isEmpty()) {
                    // Add tickets to the pool
                    for (TicketPool ticketPool : availableTickets) {
                        Ticket ticket = ticketPool.getTicket();
                        ticketPool.setStatus("available");

                        // Save the ticket in the pool
                        ticketPoolRepo.save(ticketPool);

                        // Mark the ticket as in the pool
                        ticket.setStatus("in-pool");
                        ticketRepo.save(ticket);
                    }

                    System.out.println(vendor.getTicketsPerBatch() + " tickets added to the pool by Vendor.");
                } else {
                    System.out.println("No more tickets available to add.");
                }

                // Sleep for the specified interval
                Thread.sleep(vendor.getIntervalMinutes() * 60 * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Vendor simulation interrupted: " + e.getMessage());
            }
        }
    }
}*/
