package com.thocodeonline.sheepshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code; // mã đơn
    private String buyerName; // tên người mua
    private String deliveryAddress; // địa chỉ nhận hàng
    private Date deliveryDate; // ngày giao hàng
    private String consignee; // người nhận hàng
    private String phoneNumber; // số người nhận
    private BigDecimal totalAmount; // tổng tiền hàng
    private String note; // ghi chú
    private Integer formOfPayment; // hình thức thanh toán 0 tiền mặt 1 chuyển khoản
    private Integer salesChannel; // kênh bán hàng 0 ban tay quay || 1 ban giao hang
    private Integer paymentStatus; // trạng thái thanh toán 0 chua thanh toan || 1 da thanh toan
    private Integer orderStatus; // trạng thái đơn hàng 0 xác nhận đơn hàng || 1 chờ đơn vị vận chuyển || 2 dang giao hang || 3 da giao hang
    private String createName; // người tạo
    private String payer; // người thanh toán

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @JsonIgnore
    @OneToMany(mappedBy = "bill")
    private Set<InvoiceDetails> invoiceDetails = new HashSet<InvoiceDetails>();

}
