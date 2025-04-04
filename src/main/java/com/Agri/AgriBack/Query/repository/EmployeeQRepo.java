package com.Agri.AgriBack.Query.repository;

import com.Agri.AgriBack.Query.entity.EmployeeQ;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeQRepo extends MongoRepository<EmployeeQ, String> {
}
