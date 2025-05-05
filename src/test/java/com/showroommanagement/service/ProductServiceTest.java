package com.showroommanagement.service;

import com.showroommanagement.entity.Brand;
import com.showroommanagement.entity.Employee;
import com.showroommanagement.entity.Product;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.ProductRepository;
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
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private Employee employee;
    @Mock
    private Brand brand;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeAll
    public static void toStartProductService() {
        System.out.println("Product Service Test case execution has been Started");
    }

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1);
        product.setModel("vivo y30s");
        product.setPrice(65000.0);
        product.setColour("velvet red");
        product.setStock(2);
        product.setEmployee(employee);
        product.setBrand(brand);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.createProduct(product);

        assertNotNull(savedProduct);
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getModel(), savedProduct.getModel());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(product.getColour(), savedProduct.getColour());
        assertEquals(product.getStock(), savedProduct.getStock());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testRetrieveProductById() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product foundProduct = productService.retrieveProductById(1);

        assertNotNull(foundProduct);
        assertEquals(product.getId(), foundProduct.getId());
        assertEquals(product.getModel(), foundProduct.getModel());
        assertEquals(product.getPrice(), foundProduct.getPrice());
        assertEquals(product.getColour(), foundProduct.getColour());
        assertEquals(product.getStock(), foundProduct.getStock());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveProductByIdNotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> productService.retrieveProductById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveAllProduct() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> products = productService.retrieveProduct();

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(product.getId(), products.get(0).getId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testUpdateProductById() {
        Product updatedProduct = new Product();
        updatedProduct.setModel("vivo y30s updated");
        updatedProduct.setPrice(65000.0);
        updatedProduct.setColour("velvet red");
        updatedProduct.setStock(2);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProductById(updatedProduct, 1);

        assertNotNull(result);
        assertEquals("vivo y30s updated", result.getModel());
        assertEquals("velvet red", result.getColour());
        assertEquals(65000.0, result.getPrice());
        assertEquals(2, result.getStock());
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProductByIdNotFound() {
        Product updatedProduct = new Product();
        updatedProduct.setModel("vivo y30s updated");
        updatedProduct.setPrice(65000.0);
        updatedProduct.setColour("velvet red");
        updatedProduct.setStock(2);

        when(productRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> productService.updateProductById(updatedProduct, 1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteProductById() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        String result = productService.removeProductById(1);

        assertEquals(Constant.REMOVE, result);
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).delete(any(Product.class));
    }

    @Test
    void testDeleteProductByIdNotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> productService.removeProductById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(productRepository, times(1)).findById(1);
    }

    @AfterAll
    public static void toEndProductService() {
        System.out.println("Product Service Test case has been execution finished");
    }
}
