package com.Agri.AgriBack.Command.mapper;

import com.Agri.AgriBack.Command.entity.Sensor;
import com.Agri.AgriBack.Command.entity.endDevice;
import com.Agri.AgriBack.Query.entity.SensorQ;
import com.Agri.AgriBack.Query.entity.endDeviceQ;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@Component
public class EndDeviceCToQ {

    public endDeviceQ mapperDevice(endDevice dev) {
        if (dev == null) return null;

        endDeviceQ deviceQ = new endDeviceQ();
        deviceQ.setId(dev.getId().toString());
        deviceQ.setCodDevice(dev.getCodDevice());
        deviceQ.setNivBat(dev.getNivBat());
        deviceQ.setIdlocalOutput(new ArrayList<>(dev.getIdlocalOutput()));

        List<SensorQ> sensorsQ = new ArrayList<>();
        if (dev.getSensors() != null) {
            for (Sensor sensor : dev.getSensors()) {
                SensorQ sq = mapperSensor(sensor);
                //sq.setEndDevice(deviceQ); // Important pour la relation bidirectionnelle
                sensorsQ.add(sq);
            }
        }

        deviceQ.setSensors(sensorsQ);

        return deviceQ;
    }

    private SensorQ mapperSensor(Sensor sensor) {
        if (sensor == null) return null;

        SensorQ sq = new SensorQ();
        sq.setId(sensor.getId().toString());
        sq.setOutputValue(sensor.getOutputValue());
        sq.setIndex(sensor.getIndex());
        sq.setFctMode(sensor.getFctMode());
        sq.setDescription(sensor.getDescription());
        sq.setAlertThershold(sensor.getAlertThershold());
        sq.setNormalThershold(sensor.getNormalThershold());
        sq.setTypeSensor(sensor.getTypeSensor());
        sq.setIdlocalOutput(new ArrayList<>(sensor.getIdlocalOutput()));

        return sq;
    }

}
