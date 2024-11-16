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

    public InvoiceDetails updateProductDetailsToInvoiceDetails(String invoiceId) {

        return null;
    }

    public InvoiceDetails getInvoiceDetailsById(Long id) {
        return invoiceDetailsRepository.getById(id);
    }

    public List<InvoiceDetails> getAllBillProductDetails(Long billId) {
        return invoiceDetailsRepository.findAllByBillId(billId);
    }

    public InvoiceDetails addBillDetail(InvoiceDetailsRequest request){
        InvoiceDetails billDetail = new InvoiceDetails();
        billDetail.setBill(Bill.builder().id(request.getBillId()).build());
        billDetail.setProductDetails(ProductDetails.builder().id(request.getProductDetailId()).build());
        billDetail.setQuantity(request.getQuantity());
        billDetail.setUnitPrice(request.getUnitPrice());
        return invoiceDetailsRepository.save(billDetail);
    }

    public void deleteByInvoiceId(Long id) {
        if (!invoiceDetailsRepository.existsById(id)) {
            throw new EntityNotFoundException("Bill not found with ID: " + id);
        }
        invoiceDetailsRepository.deleteByInvoiceId(id);
    }

}