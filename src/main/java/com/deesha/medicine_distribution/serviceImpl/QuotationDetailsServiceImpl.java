//package com.deesha.medicine_distribution.serviceImpl;
//
//import com.deesha.medicine_distribution.dto.QuotationDto.QuatationDetailsRequest;
//import com.deesha.medicine_distribution.dto.QuotationDto.QuatationDetailsResponse;
//import com.deesha.medicine_distribution.dto.QuotationDto.QuatationDetailsResponse.MedicineSummary;
//import com.deesha.medicine_distribution.dto.QuotationDto.QuatationDetailsResponse.ManufactureCompanySummary;
//import com.deesha.medicine_distribution.exception.ResourceNotFoundException;
//import com.deesha.medicine_distribution.model.Medicine;
//import com.deesha.medicine_distribution.model.ManufactureCompany;
//import com.deesha.medicine_distribution.model.QuotationDetails;
//import com.deesha.medicine_distribution.repository.MedicineRepository;
//import com.deesha.medicine_distribution.repository.CompaniesRepository;
//import com.deesha.medicine_distribution.repository.QuotationDetailsRepository;
//import com.deesha.medicine_distribution.service.QuotationDetailsService;
//import jakarta.transaction.Transactional;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@Transactional
//public class QuotationDetailsServiceImpl implements QuotationDetailsService {
//
//    private final QuotationDetailsRepository repository;
//    private final MedicineRepository medicineRepository;
//    private final CompaniesRepository companyRepository;
//
//    public QuotationDetailsServiceImpl(QuotationDetailsRepository repository,
//                                       MedicineRepository medicineRepository,
//                                       CompaniesRepository companyRepository) {
//        this.repository = repository;
//        this.medicineRepository = medicineRepository;
//        this.companyRepository = companyRepository;
//    }
//
//    // ---------- Create ----------
//
//    @Override
//    public QuatationDetailsResponse create(Long quotationId, QuatationDetailsRequest request) {
//        Medicine medicine = medicineRepository.findById(request.medicineId())
//                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found: " + request.medicineId()));
//
//        ManufactureCompany company = null;
//        if (request.companyId() != null) {
//            company = companyRepository.findById(request.companyId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Company not found: " + request.companyId()));
//        }
//
//        QuotationDetails entity = new QuotationDetails();
//        entity.setQuotationId(quotationId);
//        mapRequestToEntity(request, entity, medicine, company);
//        // audit fields
//        entity.setCreatedBy("system");       // replace with SecurityContext principal
//        entity.setCreatedDate(LocalDateTime.now());
//
//        return toResponse(repository.save(entity));
//    }
//
//    // ---------- Read ----------
//
//    @Override
//    @Transactional()
//    public QuatationDetailsResponse getById(Long quotationDetailsId) {
//        QuotationDetails entity = repository
//                .findByQuotationDetailsIdAndIsDeletedFalse(quotationDetailsId)
//                .orElseThrow(() -> new ResourceNotFoundException(
//                        "Quotation details not found: " + quotationDetailsId));
//        return toResponse(entity);
//    }
//
//    @Override
//    @Transactional()
//    public List<QuatationDetailsResponse> getAllByQuotationId(Long quotationId) {
//        return repository.findByQuotationIdAndIsDeletedFalse(quotationId)
//                .stream()
//                .map(this::toResponse)
//                .toList();
//    }
////    // ---------- Update ----------
////
////    @Override
////    public QuatationDetailsResponse update(Integer quotationDetailsId, QuatationDetailsRequest request) {
////        Quotation_Details entity = repository
////                .findByQuotationDetailsIdAndIsDeletedFalse(quotationDetailsId)
////                .orElseThrow(() -> new ResourceNotFoundException("Quotation details not found: " + quotationDetailsId));
////
////        Medicine medicine = medicineRepository.findById(request.medicineId())
////                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found: " + request.medicineId()));
////
////        ManufactureCompany company = null;
////        if (request.companyId() != null) {
////            company = companyRepository.findById(request.companyId())
////                    .orElseThrow(() -> new ResourceNotFoundException("Company not found: " + request.companyId()));
////        }
////
////        mapRequestToEntity(request, entity, medicine, company);
////        entity.setModifiedBy("system");      // replace with SecurityContext principal
////        entity.setModifiedDate(LocalDateTime.now());
////
////        return toResponse(repository.save(entity));
////    }
//
//    // ---------- Delete (soft) ----------
//
////    @Override
////    public void delete(Integer quotationDetailsId) {
////        int rows = repository.softDeleteById(quotationDetailsId, "system", LocalDateTime.now());
////        if (rows == 0) {
////            throw new ResourceNotFoundException("Quotation details not found: " + quotationDetailsId);
////        }
////    }
////
////    @Override
////    public void deleteAllByQuotationId(Integer quotationId) {
////        repository.softDeleteByQuotationId(quotationId, "system", LocalDateTime.now());
////    }
//
//    // ---------- Mapping helpers ----------
//
//    private void mapRequestToEntity(QuatationDetailsRequest r,
//                                    QuotationDetails e,
//                                    Medicine medicine,
//                                    ManufactureCompany company) {
//        e.setMedicine(medicine);
//        e.setMedicineName(r.medicineName());
//        e.setPreciseGenericName(r.preciseGenericName());
//        e.setCompany(company);
//        e.setCompanyName(r.companyName());
//        e.setBatchNumber(r.batchNumber());
//        e.setHsnCode(r.hsnCode());
//        e.setPackingType(r.packingType());
//        e.setPackSize(r.packSize());
//        e.setUnitOfMeasure(r.unitOfMeasure());
//        e.setQuantity(r.quantity());
//        e.setFreeQuantity(r.freeQuantity());
//        e.setMrp(r.mrp());
//        e.setRate(r.rate());
//        e.setDiscountPercentage(r.discountPercentage());
//        e.setDiscountAmount(r.discountAmount());
//        e.setSchemeType(r.schemeType());
//        e.setSchemeValue(r.schemeValue());
//        e.setGrossAmount(r.grossAmount());
//        e.setTaxableAmount(r.taxableAmount());
//        e.setGstPercentage(r.gstPercentage());
//        e.setTaxAmount(r.taxAmount());
//        e.setNetAmount(r.netAmount());
//        e.setRemark(r.remark());
//    }
//
//    private QuatationDetailsResponse toResponse(QuotationDetails e) {
//        MedicineSummary medicine = e.getMedicine() == null ? null
//                : new MedicineSummary(
//                e.getMedicine().getMedicineId(),
//                e.getMedicine().getMedicineName(),
//                e.getMedicine().getPreciseGenericName());
//
//        ManufactureCompanySummary company = e.getCompany() == null ? null
//                : new ManufactureCompanySummary(
//                e.getCompany().getCompanyId(),
//                e.getCompany().getCompanyName());
//
//        return new QuatationDetailsResponse(
//                e.getQuotationDetailsId(),
//                e.getQuotationId(),
//                medicine,
//                company,
//                e.getMedicineName(),
//                e.getPreciseGenericName(),
//                e.getCompanyName(),
//                e.getBatchNumber(),
//                e.getHsnCode(),
//                e.getPackingType(),
//                e.getPackSize(),
//                e.getUnitOfMeasure(),
//                e.getQuantity(),
//                e.getFreeQuantity(),
//                e.getMrp(),
//                e.getRate(),
//                e.getDiscountPercentage(),
//                e.getDiscountAmount(),
//                e.getSchemeType(),
//                e.getSchemeValue(),
//                e.getGrossAmount(),
//                e.getTaxableAmount(),
//                e.getGstPercentage(),
//                e.getTaxAmount(),
//                e.getNetAmount(),
//                e.getRemark(),
//                e.isActive(),
//                e.getCreatedBy(),
//                e.getCreatedDate(),
//                e.getModifiedBy(),
//                e.getModifiedDate()
//        );
//    }
//}