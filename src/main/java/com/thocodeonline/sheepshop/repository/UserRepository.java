package com.thocodeonline.sheepshop.repository;

import com.thocodeonline.sheepshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.account.username = :identifier OR u.email = :identifier OR u.phone = :identifier")
    User findByUser(@Param("identifier") String identifier);

    @Query(value = "select u from User u WHERE u.id = :id")
    User getById(@Param("id") Long id);

    @Query(value = "select u from User u")
    List<User> getAllUser();

    @Query(value = "select u from User u where u.enabled = true ")
    List<User> getAllUserActive();

    @Query(value = "select u from User u where u.enabled = false")
    List<User> getAllUserNotActive();

}
