package com.thocodeonline.sheepshop.controller;

import com.thocodeonline.sheepshop.entity.Product;
import com.thocodeonline.sheepshop.request.ProductReq;
import com.thocodeonline.sheepshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/products")
public class ProductRest {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductReq>> getAll() {
        // Lấy danh sách User từ service
        List<Product> products = productService.getAllProduct();

        // Chuyển đổi danh sách User sang danh sách UserReq trực tiếp
        List<ProductReq> productReqs = products.stream()
                .map(product -> {
                    ProductReq productReq = new ProductReq();

                    productReq.setCode(product.getCode());
                    productReq.setBarcode(product.getBarcode());
                    productReq.setName(product.getName());
                    productReq.setPrice(product.getPrice());
                    productReq.setDescription(product.getDescription());
                    productReq.setStatus(product.getStatus());

                    // Kiểm tra xem dữ liệu có tồn tại không
                    if (product.getCategory() != null && product.getBrand() != null) {
                        productReq.setBrandName(product.getBrand().getName());
                        productReq.setCategoryName(product.getCategory().getName());
                    } else {
                        productReq.setBrandName(null);
                        productReq.setCategoryName(null);
                    }

                    return productReq; // Trả về đối tượng UserReq đã tạo
                })
                .collect(Collectors.toList()); // Thu thập kết quả vào danh sách

        if (productReqs.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có người dùng
        }
        return ResponseEntity.ok(productReqs); // Trả về 200 và danh sách người dùng
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

}
