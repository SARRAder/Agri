package com.Agri.AgriBack.Query.Contoller;

import com.Agri.AgriBack.Query.entity.EmployeeQ;
import com.Agri.AgriBack.Query.services.EmployeeQService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeQController {

    @Autowired
    private EmployeeQService empService;

    @GetMapping
    public ResponseEntity<List<EmployeeQ>> getProducts() {
        return ResponseEntity.ok(empService.getAllEmployees());
    }
}
