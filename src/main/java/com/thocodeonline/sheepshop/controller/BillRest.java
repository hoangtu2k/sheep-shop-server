package com.thocodeonline.sheepshop.controller;

import com.thocodeonline.sheepshop.entity.Bill;
import com.thocodeonline.sheepshop.entity.Product;
import com.thocodeonline.sheepshop.request.BillReq;
import com.thocodeonline.sheepshop.request.ProductReq;
import com.thocodeonline.sheepshop.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sale/billtaiquay")
public class BillRest {

    @Autowired
    private BillService billService;

    @GetMapping()
    public ResponseEntity<List<BillReq>> getAllBillTaiQuay() {
        // Lấy danh sách bill từ service
        List<Bill> bills = billService.getAllBillTayQuay();

        // Nếu không có hóa đơn, trả về danh sách rỗng
        if (bills == null || bills.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>()); // Trả về danh sách rỗng
        }

        // Chuyển đổi danh sách bill sang danh sách billReqs
        List<BillReq> billReqs = bills.stream()
                .map(bill -> {
                    BillReq billReq = new BillReq();
                    billReq.setId(bill.getId()); // Lấy id từ bill
                    billReq.setCode(bill.getCode()); // Lấy code từ bill
                    billReq.setPaymentStatus(bill.getPaymentStatus()); // Lấy paymentStatus từ bill
                    billReq.setSalesChannel(bill.getSalesChannel()); // Lấy salesChannel từ bill

                    return billReq; // Trả về đối tượng đã tạo
                })
                .collect(Collectors.toList()); // Thu thập kết quả vào danh sách

        return ResponseEntity.ok(billReqs); // Trả về 200 và danh sách
    }

    @PostMapping()
    public ResponseEntity<Bill> createProduct(@RequestBody BillReq billReq) {
        // Kiểm tra tính hợp lệ
        if (billReq == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Bill createdBill = billService.createBillTaiQiay(billReq);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBill);
        } catch (Exception e) {
            // Xử lý lỗi phù hợp (có thể ghi log hoặc trả về thông báo cụ thể hơn)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
            billService.deleteBill(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
