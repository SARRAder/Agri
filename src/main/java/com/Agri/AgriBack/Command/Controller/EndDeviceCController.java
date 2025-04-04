package com.Agri.AgriBack.Command.Controller;

import com.Agri.AgriBack.Command.entity.AddDevice;
import com.Agri.AgriBack.Command.entity.Employee;
import com.Agri.AgriBack.Command.entity.endDevice;
import com.Agri.AgriBack.Command.services.EndDeviceCService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/endDevice")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EndDeviceCController {

    @Autowired
    private EndDeviceCService service;

    @PostMapping
    public ResponseEntity<endDevice> createDevice(@RequestBody AddDevice device) {

        endDevice enddevice = new endDevice();
        enddevice.setCodDevice(device.getCodDevice());
        enddevice.setNivBat(254);
        enddevice.setSensors(device.getSensors());
        enddevice.setIdlocalOutput(new ArrayList<>());
        return ResponseEntity.ok(service.createEndDevice(enddevice));
    }

    @PutMapping(value = "/{id}")     //l'id dans le path va etre passer dans l'argument
    private ResponseEntity<endDevice> updateEndDevice (@RequestBody endDevice device, @PathVariable(name ="id") Long id )
    {
        device.setId(id);
        service.updateEndDevice(device);
        return ResponseEntity.ok(device);
    }

    @DeleteMapping("/{id}")
    public String deleteDevice(@PathVariable Long id) {
        service.deleteDevice(id);
        return "L'appareil avec l'ID " + id + " a été supprimé avec succès.";
    }
}
