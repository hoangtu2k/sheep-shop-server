package com.thocodeonline.sheepshop.repository;

import com.thocodeonline.sheepshop.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetails, Long> {


}
