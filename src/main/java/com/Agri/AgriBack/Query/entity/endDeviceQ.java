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

import java.util.ArrayList;
import java.util.List;
//@EqualsAndHashCode(callSuper = true)
@EqualsAndHashCode(exclude = { "sensors"}) // This,
@ToString(exclude = { "sensors"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "EndDevice")
public class endDeviceQ {
    @Id
    private String id;
    private String codDevice;
    private int nivBat;

    @DBRef
    private List<SensorQ> sensors ;
    private List<String> idlocalOutput = new ArrayList<>();

    public List<SensorQ> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorQ> sensors) {
        this.sensors = sensors;
    }

    public List<String> getIdlocalOutput() {
        return idlocalOutput;
    }

    public void setIdlocalOutput(List<String> idlocalOutput) {
        this.idlocalOutput = idlocalOutput;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodDevice() {
        return codDevice;
    }

    public void setCodDevice(String codDevice) {
        this.codDevice = codDevice;
    }

    public int getNivBat() {
        return nivBat;
    }

    public void setNivBat(int nivBat) {
        this.nivBat = nivBat;
    }

    public endDeviceQ() {
    }
}
