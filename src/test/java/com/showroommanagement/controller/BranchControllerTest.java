package com.showroommanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showroommanagement.entity.Branch;
import com.showroommanagement.service.BranchService;
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
public class BranchControllerTest {

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchController branchController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Branch branch;

    @BeforeAll
    public static void toStartBranchController() {
        System.out.println("Branch Controller Test case execution has been started");
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(branchController).build();
        branch = new Branch();
        branch.setId(1);
        branch.setBranch("chennai");
    }

    @Test
    public void testCreateBranch() throws Exception {
        when(branchService.createBranch(any(Branch.class))).thenReturn(branch);
        mockMvc.perform(post("/api/v1/branch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constant.CREATE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.branch").value("chennai"));
        verify(branchService, times(1)).createBranch(any(Branch.class));
    }

    @Test
    public void testRetrieveById() throws Exception {
        when(branchService.retrieveBranchById(1)).thenReturn(branch);
        mockMvc.perform(get("/api/v1/branch/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.branch").value("chennai"));
        verify(branchService, times(1)).retrieveBranchById(branch.getId());
    }

    @Test
    public void testRetrieveAll() throws Exception {
        when(branchService.retrieveBranch()).thenReturn(List.of(branch));
        mockMvc.perform(get("/api/v1/branch"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].branch").value("chennai"));
        verify(branchService, times(1)).retrieveBranch();
    }

    @Test
    public void testUpdateById() throws Exception {
        when(branchService.updateBranchById(any(Branch.class), anyInt()))
                .thenReturn(branch);

        mockMvc.perform(put("/api/v1/branch/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(branch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.UPDATE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.branch").value("chennai"));

        verify(branchService, times(1)).updateBranchById(any(Branch.class), eq(1));
    }


    @Test
    public void testDeleteById() throws Exception {
        when(branchService.removeBranchById(1)).thenReturn(String.valueOf(true));

        mockMvc.perform(delete("/api/v1/branch/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.REMOVE))
                .andExpect(jsonPath("$.data").value("true"));

        verify(branchService, times(1)).removeBranchById(1);
    }

    @AfterAll
    public static void toEndBranchController() {
        System.out.println("Branch Controller Test case execution has been finished");
    }

}