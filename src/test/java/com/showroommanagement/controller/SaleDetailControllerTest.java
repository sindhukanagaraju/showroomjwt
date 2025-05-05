package com.showroommanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showroommanagement.entity.*;
import com.showroommanagement.service.SaleDetailService;
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

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class SaleDetailControllerTest {

    @Mock
    private SaleDetailService saleDetailService;

    @InjectMocks
    private SaleDetailController saleDetailController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private SaleDetail saleDetail;

    @BeforeAll
    public static void startSaleDetailTestSuite() {
        System.out.println("SaleDetail Controller Test case execution has been started");
    }

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(saleDetailController).build();

        Showroom showroom = new Showroom();
        showroom.setId(1);
        showroom.setName("Poorvika");

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Karthika");
        employee.setSalary(40000.0);
        employee.setAddress("1st street,seruchery,chennai");

        Brand brand = new Brand();
        brand.setId(1);
        brand.setBrand("Vivo");
        brand.setShowroom(showroom);

        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Viha");
        customer.setAddress("1, Viha, 25, Sakthi Street, Devi Nagar, Chennai - 600 092.");
        customer.setEmployee(employee);

        Product product = new Product();
        product.setId(1);
        product.setModel("Vivo Y30s");
        product.setPrice(65000.0);
        product.setColour("Velvet Red");
        product.setStock(2);
        product.setEmployee(employee);
        product.setBrand(brand);

        saleDetail = new SaleDetail();
        saleDetail.setId(11);
        saleDetail.setSalesDate(new Date());
        saleDetail.setCustomer(customer);
        saleDetail.setProduct(product);
    }

    @Test
    public void testCreateSale() throws Exception {
        when(saleDetailService.createSales(any(SaleDetail.class))).thenReturn(saleDetail);
        mockMvc.perform(post("/api/v1/sale")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saleDetail)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.CREATE))
                .andExpect(jsonPath("$.data.id").value(11))
                .andExpect(jsonPath("$.data.customer.name").value("Viha"))
                .andExpect(jsonPath("$.data.product.model").value("Vivo Y30s"))
                .andExpect(jsonPath("$.data.product.price").value(65000.0))
                .andExpect(jsonPath("$.data.product.employee.name").value("Karthika"))
                .andExpect(jsonPath("$.data.product.brand.brand").value("Vivo"))
                .andExpect(jsonPath("$.data.product.brand.showroom.name").value("Poorvika"));
        verify(saleDetailService, times(1)).createSales(any(SaleDetail.class));
    }

    @Test
    public void testRetrieveSalesById() throws Exception {
        when(saleDetailService.retrieveSalesById(11)).thenReturn(saleDetail);
        mockMvc.perform(get("/api/v1/sale/11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data.id").value(11))
                .andExpect(jsonPath("$.data.customer.name").value("Viha"))
                .andExpect(jsonPath("$.data.product.model").value("Vivo Y30s"))
                .andExpect(jsonPath("$.data.product.price").value(65000.0))
                .andExpect(jsonPath("$.data.product.employee.name").value("Karthika"))
                .andExpect(jsonPath("$.data.product.brand.brand").value("Vivo"))
                .andExpect(jsonPath("$.data.product.brand.showroom.name").value("Poorvika"));
        verify(saleDetailService, times(1)).retrieveSalesById(saleDetail.getId());
    }

    @Test
    public void testRetrieveAllSales() throws Exception {
        when(saleDetailService.retrieveSales()).thenReturn(List.of(saleDetail));
        mockMvc.perform(get("/api/v1/sale"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data[0].id").value(11))
                .andExpect(jsonPath("$.data[0].customer.name").value("Viha"))
                .andExpect(jsonPath("$.data[0].product.model").value("Vivo Y30s"))
                .andExpect(jsonPath("$.data[0].product.price").value(65000.0))
                .andExpect(jsonPath("$.data[0].product.employee.name").value("Karthika"))
                .andExpect(jsonPath("$.data[0].product.brand.brand").value("Vivo"))
                .andExpect(jsonPath("$.data[0].product.brand.showroom.name").value("Poorvika"));
        verify(saleDetailService, times(1)).retrieveSales();
    }

    @Test
    public void testUpdateSaleById() throws Exception {
        when(saleDetailService.updateSaleById(any(SaleDetail.class), anyInt()))
                .thenReturn(saleDetail);
        mockMvc.perform(put("/api/v1/sale/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saleDetail)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.UPDATE))
                .andExpect(jsonPath("$.data.product.model").value("Vivo Y30s"));
        verify(saleDetailService, times(1)).updateSaleById(any(SaleDetail.class), eq(11));
    }

    @Test
    public void testDeleteSaleById() throws Exception {
        when(saleDetailService.removeSaleById(11)).thenReturn("true");
        mockMvc.perform(delete("/api/v1/sale/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saleDetail)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.REMOVE))
                .andExpect(jsonPath("$.data").value("true"));
        verify(saleDetailService, times(1)).removeSaleById(11);
    }

    @AfterAll
    public static void endSaleDetailTestSuite() {
        System.out.println("SaleDetail Controller Test case execution has been finished");
    }
}
