package com.example.demo.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseEntity<Category> createCategory(Category category) {

        if(category.getCategoryName().isBlank()){
            return ResponseEntity.status(405).build();
        }else if ((categoryRepository.existsByCategoryName(category.getCategoryName()))) {
            return ResponseEntity.status(409).body(category);
        } else {
            categoryRepository.save(category);
            return ResponseEntity.status(201).build();
        }

    }

    public List<Category> getCategories() {
        return categoryRepository.findAll(Sort.by("categoryName"));
    }

    public void addItemsToCategory(Category category) {
        categoryRepository.save(category);
    }

    public Category getItemsInCategory(String categoryId) {
        if (categoryRepository.findById(categoryId).isPresent()) {
            return categoryRepository.findById(categoryId).get();
        }
        throw new RuntimeException();
    }
}
