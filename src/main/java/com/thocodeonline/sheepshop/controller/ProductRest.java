package com.thocodeonline.sheepshop.controller;

import com.thocodeonline.sheepshop.entity.Product;
import com.thocodeonline.sheepshop.entity.ProductPhoto;
import com.thocodeonline.sheepshop.request.ProductRequest;
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
    public ResponseEntity<List<ProductRequest>> getAll() {
        // Retrieve the list of products from the service
        List<Product> products = productService.getAllProduct();

        // Convert the list of Product to ProductReq
        List<ProductRequest> productRequests = products.stream()
                .map(product -> {
                    ProductRequest productRequest = new ProductRequest();
                    productRequest.setId(product.getId());
                    productRequest.setCode(product.getCode());
                    productRequest.setBarcode(product.getBarcode());
                    productRequest.setName(product.getName());
                    productRequest.setDescription(product.getDescription());
                    productRequest.setStatus(product.getStatus());
                    productRequest.setWeight(product.getWeight());


                    // Set category details if available
                    if (product.getCategory() != null) {
                        productRequest.setCategoryId(product.getCategory().getId());
                        productRequest.setCategoryName(product.getCategory().getName());
                    } else {
                        productRequest.setCategoryId(null);
                        productRequest.setCategoryName(null);
                    }

                    // Set brand details if available
                    if (product.getBrand() != null) {
                        productRequest.setBrandId(product.getBrand().getId());
                        productRequest.setBrandName(product.getBrand().getName());
                    } else {
                        productRequest.setBrandId(null);
                        productRequest.setBrandName(null);
                    }

                    // Set main image and image URL
                    if (product.getProductPhotos() != null && !product.getProductPhotos().isEmpty()) {
                        ProductPhoto mainPhoto = product.getProductPhotos().stream()
                                .filter(ProductPhoto::getMainImage)
                                .findFirst()
                                .orElse(null);

                        if (mainPhoto != null) {
                            productRequest.setMainImage(true);
                            productRequest.setImageUrl(mainPhoto.getImageUrl());
                        } else {
                            productRequest.setMainImage(false);
                            productRequest.setImageUrl(null);
                        }

                        // Collect non-main image URLs
                        List<String> notMainImageUrls = product.getProductPhotos().stream()
                                .filter(photo -> !photo.getMainImage())
                                .map(ProductPhoto::getImageUrl) // Assuming getImageUrl() exists
                                .collect(Collectors.toList());

                        productRequest.setNotMainImages(notMainImageUrls);
                    } else {
                        productRequest.setMainImage(false);
                        productRequest.setImageUrl(null); // No photos available
                        productRequest.setNotMainImages(new ArrayList<>()); // Ensure empty list
                    }

                    return productRequest;
                })
                .collect(Collectors.toList());

        // Return response based on the presence of productReqs
        if (productRequests.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no products
        }
        return ResponseEntity.ok(productRequests); // Return 200 and the list of products
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
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) {
        // Kiểm tra tính hợp lệ
        if (productRequest == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Product createdProduct = productService.createProduct(productRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (Exception e) {
            // Xử lý lỗi phù hợp (có thể ghi log hoặc trả về thông báo cụ thể hơn)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
        try {
            Product updatedProduct = productService.updateProduct(id, request);
            return ResponseEntity.ok(updatedProduct); // Trả về 200 OK và đối tượng sản phẩm đã cập nhật
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về 404 Not Found nếu không tìm thấy sản phẩm
        }
    }

}
