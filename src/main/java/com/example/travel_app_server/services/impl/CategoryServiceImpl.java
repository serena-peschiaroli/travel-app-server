package com.example.travel_app_server.services.impl;

import com.example.travel_app_server.dto.CategoryDto;
import com.example.travel_app_server.models.Category;
import com.example.travel_app_server.repositories.CategoryRepository;
import com.example.travel_app_server.services.CategoryService;
import com.example.travel_app_server.utils.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = CategoryMapper.toEntity(categoryDto);

        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {

        //fetch category
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Categoru not found with id" + id));
        //convert entity to dto


        return CategoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        //fetch all category entities and convert to dtos
        return categoryRepository.findAll().stream().map(CategoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        //fetcj existing category
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("category not found with id" + id));

        //update existing category
        existingCategory.setName(categoryDto.getName());
        existingCategory.setColor(categoryDto.getColor());

        //save updated entity
        Category updatedCategory = categoryRepository.save(existingCategory);

        //convert to dto


        return CategoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);

    }
}
