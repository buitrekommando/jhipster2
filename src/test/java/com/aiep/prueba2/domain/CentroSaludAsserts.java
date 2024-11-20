package com.aiep.prueba2.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CentroSaludAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCentroSaludAllPropertiesEquals(CentroSalud expected, CentroSalud actual) {
        assertCentroSaludAutoGeneratedPropertiesEquals(expected, actual);
        assertCentroSaludAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCentroSaludAllUpdatablePropertiesEquals(CentroSalud expected, CentroSalud actual) {
        assertCentroSaludUpdatableFieldsEquals(expected, actual);
        assertCentroSaludUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCentroSaludAutoGeneratedPropertiesEquals(CentroSalud expected, CentroSalud actual) {
        assertThat(expected)
            .as("Verify CentroSalud auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCentroSaludUpdatableFieldsEquals(CentroSalud expected, CentroSalud actual) {
        assertThat(expected)
            .as("Verify CentroSalud relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getAddress()).as("check address").isEqualTo(actual.getAddress()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCentroSaludUpdatableRelationshipsEquals(CentroSalud expected, CentroSalud actual) {
        // empty method
    }
}