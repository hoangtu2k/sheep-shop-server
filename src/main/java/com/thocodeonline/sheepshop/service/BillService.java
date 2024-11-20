package com.thocodeonline.sheepshop.service;

import com.thocodeonline.sheepshop.entity.Bill;
import com.thocodeonline.sheepshop.entity.Customer;
import com.thocodeonline.sheepshop.entity.InvoiceDetails;
import com.thocodeonline.sheepshop.entity.User;
import com.thocodeonline.sheepshop.repository.BillRepository;
import com.thocodeonline.sheepshop.repository.InvoiceDetailsRepository;
import com.thocodeonline.sheepshop.request.BillRequest;
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

    public Bill createBillTaiQiay(BillRequest billRequest){
        Bill bill = new Bill();
        bill.setCode(getNextMa());
        bill.setPaymentStatus(0);
        bill.setCreateName(billRequest.getCreateName());
        bill.setUser(User.builder().id(billRequest.getUserId()).build());
        return billRepository.save(bill);
    }

    public Bill updatePayBillTaiQuay(Long id, BillRequest billRequest){
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hóa đơn không tồn tại với ID: " + id));
        bill.setSalesChannel(billRequest.getSalesChannel());
        bill.setBuyerName(billRequest.getBuyerName());
        bill.setTotalAmount(billRequest.getTotalAmount());
        bill.setNote(billRequest.getNote());
        bill.setFormOfPayment(billRequest.getFormOfPayment());
        bill.setBuyerName(billRequest.getBuyerName());
        if (billRequest.getCustomerId() != null) {
            bill.setCustomer(Customer.builder().id(billRequest.getCustomerId()).build());
        } else {
            bill.setCustomer(null);
        }

        bill.setPayer(bill.getPayer());
        bill.setPaymentStatus(1);
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

    private String getNextMa() {
        String biggestMa = billRepository.getBiggestMa();
        if (biggestMa == null || biggestMa.isEmpty()) {
            return "HD01";
        } else {
            int currentMa = Integer.parseInt(biggestMa.substring(2));
            String newMa = "HD" + String.format("%02d", ++currentMa);
            return newMa;
        }
    }

}
