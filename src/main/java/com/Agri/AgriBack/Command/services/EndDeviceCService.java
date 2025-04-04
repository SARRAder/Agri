package com.Agri.AgriBack.Command.services;

import com.Agri.AgriBack.Command.dto.EndDeviceProducer;
import com.Agri.AgriBack.Command.entity.Employee;
import com.Agri.AgriBack.Command.entity.Sensor;
import com.Agri.AgriBack.Command.entity.endDevice;
import com.Agri.AgriBack.Command.repository.endDeviceCRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EndDeviceCService {

    @Autowired
    private EndDeviceProducer producer;

    @Autowired
    private endDeviceCRepo repo;

    public endDevice createEndDevice(endDevice device) {
        // Associer chaque capteur à l'endDevice
        if (device.getSensors() != null) {
            for (Sensor sensor : device.getSensors()) {
                sensor.setEndDevice(device);
            }
        }
        endDevice savedDevice = repo.save(device);
        producer.createEndDeviceEvent(savedDevice);
        return savedDevice;
    }

    public endDevice updateEndDevice(endDevice device) {
        endDevice savedDevice = repo.save(device);
        producer.updateEndDeviceEvent(savedDevice);
        return savedDevice;
    }

    public void deleteDevice(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            producer.deleteEndDeviceEvent(id); // Publier l'événement Kafka
        } else {
            throw new RuntimeException("L'appareil avec l'ID " + id + " n'existe pas.");
        }
    }
}
