package com.showroommanagement.controller;

import com.showroommanagement.dto.ResponseDTO;
import com.showroommanagement.entity.SaleDetail;
import com.showroommanagement.service.SaleDetailService;
import com.showroommanagement.util.Constant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SaleDetailController {

    private final SaleDetailService saleDetailService;

    public SaleDetailController(final SaleDetailService saleDetailService) {
        this.saleDetailService = saleDetailService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','CUSTOMER')")
    @PostMapping("/sale")
    public ResponseDTO createSales(@RequestBody final SaleDetail salesDetail) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.CREATE, this.saleDetailService.createSales(salesDetail));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','CUSTOMER')")
    @GetMapping("/sale/{id}")
    public ResponseDTO retrieveSalesById(@PathVariable final Integer id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.saleDetailService.retrieveSalesById(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','CUSTOMER')")
    @GetMapping("/sale")
    public ResponseDTO retrieveSales() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.saleDetailService.retrieveSales());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/sale/{id}")
    public ResponseDTO updateSaleById(@PathVariable final Integer id, @RequestBody final SaleDetail saleDetail) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.saleDetailService.updateSaleById(saleDetail, id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/sale/{id}")
    public ResponseDTO removeSaleById(@PathVariable final Integer id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.REMOVE, this.saleDetailService.removeSaleById(id));
    }

    @GetMapping("/sale/details")
    public ResponseDTO searchProducts(
            @RequestParam(defaultValue = "") String keyword,
            @PageableDefault(size = 2, sort = "salesDate", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.saleDetailService.searchProducts(keyword, pageable));
    }
}
