package com.example.blog.controller;

import com.example.blog.model.CategoryModel;
import com.example.blog.service.CategoryService;
import com.example.blog.helper.RequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*") // You can specify frontend domain here
@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<RequestResponse> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<RequestResponse> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @PostMapping("/category/create")
    public ResponseEntity<RequestResponse> createCategory(@RequestBody CategoryModel categoryData) {
        return ResponseEntity.ok(categoryService.createNewCategory(categoryData));
    }

    @PutMapping("/category/update/{id}")
    public ResponseEntity<RequestResponse> updateCategory(@PathVariable Integer id,
            @RequestBody CategoryModel categoryData) {
        return ResponseEntity.ok(categoryService.updateCategoryData(id, categoryData));
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<RequestResponse> deleteCategory(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.removeCategory(id));
    }
}
