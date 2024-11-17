package com.thocodeonline.sheepshop.service;

import com.thocodeonline.sheepshop.entity.Bill;
import com.thocodeonline.sheepshop.entity.InvoiceDetails;
import com.thocodeonline.sheepshop.entity.ProductDetails;
import com.thocodeonline.sheepshop.repository.InvoiceDetailsRepository;
import com.thocodeonline.sheepshop.request.InvoiceDetailsRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceDetailsService {

    @Autowired
    private InvoiceDetailsRepository invoiceDetailsRepository;

    public InvoiceDetails getInvoiceDetailsById(Long id) {
        return invoiceDetailsRepository.getById(id);
    }

    public List<InvoiceDetails> getAllBillProductDetails(Long billId) {
        return invoiceDetailsRepository.findAllByBillId(billId);
    }

    public InvoiceDetails addBillDetail(InvoiceDetailsRequest request) {
        // Tìm hóa đơn hiện tại
        InvoiceDetails existingDetail = invoiceDetailsRepository.findByBillAndProductDetails(request.getBillId(), request.getProductDetailId());

        if (existingDetail != null) {
            // Nếu sản phẩm đã tồn tại, cộng dồn số lượng
            existingDetail.setQuantity(existingDetail.getQuantity() + request.getQuantity());
            return invoiceDetailsRepository.save(existingDetail);
        } else {
            // Nếu sản phẩm chưa tồn tại, tạo mới
            InvoiceDetails billDetail = new InvoiceDetails();
            billDetail.setBill(Bill.builder().id(request.getBillId()).build());
            billDetail.setProductDetails(ProductDetails.builder().id(request.getProductDetailId()).build());
            billDetail.setQuantity(request.getQuantity());
            billDetail.setUnitPrice(request.getUnitPrice());
            billDetail.setColorId(request.getColorId());
            billDetail.setSizeId(request.getSizeId());

            return invoiceDetailsRepository.save(billDetail);
        }
    }

    public InvoiceDetails increaseBillDetail(Long id,InvoiceDetailsRequest request) {
            InvoiceDetails billDetail = invoiceDetailsRepository.getById(id);
            billDetail.setBill(Bill.builder().id(request.getBillId()).build());
            billDetail.setProductDetails(ProductDetails.builder().id(request.getProductDetailId()).build());
            billDetail.setQuantity(billDetail.getQuantity() + 1);
            billDetail.setSizeId(request.getSizeId());
            billDetail.setColorId(request.getColorId());
            return invoiceDetailsRepository.save(billDetail);
    }

    public InvoiceDetails reduceBillDetail(Long id, InvoiceDetailsRequest request) {
        InvoiceDetails billDetail = invoiceDetailsRepository.getById(id);
        billDetail.setBill(Bill.builder().id(request.getBillId()).build());
        billDetail.setProductDetails(ProductDetails.builder().id(request.getProductDetailId()).build());
        billDetail.setQuantity(billDetail.getQuantity() - 1);
        billDetail.setSizeId(request.getSizeId());
        billDetail.setColorId(request.getColorId());
        // Check if quantity is 0, then delete
        if (billDetail.getQuantity() <= 0) {
            invoiceDetailsRepository.delete(billDetail);
            return null; // or throw an exception, depending on your needs
        }

        return invoiceDetailsRepository.save(billDetail);
    }

    public void deleteByInvoiceId(Long id) {
        if (!invoiceDetailsRepository.existsById(id)) {
            throw new EntityNotFoundException("Bill not found with ID: " + id);
        }
        invoiceDetailsRepository.deleteByInvoiceId(id);
    }

}
