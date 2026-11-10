//package com.deesha.medicine_distribution.service;
//
//import com.deesha.medicine_distribution.dto.CompanyDto.*;
//import java.util.List;
//
//public interface MedicineService {
//
//    MedicineResponse create(MedicineRequest request);
//
//    List<MedicineResponse> getAll();
//
//    List<MedicineResponse> getByCategory(Integer categoryId);
//
//    MedicineResponse getById(Long medicineId);
//
//    MedicineResponse update(Long medicineId, MedicineRequest request);
//
//    /** Soft delete: marks the record as deleted. */
//    void delete(Long medicineId, String modifiedBy);
//}