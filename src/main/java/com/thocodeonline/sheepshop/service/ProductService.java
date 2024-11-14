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

    public ProductPhoto createProductPhoto(ProductReq image){
        ProductPhoto productImage = new ProductPhoto();
        productImage.setImageUrl(image.getImageUrl());
        productImage.setMainImage(image.getMainImage());
        productImage.setProduct(Product.builder().id(image.getProductId()).build());
        return productPhotoRepository.save(productImage);
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

        // Xóa tất cả hình ảnh hiện tại
        product.getProductPhotos().clear();

        // Cập nhật ảnh chính
        if (productReq.getImageUrl() != null) {
            ProductPhoto mainPhoto = new ProductPhoto();
            mainPhoto.setImageUrl(productReq.getImageUrl());
            mainPhoto.setMainImage(true); // Đảm bảo rằng mainImage được đặt là true
            mainPhoto.setProduct(product);
            product.getProductPhotos().add(mainPhoto);
        }

        // Lưu các hình ảnh phụ mới
        List<String> notMainImages = productReq.getNotMainImages();
        if (notMainImages != null) {
            for (String url : notMainImages) {
                if (url != null && !url.isEmpty()) { // Kiểm tra URL không null và không rỗng
                    ProductPhoto photo = new ProductPhoto();
                    photo.setImageUrl(url);
                    photo.setMainImage(false); // Đảm bảo rằng mainImage được đặt là false cho ảnh phụ
                    photo.setProduct(product);
                    product.getProductPhotos().add(photo);
                }
            }
        }

        // Lưu sản phẩm
        Product updatedProduct = productRepository.save(product);

        return updatedProduct;
    }


    private String generateUserCode() {
        return UUID.randomUUID().toString(); // Example using UUID
    }

}
