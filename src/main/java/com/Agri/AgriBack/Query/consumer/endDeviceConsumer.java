package com.Agri.AgriBack.Query.consumer;

import com.Agri.AgriBack.Command.entity.endDevice;
import com.Agri.AgriBack.Command.mapper.EndDeviceCToQ;
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

@Component
@RequiredArgsConstructor
public class endDeviceConsumer {

    @Autowired
    private EndDeviceQRepo repo;
    @Autowired
    private SensorQRepo sensorRepo;
    @Autowired
    private EndDeviceCToQ mapper;

    @KafkaListener(topics = "EndDevice_created", groupId = "Agri-group")
    public void consumeCreateEvent(endDevice device){
        System.out.println("post Event ");
        endDeviceQ endDeviceMapped = mapper.mapperDevice(device);
        repo.save(endDeviceMapped);
    }

    @KafkaListener(topics = "EndDevice_updated", groupId = "Agri-group")
    public void consumeUpdateEvent(endDevice device){
        String id = device.getId().toString();
        Optional<endDeviceQ> existingDevOpt = repo.findById(id);

        if (existingDevOpt.isPresent()) {
            // Re-mapper entièrement l'objet avec les nouvelles données
            endDeviceQ updatedDev = mapper.mapperDevice(device);

            repo.save(updatedDev);
        }
    }

    @KafkaListener(topics = "EndDevice_deleted", groupId = "Agri-group")
    public void handleDeleteEndDevice(Long deviceId) {
        String id = deviceId.toString();

        // Récupérer l'EndDeviceQ à partir de la base de données
        Optional<endDeviceQ> optionalDevice = repo.findById(id);

        if (optionalDevice.isPresent()) {
            endDeviceQ device = optionalDevice.get();

            // 1. Supprimer les capteurs associés à ce device (dans la liste des capteurs)
            List<SensorQ> sensors = device.getSensors();
            if (sensors != null && !sensors.isEmpty()) {
                for (SensorQ sensor : sensors){
                    Optional<SensorQ> optSensor =  sensorRepo.findById(sensor.getId());
                    if(optSensor.isPresent()){
                        SensorQ updatedSensor = optSensor.get();
                        updatedSensor.setEndDevice(null);
                        sensorRepo.save(updatedSensor);
                    }
                }
            }

            // 2. Supprimer le device de la base de données
            repo.deleteById(id);

            System.out.println("EndDevice supprimé avec succès, ID: " + id);
        } else {
            System.out.println("EndDevice non trouvé dans la base de données, ID: " + id);
        }
    }

}
