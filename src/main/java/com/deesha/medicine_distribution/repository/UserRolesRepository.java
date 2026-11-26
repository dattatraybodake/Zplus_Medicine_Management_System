package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.QuotationDetails;
import com.deesha.medicine_distribution.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRolesRepository extends JpaRepository<UserRoles, Integer> {
//
//    // ---------- Basic active (non-deleted) lookups ----------
//
////    Optional<QuotationDetails> findByQuotationDetailsIdAndIsDeletedFalse(Long quotationDetailsId);
////
////    List<QuotationDetails> findByQuotationIdAndIsDeletedFalse(Long quotationId);
////
////    List<QuotationDetails> findByMedicine_MedicineIdAndIsDeletedFalse(Long medicineId);
////
////    List<QuotationDetails> findByCompany_CompanyIdAndIsDeletedFalse(Long companyId);
//
//    // ---------- Existence checks ----------
//
//    boolean existsByQuotationDetailsIdAndIsDeletedFalse(Integer quotationDetailsId);
//
//    boolean existsByQuotationIdAndMedicine_MedicineIdAndIsDeletedFalse(Integer quotationId, Long medicineId);
//
//    // ---------- Aggregate helpers ----------
//
//    @Query("""
//            SELECT COALESCE(SUM(q.netAmount), 0)
//            FROM Quotation_Details q
//            WHERE q.quotationId = :quotationId AND q.isDeleted = false
//            """)
//    java.math.BigDecimal sumNetAmountByQuotationId(@Param("quotationId") Integer quotationId);
//
//    @Query("""
//            SELECT COUNT(q)
//            FROM Quotation_Details q
//            WHERE q.quotationId = :quotationId AND q.isDeleted = false
//            """)
//    long countByQuotationId(@Param("quotationId") Integer quotationId);
//
//    // ---------- Soft delete ----------
//
//    @Modifying
//    @Query("""
//            UPDATE Quotation_Details q
//            SET q.isDeleted = true,
//                q.isActive = false,
//                q.deletedBy = :deletedBy,
//                q.deletedDate = :deletedDate
//            WHERE q.quotationDetailsId = :id AND q.isDeleted = false
//            """)
//    int softDeleteById(@Param("id") Integer id,
//                       @Param("deletedBy") String deletedBy,
//                       @Param("deletedDate") LocalDateTime deletedDate);
//
//    @Modifying
//    @Query("""
//            UPDATE Quotation_Details q
//            SET q.isDeleted = true,
//                q.isActive = false,
//                q.deletedBy = :deletedBy,
//                q.deletedDate = :deletedDate
//            WHERE q.quotationId = :quotationId AND q.isDeleted = false
//            """)
//    int softDeleteByQuotationId(@Param("quotationId") Integer quotationId,
//                                @Param("deletedBy") String deletedBy,
//                                @Param("deletedDate") LocalDateTime deletedDate);
//

}
