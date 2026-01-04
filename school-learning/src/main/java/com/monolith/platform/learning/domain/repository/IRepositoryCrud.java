package com.monolith.platform.learning.domain.repository;

import org.springframework.data.domain.Page;

public interface IRepositoryCrud<T> {

    Page<T> findAll(int page, int elements, String sortBy, String sortDirection);
    T findById(Long id);
    T save(T t);
    T update(T t);
    Boolean deleteById(Long id);
}
