package com.showroommanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showroommanagement.dto.EmployeeDetailDTO;
import com.showroommanagement.entity.Branch;
import com.showroommanagement.entity.Department;
import com.showroommanagement.entity.Employee;
import com.showroommanagement.entity.Showroom;
import com.showroommanagement.service.EmployeeService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Employee employee;

    @BeforeAll
    public static void toStartEmployeeController() {
        System.out.println("Employee Controller Test case execution has been started");
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        Showroom showroom = new Showroom();
        showroom.setId(1);
        showroom.setName("Poorvika");

        Department department = new Department();
        department.setId(3);
        department.setName("sales");
        department.setShowroom(showroom);

        Branch branch = new Branch();
        branch.setId(1);
        branch.setBranch("chennai");
        branch.setShowroom(showroom);

        employee = new Employee();
        employee.setId(1);
        employee.setName("karthika");
        employee.setSalary(40000.0);
        employee.setAddress("1st street,seruchery,chennai");
        employee.setDepartment(department);
        employee.setBranch(branch);
    }

    @Test
    public void testCreateEmployee() throws Exception {
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);
        mockMvc.perform(post("/api/v1/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.CREATE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("karthika"))
                .andExpect(jsonPath("$.data.salary").value(40000.0))
                .andExpect(jsonPath("$.data.branch.branch").value("chennai"))
                .andExpect(jsonPath("$.data.department.name").value("sales"))
                .andExpect(jsonPath("$.data.department.showroom.name").value("Poorvika"));
        verify(employeeService, times(1)).createEmployee(any(Employee.class));
    }

    @Test
    public void testRetrieveEmployeeById() throws Exception {
        when(employeeService.retrieveEmployeeById(1)).thenReturn(employee);
        mockMvc.perform(get("/api/v1/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("karthika"))
                .andExpect(jsonPath("$.data.salary").value(40000.0))
                .andExpect(jsonPath("$.data.branch.branch").value("chennai"))
                .andExpect(jsonPath("$.data.department.name").value("sales"))
                .andExpect(jsonPath("$.data.department.showroom.name").value("Poorvika"));
        verify(employeeService, times(1)).retrieveEmployeeById(employee.getId());
    }

    @Test
    public void testRetrieveAllEmployee() throws Exception {
        when(employeeService.retrieveEmployee()).thenReturn(List.of(employee));
        mockMvc.perform(get("/api/v1/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("karthika"))
                .andExpect(jsonPath("$.data[0].salary").value(40000.0))
                .andExpect(jsonPath("$.data[0].branch.branch").value("chennai"))
                .andExpect(jsonPath("$.data[0].department.name").value("sales"))
                .andExpect(jsonPath("$.data[0].department.showroom.name").value("Poorvika"));
        verify(employeeService, times(1)).retrieveEmployee();
    }

    @Test
    public void testUpdateEmployeeById() throws Exception {
        when(employeeService.updateEmployeeById(any(Employee.class), anyInt()))
                .thenReturn(employee);
        mockMvc.perform(put("/api/v1/employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.UPDATE))
                .andExpect(jsonPath("$.data.name").value("karthika"))
                .andExpect(jsonPath("$.data.salary").value(40000.0))
                .andExpect(jsonPath("$.data.branch.branch").value("chennai"))
                .andExpect(jsonPath("$.data.department.name").value("sales"))
                .andExpect(jsonPath("$.data.department.showroom.name").value("Poorvika"));
        verify(employeeService, times(1)).updateEmployeeById(any(Employee.class), eq(1));
    }

    @Test
    public void testDeleteEmployeeById() throws Exception {
        when(employeeService.removeEmployeeById(1)).thenReturn("true");
        mockMvc.perform(delete("/api/v1/employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.REMOVE))
                .andExpect(jsonPath("$.data").value("true"));
        verify(employeeService, times(1)).removeEmployeeById(1);
    }

    @Test
    void testRetrieveEmployeeDetail() throws Exception {
        EmployeeDetailDTO employeeDetailDTO = new EmployeeDetailDTO();
        employeeDetailDTO.setName("karthika");
        employeeDetailDTO.setSalary(40000.0);
        employeeDetailDTO.setBranchName("chennai");
        employeeDetailDTO.setDepartmentName("sales");
        employeeDetailDTO.setShowroomName("Poorvika");
        when(employeeService.retrieveEmployeeDetail()).thenReturn(List.of(employeeDetailDTO));
        mockMvc.perform(get("/api/v1/employee/detail")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data[0].name").value("karthika"))
                .andExpect(jsonPath("$.data[0].salary").value(40000.0))
                .andExpect(jsonPath("$.data[0].branchName").value("chennai"))
                .andExpect(jsonPath("$.data[0].departmentName").value("sales"))
                .andExpect(jsonPath("$.data[0].showroomName").value("Poorvika"));
    }

    @Test
    void testRetrieveCountOfEmployee() throws Exception {
        List<String> mockNames = List.of("karthika", "sharvin", "malar");
        when(employeeService.retrieveCountOfEmployee()).thenReturn(mockNames);
        mockMvc.perform(get("/api/v1/employee/count")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0]").value("karthika"))
                .andExpect(jsonPath("$.data[1]").value("sharvin"))
                .andExpect(jsonPath("$.data[2]").value("malar"));
    }

    @AfterAll
    public static void endEmployeeControllerTest() {
        System.out.println("Employee Controller Test execution finished");
    }
}
