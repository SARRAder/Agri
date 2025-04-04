package com.Agri.AgriBack.Command.Controller;

import com.Agri.AgriBack.Command.entity.AddEmployee;
import com.Agri.AgriBack.Command.entity.AuthRequest;
import com.Agri.AgriBack.Command.entity.Employee;
import com.Agri.AgriBack.Command.repository.EmployeeCRepo;
import com.Agri.AgriBack.Command.services.EmployeeCService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeContolllerC {

    @Autowired
    private EmployeeCService empService;

    @Autowired
    private EmployeeCRepo employeeRepo;
    String code;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody AddEmployee employee) {
        Optional<Employee> existingEmployee = employeeRepo.findByEmail(employee.getEmail());
        if (existingEmployee.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already taken. Please try again");
        }
        Employee emp = new Employee();
        emp.setFirstName(employee.getFirstName());
        emp.setLastName(employee.getLastName());
        emp.setEmail(employee.getEmail());
        emp.setAddress(employee.getAddress());
        emp.setMobile(employee.getMobile());
        emp.setRole(employee.getRole());
        code = empService.generatePassword();  //generation d'un mdp random
        System.out.println("Generated password: " + code);

        emp.setPassword(code);
        empService.sendSimpleEmail(employee.getEmail(), "Password",  code);
        return ResponseEntity.ok(empService.createEmployee(emp));
    }

    @PutMapping(value = "/{id}")     //l'id dans le path va etre passer dans l'argument
    private Employee updateEmployees (@RequestBody Employee employee, @PathVariable(name ="id") Long id )
    {
        employee.setId(id);
        empService.updateEmployee(employee);
        return employee;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest auth) {
        Employee user = empService.authenticateUser(auth.getEmail(), auth.getPassword());
        if (user == null) {
            System.out.println("Authentication failed for: " + auth.getEmail());
            return ResponseEntity.status(401).body("Authentication failed"); // Unauthorized
        }
        return ResponseEntity.ok(user);
    }
}
