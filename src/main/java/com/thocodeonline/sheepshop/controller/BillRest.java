package com.thocodeonline.sheepshop.controller;

import com.thocodeonline.sheepshop.entity.Bill;
import com.thocodeonline.sheepshop.request.BillRequest;
import com.thocodeonline.sheepshop.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sale/bill")
public class BillRest {

    @Autowired
    private BillService billService;

    @GetMapping()
    public ResponseEntity<List<BillRequest>> getAllBillTaiQuay() {
        // Lấy danh sách bill từ service
        List<Bill> bills = billService.getAllBillTayQuay();

        // Nếu không có hóa đơn, trả về danh sách rỗng
        if (bills == null || bills.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>()); // Trả về danh sách rỗng
        }

        // Chuyển đổi danh sách bill sang danh sách billReqs
        List<BillRequest> billRequests = bills.stream()
                .map(bill -> {
                    BillRequest billRequest = new BillRequest();
                    billRequest.setId(bill.getId()); // Lấy id từ bill
                    billRequest.setCode(bill.getCode()); // Lấy code từ bill
                    billRequest.setPaymentStatus(bill.getPaymentStatus()); // Lấy paymentStatus từ bill
                    billRequest.setSalesChannel(bill.getSalesChannel()); // Lấy salesChannel từ bill

                    return billRequest; // Trả về đối tượng đã tạo
                })
                .collect(Collectors.toList()); // Thu thập kết quả vào danh sách

        return ResponseEntity.ok(billRequests); // Trả về 200 và danh sách
    }

    @PostMapping()
    public ResponseEntity<Bill> createBillTaiQiay(@RequestBody BillRequest billRequest) {
        // Kiểm tra tính hợp lệ
        if (billRequest == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Bill createdBill = billService.createBillTaiQiay(billRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBill);
        } catch (Exception e) {
            // Xử lý lỗi phù hợp (có thể ghi log hoặc trả về thông báo cụ thể hơn)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bill> updatePayBillTaiQuay(@PathVariable Long id, @RequestBody BillRequest request) {
        try {
            Bill updatePayBillTaiQiay = billService.updatePayBillTaiQuay(id, request);
            return ResponseEntity.ok(updatePayBillTaiQiay); // Trả về 200 OK và đối tượng sản phẩm đã cập nhật
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về 404 Not Found nếu không tìm thấy sản phẩm
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
            billService.deleteBill(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
