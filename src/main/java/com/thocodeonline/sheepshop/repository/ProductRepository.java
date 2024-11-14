package com.thocodeonline.sheepshop.repository;

import com.thocodeonline.sheepshop.entity.Product;
import com.thocodeonline.sheepshop.entity.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select u from Product u WHERE u.id = :id")
    Product getById(@Param("id") Long id);

    @Query(value = "select p from Product p")
    List<Product> getAllProduct();

    @Query("SELECT pp FROM ProductPhoto pp WHERE pp.product.id = :id")
    List<ProductPhoto> getProductPhotoByProductId(@Param("id") Long id);

}
