package org.example.article.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GenericService<E, M> {
    E save(E entity);

    void deleteById(M id);

    Optional<E> findById(M id);

    List<E> findAll();

    Page<E> findAll(Pageable pageable);

    E update(E entity, M id);
}