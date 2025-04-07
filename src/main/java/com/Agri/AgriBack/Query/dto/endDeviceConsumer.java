package com.Agri.AgriBack.Query.dto;

import com.Agri.AgriBack.Command.entity.Sensor;
import com.Agri.AgriBack.Command.entity.endDevice;
import com.Agri.AgriBack.Query.entity.SensorQ;
import com.Agri.AgriBack.Query.entity.endDeviceQ;
import com.Agri.AgriBack.Query.repository.EndDeviceQRepo;
import com.Agri.AgriBack.Query.repository.SensorQRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class endDeviceConsumer {

    @Autowired
    private EndDeviceQRepo repo;
    @Autowired
    private SensorQRepo sensorRepo;

    @KafkaListener(topics = "EndDevice_created", groupId = "Agri-group")
    public void consumeCreateEvent(endDevice device){
        System.out.println("post Event ");
        endDeviceQ endDevice = new endDeviceQ();
        endDevice.setId(device.getId().toString());
        endDevice.setCodDevice(device.getCodDevice());
        endDevice.setNivBat(device.getNivBat());
        endDevice.setIdlocalOutput(device.getIdlocalOutput());
        endDevice.setIdSensors(device.getIdSensors());
        repo.save(endDevice);
    }

    @KafkaListener(topics = "EndDevice_updated", groupId = "Agri-group")
    public void consumeUpdateEvent(endDevice device){
        endDeviceQ endDevice = new endDeviceQ();
        endDevice.setId(device.getId().toString());
        Optional<endDeviceQ> existingDevOpt = repo.findById(endDevice.getId());
        if (existingDevOpt.isPresent()) {
            endDeviceQ existingDev = existingDevOpt.get();
            existingDev.setNivBat(device.getNivBat());
            existingDev.setCodDevice(device.getCodDevice());
            existingDev.setIdlocalOutput(device.getIdlocalOutput());
            existingDev.setIdSensors(device.getIdSensors());
            repo.save(existingDev);
        }
    }

    @KafkaListener(topics = "EndDevice_deleted", groupId = "Agri-group")
    public void handleDeleteEndDevice(Long deviceId) {
        String id = deviceId.toString();
        Optional<endDeviceQ> optionalDevice = repo.findById(id);
        if (optionalDevice.isPresent()) {
            endDeviceQ device = optionalDevice.get();
            for (String sensorIdStr : device.getIdSensors()) {
                try {
                    sensorRepo.deleteById(sensorIdStr);
                } catch (NumberFormatException e) {
                    System.err.println("ID capteur non valide : " + sensorIdStr);
                }
            }
        }
        repo.deleteById(id);
    }
}
