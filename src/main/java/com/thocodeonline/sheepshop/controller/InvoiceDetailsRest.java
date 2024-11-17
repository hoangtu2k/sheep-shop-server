package com.thocodeonline.sheepshop.controller;

import com.thocodeonline.sheepshop.entity.InvoiceDetails;
import com.thocodeonline.sheepshop.entity.ProductPhoto;
import com.thocodeonline.sheepshop.request.InvoiceDetailsRequest;
import com.thocodeonline.sheepshop.service.InvoiceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sale/invoice-details")
public class InvoiceDetailsRest {

    @Autowired
    private InvoiceDetailsService invoiceDetailsService;

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDetails> findById(@PathVariable Long id) {
        InvoiceDetails invoiceDetails = invoiceDetailsService.getInvoiceDetailsById(id);

        if (invoiceDetails != null) {
            return ResponseEntity.ok(invoiceDetails);
        } else {
            // Nếu không tìm thấy, trả về HttpStatus 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bill/{billId}")
    public ResponseEntity<List<InvoiceDetailsRequest>> getAllBillProductDetails(@PathVariable Long billId) {
        List<InvoiceDetails> invoiceDetails = invoiceDetailsService.getAllBillProductDetails(billId);

        List<InvoiceDetailsRequest> invoiceDetailsRequestList = invoiceDetails.stream()
                .map(invoiceDetail -> {
                    InvoiceDetailsRequest invoiceDetailsRequest = new InvoiceDetailsRequest();
                    invoiceDetailsRequest.setInvoiceId(invoiceDetail.getId());
                    invoiceDetailsRequest.setQuantity(invoiceDetail.getQuantity());
                    invoiceDetailsRequest.setUnitPrice(invoiceDetail.getUnitPrice());
                    invoiceDetailsRequest.setBillId(invoiceDetail.getBill().getId());
                    invoiceDetailsRequest.setProductDetailId(invoiceDetail.getProductDetails().getId());
                    invoiceDetailsRequest.setProductDetailName(invoiceDetail.getProductDetails().getProduct().getName());
                    invoiceDetailsRequest.setColorId(invoiceDetail.getColorId());
                    invoiceDetailsRequest.setSizeId(invoiceDetail.getSizeId());

                    // Get the main image URL
                    String mainImageUrl = invoiceDetail.getProductDetails().getProduct().getProductPhotos().stream()
                            .filter(photo -> Boolean.TRUE.equals(photo.getMainImage()))
                            .findFirst()
                            .map(ProductPhoto::getImageUrl)
                            .orElse(null);

                    invoiceDetailsRequest.setProductDetailUrlImage(mainImageUrl);

                    return invoiceDetailsRequest;
                })
                .collect(Collectors.toList());

        if (invoiceDetailsRequestList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(invoiceDetailsRequestList);
    }

    @PostMapping()
    public ResponseEntity<InvoiceDetails> createBillDetails(@RequestBody InvoiceDetailsRequest invoiceDetailsRequest) {
        // Kiểm tra tính hợp lệ
        if (invoiceDetailsRequest == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            InvoiceDetails invoiceDetails = invoiceDetailsService.addBillDetail(invoiceDetailsRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(invoiceDetails);
        } catch (Exception e) {
            // Xử lý lỗi phù hợp (có thể ghi log hoặc trả về thông báo cụ thể hơn)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/increase/{id}")
    public ResponseEntity<InvoiceDetails> increaseBillDetail(@PathVariable Long id,@RequestBody InvoiceDetailsRequest invoiceDetailsRequest) {
        // Kiểm tra tính hợp lệ
        if (invoiceDetailsRequest == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            InvoiceDetails invoiceDetails = invoiceDetailsService.increaseBillDetail(id,invoiceDetailsRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(invoiceDetails);
        } catch (Exception e) {
            // Xử lý lỗi phù hợp (có thể ghi log hoặc trả về thông báo cụ thể hơn)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/reduce/{id}")
    public ResponseEntity<InvoiceDetails> reduceBillDetail(@PathVariable Long id,@RequestBody InvoiceDetailsRequest invoiceDetailsRequest) {
        // Kiểm tra tính hợp lệ
        if (invoiceDetailsRequest == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            InvoiceDetails invoiceDetails = invoiceDetailsService.reduceBillDetail(id,invoiceDetailsRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(invoiceDetails);
        } catch (Exception e) {
            // Xử lý lỗi phù hợp (có thể ghi log hoặc trả về thông báo cụ thể hơn)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoiceDetail(@PathVariable Long id) {
        invoiceDetailsService.deleteByInvoiceId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
