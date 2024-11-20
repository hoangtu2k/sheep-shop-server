package com.thocodeonline.sheepshop.service;

import com.thocodeonline.sheepshop.entity.*;
import com.thocodeonline.sheepshop.repository.ProductPhotoRepository;
import com.thocodeonline.sheepshop.repository.ProductRepository;
import com.thocodeonline.sheepshop.request.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Product createProduct(ProductRequest productRequest) {
        // Create a new Product instance
        Product product = new Product();

        // Generate a new code automatically if it's null
        if (productRequest.getCode() == null) {
            String generatedCode = generateUserCode();
            product.setCode(generatedCode);
        } else {
            product.setCode(productRequest.getCode());
        }

        // Set other product properties
        product.setBarcode(productRequest.getBarcode());
        product.setName(productRequest.getName());
        product.setWeight(productRequest.getWeight());
        product.setStatus(1);

        // Set category
        if (productRequest.getCategoryId() != null) {
            product.setCategory(Category.builder().id(productRequest.getCategoryId()).build());
        } else {
            product.setCategory(null);
        }

        // Set brand
        if (productRequest.getBrandId() != null) {
            product.setBrand(Brand.builder().id(productRequest.getBrandId()).build());
        } else {
            product.setBrand(null);
        }

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequest productRequest) {
        // Kiểm tra xem sản phẩm có tồn tại không
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        Product product = optionalProduct.get();

        // Cập nhật các thuộc tính sản phẩm
        if (productRequest.getCode() == null) {
            String generatedCode = generateUserCode();
            product.setCode(generatedCode);
        }
        else {
            product.setCode(productRequest.getCode());
        }

        product.setBarcode(productRequest.getBarcode());
        product.setName(productRequest.getName());
        product.setWeight(productRequest.getWeight());

        // Cập nhật danh mục
        if (productRequest.getCategoryId() != null) {
            product.setCategory(Category.builder().id(productRequest.getCategoryId()).build());
        } else {
            product.setCategory(null);
        }

        // Cập nhật thương hiệu
        if (productRequest.getBrandId() != null) {
            product.setBrand(Brand.builder().id(productRequest.getBrandId()).build());
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
