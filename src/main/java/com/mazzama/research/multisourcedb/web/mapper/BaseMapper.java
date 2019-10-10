package com.mazzama.research.multisourcedb.web.mapper;

import java.util.List;

public interface BaseMapper<M, D> {
    D toDto(M model);
    M toModel(D dto);
    List<D> toDto(List<M> modelList);
    List<M> toModel(List<D> dtoList);
}