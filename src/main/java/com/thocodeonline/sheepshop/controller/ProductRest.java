package com.thocodeonline.sheepshop.controller;

import com.thocodeonline.sheepshop.entity.Product;
import com.thocodeonline.sheepshop.entity.ProductPhoto;
import com.thocodeonline.sheepshop.request.ProductReq;
import com.thocodeonline.sheepshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/products")
public class ProductRest {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductReq>> getAll() {
        // Retrieve the list of products from the service
        List<Product> products = productService.getAllProduct();

        // Convert the list of Product to ProductReq
        List<ProductReq> productReqs = products.stream()
                .map(product -> {
                    ProductReq productReq = new ProductReq();
                    productReq.setId(product.getId());
                    productReq.setCode(product.getCode());
                    productReq.setBarcode(product.getBarcode());
                    productReq.setName(product.getName());
                    productReq.setDescription(product.getDescription());
                    productReq.setStatus(product.getStatus());
                    productReq.setWeight(product.getWeight());


                    // Set category details if available
                    if (product.getCategory() != null) {
                        productReq.setCategoryId(product.getCategory().getId());
                        productReq.setCategoryName(product.getCategory().getName());
                    } else {
                        productReq.setCategoryId(null);
                        productReq.setCategoryName(null);
                    }

                    // Set brand details if available
                    if (product.getBrand() != null) {
                        productReq.setBrandId(product.getBrand().getId());
                        productReq.setBrandName(product.getBrand().getName());
                    } else {
                        productReq.setBrandId(null);
                        productReq.setBrandName(null);
                    }

                    // Set main image and image URL
                    if (product.getProductPhotos() != null && !product.getProductPhotos().isEmpty()) {
                        ProductPhoto mainPhoto = product.getProductPhotos().stream()
                                .filter(ProductPhoto::getMainImage)
                                .findFirst()
                                .orElse(null);

                        if (mainPhoto != null) {
                            productReq.setMainImage(true);
                            productReq.setImageUrl(mainPhoto.getImageUrl());
                        } else {
                            productReq.setMainImage(false);
                            productReq.setImageUrl(null);
                        }

                        // Collect non-main image URLs
                        List<String> notMainImageUrls = product.getProductPhotos().stream()
                                .filter(photo -> !photo.getMainImage())
                                .map(ProductPhoto::getImageUrl) // Assuming getImageUrl() exists
                                .collect(Collectors.toList());

                        productReq.setNotMainImages(notMainImageUrls);
                    } else {
                        productReq.setMainImage(false);
                        productReq.setImageUrl(null); // No photos available
                        productReq.setNotMainImages(new ArrayList<>()); // Ensure empty list
                    }

                    return productReq;
                })
                .collect(Collectors.toList());

        // Return response based on the presence of productReqs
        if (productReqs.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no products
        }
        return ResponseEntity.ok(productReqs); // Return 200 and the list of products
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
            Product product = productService.getProductById(id);
            if (product != null) {
                // Nếu tìm thấy , trả về HttpStatus 200 (OK) và thông tin
                return ResponseEntity.ok(product);
            } else {
                // Nếu không tìm thấy, trả về HttpStatus 404 (Not Found)
                return ResponseEntity.notFound().build();
            }
        }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody ProductReq productReq) {
        // Kiểm tra tính hợp lệ
        if (productReq == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Product createdProduct = productService.createProduct(productReq);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (Exception e) {
            // Xử lý lỗi phù hợp (có thể ghi log hoặc trả về thông báo cụ thể hơn)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductReq request) {
        try {
            Product updatedProduct = productService.updateProduct(id, request);
            return ResponseEntity.ok(updatedProduct); // Trả về 200 OK và đối tượng sản phẩm đã cập nhật
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về 404 Not Found nếu không tìm thấy sản phẩm
        }
    }

}
