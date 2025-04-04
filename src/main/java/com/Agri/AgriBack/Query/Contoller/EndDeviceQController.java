package com.Agri.AgriBack.Query.Contoller;

import com.Agri.AgriBack.Query.entity.EmployeeQ;
import com.Agri.AgriBack.Query.entity.endDeviceQ;
import com.Agri.AgriBack.Query.services.EndDeviceQService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/endDevice")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EndDeviceQController {

    @Autowired
    private EndDeviceQService service;

    @GetMapping
    public ResponseEntity<List<endDeviceQ>> getProducts() {
        return ResponseEntity.ok(service.getAllDevices());
    }
}
