package com.showroommanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showroommanagement.entity.Brand;
import com.showroommanagement.entity.Employee;
import com.showroommanagement.entity.Product;
import com.showroommanagement.service.ProductService;
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
public class ProductControllerTest {
    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Product product;

    @BeforeAll
    public static void toStartProductController() {
        System.out.println("Product Controller Test case execution has been started");
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("karthika");
        employee.setSalary(40000.0);
        employee.setAddress("1st street,seruchery,chennai");

        Brand brand = new Brand();
        brand.setId(1);
        brand.setBrand("vivo");

        product = new Product();
        product.setId(1);
        product.setModel("vivo y30s");
        product.setPrice(65000.0);
        product.setColour("velvet red");
        product.setStock(2);
        product.setEmployee(employee);
        product.setBrand(brand);
    }

    @Test
    public void testCreateProduct() throws Exception {
        when(productService.createProduct(any(Product.class))).thenReturn(product);
        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.CREATE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.model").value("vivo y30s"))
                .andExpect(jsonPath("$.data.price").value(65000.0))
                .andExpect(jsonPath("$.data.colour").value("velvet red"))
                .andExpect(jsonPath("$.data.employee.name").value("karthika"))
                .andExpect(jsonPath("$.data.brand.brand").value("vivo"));
        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    public void testRetrieveProductById() throws Exception {
        when(productService.retrieveProductById(1)).thenReturn(product);
        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.model").value("vivo y30s"))
                .andExpect(jsonPath("$.data.price").value(65000.0))
                .andExpect(jsonPath("$.data.colour").value("velvet red"))
                .andExpect(jsonPath("$.data.employee.name").value("karthika"))
                .andExpect(jsonPath("$.data.brand.brand").value("vivo"));
        verify(productService, times(1)).retrieveProductById(product.getId());
    }

    @Test
    public void testRetrieveAllProduct() throws Exception {
        when(productService.retrieveProduct()).thenReturn(List.of(product));
        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].model").value("vivo y30s"))
                .andExpect(jsonPath("$.data[0].price").value(65000.0))
                .andExpect(jsonPath("$.data[0].colour").value("velvet red"))
                .andExpect(jsonPath("$.data[0].employee.name").value("karthika"))
                .andExpect(jsonPath("$.data[0].brand.brand").value("vivo"));
        verify(productService, times(1)).retrieveProduct();
    }

    @Test
    public void testUpdateProductById() throws Exception {
        when(productService.updateProductById(any(Product.class), anyInt()))
                .thenReturn(product);
        mockMvc.perform(put("/api/v1/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.UPDATE))
                .andExpect(jsonPath("$.data.model").value("vivo y30s"));
        verify(productService, times(1)).updateProductById(any(Product.class), eq(1));
    }

    @Test
    public void testDeleteProductById() throws Exception {
        when(productService.removeProductById(1)).thenReturn("true");
        mockMvc.perform(delete("/api/v1/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.REMOVE))
                .andExpect(jsonPath("$.data").value("true"));
        verify(productService, times(1)).removeProductById(1);
    }

    @AfterAll
    public static void toEndProductController() {
        System.out.println("Product Controller Test case execution has been finished");
    }
}
