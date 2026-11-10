package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.dto.CompanyDto.*;
import com.deesha.medicine_distribution.model.MedicineCategories;
import com.deesha.medicine_distribution.repository.MedicineCategoriesRepository;
import com.deesha.medicine_distribution.service.MedicineCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineCategoriesServiceImpl implements MedicineCategoriesService {

    private final MedicineCategoriesRepository repository;

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        String name = request.categoryName().trim();
        if (repository.existsByCategoryNameIgnoreCase(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Category already exists: " + name);
        }
        MedicineCategories entity = new MedicineCategories();
        entity.setCategoryName(name);
        return toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getById(Integer categoryId) {
        return toResponse(findOrThrow(categoryId));
    }

    @Override
    @Transactional
    public CategoryResponse update(Integer categoryId, CategoryRequest request) {
        MedicineCategories entity = findOrThrow(categoryId);
        String name = request.categoryName().trim();
        if (repository.existsByCategoryNameIgnoreCaseAndCategoryIdNot(name, categoryId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Category already exists: " + name);
        }
        entity.setCategoryName(name);
        return toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer categoryId) {
        repository.delete(findOrThrow(categoryId));
    }

    // ---------- helpers ----------
    private MedicineCategories findOrThrow(Integer categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category not found with id: " + categoryId));
    }

    private CategoryResponse toResponse(MedicineCategories entity) {
        return new CategoryResponse(entity.getCategoryId(), entity.getCategoryName());
    }
}