package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.MestreService;
import com.mycompany.myapp.domain.Mestre;
import com.mycompany.myapp.repository.MestreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Mestre.
 */
@Service
@Transactional
public class MestreServiceImpl implements MestreService {

    private final Logger log = LoggerFactory.getLogger(MestreServiceImpl.class);

    private final MestreRepository mestreRepository;

    public MestreServiceImpl(MestreRepository mestreRepository) {
        this.mestreRepository = mestreRepository;
    }

    /**
     * Save a mestre.
     *
     * @param mestre the entity to save
     * @return the persisted entity
     */
    @Override
    public Mestre save(Mestre mestre) {
        log.debug("Request to save Mestre : {}", mestre);
        return mestreRepository.save(mestre);
    }

    /**
     * Get all the mestres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Mestre> findAll(Pageable pageable) {
        log.debug("Request to get all Mestres");
        return mestreRepository.findAll(pageable);
    }


    /**
     * Get one mestre by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Mestre> findOne(Long id) {
        log.debug("Request to get Mestre : {}", id);
        return mestreRepository.findById(id);
    }

    /**
     * Delete the mestre by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mestre : {}", id);
        mestreRepository.deleteById(id);
    }
}
