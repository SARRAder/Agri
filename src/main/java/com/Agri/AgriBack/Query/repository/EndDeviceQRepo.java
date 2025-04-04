package com.Agri.AgriBack.Query.repository;

import com.Agri.AgriBack.Query.entity.endDeviceQ;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndDeviceQRepo extends MongoRepository<endDeviceQ, String> {
}
