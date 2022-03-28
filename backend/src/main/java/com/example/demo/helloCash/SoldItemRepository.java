package com.example.demo.helloCash;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoldItemRepository extends MongoRepository<SoldItem, String> {
}
