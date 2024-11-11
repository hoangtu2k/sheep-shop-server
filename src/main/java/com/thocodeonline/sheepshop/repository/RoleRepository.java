package com.thocodeonline.sheepshop.repository;

import com.thocodeonline.sheepshop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {



}
