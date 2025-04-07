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
    public void consumeCreateEvent(Sensor sensor){
        SensorQ sensorQ = new SensorQ();
        sensorQ.setTypeSensor(sensor.getTypeSensor());
        sensorQ.setId(sensor.getId().toString());
        sensorQ.setDescription(sensor.getDescription());
        sensorQ.setAlertThershold(sensor.getAlertThershold());
        sensorQ.setIndex(sensor.getIndex());
        sensorQ.setFctMode(sensor.getFctMode());
        sensorQ.setNormalThershold(sensor.getNormalThershold());
        sensorQ.setIdlocalOutput(sensor.getIdlocalOutput());
        sensorQ.setOutputValue(sensor.getOutputValue());
        sensorQ.setIdEndDevice(sensor.getIdEndDevice());
        Optional<endDeviceQ> optionalDevice = deviceRepo.findById(sensor.getIdEndDevice());
        if (optionalDevice.isPresent()) {
            endDeviceQ device = optionalDevice.get();
            List<String> ids = device.getIdSensors();
            if (ids == null) {
                ids = new ArrayList<>();
            }
            // Ajoute l'ID du capteur à la liste
            ids.add(sensor.getId().toString());
            device.setIdSensors(ids);
            deviceRepo.save(device);
        }
        repo.save(sensorQ);

    }

    @KafkaListener(topics = "Sensor_deleted", groupId = "Agri-group")
    public void handleDeleteSensorEvent(Long sensorId) {
        Optional<SensorQ> sensorOpt = repo.findById(sensorId.toString());
        if (sensorOpt.isPresent()) {
            SensorQ sensor = sensorOpt.get();

            // Récupérer l'EndDevice correspondant
            Optional<endDeviceQ> optionalDevice = deviceRepo.findById(sensor.getIdEndDevice());
            if (optionalDevice.isPresent()) {
                endDeviceQ device = optionalDevice.get();
                // Récupérer la liste des IDs de capteurs associés à cet EndDevice
                List<String> ids = device.getIdSensors();
                if (ids != null && ids.contains(sensor.getId())) {
                    // Retirer l'ID du capteur de la liste
                    ids.remove(sensor.getId());

                    // Mettre à jour l'EndDevice avec la nouvelle liste de capteurs
                    device.setIdSensors(ids);

                    // Sauvegarder l'EndDevice mis à jour
                    deviceRepo.save(device);
                }
            }
            repo.deleteById(sensorId.toString());
        }
        System.out.println("🔹 Événement Kafka reçu pour suppression du capteur ID: " + sensorId);

    }

    @KafkaListener(topics = "Sensor_updated", groupId = "Agri-group")
    public void consumeUpdateEvent(Sensor sensor){
        //On supprime le id du capteur dans le device
        List<endDeviceQ> devices = deviceRepo.findAll();
        for(endDeviceQ device : devices){
            List<String> ids = device.getIdSensors();
            if (ids != null && ids.contains(sensor.getId().toString())) {
                // Retirer l'ID du capteur de la liste
                ids.remove(sensor.getId().toString());

                // Mettre à jour l'EndDevice avec la nouvelle liste de capteurs
                device.setIdSensors(ids);
                deviceRepo.save(device);
            }
        }
        SensorQ sensorQ = new SensorQ();
        sensorQ.setId(sensor.getId().toString());
        Optional<SensorQ> existingSensorOpt = repo.findById(sensorQ.getId());
        if (existingSensorOpt.isPresent()) {
            SensorQ existingSensor = existingSensorOpt.get();
            existingSensor.setIdlocalOutput(sensor.getIdlocalOutput());
            existingSensor.setTypeSensor(sensor.getTypeSensor());
            existingSensor.setId(sensor.getId().toString());
            existingSensor.setDescription(sensor.getDescription());
            existingSensor.setAlertThershold(sensor.getAlertThershold());
            existingSensor.setIndex(sensor.getIndex());
            existingSensor.setFctMode(sensor.getFctMode());
            existingSensor.setNormalThershold(sensor.getNormalThershold());
            existingSensor.setOutputValue(sensor.getOutputValue());
            existingSensor.setIdEndDevice(sensor.getIdEndDevice());
            repo.save(existingSensor);

            Optional<endDeviceQ> optionalDevice = deviceRepo.findById(sensor.getIdEndDevice());
            if (optionalDevice.isPresent()) {
                endDeviceQ device = optionalDevice.get();
                List<String> ids = device.getIdSensors();
                if (ids == null) {
                    ids = new ArrayList<>();
                }
                // Ajoute l'ID du capteur à la liste
                ids.add(sensor.getId().toString());
                device.setIdSensors(ids);
                deviceRepo.save(device);
            }
        }

    }
}
