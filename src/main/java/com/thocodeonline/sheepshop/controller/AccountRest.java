package com.thocodeonline.sheepshop.controller;

import com.thocodeonline.sheepshop.entity.Account;
import com.thocodeonline.sheepshop.entity.User;
import com.thocodeonline.sheepshop.request.AccountReq;
import com.thocodeonline.sheepshop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/account")
public class AccountRest {

    @Autowired
    private AccountService accountService;

    @GetMapping()
    public ResponseEntity<List<AccountReq>> getAllAccout() {
    // Lấy danh sách User từ service
        List<Account> accounts = accountService.getAllAccount();

        // Chuyển đổi danh sách User sang danh sách UserReq trực tiếp
        List<AccountReq> accountReqs = accounts.stream()
                .map(account  -> {
                    AccountReq accountReq = new AccountReq();
                    accountReq.setId(account.getId());
                    accountReq.setUsername(account.getUsername());
                    return accountReq; // Trả về đối tượng UserReq đã tạo
                })
                .collect(Collectors.toList()); // Thu thập kết quả vào danh sách

        if (accountReqs.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có người dùng
        }
        return ResponseEntity.ok(accountReqs); // Trả về 200 và danh sách người dùng
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> findById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);

        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            // Nếu không tìm thấy, trả về HttpStatus 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }

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
