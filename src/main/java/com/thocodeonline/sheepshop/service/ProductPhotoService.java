package com.thocodeonline.sheepshop.service;

import com.thocodeonline.sheepshop.entity.Product;
import com.thocodeonline.sheepshop.entity.ProductPhoto;
import com.thocodeonline.sheepshop.repository.ProductPhotoRepository;
import com.thocodeonline.sheepshop.request.ProductReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductPhotoService {

    @Autowired
    private ProductPhotoRepository productPhotoRepository;

    public List<ProductPhoto> getAllProductPhoto() {
        return productPhotoRepository.getAllProductPhoto();
    }

    public ProductPhoto getProductPhotoById(Long id) {
        // Kiểm tra xem productPhoto có tồn tại không
        ProductPhoto productPhoto = productPhotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productPhotoRepository.getById(id);
    }

    public ProductPhoto createProductPhoto(ProductReq image){
        ProductPhoto productImage = new ProductPhoto();
        productImage.setImageUrl(image.getImageUrl());
        productImage.setMainImage(image.getMainImage());
        productImage.setProduct(Product.builder().id(image.getProductId()).build());
        return productPhotoRepository.save(productImage);
    }



}
