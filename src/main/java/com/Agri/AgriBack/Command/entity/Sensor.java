package com.Agri.AgriBack.Command.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Sensor_Command")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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
    @ManyToOne
    //@JsonBackReference
    @JoinColumn(name = "end_device_id") // Clé étrangère dans la table Sensor
    //@JsonIgnore
    private endDevice endDevice;

    public com.Agri.AgriBack.Command.entity.endDevice getEndDevice() {
        return endDevice;
    }

    public void setEndDevice(com.Agri.AgriBack.Command.entity.endDevice endDevice) {
        this.endDevice = endDevice;
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
