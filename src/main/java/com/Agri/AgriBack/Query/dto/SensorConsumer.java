package com.Agri.AgriBack.Query.dto;

import com.Agri.AgriBack.Command.entity.Sensor;
import com.Agri.AgriBack.Command.entity.endDevice;
import com.Agri.AgriBack.Query.entity.SensorQ;
import com.Agri.AgriBack.Query.entity.endDeviceQ;
import com.Agri.AgriBack.Query.repository.SensorQRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Optional;

public class SensorConsumer {

    @Autowired
    private SensorQRepo repo;

    @KafkaListener(topics = "Sensor_created", groupId = "Agri-group-1")
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
        sensorQ.setIdEndDevice(sensor.getEndDevice().getId().toString());
        repo.save(sensorQ);

    }

    @KafkaListener(topics = "Sensor_deleted", groupId = "Agri-group-1")
    public void handleDeleteSensorEvent(Long sensorId) {
        System.out.println("🔹 Événement Kafka reçu pour suppression du capteur ID: " + sensorId);
        repo.deleteById(sensorId.toString());
    }

    @KafkaListener(topics = "Sensor_updated", groupId = "Agri-group-1")
    public void consumeUpdateEvent(Sensor sensor){
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
            existingSensor.setIdEndDevice(sensor.getEndDevice().getId().toString());
            repo.save(existingSensor);
        }

    }
}
