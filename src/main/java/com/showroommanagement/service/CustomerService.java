package com.showroommanagement.service;

import com.showroommanagement.dto.CustomerDetailDTO;
import com.showroommanagement.entity.Customer;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.CustomerRepository;
import com.showroommanagement.util.Constant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer createCustomer(final Customer customer) {
        return this.customerRepository.save(customer);
    }

    public Customer retrieveCustomerById(final Integer id) {
        return this.customerRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
    }

    public List<Customer> retrieveCustomer() {
        return this.customerRepository.findAll();
    }

    @Transactional
    public Customer updateCustomerById(final Customer customer, final Integer id) {
        final Customer existingCustomer = this.customerRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        if (customer.getId() != null) {
            existingCustomer.setId(customer.getId());
        }
        if (customer.getName() != null) {
            existingCustomer.setName(customer.getName());
        }
        if (customer.getEmployee() != null) {
            existingCustomer.setEmployee(customer.getEmployee());
        }
        if (customer.getAddress() != null) {
            existingCustomer.setAddress(customer.getAddress());
        }
        return this.customerRepository.save(existingCustomer);
    }

    public String removeCustomerById(final Integer id) {
        final Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        this.customerRepository.delete(customer);
        return Constant.REMOVE;
    }

    public List<CustomerDetailDTO> retrieveCustomerDetail() {
        List<Customer> retrieveCustomer = this.customerRepository.findAll();
        List<CustomerDetailDTO> customerDetailDTOS = new ArrayList<>();
        for (Customer customer : retrieveCustomer) {
            CustomerDetailDTO customerDetailDTO = new CustomerDetailDTO();
            customerDetailDTO.setName(customer.getName());
            customerDetailDTO.setCustomerAddress(customer.getAddress());
            customerDetailDTO.setShowroomName(customer.getEmployee().getDepartment().getShowroom().getName());
            customerDetailDTOS.add(customerDetailDTO);
        }
        return customerDetailDTOS;
    }

    public List<String> retrieveCustomerName() {
        return customerRepository.findAllCustomerName();
    }

}


