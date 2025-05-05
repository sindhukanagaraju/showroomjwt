package com.showroommanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showroommanagement.entity.Brand;
import com.showroommanagement.service.BrandService;
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
public class BrandControllerTest {

    @Mock
    private BrandService brandService;

    @InjectMocks
    private BrandController brandController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Brand brand;

    @BeforeAll
    public static void toStartBrandController() {
        System.out.println("Brand Controller Test case execution has been started");
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();
        brand = new Brand();
        brand.setId(1);
        brand.setBrand("vivo");
    }

    @Test
    public void testCreateBrand() throws Exception {
        when(brandService.createBrand(any(Brand.class))).thenReturn(brand);
        mockMvc.perform(post("/api/v1/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brand)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constant.CREATE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.brand").value("vivo"));
        verify(brandService, times(1)).createBrand(any(Brand.class));
    }

    @Test
    public void testRetrieveById() throws Exception {
        when(brandService.retrieveBrandById(1)).thenReturn(brand);
        mockMvc.perform(get("/api/v1/brand/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.brand").value("vivo"));
        verify(brandService, times(1)).retrieveBrandById(brand.getId());
    }

    @Test
    public void testRetrieveAll() throws Exception {
        when(brandService.retrieveBrand()).thenReturn(List.of(brand));
        mockMvc.perform(get("/api/v1/brand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].brand").value("vivo"));
        verify(brandService, times(1)).retrieveBrand();
    }

    @Test
    public void testUpdateById() throws Exception {
        when(brandService.updateBrandById(any(Brand.class), anyInt()))
                .thenReturn(brand);
        mockMvc.perform(put("/api/v1/brand/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(brand)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.UPDATE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.brand").value("vivo"));
        verify(brandService, times(1)).updateBrandById(any(Brand.class), eq(1));
    }

    @Test
    public void testDeleteById() throws Exception {
        when(brandService.removeBrandById(1)).thenReturn(String.valueOf(true));
        mockMvc.perform(delete("/api/v1/brand/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brand)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.REMOVE))
                .andExpect(jsonPath("$.data").value("true"));
        verify(brandService, times(1)).removeBrandById(1);
    }

    @AfterAll
    public static void toEndBrandController() {
        System.out.println("Brand Controller Test case execution has been finished");
    }
}