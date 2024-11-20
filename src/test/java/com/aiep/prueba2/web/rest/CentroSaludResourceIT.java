package com.aiep.prueba2.web.rest;

import static com.aiep.prueba2.domain.CentroSaludAsserts.*;
import static com.aiep.prueba2.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.prueba2.IntegrationTest;
import com.aiep.prueba2.domain.CentroSalud;
import com.aiep.prueba2.repository.CentroSaludRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CentroSaludResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CentroSaludResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/centro-saluds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CentroSaludRepository centroSaludRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCentroSaludMockMvc;

    private CentroSalud centroSalud;

    private CentroSalud insertedCentroSalud;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentroSalud createEntity() {
        return new CentroSalud().name(DEFAULT_NAME).address(DEFAULT_ADDRESS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentroSalud createUpdatedEntity() {
        return new CentroSalud().name(UPDATED_NAME).address(UPDATED_ADDRESS);
    }

    @BeforeEach
    public void initTest() {
        centroSalud = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCentroSalud != null) {
            centroSaludRepository.delete(insertedCentroSalud);
            insertedCentroSalud = null;
        }
    }

    @Test
    @Transactional
    void createCentroSalud() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CentroSalud
        var returnedCentroSalud = om.readValue(
            restCentroSaludMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroSalud)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CentroSalud.class
        );

        // Validate the CentroSalud in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCentroSaludUpdatableFieldsEquals(returnedCentroSalud, getPersistedCentroSalud(returnedCentroSalud));

        insertedCentroSalud = returnedCentroSalud;
    }

    @Test
    @Transactional
    void createCentroSaludWithExistingId() throws Exception {
        // Create the CentroSalud with an existing ID
        centroSalud.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentroSaludMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroSalud)))
            .andExpect(status().isBadRequest());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        centroSalud.setName(null);

        // Create the CentroSalud, which fails.

        restCentroSaludMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroSalud)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCentroSaluds() throws Exception {
        // Initialize the database
        insertedCentroSalud = centroSaludRepository.saveAndFlush(centroSalud);

        // Get all the centroSaludList
        restCentroSaludMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centroSalud.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @Test
    @Transactional
    void getCentroSalud() throws Exception {
        // Initialize the database
        insertedCentroSalud = centroSaludRepository.saveAndFlush(centroSalud);

        // Get the centroSalud
        restCentroSaludMockMvc
            .perform(get(ENTITY_API_URL_ID, centroSalud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(centroSalud.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    @Transactional
    void getNonExistingCentroSalud() throws Exception {
        // Get the centroSalud
        restCentroSaludMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCentroSalud() throws Exception {
        // Initialize the database
        insertedCentroSalud = centroSaludRepository.saveAndFlush(centroSalud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centroSalud
        CentroSalud updatedCentroSalud = centroSaludRepository.findById(centroSalud.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCentroSalud are not directly saved in db
        em.detach(updatedCentroSalud);
        updatedCentroSalud.name(UPDATED_NAME).address(UPDATED_ADDRESS);

        restCentroSaludMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCentroSalud.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCentroSalud))
            )
            .andExpect(status().isOk());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCentroSaludToMatchAllProperties(updatedCentroSalud);
    }

    @Test
    @Transactional
    void putNonExistingCentroSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroSalud.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentroSaludMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centroSalud.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centroSalud))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCentroSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroSalud.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroSaludMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centroSalud))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCentroSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroSalud.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroSaludMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroSalud)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCentroSaludWithPatch() throws Exception {
        // Initialize the database
        insertedCentroSalud = centroSaludRepository.saveAndFlush(centroSalud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centroSalud using partial update
        CentroSalud partialUpdatedCentroSalud = new CentroSalud();
        partialUpdatedCentroSalud.setId(centroSalud.getId());

        partialUpdatedCentroSalud.address(UPDATED_ADDRESS);

        restCentroSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentroSalud.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCentroSalud))
            )
            .andExpect(status().isOk());

        // Validate the CentroSalud in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCentroSaludUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCentroSalud, centroSalud),
            getPersistedCentroSalud(centroSalud)
        );
    }

    @Test
    @Transactional
    void fullUpdateCentroSaludWithPatch() throws Exception {
        // Initialize the database
        insertedCentroSalud = centroSaludRepository.saveAndFlush(centroSalud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centroSalud using partial update
        CentroSalud partialUpdatedCentroSalud = new CentroSalud();
        partialUpdatedCentroSalud.setId(centroSalud.getId());

        partialUpdatedCentroSalud.name(UPDATED_NAME).address(UPDATED_ADDRESS);

        restCentroSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentroSalud.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCentroSalud))
            )
            .andExpect(status().isOk());

        // Validate the CentroSalud in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCentroSaludUpdatableFieldsEquals(partialUpdatedCentroSalud, getPersistedCentroSalud(partialUpdatedCentroSalud));
    }

    @Test
    @Transactional
    void patchNonExistingCentroSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroSalud.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentroSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, centroSalud.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(centroSalud))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCentroSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroSalud.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(centroSalud))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCentroSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroSalud.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroSaludMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(centroSalud)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCentroSalud() throws Exception {
        // Initialize the database
        insertedCentroSalud = centroSaludRepository.saveAndFlush(centroSalud);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the centroSalud
        restCentroSaludMockMvc
            .perform(delete(ENTITY_API_URL_ID, centroSalud.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return centroSaludRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected CentroSalud getPersistedCentroSalud(CentroSalud centroSalud) {
        return centroSaludRepository.findById(centroSalud.getId()).orElseThrow();
    }

    protected void assertPersistedCentroSaludToMatchAllProperties(CentroSalud expectedCentroSalud) {
        assertCentroSaludAllPropertiesEquals(expectedCentroSalud, getPersistedCentroSalud(expectedCentroSalud));
    }

    protected void assertPersistedCentroSaludToMatchUpdatableProperties(CentroSalud expectedCentroSalud) {
        assertCentroSaludAllUpdatablePropertiesEquals(expectedCentroSalud, getPersistedCentroSalud(expectedCentroSalud));
    }
}
