package com.thocodeonline.sheepshop.service;

import com.thocodeonline.sheepshop.entity.Brand;
import com.thocodeonline.sheepshop.entity.Category;
import com.thocodeonline.sheepshop.entity.Product;
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

    public List<Product> getAllProduct() {
        return productRepository.getAllProduct();
    }

    public Product getProductById(Long id) {
        return productRepository.getById(id);
    }

    public Product createProduct(ProductReq productReq) {
        Product product = new Product();
        if (productReq.getCode() == null) {
            // Generate a new code automatically if it's null
            String generatedCode = generateUserCode();
            product.setCode(generatedCode);
        }
        else {
            // Otherwise, set the code from the request
            product.setCode(productReq.getCode());
        }

        product.setBarcode(productReq.getBarcode());
        product.setName(productReq.getName());
        product.setPrice(productReq.getPrice());
        product.setWeight(productReq.getWeight());

        if (product.getBrand() != null) {
            product.setBrand(Brand.builder().id(productReq.getBrandId()).build());
        } else {
            product.setBrand(null);
        }

        if (product.getCategory() != null) {
            product.setCategory(Category.builder().id(productReq.getCategoryId()).build());
        } else {
            product.setCategory(null);
        }

        product.setStatus(1);
        return productRepository.save(product);
    }

    private String generateUserCode() {
        return UUID.randomUUID().toString(); // Example using UUID
    }

}
