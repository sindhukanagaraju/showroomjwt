package com.showroommanagement.service;

import com.showroommanagement.entity.Product;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.ProductRepository;
import com.showroommanagement.util.Constant;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product createProduct(final Product product) {
        return this.productRepository.save(product);
    }

    public Product retrieveProductById(final Integer id) {
        return this.productRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
    }

    public List<Product> retrieveProduct() {
        return this.productRepository.findAll();
    }

    @Transactional
    public Product updateProductById(final Product product, final Integer id) {
        final Product existingProduct = this.productRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        if (product.getId() != null) {
            existingProduct.setId(product.getId());
        }
        if (product.getModel() != null) {
            existingProduct.setModel(product.getModel());
        }
        if (product.getEmployee() != null) {
            existingProduct.setEmployee(product.getEmployee());
        }
        if (product.getPrice() != null) {
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getColour() != null) {
            existingProduct.setColour(product.getColour());
        }
        if (product.getBrand() != null) {
            existingProduct.setBrand(product.getBrand());
        }
        if (product.getStock() != null) {
            existingProduct.setStock(product.getStock());
        }
        return this.productRepository.save(existingProduct);
    }

    public Product removeProductById(final Integer id) {
        final Product product = this.productRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        this.productRepository.deleteById(id);
        return product;
    }
}


