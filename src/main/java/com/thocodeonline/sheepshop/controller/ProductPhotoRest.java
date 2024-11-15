package com.thocodeonline.sheepshop.controller;

import com.thocodeonline.sheepshop.request.ProductReq;
import com.thocodeonline.sheepshop.service.ProductPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product/image")
public class ProductPhotoRest {

    @Autowired
    private ProductPhotoService service;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ProductReq image){
        return ResponseEntity.ok(service.createProductPhoto(image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.deleteImg(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
