package com.thocodeonline.sheepshop.service;

import com.thocodeonline.sheepshop.entity.ProductPhoto;
import com.thocodeonline.sheepshop.repository.ProductPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPhotoService {

    @Autowired
    private ProductPhotoRepository productPhotoRepository;

    public List<ProductPhoto> getAllProductPhoto() {
        return productPhotoRepository.getAllProductPhoto();
    }

    public ProductPhoto getProductPhotoById(Long id) {
        return productPhotoRepository.getById(id);
    }

}
