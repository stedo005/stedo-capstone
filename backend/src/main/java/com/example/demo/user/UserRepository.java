package com.example.demo.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserData, String> {

    UserData getLastUpdateByUsername(String username);
    Optional<UserData> findByUsername(String username);

}
