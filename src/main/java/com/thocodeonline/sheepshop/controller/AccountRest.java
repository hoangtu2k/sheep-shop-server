package com.thocodeonline.sheepshop.controller;

import com.thocodeonline.sheepshop.entity.Account;
import com.thocodeonline.sheepshop.entity.User;
import com.thocodeonline.sheepshop.request.AccountReq;
import com.thocodeonline.sheepshop.request.UserReq;
import com.thocodeonline.sheepshop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/account")
public class AccountRest {

    @Autowired
    private AccountService accountService;

    @PostMapping()
    public ResponseEntity<Account> createUser(@RequestBody AccountReq accountReq) {
        // Kiểm tra tính hợp lệ của userReq
        if (accountReq == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Account createdAccount = accountService.createAccount(accountReq);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
        } catch (Exception e) {
            // Xử lý lỗi phù hợp (có thể ghi log hoặc trả về thông báo cụ thể hơn)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
