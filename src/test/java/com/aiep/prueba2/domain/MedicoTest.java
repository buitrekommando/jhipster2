package com.aiep.prueba2.domain;

import static com.aiep.prueba2.domain.CentroSaludTestSamples.*;
import static com.aiep.prueba2.domain.MedicoTestSamples.*;
import static com.aiep.prueba2.domain.PacienteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.prueba2.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MedicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medico.class);
        Medico medico1 = getMedicoSample1();
        Medico medico2 = new Medico();
        assertThat(medico1).isNotEqualTo(medico2);

        medico2.setId(medico1.getId());
        assertThat(medico1).isEqualTo(medico2);

        medico2 = getMedicoSample2();
        assertThat(medico1).isNotEqualTo(medico2);
    }

    @Test
    void pacienteTest() {
        Medico medico = getMedicoRandomSampleGenerator();
        Paciente pacienteBack = getPacienteRandomSampleGenerator();

        medico.addPaciente(pacienteBack);
        assertThat(medico.getPacientes()).containsOnly(pacienteBack);
        assertThat(pacienteBack.getMedico()).isEqualTo(medico);

        medico.removePaciente(pacienteBack);
        assertThat(medico.getPacientes()).doesNotContain(pacienteBack);
        assertThat(pacienteBack.getMedico()).isNull();

        medico.pacientes(new HashSet<>(Set.of(pacienteBack)));
        assertThat(medico.getPacientes()).containsOnly(pacienteBack);
        assertThat(pacienteBack.getMedico()).isEqualTo(medico);

        medico.setPacientes(new HashSet<>());
        assertThat(medico.getPacientes()).doesNotContain(pacienteBack);
        assertThat(pacienteBack.getMedico()).isNull();
    }

    @Test
    void centroSaludTest() {
        Medico medico = getMedicoRandomSampleGenerator();
        CentroSalud centroSaludBack = getCentroSaludRandomSampleGenerator();

        medico.setCentroSalud(centroSaludBack);
        assertThat(medico.getCentroSalud()).isEqualTo(centroSaludBack);

        medico.centroSalud(null);
        assertThat(medico.getCentroSalud()).isNull();
    }
}
