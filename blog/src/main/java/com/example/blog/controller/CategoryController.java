package com.example.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.helper.ResponseEntity;
import com.example.blog.model.CategoryModel;
import com.example.blog.service.CategoryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@ResponseBody
@RequestMapping("/api")
public class CategoryController {
    
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity getCategory(){
        return categoryService.getAllCategory();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity getSingleCategory(@PathVariable Integer id){
        return categoryService.getCategory(id);
    }

    @PostMapping("/category/create")
    public ResponseEntity createCategory(@RequestBody CategoryModel categoryData){
        return categoryService.createNewCategory(categoryData);
    }

    
    @PostMapping("/category/update/{id}")
    public ResponseEntity updateCategory(@PathVariable Integer id,@RequestBody CategoryModel categoryData){
        return categoryService.updateCategoryData(id,categoryData);
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id){
        return categoryService.removeCategory(id);
    }

    
    
    
    
}
