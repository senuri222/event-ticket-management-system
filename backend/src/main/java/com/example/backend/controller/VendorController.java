package com.example.backend.controller;

import com.example.backend.dto.ResponseDTO;
import com.example.backend.dto.VendorDTO;
import com.example.backend.service.VendorService;
import com.example.backend.utill.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/v2/vendor")

@CrossOrigin
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @Autowired
    private ResponseDTO responseDTO;

    @PostMapping(value = "/saveVendor")
    public ResponseEntity saveVendor(@RequestBody VendorDTO vendorDTO){
        try {
            String res = vendorService.saveVendor(vendorDTO);
            if (res.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(vendorDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            } else if (res.equals("06")) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Vendor Registered");
                responseDTO.setContent(vendorDTO);
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

    @PutMapping(value = "/updateVendor")
    public ResponseEntity updateVendor(@RequestBody VendorDTO vendorDTO){
        try {
            String res = vendorService.updateVendor(vendorDTO);
            if (res.equals("00")) {
                responseDTO.setCode((VarList.RSP_SUCCESS));
                responseDTO.setMessage("Success");
                responseDTO.setContent(vendorDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            } else if (res.equals("01")) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Not A Registered Vendor");
                responseDTO.setContent(vendorDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/getVendors")
    public ResponseEntity getAllVendors(){
        try {
            List<VendorDTO> vendorDTOList = vendorService.getAllVendors();
            responseDTO.setCode((VarList.RSP_SUCCESS));
            responseDTO.setMessage("Success");
            responseDTO.setContent(vendorDTOList);
            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

        } catch (Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchVendor/{vendId}")
    public ResponseEntity searchVendor(@PathVariable int vendId){
        try {
            VendorDTO vendorDTO = vendorService.searchVendor(vendId);
            if (vendorDTO !=null) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(vendorDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No Vendor Available For this empID");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(ex);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteVendor/{vendId}")
    public ResponseEntity deleteVendor(@PathVariable int vendId){
        try {
            String res = vendorService.deleteVendor(vendId);
            if (res.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No Vendor Available For this Vendor Id");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(ex);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/loginVendor")
    public ResponseEntity loginVendor(@RequestBody VendorDTO vendorDTO) {
        try {
            Boolean res = vendorService.vendorLogin(vendorDTO);
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
    }


}
