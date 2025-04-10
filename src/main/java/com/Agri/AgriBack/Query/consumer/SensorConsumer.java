package com.Agri.AgriBack.Query.consumer;

import com.Agri.AgriBack.Command.entity.Sensor;
import com.Agri.AgriBack.Command.mapper.SensorCToQ;
import com.Agri.AgriBack.Query.entity.SensorQ;
import com.Agri.AgriBack.Query.entity.endDeviceQ;
import com.Agri.AgriBack.Query.repository.EndDeviceQRepo;
import com.Agri.AgriBack.Query.repository.SensorQRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SensorConsumer {

    @Autowired
    private SensorQRepo repo;
    @Autowired
    private EndDeviceQRepo deviceRepo;

    @KafkaListener(topics = "Sensor_created", groupId = "Agri-group")
    public void consumeCreateEvent(Sensor sensor) {
        // Mapper le capteur
        SensorCToQ sensorMapper = new SensorCToQ();
        SensorQ sensorQ = sensorMapper.mapperSensor(sensor);  // Le capteur avec l'EndDeviceQ déjà ajouté

        // Vérifier si le device associé au capteur existe déjà
        if (sensor.getEndDevice() != null) {
            // Récupérer le device (endDeviceQ) par son ID
            Optional<endDeviceQ> existingDeviceOpt = deviceRepo.findById(sensor.getEndDevice().getId().toString());

            if (existingDeviceOpt.isPresent()) {
                endDeviceQ existingDevice = existingDeviceOpt.get();

                // Ajouter le capteur à la liste des capteurs de l'endDevice
                List<SensorQ> sensors = existingDevice.getSensors();
                if (sensors == null) {
                    sensors = new ArrayList<>();
                }
                sensors.add(sensorQ);  // Ajouter le capteur à la liste
                existingDevice.setSensors(sensors);  // Mettre à jour la liste des capteurs dans l'appareil

                // Sauvegarder l'appareil mis à jour
                deviceRepo.save(existingDevice);
            } else {
                // Si le device n'est pas trouvé, gérer l'erreur ici
                System.out.println("Device not found for ID: " + sensor.getEndDevice().getId());
            }
        }

        // Sauvegarder le capteur dans la base de données
        repo.save(sensorQ);
    }


    @KafkaListener(topics = "Sensor_deleted", groupId = "Agri-group")
    public void handleDeleteSensorEvent(Long sensorId) {
        // 1. Trouver le capteur dans la collection SensorQ
        Optional<SensorQ> sensorOpt = repo.findById(sensorId.toString());
        if (sensorOpt.isPresent()) {
            SensorQ sensor = sensorOpt.get();

            // 2. Récupérer l'EndDeviceQ associé au capteur
            endDeviceQ device = sensor.getEndDevice();  // Le capteur contient déjà l'EndDevice

            if (device != null) {
                // 3. Supprimer le capteur de la liste des capteurs de l'endDevice
                List<SensorQ> sensors = device.getSensors();
                if (sensors != null && sensors.contains(sensor)) {
                    sensors.remove(sensor);
                    device.setSensors(sensors);
                    deviceRepo.save(device); // Sauvegarder la modification dans EndDeviceQ
                    System.out.println("Capteur supprimé de la liste des capteurs de l'EndDevice. ID: " + sensorId);
                }
            }

            // 4. Supprimer le capteur de la base de données SensorQ
            repo.deleteById(sensorId.toString());
            System.out.println("Capteur supprimé avec succès de la base de données. ID: " + sensorId);
        } else {
            System.out.println("Capteur non trouvé dans la base de données. ID: " + sensorId);
        }
    }



    @KafkaListener(topics = "Sensor_updated", groupId = "Agri-group")
    public void consumeUpdateEvent(Sensor sensor){
       String sensorId = sensor.getId().toString();

       // Supprimer le capteur des anciens endDeviceQ
       List<endDeviceQ> devices = deviceRepo.findAll();
       for (endDeviceQ device : devices) {
           List<SensorQ> sensors = device.getSensors();
           if (sensors != null) {
               sensors.removeIf(s -> s.getId().equals(sensorId));
               device.setSensors(sensors);
               deviceRepo.save(device);
           }
       }

       // Mapper le sensor mis à jour
       SensorCToQ sensorMapper = new SensorCToQ();
       SensorQ updatedSensorQ = sensorMapper.mapperSensor(sensor);

       // Sauvegarder le capteur mis à jour
       repo.save(updatedSensorQ);

       // Associer à nouveau le capteur au bon endDeviceQ
       if (sensor.getEndDevice() != null) {
           String deviceId = sensor.getEndDevice().getId().toString();
           Optional<endDeviceQ> optionalDevice = deviceRepo.findById(deviceId);
           if (optionalDevice.isPresent()) {
               endDeviceQ device = optionalDevice.get();
               List<SensorQ> sensors = device.getSensors();
               if (sensors == null) {
                   sensors = new ArrayList<>();
               }
               sensors.add(updatedSensorQ);
               device.setSensors(sensors);
               deviceRepo.save(device);
           }
       }

    }
}
