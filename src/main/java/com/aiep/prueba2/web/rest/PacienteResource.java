package com.aiep.prueba2.web.rest;

import com.aiep.prueba2.domain.Paciente;
import com.aiep.prueba2.repository.PacienteRepository;
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
 * REST controller for managing {@link com.aiep.prueba2.domain.Paciente}.
 */
@RestController
@RequestMapping("/api/pacientes")
@Transactional
public class PacienteResource {

    private static final Logger LOG = LoggerFactory.getLogger(PacienteResource.class);

    private static final String ENTITY_NAME = "paciente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PacienteRepository pacienteRepository;

    public PacienteResource(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * {@code POST  /pacientes} : Create a new paciente.
     *
     * @param paciente the paciente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paciente, or with status {@code 400 (Bad Request)} if the paciente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Paciente> createPaciente(@Valid @RequestBody Paciente paciente) throws URISyntaxException {
        LOG.debug("REST request to save Paciente : {}", paciente);
        if (paciente.getId() != null) {
            throw new BadRequestAlertException("A new paciente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        paciente = pacienteRepository.save(paciente);
        return ResponseEntity.created(new URI("/api/pacientes/" + paciente.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, paciente.getId().toString()))
            .body(paciente);
    }

    /**
     * {@code PUT  /pacientes/:id} : Updates an existing paciente.
     *
     * @param id the id of the paciente to save.
     * @param paciente the paciente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paciente,
     * or with status {@code 400 (Bad Request)} if the paciente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paciente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updatePaciente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Paciente paciente
    ) throws URISyntaxException {
        LOG.debug("REST request to update Paciente : {}, {}", id, paciente);
        if (paciente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paciente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pacienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        paciente = pacienteRepository.save(paciente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paciente.getId().toString()))
            .body(paciente);
    }

    /**
     * {@code PATCH  /pacientes/:id} : Partial updates given fields of an existing paciente, field will ignore if it is null
     *
     * @param id the id of the paciente to save.
     * @param paciente the paciente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paciente,
     * or with status {@code 400 (Bad Request)} if the paciente is not valid,
     * or with status {@code 404 (Not Found)} if the paciente is not found,
     * or with status {@code 500 (Internal Server Error)} if the paciente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Paciente> partialUpdatePaciente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Paciente paciente
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Paciente partially : {}, {}", id, paciente);
        if (paciente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paciente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pacienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Paciente> result = pacienteRepository
            .findById(paciente.getId())
            .map(existingPaciente -> {
                if (paciente.getName() != null) {
                    existingPaciente.setName(paciente.getName());
                }
                if (paciente.getAge() != null) {
                    existingPaciente.setAge(paciente.getAge());
                }

                return existingPaciente;
            })
            .map(pacienteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paciente.getId().toString())
        );
    }

    /**
     * {@code GET  /pacientes} : get all the pacientes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pacientes in body.
     */
    @GetMapping("")
    public List<Paciente> getAllPacientes() {
        LOG.debug("REST request to get all Pacientes");
        return pacienteRepository.findAll();
    }

    /**
     * {@code GET  /pacientes/:id} : get the "id" paciente.
     *
     * @param id the id of the paciente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paciente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> getPaciente(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Paciente : {}", id);
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paciente);
    }

    /**
     * {@code DELETE  /pacientes/:id} : delete the "id" paciente.
     *
     * @param id the id of the paciente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Paciente : {}", id);
        pacienteRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
