package com.example.demo.lastUpdate;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastUpdateRepository extends MongoRepository<LastUpdate, String> {
    LastUpdate getLastUpdateByUsername(String username);
}
