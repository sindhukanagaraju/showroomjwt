package com.showroommanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showroommanagement.entity.Department;
import com.showroommanagement.entity.Showroom;
import com.showroommanagement.service.DepartmentService;
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
public class DepartmentControllerTest {
    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Department department;

    @BeforeAll
    public static void toStartDepartmentController() {
        System.out.println("Department Controller Test case execution has been started");
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
        Showroom showroom = new Showroom();
        showroom.setId(1);
        showroom.setName("Poorvika");

        department = new Department();
        department.setId(1);
        department.setName("sales");
        department.setShowroom(showroom);
    }

    @Test
    public void testCreateDepartment() throws Exception {
        when(departmentService.createDepartment(any(Department.class))).thenReturn(department);
        mockMvc.perform(post("/api/v1/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.CREATE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("sales"))
                .andExpect(jsonPath("$.data.showroom.name").value("Poorvika"));
        verify(departmentService, times(1)).createDepartment(any(Department.class));
    }

    @Test
    public void testRetrieveDepartmentById() throws Exception {
        when(departmentService.retrieveDepartmentById(1)).thenReturn(department);
        mockMvc.perform(get("/api/v1/department/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("sales"))
                .andExpect(jsonPath("$.data.showroom.name").value("Poorvika"));
        verify(departmentService, times(1)).retrieveDepartmentById(department.getId());
    }

    @Test
    public void testRetrieveAllDepartment() throws Exception {
        when(departmentService.retrieveDepartment()).thenReturn(List.of(department));
        mockMvc.perform(get("/api/v1/department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("sales"))
                .andExpect(jsonPath("$.data[0].showroom.name").value("Poorvika"));
        verify(departmentService, times(1)).retrieveDepartment();
    }

    @Test
    public void testUpdateDepartmentById() throws Exception {
        when(departmentService.updateDepartmentById(any(Department.class), anyInt()))
                .thenReturn(department);
        mockMvc.perform(put("/api/v1/department/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.UPDATE))
                .andExpect(jsonPath("$.data.name").value("sales"))
                .andExpect(jsonPath("$.data.showroom.name").value("Poorvika"));
        verify(departmentService, times(1)).updateDepartmentById(any(Department.class), eq(1));
    }

    @Test
    public void testDeleteDepartmentById() throws Exception {
        when(departmentService.removeDepartmentById(1)).thenReturn("true");
        mockMvc.perform(delete("/api/v1/department/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.REMOVE))
                .andExpect(jsonPath("$.data").value("true"));
        verify(departmentService, times(1)).removeDepartmentById(1);
    }

    @AfterAll
    public static void endDepartmentControllerTest() {
        System.out.println("Department Controller Test execution finished");
    }
}
