package com.thocodeonline.sheepshop.repository;

import com.thocodeonline.sheepshop.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query(value = "select b from Brand b WHERE b.id = :id")
    Brand getById(@Param("id") Long id);

    @Query(value = "select b from Brand b")
    List<Brand> getAllBrand();

}
