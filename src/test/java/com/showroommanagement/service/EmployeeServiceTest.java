package com.showroommanagement.service;

import com.showroommanagement.dto.EmployeeDetailDTO;
import com.showroommanagement.entity.Branch;
import com.showroommanagement.entity.Department;
import com.showroommanagement.entity.Employee;
import com.showroommanagement.entity.Showroom;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.EmployeeRepository;
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
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Department department;
    @Mock
    private Branch branch;
    @Mock
    private Showroom showroom;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeAll
    public static void toStartEmployeeService() {
        System.out.println("Employee Service Test case execution has been Started");
    }

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1);
        employee.setName("karthika");
        employee.setSalary(40000.0);
        employee.setAddress("1st street,seruchery,chennai");
        employee.setDepartment(department);
        employee.setBranch(branch);
    }

    @Test
    void testCreateEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee savedEmployee = employeeService.createEmployee(employee);

        assertNotNull(savedEmployee);
        assertEquals(employee.getId(), savedEmployee.getId());
        assertEquals(employee.getName(), savedEmployee.getName());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testRetrieveEmployeeById() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.retrieveEmployeeById(1);

        assertNotNull(foundEmployee);
        assertEquals(employee.getId(), foundEmployee.getId());
        assertEquals(employee.getName(), foundEmployee.getName());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveEmployeeByIdNotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> employeeService.retrieveEmployeeById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveAllEmployee() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<Employee> employees = employeeService.retrieveEmployee();

        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals(employee.getId(), employees.get(0).getId());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testUpdateEmployeeById() {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setName("karthikaUpdated");
        updatedEmployee.setSalary(41000.0);
        updatedEmployee.setAddress("2nd street,seruchery,chennai");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        Employee result = employeeService.updateEmployeeById(updatedEmployee, 1);

        assertNotNull(result);
        assertEquals("karthikaUpdated", result.getName());
        assertEquals(41000.0, result.getSalary());
        assertEquals("2nd street,seruchery,chennai", result.getAddress());
        verify(employeeRepository, times(1)).findById(1);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }
    @Test
    void testUpdateEmployeeByIdNotFound() {
        Employee updatedEmployee = new  Employee();
        updatedEmployee.setName("salesUpdated");
        updatedEmployee.setSalary(41000.0);
        updatedEmployee.setAddress("2nd street,seruchery,chennai");

        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> employeeService.updateEmployeeById(updatedEmployee, 1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteEmployeeById() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        String result = employeeService.removeEmployeeById(1);

        assertEquals(Constant.REMOVE, result);
        verify(employeeRepository, times(1)).findById(1);
        verify(employeeRepository, times(1)).delete(any( Employee.class));
    }

    @Test
    void testDeleteEmployeeByIdNotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> employeeService.removeEmployeeById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveEmployeeDetail() {
        showroom = new Showroom();
        showroom.setName("Poorvika");

        Department department = new Department();
        department.setName("Sales");
        department.setShowroom(showroom);

        Branch branch = new Branch();
        branch.setBranch("Chennai");

        Employee employee = new Employee();
        employee.setName("Karthika");
        employee.setSalary(50000.0);
        employee.setDepartment(department);
        employee.setBranch(branch);

        List<Employee> employeeList = List.of(employee);
        when(employeeRepository.findAll()).thenReturn(employeeList);

        List<EmployeeDetailDTO> result = employeeService.retrieveEmployeeDetail();

        assertNotNull(result);
        assertEquals(1, result.size());

        EmployeeDetailDTO dto = result.get(0);
        assertEquals("Karthika", dto.getName());
        assertEquals(50000.0, dto.getSalary());
        assertEquals("Sales", dto.getDepartmentName());
        assertEquals("Poorvika", dto.getShowroomName());
        assertEquals("Chennai", dto.getBranchName());

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveCountOfEmployee() {
        List<String> mockNames = List.of("karthika", "sharvin", "malar");
        when(employeeRepository.retrieveCountOfEmployee()).thenReturn(mockNames);

        List<String> result = employeeService.retrieveCountOfEmployee();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("karthika", result.get(0));
        assertEquals("sharvin", result.get(1));
        assertEquals("malar", result.get(2));

        verify(employeeRepository, times(1)).retrieveCountOfEmployee();
    }

    @AfterAll
    public static void toEndEmployeeService() {
        System.out.println("Employee Service Test case has been execution finished");
    }
}
