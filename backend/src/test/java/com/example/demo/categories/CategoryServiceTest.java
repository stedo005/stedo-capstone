package com.example.demo.categories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Test
    @DisplayName("should create a category")
    void test1 () {

        Category categoryToCreate = new Category();
        categoryToCreate.setCategoryName("test");

        Category categoryCreated = new Category();
        categoryCreated.setId("1");
        categoryCreated.setCategoryName("test");

        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        when(categoryRepository.save(categoryToCreate)).thenReturn(categoryCreated);

        CategoryService categoryService = new CategoryService(categoryRepository);
        ResponseEntity<Category> actual = categoryService.createCategory(categoryToCreate);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        verify(categoryRepository).save(categoryToCreate);

    }

    @Test
    @DisplayName("should not create a category")
    void test2 () {

        Category categoryToCreate = new Category();
        categoryToCreate.setCategoryName("test");

        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        when(categoryRepository.existsByCategoryName("test")).thenReturn(true);

        CategoryService categoryService = new CategoryService(categoryRepository);
        ResponseEntity<Category> actual = categoryService.createCategory(categoryToCreate);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.valueOf(409));

    }

    @Test
    @DisplayName("should delete a category")
    void test3 () {

        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        CategoryService categoryService = new CategoryService(categoryRepository);
        ResponseEntity<Void> actual = categoryService.deleteCategory("123");

        verify(categoryRepository).deleteById("123");
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));

    }

    @Test
    @DisplayName("should get items from category")
    void test4 () {

        Category category = new Category();
        category.setId("123");
        category.setItemsInCategory(List.of("item1", "item2"));

        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        when(categoryRepository.findById("123")).thenReturn(Optional.of(category));

        CategoryService categoryService = new CategoryService(categoryRepository);
        ResponseEntity<Category> actual = categoryService.getItemsInCategory("123");

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(Objects.requireNonNull(actual.getBody()).getItemsInCategory()).hasSize(2);

    }

}