package com.showroommanagement.controller;

import com.showroommanagement.dto.ResponseDTO;
import com.showroommanagement.entity.Customer;
import com.showroommanagement.service.CustomerService;
import com.showroommanagement.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customer")
    public ResponseDTO createCustomer(@RequestBody final Customer customer) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.CREATE, this.customerService.createCustomer(customer));
    }

    @GetMapping("/customer/{id}")
    public ResponseDTO retrieveCustomerById(@PathVariable final Integer id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.customerService.retrieveCustomerById(id));
    }

    @GetMapping("/customer")
    public ResponseDTO retrieveCustomer() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.customerService.retrieveCustomer());
    }

    @PutMapping("/customer/{id}")
    public ResponseDTO updateCustomerById(@PathVariable final Integer id, @RequestBody final Customer customer) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.customerService.updateCustomerById(customer, id));
    }

    @DeleteMapping("/customer/{id}")
    public ResponseDTO removeCustomerById(@PathVariable final Integer id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.REMOVE, this.customerService.removeCustomerById(id));
    }

    @GetMapping("/customer/detail")
    public ResponseDTO retrieveCustomerDetail() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.customerService.retrieveCustomerDetail());
    }

    @GetMapping("/customer/name")
    public ResponseDTO retrieveCustomerName() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.customerService.retrieveCustomerName());
    }
}
