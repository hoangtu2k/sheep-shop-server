package com.thocodeonline.sheepshop.service;

import com.thocodeonline.sheepshop.entity.Brand;
import com.thocodeonline.sheepshop.entity.Category;
import com.thocodeonline.sheepshop.entity.Product;
import com.thocodeonline.sheepshop.entity.ProductPhoto;
import com.thocodeonline.sheepshop.repository.ProductPhotoRepository;
import com.thocodeonline.sheepshop.repository.ProductRepository;
import com.thocodeonline.sheepshop.request.ProductReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPhotoService productPhotoService;
    @Autowired
    private ProductPhotoRepository productPhotoRepository;

    public List<Product> getAllProduct() {
        return productRepository.getAllProduct();
    }

    public Product getProductById(Long id) {
        return productRepository.getById(id);
    }

    public Product createProduct(ProductReq productReq) {
        // Create a new Product instance
        Product product = new Product();

        // Generate a new code automatically if it's null
        if (productReq.getCode() == null) {
            String generatedCode = generateUserCode();
            product.setCode(generatedCode);
        } else {
            product.setCode(productReq.getCode());
        }

        // Set other product properties
        product.setBarcode(productReq.getBarcode());
        product.setName(productReq.getName());
        product.setPrice(productReq.getPrice());
        product.setWeight(productReq.getWeight());
        product.setStatus(1);

        // Set category
        if (productReq.getCategoryId() != null) {
            product.setCategory(Category.builder().id(productReq.getCategoryId()).build());
        } else {
            product.setCategory(null);
        }

        // Set brand
        if (productReq.getBrandId() != null) {
            product.setBrand(Brand.builder().id(productReq.getBrandId()).build());
        } else {
            product.setBrand(null);
        }

        return productRepository.save(product);
    }

    public ProductPhoto createProductPhoto(ProductReq image){
        ProductPhoto productImage = new ProductPhoto();
        productImage.setImageUrl(image.getImageUrl());
        productImage.setMainImage(image.getMainImage());
        productImage.setProduct(Product.builder().id(image.getProductId()).build());
        return productPhotoRepository.save(productImage);
    }

    private String generateUserCode() {
        return UUID.randomUUID().toString(); // Example using UUID
    }

}
