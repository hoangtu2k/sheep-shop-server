package com.thocodeonline.sheepshop.service;

import com.thocodeonline.sheepshop.entity.Account;
import com.thocodeonline.sheepshop.entity.User;
import com.thocodeonline.sheepshop.repository.UserRepository;
import com.thocodeonline.sheepshop.request.UserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User login(String username) {
        return userRepository.findByUser(username);
    }

    public List<User> getAllUser() {
        return userRepository.getAllUser();
    }

    public User createUser(UserReq userReq) {
        User user = new User();
        if (userReq.getCode() == null) {
            // Generate a new code automatically if it's null
            String generatedCode = generateUserCode();
            user.setCode(generatedCode);
        } else {
            // Otherwise, set the code from the request
            user.setCode(userReq.getCode());
        }
        user.setName(userReq.getName());
        user.setPhone(userReq.getPhone());
        user.setEmail(userReq.getEmail());
        user.setDateOfBirth(userReq.getDateOfBirth());
        user.setGender(userReq.getGender());

        if (userReq.getImage() != null) {
            user.setImage(userReq.getImage());
        } else {
            user.setImage(null);
        }
        
        if (userReq.getAccountId() != null) {
             user.setAccount(Account.builder().id(userReq.getAccountId()).build());
        } else {
            user.setAccount(null);
        }

        user.setStatus(1);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        // Tìm người dùng theo ID
        return userRepository.getById(id);
    }

    public User deleteUser(Long id) {
        // Kiểm tra xem người dùng có tồn tại không
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found with id: " + id);
        }
        User user = optionalUser.get();

        if (user.getAccount().getRole().getName().equals("Admin")) {
            System.out.println("Không thể xóa tài khoản này");
            return userRepository.save(user);
        } else {
            user.setStatus(0);
            return userRepository.save(user);
        }

    }

    public User updateUser( Long id, UserReq userReq) {
        // Kiểm tra xem người dùng có tồn tại không
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found with id: " + id);
        }
        User user = optionalUser.get();
        if (userReq.getCode() == null) {
            // Generate a new code automatically if it's null
            String generatedCode = generateUserCode();
            user.setCode(generatedCode);
        } else {
            // Otherwise, set the code from the request
            user.setCode(userReq.getCode());
        }
        user.setName(userReq.getName());
        user.setPhone(userReq.getPhone());
        user.setEmail(userReq.getEmail());
        user.setDateOfBirth(userReq.getDateOfBirth());
        user.setGender(userReq.getGender());

        user.setImage(userReq.getImage());

        // Check the accountId provided in the request
        Long accountId = userReq.getAccountId();
        if (accountId != null) {
            // Check if any other user already uses this account ID
            List<User> allUsers = userRepository.getAllUser(); // Fetch all users
            boolean accountUsed = allUsers.stream()
                    .anyMatch(existingUser -> existingUser.getAccount() != null &&
                            existingUser.getAccount().getId().equals(accountId) &&
                            !existingUser.getId().equals(id)); // Exclude the current user

            if (accountUsed) {
                user.setAccount(null);
                System.out.println("Đã có user sử dụng tài khoản này");
            } else {
                user.setAccount(Account.builder().id(accountId).build());
            }
        } else {
            user.setAccount(null); // Set account to null if accountId is not provided
        }

        return userRepository.save(user);
    }

    private String generateUserCode() {
        return UUID.randomUUID().toString(); // Example using UUID
    }

}
