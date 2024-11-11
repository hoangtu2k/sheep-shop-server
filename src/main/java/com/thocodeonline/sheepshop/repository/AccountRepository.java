package com.thocodeonline.sheepshop.repository;

import com.thocodeonline.sheepshop.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


}
