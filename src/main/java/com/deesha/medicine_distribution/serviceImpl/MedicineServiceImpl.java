//package com.deesha.medicine_distribution.serviceImpl;
//
//import com.deesha.medicine_distribution.dto.CompanyDto.*;
//import com.deesha.medicine_distribution.model.Medicine;
//import com.deesha.medicine_distribution.model.MedicineCategories;
//import com.deesha.medicine_distribution.repository.MedicineCategoriesRepository;
//import com.deesha.medicine_distribution.repository.MedicineRepository;
//import com.deesha.medicine_distribution.service.MedicineService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class MedicineServiceImpl implements MedicineService {
//
//    private final MedicineRepository medicineRepository;
//    private final MedicineCategoriesRepository categoriesRepository;
//
//    @Override
//    @Transactional
//    public MedicineResponse create(MedicineRequest request) {
//        Medicine entity = new Medicine();
//        applyRequest(entity, request);
//        entity.setCreatedBy(request.createdBy());
//        entity.setIsDeleted(false);
//        return toResponse(medicineRepository.save(entity));
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<MedicineResponse> getAll() {
//        return medicineRepository.findByIsDeletedFalse().stream().map(this::toResponse).toList();
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<MedicineResponse> getByCategory(Integer categoryId) {
//        return medicineRepository.findByMedicineCategory_CategoryIdAndIsDeletedFalse(categoryId)
//                .stream().map(this::toResponse).toList();
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public MedicineResponse getById(Long medicineId) {
//        return toResponse(findOrThrow(medicineId));
//    }
//
//    @Override
//    @Transactional
//    public MedicineResponse update(Long medicineId, MedicineRequest request) {
//        Medicine entity = findOrThrow(medicineId);
//        applyRequest(entity, request);
//        entity.setModifiedBy(request.modifiedBy());
//        return toResponse(medicineRepository.save(entity));
//    }
//
//    @Override
//    @Transactional
//    public void delete(Long medicineId, String modifiedBy) {
//        Medicine entity = findOrThrow(medicineId);
//        entity.setIsDeleted(true);
//        entity.setModifiedBy(modifiedBy);
//        medicineRepository.save(entity);
//    }
//
//    // ---------- helpers ----------
//    private Medicine findOrThrow(Long medicineId) {
//        return medicineRepository.findByMedicineIdAndIsDeletedFalse(medicineId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
//                        "Medicine not found with id: " + medicineId));
//    }
//
//    private void applyRequest(Medicine entity, MedicineRequest r) {
//        MedicineCategories category = categoriesRepository.findById(r.medicineCategoryId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                        "Invalid medicine category id: " + r.medicineCategoryId()));
//
//        entity.setMedicineName(r.medicineName().trim());
//        entity.setBrandName(r.brandName());
//        entity.setMedicineType(r.medicineType());
//        entity.setPreciseGenericName(r.preciseGenericName());
//        entity.setCommercialBrandName(r.commercialBrandName());
//        entity.setPrice(r.price());
//        entity.setUnitOfMeasure(r.unitOfMeasure());
//        entity.setTaxCategory(r.taxCategory());
//        entity.setGstPercent(r.gstPercent());
//        entity.setTotalQuantity(r.totalQuantity());
//        entity.setMedicineCategory(category);
//    }
//
//    private MedicineResponse toResponse(Medicine m) {
//        MedicineCategories c = m.getMedicineCategory();
//        return new MedicineResponse(
//                m.getMedicineId(),
//                m.getMedicineName(),
//                m.getBrandName(),
//                m.getMedicineType(),
//                m.getPreciseGenericName(),
//                m.getCommercialBrandName(),
//                m.getPrice(),
//                m.getUnitOfMeasure(),
//                m.getTaxCategory(),
//                m.getGstPercent(),
//                m.getTotalQuantity(),
//                c != null ? c.getCategoryId() : null,
//                c != null ? c.getCategoryName() : null,
//                m.getCreatedBy(),
//                m.getCreatedAt(),
//                m.getModifiedBy(),
//                m.getModifiedAt(),
//                m.getIsDeleted()
//        );
//    }
//}