package com.thocodeonline.sheepshop.service;

import com.thocodeonline.sheepshop.entity.*;
import com.thocodeonline.sheepshop.repository.ProductPhotoRepository;
import com.thocodeonline.sheepshop.repository.ProductRepository;
import com.thocodeonline.sheepshop.request.ProductReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


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

    public Product updateProduct(Long id, ProductReq productReq) {
        // Kiểm tra xem sản phẩm có tồn tại không
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        Product product = optionalProduct.get();

        // Cập nhật các thuộc tính sản phẩm
        if (productReq.getCode() == null) {
            String generatedCode = generateUserCode();
            product.setCode(generatedCode);
        }
        else {
            product.setCode(productReq.getCode());
        }

        product.setBarcode(productReq.getBarcode());
        product.setName(productReq.getName());
        product.setWeight(productReq.getWeight());
        product.setPrice(productReq.getPrice());

        // Cập nhật danh mục
        if (productReq.getCategoryId() != null) {
            product.setCategory(Category.builder().id(productReq.getCategoryId()).build());
        } else {
            product.setCategory(null);
        }

        // Cập nhật thương hiệu
        if (productReq.getBrandId() != null) {
            product.setBrand(Brand.builder().id(productReq.getBrandId()).build());
        } else {
            product.setBrand(null);
        }

        // Lưu sản phẩm
        return productRepository.save(product);
    }

    private String generateUserCode() {
        return UUID.randomUUID().toString(); // Example using UUID
    }

}
