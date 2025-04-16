package com.showroommanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showroommanagement.dto.CustomerDetailDTO;
import com.showroommanagement.entity.*;
import com.showroommanagement.service.CustomerService;
import com.showroommanagement.util.Constant;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Customer customer;

    @BeforeAll
    public static void toStartCustomerController() {
        System.out.println("Customer Controller Test case execution has been started");
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        Showroom showroom = new Showroom();
        showroom.setId(1);
        showroom.setName("poorvika");

        Department department = new Department();
        department.setId(3);
        department.setName("sales");
        department.setShowroom(showroom);

        Branch branch = new Branch();
        branch.setId(1);
        branch.setBranch("chennai");
        branch.setShowroom(showroom);

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("karthika");
        employee.setSalary(88000.0);
        employee.setAddress("1st street,seruchery,chennai");
        employee.setDepartment(department);
        employee.setBranch(branch);

        customer = new Customer();
        customer.setId(1);
        customer.setName("viha");
        customer.setAddress("1, viha, 25, Sakthi Street,Devi Nagar, Chennai - 600 092.");
        customer.setEmployee(employee);
    }

    @Test
    public void testCreateCustomer() throws Exception {
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.CREATE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("viha"));

        verify(customerService, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    public void testRetrieveCustomerById() throws Exception {
        when(customerService.retrieveCustomerById(1)).thenReturn(customer);
        mockMvc.perform(get("/api/v1/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("viha"));

        verify(customerService, times(1)).retrieveCustomerById(customer.getId());
    }

    @Test
    public void testRetrieveAllCustomers() throws Exception {
        when(customerService.retrieveCustomer()).thenReturn(List.of(customer));
        mockMvc.perform(get("/api/v1/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("viha"));

        verify(customerService, times(1)).retrieveCustomer();
    }

    @Test
    public void testUpdateCustomerById() throws Exception {
        when(customerService.updateCustomerById(any(Customer.class), anyInt()))
                .thenReturn(customer);
        mockMvc.perform(put("/api/v1/customer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.UPDATE))
                .andExpect(jsonPath("$.data.name").value("viha"));
        verify(customerService, times(1)).updateCustomerById(any(Customer.class), eq(1));
    }

    @Test
    public void testDeleteCustomerById() throws Exception {
        when(customerService.removeCustomerById(1)).thenReturn("true");
        mockMvc.perform(delete("/api/v1/customer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.REMOVE))
                .andExpect(jsonPath("$.data").value("true"));
        verify(customerService, times(1)).removeCustomerById(1);
    }

    @Test
    public void testRetrieveAllCustomerDetail() throws Exception {
        CustomerDetailDTO customerDetailDTO = new CustomerDetailDTO();
        customerDetailDTO.setName("viha");
        customerDetailDTO.setCustomerAddress("1, viha, 25, Sakthi Street,Devi Nagar, Chennai - 600 092.");
        customerDetailDTO.setShowroomName("Poorvika");
        when(customerService.retrieveCustomerDetail()).thenReturn(List.of(customerDetailDTO));
        mockMvc.perform(get("/api/v1/customer/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data[0].name").value("viha"))
                .andExpect(jsonPath("$.data[0].customerAddress").value("1, viha, 25, Sakthi Street,Devi Nagar, Chennai - 600 092."))
                .andExpect(jsonPath("$.data[0].showroomName").value("Poorvika"));
    }

    @Test
    void testRetrieveCustomerDetail() throws Exception {
        CustomerDetailDTO customerDetailDTO = new CustomerDetailDTO();
        customerDetailDTO.setName("viha");
        customerDetailDTO.setCustomerAddress("1, viha, 25, Sakthi Street,Devi Nagar, Chennai - 600 092.");
        customerDetailDTO.setShowroomName("Poorvika");

        when(customerService.retrieveCustomerDetail()).thenReturn(List.of(customerDetailDTO));

        mockMvc.perform(get("/api/v1/customer/details")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data[0].name").value("viha"))
                .andExpect(jsonPath("$.data[0].customerAddress").value("1, viha, 25, Sakthi Street,Devi Nagar, Chennai - 600 092."))
                .andExpect(jsonPath("$.data[0].showroomName").value("Poorvika"));
    }

    @Test
    void testRetrieveCustomerName() throws Exception {
        List<String> mockNames = List.of("viha", "pavi", "sadhana");
        when(customerService.retrieveCustomerName()).thenReturn(mockNames);

        mockMvc.perform(get("/api/v1/customer/name")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0]").value("viha"))
                .andExpect(jsonPath("$.data[1]").value("pavi"))
                .andExpect(jsonPath("$.data[2]").value("sadhana"));
    }


    @AfterAll
    public static void endCustomerControllerTest() {
        System.out.println("Customer Controller Test execution finished");
    }
}
