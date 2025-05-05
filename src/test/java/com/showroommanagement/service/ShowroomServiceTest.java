package com.showroommanagement.service;

import com.showroommanagement.entity.Showroom;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.ShowroomRepository;
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
public class ShowroomServiceTest {
    @Mock
    private ShowroomRepository showroomRepository;

    @InjectMocks
    private ShowroomService showroomService;

    private Showroom showroom;

    @BeforeAll
    public static void toStartShowroomService() {
        System.out.println("Showroom Service Test case execution has been Started");
    }

    @BeforeEach
    void setUp() {
        showroom = new Showroom();
        showroom.setId(1);
        showroom.setName("Poorvika");
        showroom.setAddress("1/2 Sipcot Information Technology Park, Near Siruseri Special Economic Zone Navallur Post, Sirucheri, chennai.");
        showroom.setContactNumber("9056734576");
    }

    @Test
    void testCreateShowroom() {
        when(showroomRepository.save(any(Showroom.class))).thenReturn(showroom);

        Showroom savedShowroom = showroomService.createShowroom(showroom);

        assertNotNull(savedShowroom);
        assertEquals(showroom.getId(), savedShowroom.getId());
        assertEquals(showroom.getName(), savedShowroom.getName());
        assertEquals(showroom.getAddress(), savedShowroom.getAddress());
        assertEquals(showroom.getContactNumber(), savedShowroom.getContactNumber());
        verify(showroomRepository, times(1)).save(any(Showroom.class));
    }

    @Test
    void testRetrieveShowroomById() {
        when(showroomRepository.findById(1)).thenReturn(Optional.of(showroom));

        Showroom foundShowroom = showroomService.retrieveShowroomById(1);

        assertNotNull(foundShowroom);
        assertEquals(showroom.getId(), foundShowroom.getId());
        assertEquals(showroom.getName(), foundShowroom.getName());
        assertEquals(showroom.getAddress(), foundShowroom.getAddress());
        assertEquals(showroom.getContactNumber(), foundShowroom.getContactNumber());

        verify(showroomRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveShowroomByIdNotFound() {
        when(showroomRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> showroomService.retrieveShowroomById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(showroomRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveAllShowroom() {
        when(showroomRepository.findAll()).thenReturn(List.of(showroom));

        List<Showroom> showrooms = showroomService.retrieveShowroom();

        assertNotNull(showrooms);
        assertEquals(1, showrooms.size());
        assertEquals(showroom.getId(), showrooms.get(0).getId());
        verify(showroomRepository, times(1)).findAll();
    }

    @Test
    void testUpdateShowroomById() {
        Showroom updatedShowroom = new Showroom();
        updatedShowroom.setId(1);
        updatedShowroom.setName("Poorvika Updated");

        when(showroomRepository.findById(1)).thenReturn(Optional.of(showroom));
        when(showroomRepository.save(any(Showroom.class))).thenReturn(updatedShowroom);

        Showroom result = showroomService.updateShowroomById(updatedShowroom, 1);

        assertNotNull(result);
        assertEquals("Poorvika Updated", result.getName());
        verify(showroomRepository, times(1)).findById(1);
        verify(showroomRepository, times(1)).save(any(Showroom.class));
    }

    @Test
    void testUpdateShowroomByIdNotFound() {
        Showroom updatedShowroom = new Showroom();
        updatedShowroom.setId(1);
        updatedShowroom.setName("Poorvika Updated");

        when(showroomRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> showroomService.updateShowroomById(updatedShowroom, 1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(showroomRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteShowroomById() {
        when(showroomRepository.findById(1)).thenReturn(Optional.of(showroom));

        String result = showroomService.removeShowroomById(1);

        assertEquals(Constant.REMOVE, result);
        verify(showroomRepository, times(1)).findById(1);
        verify(showroomRepository, times(1)).delete(any(Showroom.class));
    }

    @Test
    void testDeleteShowroomByIdNotFound() {
        when(showroomRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> showroomService.removeShowroomById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(showroomRepository, times(1)).findById(1);
    }

    @AfterAll
    public static void toEndShowroomService() {
        System.out.println("Showroom Service Test case has been execution finished");
    }
}
