package com.Agri.AgriBack.Query.dto;

import com.Agri.AgriBack.Command.entity.Employee;
import com.Agri.AgriBack.Query.entity.EmployeeQ;
import com.Agri.AgriBack.Query.entity.Product;
import com.Agri.AgriBack.Query.repository.EmployeeQRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class employeeConsumer {

    @Autowired
    private EmployeeQRepo repo;

    @KafkaListener(topics = "Employee_created", groupId = "Agri-group")
    public void consumeCreateEvent(Employee employee) {
        EmployeeQ emp = new EmployeeQ();
        emp.setId(employee.getId().toString());
        emp.setAddress(employee.getAddress());
        emp.setEmail(employee.getEmail());
        emp.setFirstName(employee.getFirstName());
        emp.setMobile(employee.getMobile());
        emp.setLastName(employee.getLastName());
        emp.setPassword(employee.getPassword());
        emp.setRole(employee.getRole());
        repo.save(emp);
    }

    @KafkaListener(topics = "Employee_updated", groupId = "Agri-group")
    public void consumeUpdateEvent(Employee employee) {
        EmployeeQ emp = new EmployeeQ();
        emp.setId(employee.getId().toString());
        Optional<EmployeeQ> existingEmpOpt = repo.findById(emp.getId());
        if (existingEmpOpt.isPresent()) {
            EmployeeQ existingEmp = existingEmpOpt.get();
            existingEmp.setAddress(employee.getAddress());
            existingEmp.setEmail(employee.getEmail());
            existingEmp.setFirstName(employee.getFirstName());
            existingEmp.setMobile(employee.getMobile());
            existingEmp.setLastName(employee.getLastName());
            existingEmp.setPassword(employee.getPassword());
            existingEmp.setRole(employee.getRole());
            repo.save(existingEmp);
        }
    }

}
