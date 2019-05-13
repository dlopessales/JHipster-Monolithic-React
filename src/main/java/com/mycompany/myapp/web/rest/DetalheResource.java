package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Detalhe;
import com.mycompany.myapp.service.DetalheService;
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
 * REST controller for managing Detalhe.
 */
@RestController
@RequestMapping("/api")
public class DetalheResource {

    private final Logger log = LoggerFactory.getLogger(DetalheResource.class);

    private static final String ENTITY_NAME = "detalhe";

    private final DetalheService detalheService;

    public DetalheResource(DetalheService detalheService) {
        this.detalheService = detalheService;
    }

    /**
     * POST  /detalhes : Create a new detalhe.
     *
     * @param detalhe the detalhe to create
     * @return the ResponseEntity with status 201 (Created) and with body the new detalhe, or with status 400 (Bad Request) if the detalhe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/detalhes")
    public ResponseEntity<Detalhe> createDetalhe(@RequestBody Detalhe detalhe) throws URISyntaxException {
        log.debug("REST request to save Detalhe : {}", detalhe);
        if (detalhe.getId() != null) {
            throw new BadRequestAlertException("A new detalhe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Detalhe result = detalheService.save(detalhe);
        return ResponseEntity.created(new URI("/api/detalhes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /detalhes : Updates an existing detalhe.
     *
     * @param detalhe the detalhe to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated detalhe,
     * or with status 400 (Bad Request) if the detalhe is not valid,
     * or with status 500 (Internal Server Error) if the detalhe couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/detalhes")
    public ResponseEntity<Detalhe> updateDetalhe(@RequestBody Detalhe detalhe) throws URISyntaxException {
        log.debug("REST request to update Detalhe : {}", detalhe);
        if (detalhe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Detalhe result = detalheService.save(detalhe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detalhe.getId().toString()))
            .body(result);
    }

    /**
     * GET  /detalhes : get all the detalhes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of detalhes in body
     */
    @GetMapping("/detalhes")
    public ResponseEntity<List<Detalhe>> getAllDetalhes(Pageable pageable) {
        log.debug("REST request to get a page of Detalhes");
        Page<Detalhe> page = detalheService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/detalhes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /detalhes/:id : get the "id" detalhe.
     *
     * @param id the id of the detalhe to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detalhe, or with status 404 (Not Found)
     */
    @GetMapping("/detalhes/{id}")
    public ResponseEntity<Detalhe> getDetalhe(@PathVariable Long id) {
        log.debug("REST request to get Detalhe : {}", id);
        Optional<Detalhe> detalhe = detalheService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detalhe);
    }

    /**
     * DELETE  /detalhes/:id : delete the "id" detalhe.
     *
     * @param id the id of the detalhe to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/detalhes/{id}")
    public ResponseEntity<Void> deleteDetalhe(@PathVariable Long id) {
        log.debug("REST request to delete Detalhe : {}", id);
        detalheService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
