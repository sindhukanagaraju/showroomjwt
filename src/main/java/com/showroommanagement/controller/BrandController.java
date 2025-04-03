package com.showroommanagement.controller;

import com.showroommanagement.dto.ResponseDTO;
import com.showroommanagement.entity.Brand;
import com.showroommanagement.service.BrandService;
import com.showroommanagement.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class BrandController {
    private final BrandService brandService;

    public BrandController(final BrandService brandService) {
        this.brandService = brandService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/brand")
    public ResponseDTO createBrand(@RequestBody final Brand brand) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.CREATE, this.brandService.createBrand(brand));
    }

    @GetMapping("/brand/{id}")
    public ResponseDTO retrieveBrandById(@PathVariable final Integer id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.brandService.retrieveBrandById(id));
    }

    @GetMapping("/brand/retrieve")
    public ResponseDTO retrieveBrand() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.brandService.retrieveBrand());
    }

    @PreAuthorize("hasAnyAuthority(ADMIN')")
    @PutMapping("/brand/{id}")
    public ResponseDTO updateBranch(@PathVariable final Integer id, @RequestBody final Brand brand) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.brandService.updateBrandById(brand, id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/brand/{id}")
    public ResponseDTO removeBranchById(@PathVariable final Integer id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.REMOVE, this.brandService.removeBrandById(id));
    }
}
