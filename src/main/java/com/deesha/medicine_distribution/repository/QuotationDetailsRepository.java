//package com.deesha.medicine_distribution.repository;
//
//import com.deesha.medicine_distribution.model.QuotationDetails;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface QuotationDetailsRepository extends JpaRepository<QuotationDetails, Long> {
//    Optional<QuotationDetails> findByQuotationDetailsIdAndIsDeletedFalse(Long quotationDetailsId);
//    List<QuotationDetails> findByQuotationIdAndIsDeletedFalse(Long quotationId);
//}
