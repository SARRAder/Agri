package com.Agri.AgriBack.Command.entity;

import java.util.ArrayList;
import java.util.List;

public class AddDevice {
    private String codDevice;
    private List<String> idSensors = new ArrayList<>();

    public List<String> getIdSensors() {
        return idSensors;
    }

    public void setIdSensors(List<String> idSensors) {
        this.idSensors = idSensors;
    }

    public String getCodDevice() {
        return codDevice;
    }

    public void setCodDevice(String codDevice) {
        this.codDevice = codDevice;
    }
}
