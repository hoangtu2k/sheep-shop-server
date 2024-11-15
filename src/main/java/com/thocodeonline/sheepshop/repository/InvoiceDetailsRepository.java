package com.thocodeonline.sheepshop.repository;

import com.thocodeonline.sheepshop.entity.InvoiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails, Long> {

    @Query(value = "SELECT bd FROM InvoiceDetails bd WHERE bd.bill.id = :billId")
    List<InvoiceDetails> findAllByBillId(@Param("billId") Long billId);



}
