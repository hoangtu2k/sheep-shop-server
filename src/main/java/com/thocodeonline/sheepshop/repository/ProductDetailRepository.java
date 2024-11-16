package com.thocodeonline.sheepshop.repository;

import com.thocodeonline.sheepshop.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetails, Long> {

    @Query(value = "select pd from ProductDetails pd where pd.product.status = 1")
    List<ProductDetails> getAllProductDetails();

    @Query(value = "select pd from ProductDetails pd WHERE pd.id = :id")
    ProductDetails getById(@Param("id") Long id);

}
