package com.showroommanagement.service;

import com.showroommanagement.dto.PaginationDTO;
import com.showroommanagement.dto.SaleDetailDTO;
import com.showroommanagement.entity.*;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.SaleDetailRepository;
import com.showroommanagement.util.Constant;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleDetailServiceTest {
    @Mock
    private SaleDetailRepository saleDetailRepository;

    @Mock
    private Product product;

    @Mock
    private Customer customer;

    @InjectMocks
    private SaleDetailService saleDetailService;
    @Mock
    private SaleDetail saleDetail;

    @BeforeAll
    public static void toStartSaleDetailService() {
        System.out.println("SaleDetail Service Test case execution has been Started");
    }

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1);
        product.setModel("vivo y30s");
        product.setPrice(65000.0);
        product.setColour("velvet red");
        product.setStock(2);

        customer = new Customer();
        customer.setId(1);
        customer.setName("viha");
        customer.setAddress("25, Sakthi Street,Devi Nagar, Chennai - 600 092.");

        saleDetail = new SaleDetail();
        saleDetail.setId(11);
        saleDetail.setSalesDate(new Date());
        saleDetail.setCustomer(customer);
        saleDetail.setProduct(product);
    }

    @Test
    void testCreateSale() {
        when(saleDetailRepository.save(any(SaleDetail.class))).thenReturn(saleDetail);

        SaleDetail savedSaleDetail = saleDetailService.createSales(saleDetail);

        assertNotNull(savedSaleDetail);
        assertEquals(saleDetail.getId(), savedSaleDetail.getId());
        assertEquals(saleDetail.getCustomer().getName(), savedSaleDetail.getCustomer().getName());
        assertEquals(saleDetail.getCustomer().getAddress(), savedSaleDetail.getCustomer().getAddress());
        assertEquals(saleDetail.getProduct().getModel(), savedSaleDetail.getProduct().getModel());
        assertEquals(saleDetail.getProduct().getPrice(), savedSaleDetail.getProduct().getPrice());
        assertEquals(saleDetail.getProduct().getColour(), savedSaleDetail.getProduct().getColour());

        verify(saleDetailRepository, times(1)).save(any(SaleDetail.class));
    }

    @Test
    void testRetrieveSaleById() {
        when(saleDetailRepository.findById(1)).thenReturn(Optional.of(saleDetail));

        SaleDetail foundSale = saleDetailService.retrieveSalesById(1);

        assertNotNull(foundSale);
        assertEquals(saleDetail.getId(), foundSale.getId());
        assertEquals(saleDetail.getCustomer().getName(), foundSale.getCustomer().getName());
        assertEquals(saleDetail.getCustomer().getAddress(), foundSale.getCustomer().getAddress());
        assertEquals(saleDetail.getProduct().getModel(), foundSale.getProduct().getModel());
        assertEquals(saleDetail.getProduct().getPrice(), foundSale.getProduct().getPrice());
        assertEquals(saleDetail.getProduct().getColour(), foundSale.getProduct().getColour());


        verify(saleDetailRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveSaleByIdNotFound() {
        when(saleDetailRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> saleDetailService.retrieveSalesById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(saleDetailRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveAllSale() {
        when(saleDetailRepository.findAll()).thenReturn(List.of(saleDetail));

        List<SaleDetail> saleDetails = saleDetailService.retrieveSales();

        assertNotNull(saleDetails);
        assertEquals(1, saleDetails.size());
        assertEquals(saleDetail.getId(), saleDetails.get(0).getId());
        verify(saleDetailRepository, times(1)).findAll();
    }

    @Test
    void testUpdateSaleById() {
        SaleDetail updatedSale = new SaleDetail();
        updatedSale.setId(1);

        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("viha updated");
        updatedSale.setCustomer(updatedCustomer);

        Product updatedProduct = new Product();
        updatedProduct.setModel("vivo y30s updated");
        updatedSale.setProduct(updatedProduct);

        when(saleDetailRepository.findById(1)).thenReturn(Optional.of(saleDetail));
        when(saleDetailRepository.save(any(SaleDetail.class))).thenReturn(updatedSale);

        SaleDetail result = saleDetailService.updateSaleById(updatedSale, 1);

        assertNotNull(result);
        assertEquals("viha updated", result.getCustomer().getName());
        assertEquals("vivo y30s updated", result.getProduct().getModel());
        verify(saleDetailRepository, times(1)).findById(1);
        verify(saleDetailRepository, times(1)).save(any(SaleDetail.class));
    }


    @Test
    void testUpdateSaleByIdNotFound() {
        SaleDetail updatedSale = new SaleDetail();
        updatedSale.setId(1);

        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("viha updated");
        updatedSale.setCustomer(updatedCustomer);

        Product updatedProduct = new Product();
        updatedProduct.setModel("vivo y30s updated");
        updatedSale.setProduct(updatedProduct);

        when(saleDetailRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () ->
                saleDetailService.updateSaleById(updatedSale, 1)
        );

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(saleDetailRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteSaleById() {
        when(saleDetailRepository.findById(1)).thenReturn(Optional.of(saleDetail));

        String result = saleDetailService.removeSaleById(1);

        assertEquals(Constant.REMOVE, result);
        verify(saleDetailRepository, times(1)).findById(1);
        verify(saleDetailRepository, times(1)).delete(any(SaleDetail.class));
    }

    @Test
    void testDeleteSaleByIdNotFound() {
        when(saleDetailRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> saleDetailService.removeSaleById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(saleDetailRepository, times(1)).findById(1);
    }

    @Test
    void searchProductsWithPaginationDTO() {

        String keyword = "vivo y30s";
        Pageable pageable = PageRequest.of(0, 10);

        saleDetail = new SaleDetail();
        Product product = new Product();
        Employee employee = new Employee();
        Branch branch = new Branch();
        Showroom showroom = new Showroom();
        Department department = new Department();
        Brand brand = new Brand();
        Customer customer = new Customer();

        showroom.setName("Poorvika");
        branch.setShowroom(showroom);
        branch.setBranch("chennai");
        department.setName("Sales");
        employee.setName("karthika");
        employee.setBranch(branch);
        employee.setDepartment(department);
        product.setModel("vivo y30s");
        product.setEmployee(employee);
        product.setBrand(brand);
        brand.setBrand("vivo");
        customer.setName("viha");
        customer.setAddress("25, Sakthi Street,Devi Nagar, Chennai - 600 092.");
        saleDetail.setProduct(product);
        saleDetail.setCustomer(customer);

        List<SaleDetail> saleDetailList = List.of(saleDetail);
        Page<SaleDetail> page = new PageImpl<>(saleDetailList, pageable, saleDetailList.size());

        when(saleDetailRepository.searchProducts(keyword, pageable)).thenReturn(page);

        PaginationDTO result = saleDetailService.searchProducts(keyword, pageable);

        @SuppressWarnings("unchecked")
        List<SaleDetailDTO> dtos = (List<SaleDetailDTO>) result.getDetails();

        assertEquals(1, result.getTotalPage());
        assertEquals(1L, result.getTotalElement());
        assertEquals(10, result.getPageSize());

        SaleDetailDTO dto = dtos.get(0);
        assertEquals("Poorvika", dto.getShowroomName());
        assertEquals("vivo", dto.getBrandName());
        assertEquals("vivo y30s", dto.getProductModel());
        assertEquals("karthika", dto.getEmployeeName());
        assertEquals("Sales", dto.getDepartmentName());
        assertEquals("viha", dto.getCustomerName());
        assertEquals("25, Sakthi Street,Devi Nagar, Chennai - 600 092.", dto.getCustomerAddress());
        assertEquals("chennai", dto.getBranchName());
}

    @AfterAll
    public static void toEndSaleDetailService() {
        System.out.println("SaleDetail Service Test case has been execution finished");
    }
}