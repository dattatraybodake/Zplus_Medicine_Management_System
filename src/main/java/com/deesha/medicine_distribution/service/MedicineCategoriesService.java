package com.deesha.medicine_distribution.service;

import com.deesha.medicine_distribution.dto.CompanyDto.*;

import java.util.List;

public interface MedicineCategoriesService {

    CategoryResponse create(CategoryRequest request);

    List<CategoryResponse> getAll();

    CategoryResponse getById(Integer categoryId);

    CategoryResponse update(Integer categoryId, CategoryRequest request);

    void delete(Integer categoryId);
}
