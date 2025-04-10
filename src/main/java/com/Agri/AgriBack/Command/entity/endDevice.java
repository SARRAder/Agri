package com.Agri.AgriBack.Command.entity;

import com.Agri.AgriBack.Query.entity.SensorQ;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EndDevice_Command")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignore les références Hibernate inutiles
//@EqualsAndHashCode(exclude = { "sensors"}) // This,
//@ToString(exclude = { "sensors"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class endDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codDevice;
    private int nivBat;
    private List<String> idlocalOutput = new ArrayList<>();

    //@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    //@JsonManagedReference // Marque cette relation comme la racine
    @OneToMany(mappedBy = "endDevice", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY )

    private List<Sensor> sensors = new ArrayList<>();
    //@JsonIgnore
    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

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


    public endDevice() {
    }
}
