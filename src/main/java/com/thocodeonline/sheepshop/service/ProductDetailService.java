package com.thocodeonline.sheepshop.service;

import com.thocodeonline.sheepshop.entity.ProductDetails;
import com.thocodeonline.sheepshop.repository.ProductDetailRepository;
import com.thocodeonline.sheepshop.request.ProductReq;
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

    public ProductDetails updateProductDetailQuantityService(Long id, ProductReq productReq) {
        // Tìm sản phẩm theo ID
        ProductDetails productDetails = productDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại với ID: " + id));

        // Kiểm tra số lượng yêu cầu
        if (productReq.getQuantity() == 1) {
            // Giảm số lượng sản phẩm
            productDetails.setQuantity(productDetails.getQuantity() - 1);
        } else {
            throw new RuntimeException("Số lượng chỉ được trừ 1: " + productReq.getQuantity());
        }

        // Lưu lại sản phẩm đã cập nhật
        return productDetailRepository.save(productDetails);
    }


}
