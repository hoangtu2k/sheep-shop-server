package com.thocodeonline.sheepshop.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class InvoiceDetailsRequest {
        private long invoiceId;
        private BigDecimal unitPrice;
        private long colorId ;
        private long sizeId;
        private long billId;
        private Integer quantity;
        private long productDetailId;
        private String productDetailName;
        private String productDetailUrlImage;
        private boolean productDetailMainImage;
}
