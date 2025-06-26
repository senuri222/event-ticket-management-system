package com.example.backend.repo;

import com.example.backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {
    @Query("SELECT t FROM Ticket t WHERE t.event.eventid = :eventId AND t.poolStatus IS NULL")
    List<Ticket> findTop5ByEventIdAndPoolStatusIsNull(@Param("eventId") int eventId, Pageable pageable);
}
