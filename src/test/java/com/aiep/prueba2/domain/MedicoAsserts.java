package com.aiep.prueba2.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class MedicoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMedicoAllPropertiesEquals(Medico expected, Medico actual) {
        assertMedicoAutoGeneratedPropertiesEquals(expected, actual);
        assertMedicoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMedicoAllUpdatablePropertiesEquals(Medico expected, Medico actual) {
        assertMedicoUpdatableFieldsEquals(expected, actual);
        assertMedicoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMedicoAutoGeneratedPropertiesEquals(Medico expected, Medico actual) {
        assertThat(expected)
            .as("Verify Medico auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMedicoUpdatableFieldsEquals(Medico expected, Medico actual) {
        assertThat(expected)
            .as("Verify Medico relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getSpecialty()).as("check specialty").isEqualTo(actual.getSpecialty()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMedicoUpdatableRelationshipsEquals(Medico expected, Medico actual) {
        assertThat(expected)
            .as("Verify Medico relationships")
            .satisfies(e -> assertThat(e.getCentroSalud()).as("check centroSalud").isEqualTo(actual.getCentroSalud()));
    }
}
