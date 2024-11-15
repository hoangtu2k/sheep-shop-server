package com.thocodeonline.sheepshop.service;

import com.thocodeonline.sheepshop.entity.Bill;
import com.thocodeonline.sheepshop.entity.InvoiceDetails;
import com.thocodeonline.sheepshop.repository.BillRepository;
import com.thocodeonline.sheepshop.repository.InvoiceDetailsRepository;
import com.thocodeonline.sheepshop.request.BillReq;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private InvoiceDetailsRepository invoiceDetailsRepository;

    public List<Bill> getAllBillTayQuay(){
        return billRepository.getAllBillTaiQuay();
    }

    public Bill createBillTaiQiay(BillReq billReq){
        Bill bill = new Bill();
        bill.setCode(generateUserCode());
        bill.setPaymentStatus(0);
        bill.setSalesChannel(0);
        return billRepository.save(bill);
    }

    public void deleteBill(Long id) {
        if (!billRepository.existsById(id)) {
            throw new EntityNotFoundException("Bill not found with ID: " + id);
        }

        // Xóa tất cả các chi tiết hóa đơn liên quan
        List<InvoiceDetails> invoiceDetails = invoiceDetailsRepository.findAllByBillId(id);
        invoiceDetailsRepository.deleteAll(invoiceDetails);
        billRepository.deleteByBillId(id);
    }

    private String generateUserCode() {
        return UUID.randomUUID().toString(); // Example using UUID
    }

}
