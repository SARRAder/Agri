package com.Agri.AgriBack.Command.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Sensor_Command")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int outputValue;
    private  int index;
    private int fctMode;
    private String description;
    private int alertThershold;
    private int normalThershold;
    private List<String> idlocalOutput = new ArrayList<>();
    private Sensors typeSensor;
    private String idEndDevice;

    public String getIdEndDevice() {
        return idEndDevice;
    }

    public void setIdEndDevice(String idEndDevice) {
        this.idEndDevice = idEndDevice;
    }

    public enum Sensors {
        bme680,
        soilMoisture,
        lightIntensity
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getIdlocalOutput() {
        return idlocalOutput;
    }

    public void setIdlocalOutput(List<String> idlocalOutput) {
        this.idlocalOutput = idlocalOutput;
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

    public Sensors getTypeSensor() {
        return typeSensor;
    }

    public void setTypeSensor(Sensors typeSensor) {
        this.typeSensor = typeSensor;
    }


    public Sensor() {
    }
}
