package com.thocodeonline.sheepshop.controller;

import com.thocodeonline.sheepshop.entity.Customer;
import com.thocodeonline.sheepshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/customers")
public class CustomerRest {

    @Autowired
    private CustomerService customerService;

    @PostMapping()
    public ResponseEntity<List<Customer>> getAllCustomer() {
        List<Customer> customers = customerService.getAllCustomer();
        return ResponseEntity.ok(customers);
    }

}
