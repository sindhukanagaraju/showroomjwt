package com.showroommanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showroommanagement.entity.Showroom;
import com.showroommanagement.service.ShowroomService;
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
public class ShowroomControllerTest {

    @Mock
    private ShowroomService showroomService;

    @InjectMocks
    private ShowroomController showroomController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Showroom showroom;

    @BeforeAll
    public static void toStartShowroomController() {
        System.out.println("Showroom Controller Test case execution has been started");
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(showroomController).build();
        showroom = new Showroom();
        showroom.setId(1);
        showroom.setName("Poorvika");
        showroom.setAddress("1/2 Sipcot Information Technology Park, Near Siruseri Special Economic Zone Navallur Post, Sirucheri, chennai.");
        showroom.setContactNumber("9056734576");
    }

    @Test
    public void testCreateShowroom() throws Exception {
        when(showroomService.createShowroom(any(Showroom.class))).thenReturn(showroom);
        mockMvc.perform(post("/api/v1/showroom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(showroom)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constant.CREATE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Poorvika"));
        verify(showroomService, times(1)).createShowroom(any(Showroom.class));
    }

    @Test
    public void testRetrieveById() throws Exception {
        when(showroomService.retrieveShowroomById(1)).thenReturn(showroom);
        mockMvc.perform(get("/api/v1/showroom/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Poorvika"));
        verify(showroomService, times(1)).retrieveShowroomById(showroom.getId());
    }

    @Test
    public void testRetrieveAll() throws Exception {
        when(showroomService.retrieveShowroom()).thenReturn(List.of(showroom));
        mockMvc.perform(get("/api/v1/showroom"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Poorvika"));
        verify(showroomService, times(1)).retrieveShowroom();
    }

    @Test
    public void testPatchById() throws Exception {
        Showroom showroom = new Showroom();
        showroom.setId(1);
        showroom.setName("Poorvika");

        when(showroomService.patchById(any(Showroom.class), eq(1)))
                .thenReturn(showroom);

        mockMvc.perform(patch("/api/v1/showroom/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(showroom)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.UPDATE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Poorvika"));

        verify(showroomService, times(1)).patchById(any(Showroom.class), eq(1));
    }


    @Test
    public void testUpdateById() throws Exception {
        when(showroomService.updateShowroomById(any(Showroom.class), anyInt()))
                .thenReturn(showroom);

        mockMvc.perform(put("/api/v1/showroom/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(showroom)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.UPDATE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Poorvika"));

        verify(showroomService, times(1)).updateShowroomById(any(Showroom.class), eq(1));
    }


@Test
public void testDeleteById() throws Exception {
    when(showroomService.removeShowroomById(2)).thenReturn(String.valueOf(true));
    mockMvc.perform(delete("/api/v1/showroom/2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(showroom)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.message").value(Constant.REMOVE))
            .andExpect(jsonPath("$.data").value("true"));
    verify(showroomService, times(1)).removeShowroomById(2);
}

@AfterAll
public static void toEndShowroomController() {
    System.out.println("Showroom Controller Test case execution has been finished");
}

}