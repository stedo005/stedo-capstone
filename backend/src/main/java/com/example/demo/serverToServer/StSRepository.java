package com.example.demo.serverToServer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StSRepository extends MongoRepository<StSModell, String> {
}
