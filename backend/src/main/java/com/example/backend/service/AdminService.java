package com.example.backend.service;

import com.example.backend.dto.AdminDTO;
import com.example.backend.entity.Admin;
import com.example.backend.repo.AdminRepo;
import com.example.backend.utill.VarList;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.patterns.AndAnnotationTypePattern;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
@Transactional
public class AdminService {
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    public String saveAdmin(AdminDTO adminDTO){
        String hashedPassword = passwordEncoder.encode(adminDTO.getPassword());
        adminDTO.setPassword(hashedPassword);
        if(adminRepo.existsById(adminDTO.getId())){
            return VarList.RSP_DUPLICATED;
        } else{
            adminRepo.save(modelMapper.map(adminDTO, Admin.class));
            return VarList.RSP_SUCCESS;
        }
    }
    /**public Boolean adminLogin(AdminDTO adminDTO) {
        Optional<Admin> adminOptional = adminRepo.findByEmail(adminDTO.getEmail());

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();

            return passwordEncoder.matches(adminDTO.getPassword(), admin.getPassword());
        }

        // Return false if no matching vendor is found
        return false;
    }*/

    public Boolean adminLogin1(AdminDTO adminDTO) {
        return adminRepo.findByEmail(adminDTO.getEmail())
                .map(admin -> passwordEncoder.matches(adminDTO.getPassword(), admin.getPassword()))
                .orElse(false);
    }

    /*public Boolean adminLogin1(AdminDTO adminDTO) {
        return adminRepo.findByEmail(adminDTO.getEmail())
                .map(admin -> adminDTO.getPassword().equals(admin.getPassword()))
                .orElse(false);
    }*/

}
