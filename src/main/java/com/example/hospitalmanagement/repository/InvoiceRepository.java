package com.example.hospitalmanagement.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hospitalmanagement.model.Invoice;
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
