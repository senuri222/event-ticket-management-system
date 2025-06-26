package com.example.backend.service;

import com.example.backend.dto.EventDTO;
import com.example.backend.dto.TicketDTO;
import com.example.backend.entity.Event;
import com.example.backend.entity.Ticket;
import com.example.backend.repo.EventRepo;
import com.example.backend.repo.TicketRepo;
import com.example.backend.utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    TicketDTO ticketDTO;


    public String saveEvent(EventDTO eventDTO){
        if(eventRepo.existsById(eventDTO.getEventid())){
            return VarList.RSP_DUPLICATED;
        } else{
            Event event = eventRepo.save(modelMapper.map(eventDTO, Event.class));
            generateTickets(event);

            return VarList.RSP_SUCCESS;
        }
    }

    /*
    public String saveEvent(EventDTO eventDTO) {
        if (eventRepo.existsById(eventDTO.getEventid())) {
            return "06"; // Duplicated
        }

        Event event = new Event();
        event.setEventName(eventDTO.getEventName());
        event.setDate(eventDTO.getDate());
        event.setTotalTickets(eventDTO.getTotalTickets());
        eventRepo.save(event);

        // Generate tickets dynamically
        for (int i = 0; i < eventDTO.getTotalTickets(); i++) {
            Ticket ticket = new Ticket();
            ticket.setEvent(event); // Associate ticket with the event
            ticket.setTicketNumber(Integer.parseInt(UUID.randomUUID().toString())); // Generate unique code
            ticketRepo.save(ticket);
        }

        return "00"; // Success
    }*/


    private void generateTickets(Event event) {
        if (event == null) {
            // Log or throw an exception if event is null
            System.out.println("Event is null, cannot generate tickets");
            return;
        }

        int totalTickets = event.getTotalTickets();
        List<Ticket> tickets = new ArrayList<>();
        float price = event.getTicketPrice();

        for (int i = 1; i <= totalTickets; i++) {
            Ticket ticket = new Ticket();
            ticket.setEvent(event);
            ticket.setTicketNumber(i);
            ticket.setStatus("available");
            ticket.setPrice(price);
            //ticketRepo.save(modelMapper.map(ticketDTO, Ticket.class));
            ticketRepo.save(ticket);
        }

        // Log ticket creation
        System.out.println("Generated " + tickets.size() + " tickets for event: " + event.getEventName());
        try {
            ticketRepo.saveAll(tickets);
            System.out.println("Tickets saved successfully.");
        } catch (Exception e) {
            System.out.println("Error while saving tickets: " + e.getMessage());
        }
    }


    public String updateEvent(EventDTO eventDTO){
        if(eventRepo.existsById(eventDTO.getEventid())){
            Event existingEvent = eventRepo.findById(eventDTO.getEventid()).orElseThrow(() -> new RuntimeException("Event not found"));
            int currentTicketCount = existingEvent.getTickets().size();
            int updatedTicketCount = eventDTO.getTotalTickets();

            if (updatedTicketCount != currentTicketCount) {
                adjustTickets(existingEvent, updatedTicketCount, currentTicketCount);
            }

            // update event details
            existingEvent.setEventName(eventDTO.getEventName());
            existingEvent.setDate(eventDTO.getDate());
            existingEvent.setLocation(eventDTO.getLocation());
            existingEvent.setEventDescription(eventDTO.getEventDescription());
            existingEvent.setTotalTickets(eventDTO.getTotalTickets());
            existingEvent.setTicketPrice(eventDTO.getTicketPrice());
            eventRepo.save(modelMapper.map(eventDTO, Event.class));
            return VarList.RSP_SUCCESS;
        } else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    private void adjustTickets(Event event, int updatedTicketCount, int currentTicketCount) {
        List<Ticket> tickets = event.getTickets();
        if (updatedTicketCount > currentTicketCount) {
            // Add additional tickets
            int ticketsToAdd = updatedTicketCount - currentTicketCount;
            for (int i = 1; i <= ticketsToAdd; i++) {
                Ticket newTicket = new Ticket();
                newTicket.setEvent(event);
                newTicket.setTicketNumber(currentTicketCount + i);
                newTicket.setStatus("available");
                newTicket.setPrice(event.getTicketPrice());
                ticketRepo.save(newTicket);
                tickets.add(newTicket);
            }
        }
    }

    public void removeExtraTickets(List<Ticket> tickets, Long eventId, int updatedTicketCount) {
        // get the current ticket count
        long currentTicketCount = tickets.stream()
                .filter(ticket -> ticket.getEvent() != null && ticket.getEvent().getEventid() == eventId)
                .count();

        if (updatedTicketCount < currentTicketCount) {
            // remove extra tickets
            int remove = (int) (currentTicketCount - updatedTicketCount);
            List<Ticket> ticketsToRemove = tickets.stream()
                    .filter(ticket -> ticket.getEvent() != null && ticket.getEvent().getEventid() == eventId)
                    .limit(remove)
                    .collect(Collectors.toList());

            // delete tickets
            for (Ticket ticketToRemove : ticketsToRemove) {
                ticketRepo.delete(ticketToRemove);
                tickets.remove(ticketToRemove);
            }
        }
    }


    public List<EventDTO> getAllEvents(){
        List<Event> eventList = eventRepo.findAll();
        return modelMapper.map(eventList, new TypeToken<ArrayList<EventDTO>>(){}.getType());
    }

    public EventDTO searchEvent(int eventId){
        if (eventRepo.existsById(eventId)) {
            Event event = eventRepo.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
            return modelMapper.map(event, EventDTO.class);
        } else {
            return null;
        }
    }

    public String deleteEvent(int eventId){
        if (eventRepo.existsById(eventId)) {
            eventRepo.deleteById(eventId);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }


    public EventDTO getEventDetailsById(int eventId) {
        Event event = eventRepo.findById(eventId).orElse(null);
        if (event == null) {
            return null; // Event not found
        }
        return modelMapper.map(event, EventDTO.class); // Convert Event to EventDTO
    }

    /*public String buyTickets(EventDTO eventDTO, int numberOfTickets) {
       // Event event = eventRepo.findById(eventDTO.getEventid()).orElse(null);


        /*if (event == null) {
            return "01"; // Event not found
        }

        int totalTickets = eventDTO.getTotalTickets();
        int soldTickets = eventDTO.getSoldTickets();

        // Check if there are enough tickets available
        if (totalTickets - soldTickets < numberOfTickets) {
            return "01"; // Not enough tickets available
        }

        // Update the soldTickets and totalTickets fields
        eventDTO.setSoldTickets(soldTickets + numberOfTickets);
        eventDTO.setTotalTickets(totalTickets - numberOfTickets);

        // Save the updated event
        //eventRepo.save(event);
        eventRepo.save(modelMapper.map(eventDTO, Event.class));

        return "00"; // Success
    }*/

    public String buyTickets(EventDTO eventDTO, int numberOfTickets){
        if(eventRepo.existsById(eventDTO.getEventid())){
            Event existingEvent = eventRepo.findById(eventDTO.getEventid()).orElseThrow(() -> new RuntimeException("Event not found"));



            if (eventDTO == null) {
                return "01"; // Event not found
            }

            int totalTickets = eventDTO.getTotalTickets();
            int soldTickets = eventDTO.getSoldTickets();

            // Check if there are enough tickets available
            if (totalTickets - soldTickets < numberOfTickets) {
                return "01"; // Not enough tickets available
            }

            // Update the soldTickets and totalTickets fields
            eventDTO.setSoldTickets(soldTickets + numberOfTickets);
            eventDTO.setTotalTickets(totalTickets - numberOfTickets);


            // update event details
            existingEvent.setEventName(eventDTO.getEventName());
            existingEvent.setDate(eventDTO.getDate());
            existingEvent.setLocation(eventDTO.getLocation());
            existingEvent.setEventDescription(eventDTO.getEventDescription());
            //existingEvent.setTotalTickets(totalTickets);
            //existingEvent.setSoldTickets(soldTickets);
            existingEvent.setTicketPrice(eventDTO.getTicketPrice());
            eventRepo.save(modelMapper.map(eventDTO, Event.class));
            return VarList.RSP_SUCCESS;
        } else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
}
