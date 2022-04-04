package com.example.demo.categories;

import lombok.RequiredArgsConstructor;
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

        return categoryRepository.findAll().stream()
                .sorted((c1, c2) -> c1.getCategoryName().compareTo(c2.getCategoryName()))
                .toList();
    }

    public void addItemsToCategory(Category category) {
        categoryRepository.save(category);
    }

    public ResponseEntity<Category> getItemsInCategory(String categoryId) {
        if (categoryRepository.findById(categoryId).isPresent()) {
            return ResponseEntity.status(200).body(categoryRepository.findById(categoryId).get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> deleteCategory(String id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
