package com.aiep.prueba2.domain;

import static com.aiep.prueba2.domain.CentroSaludTestSamples.*;
import static com.aiep.prueba2.domain.MedicoTestSamples.*;
import static com.aiep.prueba2.domain.PacienteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.prueba2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PacienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paciente.class);
        Paciente paciente1 = getPacienteSample1();
        Paciente paciente2 = new Paciente();
        assertThat(paciente1).isNotEqualTo(paciente2);

        paciente2.setId(paciente1.getId());
        assertThat(paciente1).isEqualTo(paciente2);

        paciente2 = getPacienteSample2();
        assertThat(paciente1).isNotEqualTo(paciente2);
    }

    @Test
    void medicoTest() {
        Paciente paciente = getPacienteRandomSampleGenerator();
        Medico medicoBack = getMedicoRandomSampleGenerator();

        paciente.setMedico(medicoBack);
        assertThat(paciente.getMedico()).isEqualTo(medicoBack);

        paciente.medico(null);
        assertThat(paciente.getMedico()).isNull();
    }

    @Test
    void centroSaludTest() {
        Paciente paciente = getPacienteRandomSampleGenerator();
        CentroSalud centroSaludBack = getCentroSaludRandomSampleGenerator();

        paciente.setCentroSalud(centroSaludBack);
        assertThat(paciente.getCentroSalud()).isEqualTo(centroSaludBack);

        paciente.centroSalud(null);
        assertThat(paciente.getCentroSalud()).isNull();
    }
}
