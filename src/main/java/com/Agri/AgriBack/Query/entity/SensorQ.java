package com.Agri.AgriBack.Query.entity;

import com.Agri.AgriBack.Command.entity.Sensor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Sensor")
public class SensorQ {
    @Id
    private String id;
    private int outputValue;
    private  int index;
    private int fctMode;
    private String description;
    private int alertThershold;
    private int normalThershold;
    private Sensor.Sensors typeSensor;
    private List<String> idlocalOutput = new ArrayList<>();
    @DBRef
    private endDeviceQ EndDevice;

    public endDeviceQ getEndDevice() {
        return EndDevice;
    }

    public void setEndDevice(endDeviceQ endDevice) {
        EndDevice = endDevice;
    }

    public List<String> getIdlocalOutput() {
        return idlocalOutput;
    }

    public void setIdlocalOutput(List<String> idlocalOutput) {
        this.idlocalOutput = idlocalOutput;
    }

    public Sensor.Sensors getTypeSensor() {
        return typeSensor;
    }

    public void setTypeSensor(Sensor.Sensors typeSensor) {
        this.typeSensor = typeSensor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(int outputValue) {
        this.outputValue = outputValue;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getFctMode() {
        return fctMode;
    }

    public void setFctMode(int fctMode) {
        this.fctMode = fctMode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAlertThershold() {
        return alertThershold;
    }

    public void setAlertThershold(int alertThershold) {
        this.alertThershold = alertThershold;
    }

    public int getNormalThershold() {
        return normalThershold;
    }

    public void setNormalThershold(int normalThershold) {
        this.normalThershold = normalThershold;
    }

    public SensorQ() {
    }
}
