package com.Agri.AgriBack.Command.services;

import com.Agri.AgriBack.Command.dto.SensorProducer;
import com.Agri.AgriBack.Command.entity.Sensor;
import com.Agri.AgriBack.Command.entity.endDevice;
import com.Agri.AgriBack.Command.repository.SensorCRepo;
import com.Agri.AgriBack.Command.repository.endDeviceCRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Long deviceId = Long.parseLong(sensor.getIdEndDevice());
        Sensor savedSensor = this.repo.save(sensor);
        Optional<endDevice> optionalDevice = deviceRepo.findById(deviceId);
        if (optionalDevice.isPresent()) {
            endDevice device = optionalDevice.get();
            List<String> ids = device.getIdSensors();
            if (ids == null) {
                ids = new ArrayList<>();
            }
            // Ajoute l'ID du capteur à la liste
            ids.add(sensor.getId().toString());
            device.setIdSensors(ids);
            deviceRepo.save(device);
        }

        this.producer.createSensorEvent(savedSensor);
        return savedSensor;
    }

    public Sensor UpdateSensor(Sensor sensor) {
        List<endDevice> devices = deviceRepo.findAll();
        for(endDevice device : devices){
            List<String> ids = device.getIdSensors();
            if (ids != null && ids.contains(sensor.getId().toString())) {
                // Retirer l'ID du capteur de la liste
                ids.remove(sensor.getId().toString());

                // Mettre à jour l'EndDevice avec la nouvelle liste de capteurs
                device.setIdSensors(ids);
                deviceRepo.save(device);
            }
        }
        Long deviceId = Long.parseLong(sensor.getIdEndDevice());
        Optional<endDevice> optionalDevice = deviceRepo.findById(deviceId);
        if (optionalDevice.isPresent()) {
            endDevice device = optionalDevice.get();
            List<String> ids = device.getIdSensors();
            if (ids == null) {
                ids = new ArrayList<>();
            }
            // Ajoute l'ID du capteur à la liste
            ids.add(sensor.getId().toString());
            device.setIdSensors(ids);
            deviceRepo.save(device);
        }
        Sensor savedSensor = this.repo.save(sensor);
        this.producer.updateSensorEvent(savedSensor);
        return savedSensor;
    }

    public void deleteSensor(Long id) {
        if (repo.existsById(id)) {
            Optional<Sensor> sensorOpt = repo.findById(id);
            if (sensorOpt.isPresent()) {
                Sensor sensor = sensorOpt.get();
                Long deviceId = Long.parseLong(sensor.getIdEndDevice());

                // Récupérer l'EndDevice correspondant
                Optional<endDevice> optionalDevice = deviceRepo.findById(deviceId);
                if (optionalDevice.isPresent()) {
                    endDevice device = optionalDevice.get();

                    // Récupérer la liste des IDs de capteurs associés à cet EndDevice
                    List<String> ids = device.getIdSensors();
                    if (ids != null && ids.contains(sensor.getId().toString())) {
                        // Retirer l'ID du capteur de la liste
                        ids.remove(sensor.getId().toString());

                        // Mettre à jour l'EndDevice avec la nouvelle liste de capteurs
                        device.setIdSensors(ids);

                        // Sauvegarder l'EndDevice mis à jour
                        deviceRepo.save(device);
                    }
                }
                repo.deleteById(id);
                producer.deleteSensorEvent(id); // Publier l'événement Kafka
            } else {
                throw new RuntimeException("L'appareil avec l'ID " + id + " n'existe pas.");
            }
        }
    }
}
