package com.Agri.AgriBack.Command.entity;

import com.Agri.AgriBack.Query.entity.SensorQ;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EndDevice_Command")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignore les références Hibernate inutiles
public class endDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codDevice;
    private int nivBat;
    private List<String> idlocalOutput = new ArrayList<>();

    @OneToMany(mappedBy = "endDevice", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Marque cette relation comme la racine
    private List<Sensor> sensors = new ArrayList<>();

    public List<String> getIdlocalOutput() {
        return idlocalOutput;
    }

    public void setIdlocalOutput(List<String> idlocalOutput) {
        this.idlocalOutput = idlocalOutput;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public endDevice() {
    }
}
