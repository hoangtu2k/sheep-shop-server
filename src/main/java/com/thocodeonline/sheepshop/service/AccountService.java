package com.thocodeonline.sheepshop.service;


import com.thocodeonline.sheepshop.entity.Account;
import com.thocodeonline.sheepshop.entity.User;
import com.thocodeonline.sheepshop.repository.AccountRepository;
import com.thocodeonline.sheepshop.request.AccountReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<Account> getAllAccount() {
        return accountRepository.getAllAccount();
    }

    public Account getAccountById(Long id) {
        return accountRepository.getById(id);
    }

    public Account createAccount(AccountReq accountReq) {
        Account account = new Account();
        account.setUsername(accountReq.getUsername());
        account.setPassword(passwordEncoder.encode(accountReq.getPassword()));
        account.setStatus(1);
        return accountRepository.save(account);
    }

}
