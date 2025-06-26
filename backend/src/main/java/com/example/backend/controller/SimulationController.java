/*package com.example.backend.controller;
import com.example.backend.service.CustomerService;
import com.example.backend.service.VendorSimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public class SimulationController {
    @Autowired
    private VendorSimulationService vendorSimulationService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/start")
    public ResponseEntity<String> startSimulation() {
        try {
            // Start both vendor and customer simulations
            vendorSimulationService.startSimulation();  // Corrected to use the proper method
            customerService.startSimulation();

            return ResponseEntity.ok("Simulation started.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error starting simulation: " + e.getMessage());
        }

    }
}
*/