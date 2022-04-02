package com.example.demo.categories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

        Category categoryCreated = new Category();
        categoryCreated.setId("1");
        categoryCreated.setCategoryName("test");

        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        when(categoryRepository.existsByCategoryName("test")).thenReturn(true);

        CategoryService categoryService = new CategoryService(categoryRepository);
        ResponseEntity<Category> actual = categoryService.createCategory(categoryToCreate);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.valueOf(409));

    }

}