package com.example.backend.dto;

import org.springframework.stereotype.Component;

@Component
public class TicketPurchaseRequestTDO {
    private int eventId;
    private int numberOfTickets;

    public TicketPurchaseRequestTDO(){

    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}
