package com.thocodeonline.sheepshop.controller;

import com.thocodeonline.sheepshop.entity.Brand;
import com.thocodeonline.sheepshop.request.AttributeProductReq;
import com.thocodeonline.sheepshop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/brands")
public class BrandRest {

    @Autowired
    private BrandService brandService;

    @GetMapping()
    public ResponseEntity<List<AttributeProductReq>> getAllAccout() {
        List<Brand> brands = brandService.getAllBrand();

        // Chuyển đổi danh sách User sang danh sách UserReq trực tiếp
        List<AttributeProductReq> attributeProductReqs = brands.stream()
                .map(brand  -> {
                    AttributeProductReq req = new AttributeProductReq();
                    req.setId(brand.getId());
                    req.setName(brand.getName());
                    return req; // Trả về đối tượng UserReq đã tạo
                })
                .collect(Collectors.toList()); // Thu thập kết quả vào danh sách

        if (attributeProductReqs.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có người dùng
        }
        return ResponseEntity.ok(attributeProductReqs); // Trả về 200 và danh sách người dùng
    }

}
