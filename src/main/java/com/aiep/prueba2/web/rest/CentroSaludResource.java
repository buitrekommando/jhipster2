package com.aiep.prueba2.web.rest;

import com.aiep.prueba2.domain.CentroSalud;
import com.aiep.prueba2.repository.CentroSaludRepository;
import com.aiep.prueba2.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.aiep.prueba2.domain.CentroSalud}.
 */
@RestController
@RequestMapping("/api/centro-saluds")
@Transactional
public class CentroSaludResource {

    private static final Logger LOG = LoggerFactory.getLogger(CentroSaludResource.class);

    private static final String ENTITY_NAME = "centroSalud";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CentroSaludRepository centroSaludRepository;

    public CentroSaludResource(CentroSaludRepository centroSaludRepository) {
        this.centroSaludRepository = centroSaludRepository;
    }

    /**
     * {@code POST  /centro-saluds} : Create a new centroSalud.
     *
     * @param centroSalud the centroSalud to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centroSalud, or with status {@code 400 (Bad Request)} if the centroSalud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CentroSalud> createCentroSalud(@Valid @RequestBody CentroSalud centroSalud) throws URISyntaxException {
        LOG.debug("REST request to save CentroSalud : {}", centroSalud);
        if (centroSalud.getId() != null) {
            throw new BadRequestAlertException("A new centroSalud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        centroSalud = centroSaludRepository.save(centroSalud);
        return ResponseEntity.created(new URI("/api/centro-saluds/" + centroSalud.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, centroSalud.getId().toString()))
            .body(centroSalud);
    }

    /**
     * {@code PUT  /centro-saluds/:id} : Updates an existing centroSalud.
     *
     * @param id the id of the centroSalud to save.
     * @param centroSalud the centroSalud to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centroSalud,
     * or with status {@code 400 (Bad Request)} if the centroSalud is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centroSalud couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CentroSalud> updateCentroSalud(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CentroSalud centroSalud
    ) throws URISyntaxException {
        LOG.debug("REST request to update CentroSalud : {}, {}", id, centroSalud);
        if (centroSalud.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centroSalud.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centroSaludRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        centroSalud = centroSaludRepository.save(centroSalud);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centroSalud.getId().toString()))
            .body(centroSalud);
    }

    /**
     * {@code PATCH  /centro-saluds/:id} : Partial updates given fields of an existing centroSalud, field will ignore if it is null
     *
     * @param id the id of the centroSalud to save.
     * @param centroSalud the centroSalud to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centroSalud,
     * or with status {@code 400 (Bad Request)} if the centroSalud is not valid,
     * or with status {@code 404 (Not Found)} if the centroSalud is not found,
     * or with status {@code 500 (Internal Server Error)} if the centroSalud couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CentroSalud> partialUpdateCentroSalud(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CentroSalud centroSalud
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CentroSalud partially : {}, {}", id, centroSalud);
        if (centroSalud.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centroSalud.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centroSaludRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CentroSalud> result = centroSaludRepository
            .findById(centroSalud.getId())
            .map(existingCentroSalud -> {
                if (centroSalud.getName() != null) {
                    existingCentroSalud.setName(centroSalud.getName());
                }
                if (centroSalud.getAddress() != null) {
                    existingCentroSalud.setAddress(centroSalud.getAddress());
                }

                return existingCentroSalud;
            })
            .map(centroSaludRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centroSalud.getId().toString())
        );
    }

    /**
     * {@code GET  /centro-saluds} : get all the centroSaluds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centroSaluds in body.
     */
    @GetMapping("")
    public List<CentroSalud> getAllCentroSaluds() {
        LOG.debug("REST request to get all CentroSaluds");
        return centroSaludRepository.findAll();
    }

    /**
     * {@code GET  /centro-saluds/:id} : get the "id" centroSalud.
     *
     * @param id the id of the centroSalud to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centroSalud, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CentroSalud> getCentroSalud(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CentroSalud : {}", id);
        Optional<CentroSalud> centroSalud = centroSaludRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(centroSalud);
    }

    /**
     * {@code DELETE  /centro-saluds/:id} : delete the "id" centroSalud.
     *
     * @param id the id of the centroSalud to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCentroSalud(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CentroSalud : {}", id);
        centroSaludRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
