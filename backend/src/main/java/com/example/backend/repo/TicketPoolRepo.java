package com.example.backend.repo;

import com.example.backend.entity.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPoolRepo extends JpaRepository<TicketPool, Integer > {
}
