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
        // Supprimer la référence circulaire des capteurs avant de les stocker
        //Créer une copie des Sensors sans la référence à endDevice
        List<Sensor> sensorsWithoutLoop = device.getSensors().stream().map(sensor -> {
            Sensor newSensor = new Sensor();
            newSensor.setId(sensor.getId());
            newSensor.setOutputValue(sensor.getOutputValue());
            newSensor.setIndex(sensor.getIndex());
            newSensor.setFctMode(sensor.getFctMode());
            newSensor.setDescription(sensor.getDescription());
            newSensor.setAlertThershold(sensor.getAlertThershold());
            newSensor.setNormalThershold(sensor.getNormalThershold());
            newSensor.setIdlocalOutput(sensor.getIdlocalOutput());
            newSensor.setTypeSensor(sensor.getTypeSensor());

            return newSensor; // Ne pas réassigner `setEndDevice` pour éviter la boucle
        }).collect(Collectors.toList());

        endDevice.setSensors(sensorsWithoutLoop);
        repo.save(endDevice);

        // Enregistrement dans la collection Sensor (si la liste n'est pas vide)
        if (!sensorsWithoutLoop.isEmpty()) {
            // Création d'une nouvelle liste pour SensorQ si nécessaire
            List<SensorQ> sensorsQ = sensorsWithoutLoop.stream()
                    .map(sensor -> {
                        SensorQ sensorQ = new SensorQ();
                        // Copie des propriétés de Sensor vers SensorQ
                        sensorQ.setId(sensor.getId().toString());
                        sensorQ.setOutputValue(sensor.getOutputValue());
                        sensorQ.setIndex(sensor.getIndex());
                        sensorQ.setFctMode(sensor.getFctMode());
                        sensorQ.setDescription(sensor.getDescription());
                        sensorQ.setAlertThershold(sensor.getAlertThershold());
                        sensorQ.setNormalThershold(sensor.getNormalThershold());
                        sensorQ.setIdlocalOutput(sensor.getIdlocalOutput());
                        sensorQ.setTypeSensor(sensor.getTypeSensor());
                        sensorQ.setIdEndDevice(device.getId().toString());
                        return sensorQ;
                    })
                    .collect(Collectors.toList());

            // Enregistrement dans MongoDB
            sensorRepo.saveAll(sensorsQ);
        }
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
            List<Sensor> sensorsWithoutLoop = device.getSensors().stream().map(sensor -> {
                Sensor newSensor = new Sensor();
                newSensor.setId(sensor.getId());
                newSensor.setOutputValue(sensor.getOutputValue());
                newSensor.setIndex(sensor.getIndex());
                newSensor.setFctMode(sensor.getFctMode());
                newSensor.setDescription(sensor.getDescription());
                newSensor.setAlertThershold(sensor.getAlertThershold());
                newSensor.setNormalThershold(sensor.getNormalThershold());
                newSensor.setIdlocalOutput(sensor.getIdlocalOutput());
                newSensor.setTypeSensor(sensor.getTypeSensor());
                return newSensor; // Ne pas réassigner `setEndDevice` pour éviter la boucle
            }).collect(Collectors.toList());

            existingDev.setSensors(sensorsWithoutLoop);
            repo.save(existingDev);

        }
    }

    //@KafkaListener(topics = "EndDevice_deleted", groupId = "Agri-group")
    public void handleDeleteEndDevice(Long deviceId) {
        String id = deviceId.toString();

        // 1. Trouver le device
        endDeviceQ device = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found with id: " + id));

        if (device.getSensors() != null && !device.getSensors().isEmpty()) {
            // Convertir les Sensor en SensorQ avant suppression
            List<SensorQ> sensorsQ = device.getSensors().stream()
                    .map(sensor -> {
                        SensorQ sensorQ = new SensorQ();
                        // Copier les propriétés nécessaires de Sensor vers SensorQ
                        sensorQ.setId(sensor.getId().toString());
                        sensorQ.setOutputValue(sensor.getOutputValue());
                        sensorQ.setIndex(sensor.getIndex());
                        sensorQ.setFctMode(sensor.getFctMode());
                        sensorQ.setDescription(sensor.getDescription());
                        sensorQ.setAlertThershold(sensor.getAlertThershold());
                        sensorQ.setNormalThershold(sensor.getNormalThershold());
                        sensorQ.setIdlocalOutput(sensor.getIdlocalOutput());
                        sensorQ.setTypeSensor(sensor.getTypeSensor());
                        sensorQ.setIdEndDevice(device.getId().toString());
                        return sensorQ;
                    })
                    .collect(Collectors.toList());

            sensorRepo.deleteAll(sensorsQ);
        }

        // 3. Supprimer le device
        repo.deleteById(id);
    }
}
