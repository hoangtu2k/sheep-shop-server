package com.thocodeonline.sheepshop.repository;

import com.thocodeonline.sheepshop.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "select a from Account a WHERE a.id = :id")
    Account getById(@Param("id") Long id);

    @Query(value = "select a from Account a")
    List<Account> getAllAccount();

}
