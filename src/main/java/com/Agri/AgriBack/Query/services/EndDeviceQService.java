package com.Agri.AgriBack.Query.services;

import com.Agri.AgriBack.Query.entity.endDeviceQ;
import com.Agri.AgriBack.Query.repository.EndDeviceQRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EndDeviceQService {

    @Autowired
    private EndDeviceQRepo repo;

    public List<endDeviceQ> getAllDevices() {
        return repo.findAll();
        //return repo.findAllWithSensors();
    }
}
