package com.thocodeonline.sheepshop.service;

import com.thocodeonline.sheepshop.entity.ProductDetails;
import com.thocodeonline.sheepshop.repository.ProductDetailRepository;
import com.thocodeonline.sheepshop.request.ProductRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    public List<ProductDetails> getAllProductDetails() {
        return productDetailRepository.getAllProductDetails();
    }

    public ProductDetails getProductDetailsById(Long id) {
        return productDetailRepository.getById(id);
    }

    public ProductDetails updateProductDetailreduceQuantityService(Long id, ProductRequest productRequest) {
        // Tìm sản phẩm theo ID
        ProductDetails productDetails = productDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại với ID: " + id));

        // Kiểm tra số lượng yêu cầu
        if (productRequest.getQuantity() == 1) {
            // Giảm số lượng sản phẩm
            productDetails.setQuantity(productDetails.getQuantity() - 1);
        } else {
            throw new RuntimeException("Số lượng chỉ được trừ 1: " + productRequest.getQuantity());
        }

        // Lưu lại sản phẩm đã cập nhật
        return productDetailRepository.save(productDetails);
    }

    public ProductDetails updateProductDetailincreaseQuantityService(Long id, ProductRequest productRequest) {
        // Tìm sản phẩm theo ID
        ProductDetails productDetails = productDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại với ID: " + id));

        // Kiểm tra số lượng yêu cầu
        if (productRequest.getQuantity() == 1) {
            // tăng lại số lượng sản phẩm
            productDetails.setQuantity(productDetails.getQuantity() + 1);
        } else {
            throw new RuntimeException("Số lượng chỉ được cộng 1: " + productRequest.getQuantity());
        }

        // Lưu lại sản phẩm đã cập nhật
        return productDetailRepository.save(productDetails);
    }

    public ProductDetails deleteProductDetailUpdateQuantity(Long id, ProductRequest productRequest) {
        // Tìm sản phẩm theo ID
        ProductDetails productDetails = productDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại với ID: " + id));

        productDetails.setQuantity(productDetails.getQuantity() + productRequest.getQuantity());
        // Lưu lại sản phẩm đã cập nhật
        return productDetailRepository.save(productDetails);
    }


}
