package com.Agri.AgriBack.Command.services;

import com.Agri.AgriBack.Command.producer.SensorProducer;
import com.Agri.AgriBack.Command.entity.Sensor;
import com.Agri.AgriBack.Command.repository.SensorCRepo;
import com.Agri.AgriBack.Command.repository.endDeviceCRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorCService {

    @Autowired
    private SensorCRepo repo;
    @Autowired
    private SensorProducer producer;
    @Autowired
    private endDeviceCRepo deviceRepo;

    public Sensor CreateSensor(Sensor sensor) {
       // Long deviceId = Long.parseLong(sensor.getEndDevice().getId());
        Sensor savedSensor = this.repo.save(sensor);
        this.producer.createSensorEvent(savedSensor);
        return savedSensor;
    }

    public Sensor UpdateSensor(Sensor sensor) {
        Sensor savedSensor = this.repo.save(sensor);
        this.producer.updateSensorEvent(savedSensor);
        return savedSensor;
    }

    public void deleteSensor(Long id) {
        if (repo.existsById(id)) {

                repo.deleteById(id);
                producer.deleteSensorEvent(id); // Publier l'événement Kafka
        }
        else
            {
                throw new RuntimeException("L'appareil avec l'ID " + id + " n'existe pas.");
            }
    }
}

