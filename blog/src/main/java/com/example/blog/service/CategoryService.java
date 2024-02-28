package com.example.blog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.entity.Category;
import com.example.blog.helper.ResponseEntity;
import com.example.blog.model.CategoryModel;
import com.example.blog.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public ResponseEntity getAllCategory() {
        ResponseEntity response = new ResponseEntity();
        List<CategoryModel> listCategoryData = new ArrayList<>();
        try {

            response.setStatus("OK");
            List<Category> cateOptional = (List<Category>) categoryRepository.findAll();

            if (!cateOptional.isEmpty()) {
                for (Category categoryValue : cateOptional) {
                    CategoryModel categoryData = new CategoryModel();

                    categoryData.setCategoryId(categoryValue.getCategoryId());
                    categoryData.setCategoriesTitle(categoryValue.getCategoriesTitle());
                    categoryData.setCategoriesDescription(categoryValue.getCategoriesDescription());
                    categoryData.setCategoryCreateDateTime(categoryValue.getCategoryCreateDateTime());


                    listCategoryData.add(categoryData);
                }
                response.setMessage("Categories List are.");
                response.setData(listCategoryData);
            } else {
                response.setMessage("Categories List not available.");
            }

        } catch (Exception e) {
            response.setMessage("Something Went Wrong: " + e.getMessage());
            response.setStatus("NOT OK");
        }

        return response;
    }

    public ResponseEntity getCategory(Integer id) {
        ResponseEntity response = new ResponseEntity();
        try {
            Optional<Category> category = categoryRepository.findById(id);

            if (category.isPresent()) {
                response.setMessage("Category Data.");
                response.setStatus("OK");
                response.setData(category);
            } else {
                response.setMessage("Category Value with that id" + id + " not Present.");
                response.setStatus("OK");
            }
        } catch (Exception e) {
            response.setMessage("Something Went Wrong:" + e);
            response.setStatus("OK");
        }

        return response;
    }

    public ResponseEntity createNewCategory(CategoryModel categoryData) {
        ResponseEntity response = new ResponseEntity();

        try {
            Category category = new Category();

            category.setCategoriesTitle(categoryData.getCategoriesTitle());
            category.setCategoriesDescription(categoryData.getCategoriesDescription());
            category.setCategoryCreateDateTime(LocalDateTime.now());

            Category savedCategory = categoryRepository.save(category);
            response.setMessage("Category Data has been saved.");
            response.setStatus("OK");
            response.setData(savedCategory);

        } catch (Exception e) {
            response.setMessage("Something Went Wrong:" + e);
            response.setStatus("NOT OK");
        }

        return response;
    }

    public ResponseEntity updateCategoryData(Integer id, CategoryModel categoryData) {
        ResponseEntity response = new ResponseEntity();

        try {

            Optional<Category> category = categoryRepository.findById(id);

            if (category.isPresent()) {

                if (categoryData.getCategoriesTitle() != null) {
                    category.get().setCategoriesTitle(categoryData.getCategoriesTitle());
                }
                if (categoryData.getCategoriesDescription() != null) {
                    category.get().setCategoriesDescription(categoryData.getCategoriesDescription());
                }
                categoryRepository.save(category.get());
                response.setMessage("Category Data with that id " + id + " Updated.");
                response.setStatus("OK");
                response.setData(category);
            } else {
                response.setMessage("Category Value is not Present.");
                response.setStatus("OK");
            }

        } catch (Exception e) {
            response.setMessage("Something Went Wrong:" + e);
            response.setStatus("NOT OK");
        }
        return response;
    }

    public ResponseEntity removeCategory(Integer id) {
        ResponseEntity response = new ResponseEntity();
        try {
            Optional<Category> category = categoryRepository.findById(id);

            if (category.isPresent()) {
                categoryRepository.deleteById(id);
                response.setMessage("Category Data Deleted.");
                response.setStatus("OK");
                response.setData(category);
            } else {
                response.setMessage("Category Value with that id " + id + " not Present.");
                response.setStatus("OK");
            }
        } catch (Exception e) {
            response.setMessage("Something Went Wrong:" + e);
            response.setStatus("OK");
        }

        return response;

    }

}
