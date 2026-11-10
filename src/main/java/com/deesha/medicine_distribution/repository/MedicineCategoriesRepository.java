package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.MedicineCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MedicineCategoriesRepository extends JpaRepository<MedicineCategories, Integer> {

    boolean existsByCategoryNameIgnoreCase(String categoryName);

    boolean existsByCategoryNameIgnoreCaseAndCategoryIdNot(String categoryName, Integer categoryId);
}
