package com.example.backend.controller;

import com.example.backend.dto.AdminDTO;
import com.example.backend.dto.ResponseDTO;
import com.example.backend.dto.VendorDTO;
import com.example.backend.service.AdminService;
import com.example.backend.utill.VarList;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/admin")

@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ResponseDTO responseDTO;

    @PostMapping(value = "/saveAdmin")
    public ResponseEntity saveAdmin(@RequestBody AdminDTO adminDTO){
        try {
            String res = adminService.saveAdmin(adminDTO);
            if (res.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(adminDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            } else if (res.equals("06")) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Vendor Registered");
                responseDTO.setContent(adminDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Eroor");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }
        } catch (Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*@PostMapping(value = "/loginAdmin")
    public ResponseEntity loginAdmin1(@RequestBody AdminDTO adminDTO) {
        try {
            Boolean res = adminService.adminLogin(adminDTO);
            if (res) { // Successful login
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Login successful");
                responseDTO.setContent(1);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else { // Failed login
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Invalid email or password");
                responseDTO.setContent(0);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) { // Handle unexpected errors
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDTO> loginAdmin1(@RequestBody @Valid AdminDTO adminDTO) {
        try {
            boolean res = adminService.adminLogin1(adminDTO);
            if (res) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Login successful");
                responseDTO.setContent(1);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Invalid email or password");
                responseDTO.setContent(0);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("An error occurred: " + ex.getMessage());
            responseDTO.setContent(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }


}
