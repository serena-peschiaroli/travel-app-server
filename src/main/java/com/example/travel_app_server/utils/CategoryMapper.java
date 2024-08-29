package com.example.travel_app_server.utils;

import com.example.travel_app_server.dto.CategoryDto;
import com.example.travel_app_server.models.Category;

public class CategoryMapper {

    public static CategoryDto toDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .color(category.getColor())
                .build();

    }

    public static Category toEntity(CategoryDto categoryDto){
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .color(categoryDto.getColor())
                .build();
    }
}
