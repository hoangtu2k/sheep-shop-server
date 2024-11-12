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
    private Integer salesChannel; // kênh bán hàng
    private Integer paymentStatus; // trạng thái thanh toán
    private Integer orderStatus; // trạng thái đơn hàng

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "bill")
    private Set<InvoiceDetails> invoiceDetails = new HashSet<InvoiceDetails>();

}
