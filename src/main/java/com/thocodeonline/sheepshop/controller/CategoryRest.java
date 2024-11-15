package com.thocodeonline.sheepshop.controller;

import com.thocodeonline.sheepshop.entity.Category;
import com.thocodeonline.sheepshop.request.AttributeProductRequest;
import com.thocodeonline.sheepshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/categories")
public class CategoryRest {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<AttributeProductRequest>> getAllAccout() {
        List<Category> categories = categoryService.getAllCategory();

        // Chuyển đổi danh sách User sang danh sách UserReq trực tiếp
        List<AttributeProductRequest> attributeProductReqs = categories.stream()
                .map(category  -> {
                    AttributeProductRequest req = new AttributeProductRequest();
                    req.setId(category.getId());
                    req.setName(category.getName());
                    return req; // Trả về đối tượng UserReq đã tạo
                })
                .collect(Collectors.toList()); // Thu thập kết quả vào danh sách

        if (attributeProductReqs.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có người dùng
        }
        return ResponseEntity.ok(attributeProductReqs); // Trả về 200 và danh sách người dùng
    }

}
