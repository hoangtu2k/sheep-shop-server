package com.thocodeonline.sheepshop.controller;

import com.thocodeonline.sheepshop.entity.User;
import com.thocodeonline.sheepshop.request.UserReq;
import com.thocodeonline.sheepshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/users")
public class UserRest {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserReq>> getAll() {
        // Lấy danh sách User từ service
        List<User> users = userService.getAllUser();

        // Chuyển đổi danh sách User sang danh sách UserReq trực tiếp
        List<UserReq> userReqs = users.stream()
                .map(user -> {
                    UserReq userReq = new UserReq();
                    userReq.setId(user.getId());
                    userReq.setCode(user.getCode());
                    userReq.setName(user.getName());
                    userReq.setPhone(user.getPhone());
                    userReq.setEmail(user.getEmail());
                    userReq.setDateOfBirth(user.getDateOfBirth());
                    userReq.setGender(user.getGender());
                    userReq.setAddress(user.getAddress());
                    userReq.setStatus(user.getStatus());
                    userReq.setEnabled(user.isEnabled());

                    // Kiểm tra xem tài khoản và vai trò có tồn tại không
                    if (user.getAccount() != null && user.getAccount().getRole() != null) {
                        userReq.setRoleName(user.getAccount().getRole().getName());
                        userReq.setUsername(user.getAccount().getUsername());
                    } else {
                        userReq.setRoleName(null); // Đặt là null nếu không có vai trò
                        userReq.setUsername(null);

                    }

                    return userReq; // Trả về đối tượng UserReq đã tạo
                })
                .collect(Collectors.toList()); // Thu thập kết quả vào danh sách

        if (userReqs.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có người dùng
        }
        return ResponseEntity.ok(userReqs); // Trả về 200 và danh sách người dùng
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.getUserById(id); // Giả sử trả về người dùng theo ID

        if (user != null) {
            // Nếu tìm thấy người dùng, trả về HttpStatus 200 (OK) và thông tin người dùng
            return ResponseEntity.ok(user);
        } else {
            // Nếu không tìm thấy, trả về HttpStatus 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody UserReq userReq) {
        // Kiểm tra tính hợp lệ của userReq
        if (userReq == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            User createdUser = userService.createUser(userReq);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            // Xử lý lỗi phù hợp (có thể ghi log hoặc trả về thông báo cụ thể hơn)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserReq userReq) {
        User updatedUser = userService.updateUser(id, userReq);
        return ResponseEntity.ok(updatedUser); // Trả về 200 OK và đối tượng UserReq đã cập nhật
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        User deleteUser = userService.deleteUser(id);
        return ResponseEntity.ok(deleteUser); // Trả về 200 OK và đối tượng UserReq đã cập nhật
    }

}
