package com.example.backend.service;

import com.example.backend.dto.VendorDTO;
import com.example.backend.entity.Vendor;
import com.example.backend.repo.VendorRepo;
import com.example.backend.utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VendorService {
    @Autowired
    private VendorRepo vendorRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String saveVendor(VendorDTO vendorDTO){
        String hashedPassword = passwordEncoder.encode(vendorDTO.getPassword());
        vendorDTO.setPassword(hashedPassword);
        if(vendorRepo.existsById(vendorDTO.getId())){
            return VarList.RSP_DUPLICATED;
        } else{
            vendorRepo.save(modelMapper.map(vendorDTO, Vendor.class));
            return VarList.RSP_SUCCESS;
        }
    }

    public String updateVendor(VendorDTO vendorDTO){
        String hashedPassword = passwordEncoder.encode(vendorDTO.getPassword());
        vendorDTO.setPassword(hashedPassword);
        if(vendorRepo.existsById(vendorDTO.getId())){
            vendorRepo.save(modelMapper.map(vendorDTO, Vendor.class));
            return VarList.RSP_SUCCESS;
        } else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<VendorDTO> getAllVendors(){
        List<Vendor> vendorList = vendorRepo.findAll();
        return modelMapper.map(vendorList, new TypeToken<ArrayList<VendorDTO>>(){}.getType());
    }

    public VendorDTO searchVendor(int vendId){
        if (vendorRepo.existsById(vendId)) {
            Vendor vendor = vendorRepo.findById(vendId).orElseThrow(() -> new RuntimeException("Vendor not found"));
            return modelMapper.map(vendor, VendorDTO.class);
        } else {
            return null;
        }
    }

    public String deleteVendor(int vendId){
        if (vendorRepo.existsById(vendId)) {
            vendorRepo.deleteById(vendId);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public Boolean vendorLogin(VendorDTO vendorDTO) {
        // Query the database for the vendor by email
        Optional<Vendor> vendorOptional = vendorRepo.findByEmail(vendorDTO.getEmail());

        if (vendorOptional.isPresent()) {
            Vendor vendor = vendorOptional.get();

            // Check if the password matches
            return passwordEncoder.matches(vendorDTO.getPassword(), vendor.getPassword());
        }

        // Return false if no matching vendor is found
        return false;
    }



}
