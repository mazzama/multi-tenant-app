package com.mazzama.research.multisourcedb.mapper;

import com.mazzama.research.multisourcedb.domain.Product;
import com.mazzama.research.multisourcedb.dto.ProductRequest;
import com.mazzama.research.multisourcedb.dto.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
        injectionStrategy = CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = ALWAYS,
        nullValuePropertyMappingStrategy = IGNORE)
public interface ProductMapper extends BaseMapper<Product, ProductResponse> {

    Product requestToModel(ProductRequest productRequest);
}
