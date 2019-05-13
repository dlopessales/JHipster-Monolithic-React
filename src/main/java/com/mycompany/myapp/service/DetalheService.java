package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Detalhe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Detalhe.
 */
public interface DetalheService {

    /**
     * Save a detalhe.
     *
     * @param detalhe the entity to save
     * @return the persisted entity
     */
    Detalhe save(Detalhe detalhe);

    /**
     * Get all the detalhes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Detalhe> findAll(Pageable pageable);


    /**
     * Get the "id" detalhe.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Detalhe> findOne(Long id);

    /**
     * Delete the "id" detalhe.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
