package com.showroommanagement.controller;

import com.showroommanagement.dto.ResponseDTO;
import com.showroommanagement.entity.Department;
import com.showroommanagement.service.DepartmentService;
import com.showroommanagement.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(final DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/department")
    public ResponseDTO createDepartment(@RequestBody final Department department) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.CREATE, this.departmentService.createDepartment(department));
    }

    @GetMapping("/department/{id}")
    public ResponseDTO retrieveDepartmentById(@PathVariable final Integer id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.departmentService.retrieveDepartmentById(id));
    }

    @GetMapping("/department/retrieve")
    public ResponseDTO retrieveDepartment() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.departmentService.retrieveDepartment());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/department/{id}")
    public ResponseDTO updateDepartmentById(@PathVariable final Integer id, @RequestBody final Department department) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.departmentService.updateDepartmentById(department, id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/department/{id}")
    public ResponseDTO removeDepartmentById(@PathVariable final Integer id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.REMOVE, this.departmentService.removeDepartmentById(id));
    }
}
