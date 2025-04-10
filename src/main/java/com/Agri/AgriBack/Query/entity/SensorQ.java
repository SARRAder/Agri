package com.Agri.AgriBack.Query.entity;

import com.Agri.AgriBack.Command.entity.Sensor;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.Agri.AgriBack.Command.entity.endDevice;

import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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
    //@JsonIgnore
    @DBRef
    private endDeviceQ endDevice;


    public endDeviceQ getEndDevice() {
        return endDevice;
    }

    public void setEndDevice(endDeviceQ endDevice) {
        this.endDevice = endDevice;
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
