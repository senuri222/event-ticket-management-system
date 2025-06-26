package com.example.backend.service;

import com.example.backend.dto.TicketDTO;
import com.example.backend.entity.Ticket;
import com.example.backend.entity.TicketPool;
import com.example.backend.repo.EventRepo;
import com.example.backend.repo.TicketPoolRepo;
import com.example.backend.repo.TicketRepo;
import com.example.backend.utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TicketService {
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TicketPoolRepo ticketPoolRepo;
    @Autowired
    private EventRepo eventRepository;

    public String saveTicket(TicketDTO ticketDTO) {
        if (ticketRepo.existsById(ticketDTO.getTicketId())) {
            return VarList.RSP_DUPLICATED;
        } else {
            ticketRepo.save(modelMapper.map(ticketDTO, Ticket.class));
            return VarList.RSP_SUCCESS;
        }
    }

    public String updateTicket(TicketDTO ticketDTO) {
        if (ticketRepo.existsById(ticketDTO.getTicketId())) {
            ticketRepo.save(modelMapper.map(ticketDTO, Ticket.class));
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<TicketDTO> getAllTickets() {
        List<Ticket> ticketList = ticketRepo.findAll();
        return modelMapper.map(ticketList, new TypeToken<ArrayList<TicketDTO>>() {
        }.getType());
    }

    public TicketDTO searchTicket(int ticketId) {
        if (ticketRepo.existsById(ticketId)) {
            Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
            return modelMapper.map(ticket, TicketDTO.class);
        } else {
            return null;
        }
    }

    public String deleteTicket(int ticketId) {
        if (ticketRepo.existsById(ticketId)) {
            ticketRepo.deleteById(ticketId);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public void addTicketsToPool(int eventId) {
        // Create a Pageable to fetch the top 5 tickets
        Pageable pageable = PageRequest.of(0, 5);  // Page 0, 5 tickets

        // Fetch top 5 available tickets for the given event_id (not yet in the pool)
        List<Ticket> tickets = ticketRepo.findTop5ByEventIdAndPoolStatusIsNull(eventId, (java.awt.print.Pageable) pageable);

        if (!tickets.isEmpty()) {
            for (Ticket ticket : tickets) {
                // Create a new TicketPool entity for each ticket
                TicketPool ticketPool = new TicketPool();

                // Set the Event associated with the ticket in the TicketPool
                ticketPool.setEvent(ticket.getEvent());  // Link to the Event entity

                // Set the status for the TicketPool (this can be customized as per your needs)
                ticketPool.setStatus("active");  // Mark it as active in the pool

                // Associate the ticket with the TicketPool
                ticketPool.setTicket(ticket);  // Link the ticket to the ticket pool

                // Save the TicketPool entity to the database
                ticketPoolRepo.save(ticketPool);  // Save the new ticket pool record

                // Update the ticket's pool status to mark it as in the pool
                ticket.setPoolStatus("inPool");
                ticketRepo.save(ticket);  // Save the updated ticket
            }
        }
    }
}
