package com.example.demo.categories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    boolean existsByCategoryName(String categoryName);
    Optional<Category> findById(String id);

}
