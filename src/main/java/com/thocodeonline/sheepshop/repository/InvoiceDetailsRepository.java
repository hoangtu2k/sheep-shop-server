package com.thocodeonline.sheepshop.repository;

import com.thocodeonline.sheepshop.entity.Account;
import com.thocodeonline.sheepshop.entity.InvoiceDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails, Long> {

    @Query(value = "SELECT bd FROM InvoiceDetails bd WHERE bd.bill.id = :billId")
    List<InvoiceDetails> findAllByBillId(@Param("billId") Long billId);

    @Modifying
    @Transactional
    @Query("DELETE FROM InvoiceDetails bd WHERE bd.id = :id")
    void deleteByInvoiceId(@Param("id") Long id);

    @Query(value = "select bd from InvoiceDetails bd WHERE bd.id = :id")
    InvoiceDetails getById(@Param("id") Long id);

    @Query("SELECT bd FROM InvoiceDetails bd WHERE bd.bill.id = :billId AND bd.productDetails.id = :productDetailId")
    InvoiceDetails findByBillAndProductDetails(@Param("billId") Long billId, @Param("productDetailId") Long productDetailId);

}
