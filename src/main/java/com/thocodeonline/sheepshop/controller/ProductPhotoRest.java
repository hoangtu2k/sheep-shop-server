package com.thocodeonline.sheepshop.controller;

import com.thocodeonline.sheepshop.request.ProductReq;
import com.thocodeonline.sheepshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product/image")
public class ProductPhotoRest {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ProductReq image){
        return ResponseEntity.ok(service.createProductPhoto(image));
    }



}
