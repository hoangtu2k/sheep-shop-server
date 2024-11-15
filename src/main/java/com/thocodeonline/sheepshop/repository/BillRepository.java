package com.thocodeonline.sheepshop.repository;

import com.thocodeonline.sheepshop.entity.Bill;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query(value = "select b from Bill b where b.paymentStatus = 0 and b.salesChannel = 0 ")
    List<Bill> getAllBillTaiQuay();

    @Modifying
    @Transactional
    @Query("DELETE FROM Bill b WHERE b.id = :billId")
    void deleteByBillId(@Param("billId") Long billId);

}
