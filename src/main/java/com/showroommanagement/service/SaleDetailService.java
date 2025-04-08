package com.showroommanagement.service;

import com.showroommanagement.dto.PaginationDTO;
import com.showroommanagement.dto.SaleDetailDTO;
import com.showroommanagement.entity.SaleDetail;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.SaleDetailRepository;
import com.showroommanagement.util.Constant;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleDetailService {
    private final SaleDetailRepository saleDetailRepository;

    public SaleDetailService(final SaleDetailRepository saleDetailRepository) {
        this.saleDetailRepository = saleDetailRepository;
    }

    @Transactional
    public SaleDetail createSales(final SaleDetail salesDetails) {
        return this.saleDetailRepository.save(salesDetails);
    }

    public SaleDetail retrieveSalesById(final Integer id) {
        return this.saleDetailRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
    }

    public List<SaleDetail> retrieveSales() {
        return this.saleDetailRepository.findAll();
    }

    @Transactional
    public SaleDetail updateSalesById(final SaleDetail salesDetails, final Integer id) {
        final SaleDetail existingSalesDetails = this.saleDetailRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        if (salesDetails.getId() != null) {
            existingSalesDetails.setId(salesDetails.getId());
        }
        if (salesDetails.getSalesDate() != null) {
            existingSalesDetails.setSalesDate(salesDetails.getSalesDate());
        }
        if (salesDetails.getProduct() != null) {
            existingSalesDetails.setProduct(salesDetails.getProduct());
        }
        if (salesDetails.getCustomer() != null) {
            existingSalesDetails.setCustomer(salesDetails.getCustomer());
        }
        return this.saleDetailRepository.save(existingSalesDetails);
    }

    public String removeSaleDetailById(final Integer id) {
        final SaleDetail saleDetail = this.saleDetailRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        this.saleDetailRepository.delete(saleDetail);
        return Constant.REMOVE;
    }

    public PaginationDTO searchProducts(final String keyword, final Pageable pageable) {
        final Page<SaleDetail> salesPage = this.saleDetailRepository.searchProducts(keyword, pageable);
        if (salesPage.isEmpty()) {
            throw new BadRequestServiceAlertException("No sales found for keyword: " + keyword);
        }
        final List<SaleDetailDTO> saleDetailResponse = salesPage.map(salesDetails -> {
            final SaleDetailDTO saleDetailDTO = new SaleDetailDTO();
            saleDetailDTO.setShowroomName(salesDetails.getProduct().getEmployee().getBranch().getShowroom().getName());
            saleDetailDTO.setBrandName(salesDetails.getProduct().getBrand().getBrand());
            saleDetailDTO.setProductModel(salesDetails.getProduct().getModel());
            saleDetailDTO.setProductPrice(salesDetails.getProduct().getPrice());
            saleDetailDTO.setEmployeeName(salesDetails.getProduct().getEmployee().getName());
            saleDetailDTO.setSalesDate(salesDetails.getSalesDate());
            saleDetailDTO.setDepartmentName(salesDetails.getProduct().getEmployee().getDepartment().getName());
            saleDetailDTO.setCustomerName(salesDetails.getCustomer().getName());
            saleDetailDTO.setCustomerAddress(salesDetails.getCustomer().getAddress());
            saleDetailDTO.setBranchName(salesDetails.getProduct().getEmployee().getBranch().getBranch());
            return saleDetailDTO;
        }).getContent();

        return new PaginationDTO(
                salesPage.getTotalPages(),
                salesPage.getTotalElements(),
                salesPage.getSize(),
                saleDetailResponse
        );
    }

}

