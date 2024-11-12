package com.thocodeonline.sheepshop.service;

import com.thocodeonline.sheepshop.entity.Brand;
import com.thocodeonline.sheepshop.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllBrand() {
        return brandRepository.getAllBrand();
    }

}
