package com.mazzama.research.multisourcedb.web.controller;

import com.mazzama.research.multisourcedb.tenant.model.Product;
import com.mazzama.research.multisourcedb.tenant.repository.ProductRepository;
import com.mazzama.research.multisourcedb.web.mapper.ProductMapper;
import com.mazzama.research.multisourcedb.web.payload.ProductRequest;
import com.mazzama.research.multisourcedb.web.payload.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public List<ProductResponse> getAllProduct() {
        return productMapper.toDto(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable("id") int id) {
        Product productModel = productRepository.findById(id).get();
        LOG.info(productModel.toString());
        ProductResponse productResponse = productMapper.toDto(productModel);
        return productResponse;
    }

    @PostMapping
    public ProductResponse saveProduct(@RequestBody ProductRequest product) {
        Product returnProduct = productRepository.save(productMapper.requestToModel(product));
        LOG.info(returnProduct.toString());
        return productMapper.toDto(returnProduct);
    }

}