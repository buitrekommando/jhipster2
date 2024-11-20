package com.aiep.prueba2.web.rest;

import com.aiep.prueba2.domain.Medico;
import com.aiep.prueba2.repository.MedicoRepository;
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
 * REST controller for managing {@link com.aiep.prueba2.domain.Medico}.
 */
@RestController
@RequestMapping("/api/medicos")
@Transactional
public class MedicoResource {

    private static final Logger LOG = LoggerFactory.getLogger(MedicoResource.class);

    private static final String ENTITY_NAME = "medico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicoRepository medicoRepository;

    public MedicoResource(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    /**
     * {@code POST  /medicos} : Create a new medico.
     *
     * @param medico the medico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medico, or with status {@code 400 (Bad Request)} if the medico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Medico> createMedico(@Valid @RequestBody Medico medico) throws URISyntaxException {
        LOG.debug("REST request to save Medico : {}", medico);
        if (medico.getId() != null) {
            throw new BadRequestAlertException("A new medico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        medico = medicoRepository.save(medico);
        return ResponseEntity.created(new URI("/api/medicos/" + medico.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, medico.getId().toString()))
            .body(medico);
    }

    /**
     * {@code PUT  /medicos/:id} : Updates an existing medico.
     *
     * @param id the id of the medico to save.
     * @param medico the medico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medico,
     * or with status {@code 400 (Bad Request)} if the medico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Medico> updateMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Medico medico
    ) throws URISyntaxException {
        LOG.debug("REST request to update Medico : {}, {}", id, medico);
        if (medico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        medico = medicoRepository.save(medico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medico.getId().toString()))
            .body(medico);
    }

    /**
     * {@code PATCH  /medicos/:id} : Partial updates given fields of an existing medico, field will ignore if it is null
     *
     * @param id the id of the medico to save.
     * @param medico the medico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medico,
     * or with status {@code 400 (Bad Request)} if the medico is not valid,
     * or with status {@code 404 (Not Found)} if the medico is not found,
     * or with status {@code 500 (Internal Server Error)} if the medico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Medico> partialUpdateMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Medico medico
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Medico partially : {}, {}", id, medico);
        if (medico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Medico> result = medicoRepository
            .findById(medico.getId())
            .map(existingMedico -> {
                if (medico.getName() != null) {
                    existingMedico.setName(medico.getName());
                }
                if (medico.getSpecialty() != null) {
                    existingMedico.setSpecialty(medico.getSpecialty());
                }

                return existingMedico;
            })
            .map(medicoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medico.getId().toString())
        );
    }

    /**
     * {@code GET  /medicos} : get all the medicos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicos in body.
     */
    @GetMapping("")
    public List<Medico> getAllMedicos() {
        LOG.debug("REST request to get all Medicos");
        return medicoRepository.findAll();
    }

    /**
     * {@code GET  /medicos/:id} : get the "id" medico.
     *
     * @param id the id of the medico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Medico> getMedico(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Medico : {}", id);
        Optional<Medico> medico = medicoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(medico);
    }

    /**
     * {@code DELETE  /medicos/:id} : delete the "id" medico.
     *
     * @param id the id of the medico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedico(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Medico : {}", id);
        medicoRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
