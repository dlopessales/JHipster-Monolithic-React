package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Mestre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Mestre.
 */
public interface MestreService {

    /**
     * Save a mestre.
     *
     * @param mestre the entity to save
     * @return the persisted entity
     */
    Mestre save(Mestre mestre);

    /**
     * Get all the mestres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Mestre> findAll(Pageable pageable);


    /**
     * Get the "id" mestre.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Mestre> findOne(Long id);

    /**
     * Delete the "id" mestre.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
