package com.example.backend.dto;

import com.example.backend.entity.Event;
import com.example.backend.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketPoolDTO {
    private int poolId;
    private String status;
    private Ticket ticket;
    private Event event;

    public TicketPoolDTO() {}

    public int getPoolId() {
        return poolId;
    }

    public void setPoolId(int poolId) {
        this.poolId = poolId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
