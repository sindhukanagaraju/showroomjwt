package com.showroommanagement.controller;

import com.showroommanagement.dto.ResponseDTO;
import com.showroommanagement.entity.Branch;
import com.showroommanagement.service.BranchService;
import com.showroommanagement.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class BranchController {

    private final BranchService branchService;

    public BranchController(final BranchService branchService) {
        this.branchService = branchService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/branch")
    public ResponseDTO createBranch(@RequestBody final Branch branch) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.CREATE, this.branchService.createBranch(branch));
    }

    @GetMapping("/branch/{id}")
    public ResponseDTO retrieveBranchById(@PathVariable final Integer id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.branchService.retrieveBranchById(id));
    }

    @GetMapping("/branch")
    public ResponseDTO retrieveBranch() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.branchService.retrieveBranch());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/branch/{id}")
    public ResponseDTO updateBranch(@PathVariable final Integer id, @RequestBody final Branch branch) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.branchService.updateBranchById(branch, id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/branch/{id}")
    public ResponseDTO removeBranchById(@PathVariable final Integer id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.REMOVE, this.branchService.removeBranchById(id));
    }
}
