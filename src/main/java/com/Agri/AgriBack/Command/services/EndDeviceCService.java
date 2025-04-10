package com.Agri.AgriBack.Command.services;

import com.Agri.AgriBack.Command.producer.EndDeviceProducer;
import com.Agri.AgriBack.Command.entity.endDevice;
import com.Agri.AgriBack.Command.repository.SensorCRepo;
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
    @Autowired
    private SensorCRepo sensorRepo;

    public endDevice createEndDevice(endDevice device) {
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
           /* Optional<endDevice> optionalDevice = repo.findById(id);
            if (optionalDevice.isPresent()) {
                endDevice device = optionalDevice.get();
                for (Sensor sensor : device.getSensors()) {
                    try {
                        Long sensorId = Long.parseLong(sensor.getId());
                        sensorRepo.deleteById(sensorId);
                    } catch (NumberFormatException e) {
                        System.err.println("ID capteur non valide : " + sensorIdStr);
                    }
                }
            }*/
                repo.deleteById(id);
                producer.deleteEndDeviceEvent(id); // Publier l'événement Kafka

        } else {
            throw new RuntimeException("L'appareil avec l'ID " + id + " n'existe pas.");
        }
    }
}
