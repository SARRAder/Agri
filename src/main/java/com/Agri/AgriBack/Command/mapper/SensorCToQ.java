package com.Agri.AgriBack.Command.mapper;

import com.Agri.AgriBack.Command.entity.Sensor;
import com.Agri.AgriBack.Query.entity.SensorQ;
import com.Agri.AgriBack.Query.entity.endDeviceQ;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@NoArgsConstructor
@Component
public class SensorCToQ {
    public SensorQ mapperSensor(Sensor sensor) {
        SensorQ sensorQ = new SensorQ();

        sensorQ.setId(sensor.getId().toString());
        sensorQ.setOutputValue(sensor.getOutputValue());
        sensorQ.setIndex(sensor.getIndex());
        sensorQ.setFctMode(sensor.getFctMode());
        sensorQ.setDescription(sensor.getDescription());
        sensorQ.setAlertThershold(sensor.getAlertThershold());
        sensorQ.setNormalThershold(sensor.getNormalThershold());
        sensorQ.setTypeSensor(sensor.getTypeSensor());
        sensorQ.setIdlocalOutput(new ArrayList<>(sensor.getIdlocalOutput()));

        // Si l'EndDevice existe, le mapper en endDeviceQ et l'ajouter à sensorQ
        if (sensor.getEndDevice() != null) {
            EndDeviceCToQ deviceMapper = new EndDeviceCToQ();
            endDeviceQ deviceQ = deviceMapper.mapperDevice(sensor.getEndDevice());
            sensorQ.setEndDevice(deviceQ);  // Assigner le endDeviceQ au capteur
        }
        return sensorQ;
    }
}
