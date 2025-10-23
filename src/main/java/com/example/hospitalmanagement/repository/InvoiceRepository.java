package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("SELECT i FROM Invoice i WHERE " +
            "LOWER(i.patientId) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(i.paymentStatus) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Invoice> searchInvoices(@Param("keyword") String keyword);
}
