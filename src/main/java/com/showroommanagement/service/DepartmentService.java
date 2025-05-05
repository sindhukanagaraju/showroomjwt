package com.showroommanagement.service;

import com.showroommanagement.entity.Department;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.DepartmentRepository;
import com.showroommanagement.util.Constant;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(final DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public Department createDepartment(final Department Department) {
        return this.departmentRepository.save(Department);
    }

    public Department retrieveDepartmentById(final Integer id) {
        return this.departmentRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
    }

    public List<Department> retrieveDepartment() {
        return this.departmentRepository.findAll();
    }

    @Transactional
    public Department updateDepartmentById(final Department department, final Integer id) {
        final Department existingDepartment = this.departmentRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        if (department.getName() != null) {
            existingDepartment.setName(department.getName());
        }
        if (department.getShowroom() != null) {
            existingDepartment.setShowroom(department.getShowroom());
        }
        return this.departmentRepository.save(existingDepartment);
    }

    public String removeDepartmentById(final Integer id) {
        final Department department = this.departmentRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        this.departmentRepository.delete(department);
        return Constant.REMOVE;
    }
}

