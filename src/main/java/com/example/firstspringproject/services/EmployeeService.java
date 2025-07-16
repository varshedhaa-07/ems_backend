package com.example.firstspringproject.services;

import com.example.firstspringproject.models.RegisterDetails;
import com.example.firstspringproject.models.Roles;
import com.example.firstspringproject.models.UserDetailsDto;
import com.example.firstspringproject.repository.RegisterDetailsRepository;
import com.example.firstspringproject.repository.RolesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    RegisterDetailsRepository registerDetailsRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<RegisterDetails> getMethod() {
        return registerDetailsRepository.findAll();
    }

    public RegisterDetails getEmployeeById(int empId) {
        return registerDetailsRepository.findById(empId).orElse(new RegisterDetails());
    }

    public RegisterDetails getEmployeeByRole(String role) {
        return registerDetailsRepository.findByRole(role).orElse(new RegisterDetails());
    }

    public String addEmployee(UserDetailsDto register) {
        RegisterDetails registerDetails = new RegisterDetails();
        registerDetails.setEmpId(register.getEmpId());
        registerDetails.setName(register.getName());
        registerDetails.setEmail(register.getEmail());
        registerDetails.setPassword(passwordEncoder.encode(register.getPassword()));
        registerDetails.setUserName(register.getUserName());
        Set<Roles> roles = new HashSet<>();
        for(String roleName: register.getRoleNames()){
            Roles role = rolesRepository.findByRoleName(roleName)
                    .orElseThrow(()->new RuntimeException("User not found" + roleName));
            roles.add(role);
        }
        registerDetails.setRoles(roles);
        System.out.println("Registration"+ registerDetails);
        registerDetailsRepository.save(registerDetails);
        return "Employee Added Successfully";
    }

    public String updateEmployee(int empId, UserDetailsDto dto) {
        RegisterDetails updatedEmp = modelMapper.map(dto, RegisterDetails.class);

        RegisterDetails existingEmp = registerDetailsRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (updatedEmp.getName() != null) existingEmp.setName(updatedEmp.getName());
        if (updatedEmp.getEmail() != null) existingEmp.setEmail(updatedEmp.getEmail());
        if (updatedEmp.getUserName() != null) existingEmp.setUserName(updatedEmp.getUserName());
        if (updatedEmp.getPassword() != null) existingEmp.setPassword(passwordEncoder.encode(updatedEmp.getPassword()));

        registerDetailsRepository.save(existingEmp);
        return "Employee updated successfully";
    }




    public String deleteEmployee(int empId) {
        Optional<RegisterDetails> optionalUser = registerDetailsRepository.findById(empId);

        if (optionalUser.isEmpty()) {
            return "Employee not found";
        }

        RegisterDetails user = optionalUser.get();

        // Step 1: Clear roles (removes associations from join table)
        user.getRoles().clear();
        registerDetailsRepository.save(user);  // Persist the cleared roles

        // Step 2: Now safely delete the user
        registerDetailsRepository.deleteById(empId);

        return "Employee deleted successfully";
    }

}
