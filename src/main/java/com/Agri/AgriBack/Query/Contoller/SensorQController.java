package com.Agri.AgriBack.Query.Contoller;

import com.Agri.AgriBack.Query.entity.SensorQ;
import com.Agri.AgriBack.Query.entity.endDeviceQ;
import com.Agri.AgriBack.Query.services.SensorQService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sensor")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SensorQController {

    @Autowired
    private SensorQService service;

    @GetMapping
    public ResponseEntity<List<SensorQ>> getProducts() {
        return ResponseEntity.ok(service.getAllSensors());
    }
}
