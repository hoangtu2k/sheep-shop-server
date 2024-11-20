package com.thocodeonline.sheepshop.service;

import com.thocodeonline.sheepshop.entity.Customer;
import com.thocodeonline.sheepshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomer() {
        return customerRepository.getAllCustomer();
    }

}
