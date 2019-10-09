package com.mazzama.research.multisourcedb.controller;

import com.mazzama.research.multisourcedb.domain.Product;
import com.mazzama.research.multisourcedb.dto.ProductRequest;
import com.mazzama.research.multisourcedb.dto.ProductResponse;
import com.mazzama.research.multisourcedb.mapper.ProductMapper;
import com.mazzama.research.multisourcedb.repository.IProductRepository;
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
    private IProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public List<ProductResponse> getAllProduct() {
        return productMapper.toDto(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable("id") Integer id) {
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
