package com.showroommanagement.service;

import com.showroommanagement.dto.EmployeeDetailDTO;
import com.showroommanagement.entity.Employee;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.EmployeeRepository;
import com.showroommanagement.util.Constant;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Employee createEmployee(final Employee employee) {
        return this.employeeRepository.save(employee);
    }

    public Employee retrieveEmployeeById(final Integer id) {
        return this.employeeRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
    }

    public List<Employee> retrieveEmployee() {
        return this.employeeRepository.findAll();
    }

    @Transactional
    public Employee updateEmployeeById(final Employee employee, final Integer id) {
        final Employee existingEmployee = this.employeeRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));

        if (employee.getName() != null) {
            existingEmployee.setName(employee.getName());
        }
        if (employee.getSalary() != null) {
            existingEmployee.setSalary(employee.getSalary());
        }
        if (employee.getAddress() != null) {
            existingEmployee.setAddress(employee.getAddress());
        }
        if (employee.getDepartment() != null) {
            existingEmployee.setDepartment(employee.getDepartment());
        }
        if (employee.getBranch() != null) {
            existingEmployee.setBranch(employee.getBranch());
        }
        return this.employeeRepository.save(existingEmployee);
    }

    public String removeEmployeeById(final Integer id) {
        final Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        this.employeeRepository.delete(employee);
        return Constant.REMOVE;
    }

    public List<EmployeeDetailDTO> retrieveEmployeeDetail() {
        List<Employee> retrieveEmployee = this.employeeRepository.findAll();
        List<EmployeeDetailDTO> employeeDetailDTOS = new ArrayList<>();
        for (Employee employee : retrieveEmployee) {
            EmployeeDetailDTO employeeDetailDTO = new EmployeeDetailDTO();
            employeeDetailDTO.setName(employee.getName());
            employeeDetailDTO.setSalary(employee.getSalary());
            employeeDetailDTO.setDepartmentName(employee.getDepartment().getName());
            employeeDetailDTO.setShowroomName(employee.getDepartment().getShowroom().getName());
            employeeDetailDTO.setBranchName(employee.getBranch().getBranch());
            employeeDetailDTOS.add(employeeDetailDTO);
        }
        return employeeDetailDTOS;
    }

    public List<String> retrieveCountOfEmployee() {
        return this.employeeRepository.retrieveCountOfEmployee();
    }
}
