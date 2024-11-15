package com.thocodeonline.sheepshop.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter@Setter
public class BillReq {
    private Long id;
    private String code; // mã đơn
    private String buyerName; // tên người mua
    private String deliveryAddress; // địa chỉ nhận hàng
    private Date deliveryDate; // ngày giao hàng
    private String consignee; // người nhận hàng
    private String phoneNumber; // số người nhận
    private BigDecimal totalAmount; // tổng tiền hàng
    private String note; // ghi chú
    private Integer salesChannel; // kênh bán hàng 0 ban tay quay || 1 ban online
    private Integer paymentStatus; // trạng thái thanh toán 0 chua thanh toan || 1 da thanh toan
    private Integer orderStatus;
}
