package com.example.travel_app_server.services;

import com.example.travel_app_server.dto.CategoryDto;
import com.example.travel_app_server.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
  CategoryDto addCategory(CategoryDto categoryDto);
  CategoryDto getCategoryById(Long id);
  List<CategoryDto> getAllCategories();

  CategoryDto updateCategory(Long id, CategoryDto categoryDto );
  void deleteCategory(Long id);



}
