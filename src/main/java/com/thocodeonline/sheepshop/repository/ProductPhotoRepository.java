package com.thocodeonline.sheepshop.repository;

import com.thocodeonline.sheepshop.entity.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {

    @Query(value = "select p from ProductPhoto p WHERE p.id = :id")
    ProductPhoto getById(@Param("id") Long id);

    @Query(value = "select p from ProductPhoto p")
    List<ProductPhoto> getAllProductPhoto();


    @Query(value = "Select e from ProductPhoto  e where e.product.id = :id and e.mainImage = true")
    List<ProductPhoto> getAllByIdSP(@Param("id") Integer id);
    @Query(value = "Select e from ProductPhoto  e where e.product.id = :id and e.mainImage = false ")
    List<ProductPhoto> getAllByIdSP1(@Param("id") Integer id);

}
