package com.thocodeonline.sheepshop.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter@Setter
public class BillRequest {
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
    private Integer orderStatus; // trạng thái đơn hàng 0 xác nhận đơn hàng || 1 chờ đơn vị vận chuyển || 2 dang giao hang || 3 da giao hang
    private String createName; // người tạo
    private String payer; // người thanh toán
    private Long userId;
    private Long customerId;
    private String customerName;

}
