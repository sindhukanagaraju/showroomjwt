package com.showroommanagement.service;

import com.showroommanagement.dto.CustomerDetailDTO;
import com.showroommanagement.entity.Customer;
import com.showroommanagement.entity.Department;
import com.showroommanagement.entity.Employee;
import com.showroommanagement.entity.Showroom;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.CustomerRepository;
import com.showroommanagement.util.Constant;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private Employee employee;
    @Mock
    private Showroom showroom;
    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeAll
    public static void toStartCustomerService() {
        System.out.println("Customer Service Test case execution has been Started");
    }

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1);
        customer.setName("viha");
        customer.setAddress("25, Sakthi Street,Devi Nagar, Chennai - 600 092.");
        customer.setEmployee(employee);
    }

    @Test
    void testCreateCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer savedCustomer = customerService.createCustomer(customer);

        assertNotNull(savedCustomer);
        assertEquals(customer.getId(), savedCustomer.getId());
        assertEquals(customer.getName(), savedCustomer.getName());
        assertEquals(customer.getAddress(), savedCustomer.getAddress());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testRetrieveCustomerById() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Customer retrieveCustomer = customerService.retrieveCustomerById(1);

        assertNotNull(retrieveCustomer);
        assertEquals(customer.getId(), retrieveCustomer.getId());
        assertEquals(customer.getName(), retrieveCustomer.getName());
        assertEquals(customer.getAddress(), retrieveCustomer.getAddress());
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveCustomerByIdNotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> customerService.retrieveCustomerById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveAllCustomer() {
        when(customerRepository.findAll()).thenReturn(List.of(customer));

        List<Customer> customers = customerService.retrieveCustomer();

        assertNotNull(customers);
        assertEquals(1, customers.size());
        assertEquals(customer.getId(), customers.get(0).getId());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testUpdateCustomerById() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1);
        updatedCustomer.setName("viha updated");

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        Customer customer1 = customerService.updateCustomerById(updatedCustomer, 1);

        assertNotNull(customer1);
        assertEquals("viha updated", customer.getName());
        verify(customerRepository, times(1)).findById(1);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomerByIdNotFound() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1);
        updatedCustomer.setName("viha updated");

        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> customerService.updateCustomerById(updatedCustomer, 1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteCustomerById() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        String result = customerService.removeCustomerById(1);

        assertEquals(Constant.REMOVE, result);
        verify(customerRepository, times(1)).findById(1);
        verify(customerRepository, times(1)).delete(any(Customer.class));
    }

    @Test
    void testDeleteCustomerByIdNotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> customerService.removeCustomerById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveCustomerDetail() {

        showroom = new Showroom();
        showroom.setName("Poorvika");

        Department department = new Department();
        department.setShowroom(showroom);

        Employee employee = new Employee();
        employee.setDepartment(department);

        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("viha");
        customer.setAddress("25, Sakthi Street,Devi Nagar, Chennai - 600 092.");
        customer.setEmployee(employee);

        List<Customer> mockCustomerList = List.of(customer);
        when(customerRepository.findAll()).thenReturn(mockCustomerList);

        List<CustomerDetailDTO> result = customerService.retrieveCustomerDetail();

        assertNotNull(result);
        assertEquals(1, result.size());

        CustomerDetailDTO dto = result.get(0);
        assertEquals("viha", dto.getName());
        assertEquals("25, Sakthi Street,Devi Nagar, Chennai - 600 092.", dto.getCustomerAddress());
        assertEquals("Poorvika", dto.getShowroomName());

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveCustomerName() {
        List<String> mockNames = List.of("viha", "pavi", "sadhana");
        when(customerRepository.findAllCustomerName()).thenReturn(mockNames);

        List<String> result = customerService.retrieveCustomerName();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("viha", result.get(0));
        assertEquals("pavi", result.get(1));
        assertEquals("sadhana", result.get(2));

        verify(customerRepository, times(1)).findAllCustomerName();
    }

    @AfterAll
    public static void toEndCustomerService() {
        System.out.println("Customer Service Test case has been execution finished");
    }
}