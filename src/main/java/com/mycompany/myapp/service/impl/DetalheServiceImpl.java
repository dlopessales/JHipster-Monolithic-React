package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DetalheService;
import com.mycompany.myapp.domain.Detalhe;
import com.mycompany.myapp.repository.DetalheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Detalhe.
 */
@Service
@Transactional
public class DetalheServiceImpl implements DetalheService {

    private final Logger log = LoggerFactory.getLogger(DetalheServiceImpl.class);

    private final DetalheRepository detalheRepository;

    public DetalheServiceImpl(DetalheRepository detalheRepository) {
        this.detalheRepository = detalheRepository;
    }

    /**
     * Save a detalhe.
     *
     * @param detalhe the entity to save
     * @return the persisted entity
     */
    @Override
    public Detalhe save(Detalhe detalhe) {
        log.debug("Request to save Detalhe : {}", detalhe);
        return detalheRepository.save(detalhe);
    }

    /**
     * Get all the detalhes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Detalhe> findAll(Pageable pageable) {
        log.debug("Request to get all Detalhes");
        return detalheRepository.findAll(pageable);
    }


    /**
     * Get one detalhe by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Detalhe> findOne(Long id) {
        log.debug("Request to get Detalhe : {}", id);
        return detalheRepository.findById(id);
    }

    /**
     * Delete the detalhe by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Detalhe : {}", id);
        detalheRepository.deleteById(id);
    }
}
