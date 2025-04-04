package com.Agri.AgriBack.Query.services;

import com.Agri.AgriBack.Query.entity.SensorQ;
import com.Agri.AgriBack.Query.repository.SensorQRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorQService {

    @Autowired
    private SensorQRepo repo;

    public List<SensorQ> getAllSensors(){
        return repo.findAll();
    }
}
