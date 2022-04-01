package com.example.demo.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseEntity<Void> createCategory(Category category) {

        if ((categoryRepository.existsByCategoryName(category.getCategoryName()))) {
            return ResponseEntity.status(409).build();
        } else {
            categoryRepository.save(category);
            return ResponseEntity.status(201).build();
        }

    }

}
