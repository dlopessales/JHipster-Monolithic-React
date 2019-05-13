package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Mestre;
import com.mycompany.myapp.service.MestreService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Mestre.
 */
@RestController
@RequestMapping("/api")
public class MestreResource {

    private final Logger log = LoggerFactory.getLogger(MestreResource.class);

    private static final String ENTITY_NAME = "mestre";

    private final MestreService mestreService;

    public MestreResource(MestreService mestreService) {
        this.mestreService = mestreService;
    }

    /**
     * POST  /mestres : Create a new mestre.
     *
     * @param mestre the mestre to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mestre, or with status 400 (Bad Request) if the mestre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mestres")
    public ResponseEntity<Mestre> createMestre(@RequestBody Mestre mestre) throws URISyntaxException {
        log.debug("REST request to save Mestre : {}", mestre);
        if (mestre.getId() != null) {
            throw new BadRequestAlertException("A new mestre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mestre result = mestreService.save(mestre);
        return ResponseEntity.created(new URI("/api/mestres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mestres : Updates an existing mestre.
     *
     * @param mestre the mestre to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mestre,
     * or with status 400 (Bad Request) if the mestre is not valid,
     * or with status 500 (Internal Server Error) if the mestre couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mestres")
    public ResponseEntity<Mestre> updateMestre(@RequestBody Mestre mestre) throws URISyntaxException {
        log.debug("REST request to update Mestre : {}", mestre);
        if (mestre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mestre result = mestreService.save(mestre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mestre.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mestres : get all the mestres.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mestres in body
     */
    @GetMapping("/mestres")
    public ResponseEntity<List<Mestre>> getAllMestres(Pageable pageable) {
        log.debug("REST request to get a page of Mestres");
        Page<Mestre> page = mestreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mestres");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /mestres/:id : get the "id" mestre.
     *
     * @param id the id of the mestre to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mestre, or with status 404 (Not Found)
     */
    @GetMapping("/mestres/{id}")
    public ResponseEntity<Mestre> getMestre(@PathVariable Long id) {
        log.debug("REST request to get Mestre : {}", id);
        Optional<Mestre> mestre = mestreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mestre);
    }

    /**
     * DELETE  /mestres/:id : delete the "id" mestre.
     *
     * @param id the id of the mestre to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mestres/{id}")
    public ResponseEntity<Void> deleteMestre(@PathVariable Long id) {
        log.debug("REST request to delete Mestre : {}", id);
        mestreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
