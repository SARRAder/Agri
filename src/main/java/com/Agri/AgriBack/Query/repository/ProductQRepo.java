package com.Agri.AgriBack.Query.repository;

import com.Agri.AgriBack.Query.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


    public interface ProductQRepo extends MongoRepository<Product, String> {

    }

