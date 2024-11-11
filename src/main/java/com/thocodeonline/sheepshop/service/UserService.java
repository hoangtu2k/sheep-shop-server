package com.thocodeonline.sheepshop.service;

import com.thocodeonline.sheepshop.entity.User;
import com.thocodeonline.sheepshop.repository.UserRepository;
import com.thocodeonline.sheepshop.request.UserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<User> getAllUserActive() {
        return userRepository.getAllUserActive();
    }

    public List<User> getAllUserNotActive() {
        return userRepository.getAllUserNotActive();
    }

    public User createUser(UserReq userReq) {
        User user = new User();
        user.setCode(userReq.getCode());
        user.setName(userReq.getName());
        user.setPhone(userReq.getPhone());
        user.setEmail(userReq.getEmail());
        user.setDateOfBirth(userReq.getDateOfBirth());
        user.setGender(userReq.getGender());
        user.setAddress(userReq.getAddress());
        user.setStatus(1);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        // Tìm người dùng theo ID
        return userRepository.getById(id);
    }


    public User updateUser( Long id, UserReq userReq) {
        // Kiểm tra xem người dùng có tồn tại không
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found with id: " + id);
        }
        User user = optionalUser.get();
        user.setCode(userReq.getCode());
        user.setName(userReq.getName());
        user.setPhone(userReq.getPhone());
        user.setEmail(userReq.getEmail());
        user.setDateOfBirth(userReq.getDateOfBirth());
        user.setGender(userReq.getGender());

        return userRepository.save(user);
    }


}
