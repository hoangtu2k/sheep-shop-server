package com.thocodeonline.sheepshop.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductReq {
    private Long id;
    private String code;
    private String barcode;
    private String name;
    private String material;
    private Double weight;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private Long brandId;
    private String categoryName;
    private String brandName;
    private Integer status;
    private Boolean mainImage;
    private String imageUrl;
    private Long productId;
}
