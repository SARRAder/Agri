package com.Agri.AgriBack.Query.services;

import com.Agri.AgriBack.Query.entity.EmployeeQ;
import com.Agri.AgriBack.Query.repository.EmployeeQRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeQService {

    @Autowired
    private EmployeeQRepo repo;

    public List<EmployeeQ> getAllEmployees() {
        return repo.findAll();
    }
}
