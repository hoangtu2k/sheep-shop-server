package com.thocodeonline.sheepshop.controller;

import com.thocodeonline.sheepshop.entity.Product;
import com.thocodeonline.sheepshop.entity.ProductDetails;
import com.thocodeonline.sheepshop.entity.ProductPhoto;
import com.thocodeonline.sheepshop.request.ProductReq;
import com.thocodeonline.sheepshop.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/productdetail")
public class ProductDetailRest {

    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping()
    public ResponseEntity<List<ProductReq>> getAllProductDetails() {
        List<ProductDetails> productDetails = productDetailService.getAllProductDetails();

        // Convert the list of Product to ProductReq
        List<ProductReq> productReqs = productDetails.stream()
                .map(productDetail -> {
                    ProductReq productReq = new ProductReq();
                    productReq.setId(productDetail.getId());
                    productReq.setCode(productDetail.getProduct().getCode());
                    productReq.setBarcode(productDetail.getProduct().getBarcode());
                    productReq.setName(productDetail.getProduct().getName());
                    productReq.setPrice(productDetail.getPrice());
                    productReq.setDescription(productDetail.getProduct().getDescription());
                    productReq.setStatus(productDetail.getProduct().getStatus());
                    productReq.setWeight(productDetail.getProduct().getWeight());
                    productReq.setQuantity(productDetail.getQuantity());


                    // Set color details if available
                    if (productDetail.getColor() != null) {
                        productReq.setColorId(productDetail.getColor().getId());
                        productReq.setColorName(productDetail.getColor().getName());
                    } else {
                        productReq.setColorId(null);
                        productReq.setColorName(null);
                    }

                    // Set size details if available
                    if (productDetail.getSize() != null) {
                        productReq.setSizeId(productDetail.getSize().getId());
                        productReq.setSizeName(productDetail.getSize().getName());
                    } else {
                        productReq.setSizeId(null);
                        productReq.setSizeName(null);
                    }

                    // Set category details if available
                    if (productDetail.getProduct().getCategory() != null) {
                        productReq.setCategoryId(productDetail.getProduct().getCategory().getId());
                        productReq.setCategoryName(productDetail.getProduct().getCategory().getName());
                    } else {
                        productReq.setCategoryId(null);
                        productReq.setCategoryName(null);
                    }

                    // Set brand details if available
                    if (productDetail.getProduct().getBrand() != null) {
                        productReq.setBrandId(productDetail.getProduct().getBrand().getId());
                        productReq.setBrandName(productDetail.getProduct().getBrand().getName());
                    } else {
                        productReq.setBrandId(null);
                        productReq.setBrandName(null);
                    }

                    // Set main image and image URL
                    if (productDetail.getProduct().getProductPhotos() != null && !productDetail.getProduct().getProductPhotos().isEmpty()) {
                        ProductPhoto mainPhoto = productDetail.getProduct().getProductPhotos().stream()
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
                        List<String> notMainImageUrls = productDetail.getProduct().getProductPhotos().stream()
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

    @PutMapping("/update-quantity/{id}")
    public ResponseEntity<ProductDetails> updateQuantityProductSreach(@PathVariable Long id ,@RequestBody ProductReq productReq) {
        try {
            ProductDetails updatedProductdetails = productDetailService.updateProductDetailQuantityService(id, productReq);
            return ResponseEntity.ok(updatedProductdetails); // Trả về 200 OK và đối tượng sản phẩm đã cập nhật
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về 404 Not Found nếu không tìm thấy sản phẩm

        }
    }

}
