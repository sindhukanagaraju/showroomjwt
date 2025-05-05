package com.showroommanagement.service;

import com.showroommanagement.entity.Brand;
import com.showroommanagement.entity.Showroom;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.BrandRepository;
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
public class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    private Brand brand;

    @BeforeAll
    public static void toStartBrandService() {
        System.out.println("Brand Service Test case execution has been Started");
    }

    @BeforeEach
    void setUp() {
        Showroom  showroom = new Showroom();
        showroom.setName("Poorvika");
        showroom.setAddress("1/2 Sipcot Information Technology Park, Near Siruseri Special Economic Zone Navallur Post, Sirucheri, chennai.");
        showroom.setContactNumber("9056734576");

        brand = new Brand();
        brand.setId(1);
        brand.setBrand("vivo");
        brand.setShowroom(showroom);
    }

    @Test
    void testCreateBrand() {
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        Brand savedBrand = brandService.createBrand(brand);

        assertNotNull(savedBrand);
        assertEquals(brand.getId(), savedBrand.getId());
        assertEquals(brand.getBrand(), savedBrand.getBrand());
        verify(brandRepository, times(1)).save(any(Brand.class));
    }

    @Test
    void testRetrieveBrandById() {
        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));

        Brand foundBrand = brandService.retrieveBrandById(1);

        assertNotNull(foundBrand);
        assertEquals(brand.getId(), foundBrand.getId());
        assertEquals(brand.getBrand(), foundBrand.getBrand());
        verify(brandRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveBrandByIdNotFound() {
        when(brandRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> brandService.retrieveBrandById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(brandRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveAllBrands() {
        when(brandRepository.findAll()).thenReturn(List.of(brand));

        List<Brand> brands = brandService.retrieveBrand();

        assertNotNull(brands);
        assertEquals(1, brands.size());
        assertEquals(brand.getId(), brands.get(0).getId());
        verify(brandRepository, times(1)).findAll();
    }

    @Test
    void testUpdateBrandById() {
        Showroom  updatedShowroom = new Showroom();
        updatedShowroom.setName("Poorvika Updated");
        updatedShowroom.setAddress("1/2 Sipcot Information Technology Park, Near Siruseri Special Economic Zone Navallur Post, Sirucheri, chennai.");
        updatedShowroom.setContactNumber("9056734576");

        Brand updatedBrand = new Brand();
        updatedBrand.setBrand("updatedVivo");
        updatedBrand.setShowroom(updatedShowroom);

        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));
        when(brandRepository.save(any(Brand.class))).thenReturn(updatedBrand);

        Brand result = brandService.updateBrandById(updatedBrand, 1);

        assertNotNull(result);
        assertEquals("updatedVivo", result.getBrand());
        assertEquals("Poorvika Updated", result.getShowroom().getName());
        verify(brandRepository, times(1)).findById(1);
        verify(brandRepository, times(1)).save(any(Brand.class));
    }

    @Test
    void testUpdateBrandByIdNotFound() {
        Showroom  updatedShowroom = new Showroom();
        updatedShowroom.setName("Poorvika Updated");
        updatedShowroom.setAddress("1/2 Sipcot Information Technology Park, Near Siruseri Special Economic Zone Navallur Post, Sirucheri, chennai.");
        updatedShowroom.setContactNumber("9056734576");

        Brand updatedBrand = new Brand();
        updatedBrand.setBrand("updatedVivo");
        updatedBrand.setShowroom(updatedShowroom);

        when(brandRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> brandService.updateBrandById(updatedBrand, 1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(brandRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteBrandById() {
        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));

        String result = brandService.removeBrandById(1);

        assertEquals(Constant.REMOVE, result);
        verify(brandRepository, times(1)).findById(1);
        verify(brandRepository, times(1)).delete(any(Brand.class));
    }

    @Test
    void testDeleteBrandByIdNotFound() {
        when(brandRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> brandService.removeBrandById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(brandRepository, times(1)).findById(1);
    }

    @AfterAll
    public static void toEndBrandService() {
        System.out.println("Brand Service Test case has been execution finished");
    }
}
