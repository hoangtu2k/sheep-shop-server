package com.thocodeonline.sheepshop.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter @Getter
public class ProductReq {
    private Long id;
    private String code;
    private String barcode;
    private String name;
    private String material;
    private Double weight;
    private String description;
    private BigDecimal price;
    private Integer status;
    private Boolean mainImage;
    private List<String> notMainImages; // List of non-main image URLs
    private String imageUrl;
    private Long productId;
    private Long categoryId;
    private Long brandId;
    private Long colorId;
    private Long sizeId;
    private String categoryName;
    private String brandName;
    private String colorName;
    private String sizeName;
}
