package com.showroommanagement.service;

import com.showroommanagement.entity.Department;
import com.showroommanagement.entity.Showroom;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.DepartmentRepository;
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
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private Showroom showroom;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;

    @BeforeAll
    public static void toStartDepartmentService() {
        System.out.println("Department Service Test case execution has been Started");
    }

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1);
        department.setName("sales");
        department.setShowroom(showroom);
    }

    @Test
    void testCreateDepartment() {
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Department savedDepartment = departmentService.createDepartment(department);

        assertNotNull(savedDepartment);
        assertEquals(department.getId(), savedDepartment.getId());
        assertEquals(department.getName(), savedDepartment.getName());
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void testRetrieveDepartmentById() {
        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));

        Department foundDepartment = departmentService.retrieveDepartmentById(1);

        assertNotNull(foundDepartment);
        assertEquals(department.getId(), foundDepartment.getId());
        assertEquals(department.getName(), foundDepartment.getName());
        verify(departmentRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveDepartmentByIdNotFound() {
        when(departmentRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> departmentService.retrieveDepartmentById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(departmentRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveAllDepartment() {
        when(departmentRepository.findAll()).thenReturn(List.of(department));

        List<Department> departments = departmentService.retrieveDepartment();

        assertNotNull(departments);
        assertEquals(1, departments.size());
        assertEquals(department.getId(), departments.get(0).getId());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void testUpdateDepartmentById() {
        Department updatedDepartment = new Department();
        updatedDepartment.setId(1);
        updatedDepartment.setName("salesUpdated");

        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(updatedDepartment);

        Department result = departmentService.updateDepartmentById(updatedDepartment, 1);

        assertNotNull(result);
        assertEquals("salesUpdated", result.getName());
        verify(departmentRepository, times(1)).findById(1);
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void testUpdateDepartmentByIdNotFound() {
        Department updatedDepartment = new Department();
        updatedDepartment.setId(1);
        updatedDepartment.setName("salesUpdated");

        when(departmentRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> departmentService.updateDepartmentById(updatedDepartment, 1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(departmentRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteDepartmentById() {
        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));

        String result = departmentService.removeDepartmentById(1);

        assertEquals(Constant.REMOVE, result);
        verify(departmentRepository, times(1)).findById(1);
        verify(departmentRepository, times(1)).delete(any(Department.class));
    }

    @Test
    void testDeleteDepartmentByIdNotFound() {
        when(departmentRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> departmentService.removeDepartmentById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(departmentRepository, times(1)).findById(1);
    }

    @AfterAll
    public static void toEndDepartmentService() {
        System.out.println("Department Service Test case has been execution finished");
    }
}
