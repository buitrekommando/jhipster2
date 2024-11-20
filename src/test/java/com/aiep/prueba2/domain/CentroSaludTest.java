package com.aiep.prueba2.domain;

import static com.aiep.prueba2.domain.CentroSaludTestSamples.*;
import static com.aiep.prueba2.domain.MedicoTestSamples.*;
import static com.aiep.prueba2.domain.PacienteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.prueba2.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CentroSaludTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentroSalud.class);
        CentroSalud centroSalud1 = getCentroSaludSample1();
        CentroSalud centroSalud2 = new CentroSalud();
        assertThat(centroSalud1).isNotEqualTo(centroSalud2);

        centroSalud2.setId(centroSalud1.getId());
        assertThat(centroSalud1).isEqualTo(centroSalud2);

        centroSalud2 = getCentroSaludSample2();
        assertThat(centroSalud1).isNotEqualTo(centroSalud2);
    }

    @Test
    void pacienteTest() {
        CentroSalud centroSalud = getCentroSaludRandomSampleGenerator();
        Paciente pacienteBack = getPacienteRandomSampleGenerator();

        centroSalud.addPaciente(pacienteBack);
        assertThat(centroSalud.getPacientes()).containsOnly(pacienteBack);
        assertThat(pacienteBack.getCentroSalud()).isEqualTo(centroSalud);

        centroSalud.removePaciente(pacienteBack);
        assertThat(centroSalud.getPacientes()).doesNotContain(pacienteBack);
        assertThat(pacienteBack.getCentroSalud()).isNull();

        centroSalud.pacientes(new HashSet<>(Set.of(pacienteBack)));
        assertThat(centroSalud.getPacientes()).containsOnly(pacienteBack);
        assertThat(pacienteBack.getCentroSalud()).isEqualTo(centroSalud);

        centroSalud.setPacientes(new HashSet<>());
        assertThat(centroSalud.getPacientes()).doesNotContain(pacienteBack);
        assertThat(pacienteBack.getCentroSalud()).isNull();
    }

    @Test
    void medicoTest() {
        CentroSalud centroSalud = getCentroSaludRandomSampleGenerator();
        Medico medicoBack = getMedicoRandomSampleGenerator();

        centroSalud.addMedico(medicoBack);
        assertThat(centroSalud.getMedicos()).containsOnly(medicoBack);
        assertThat(medicoBack.getCentroSalud()).isEqualTo(centroSalud);

        centroSalud.removeMedico(medicoBack);
        assertThat(centroSalud.getMedicos()).doesNotContain(medicoBack);
        assertThat(medicoBack.getCentroSalud()).isNull();

        centroSalud.medicos(new HashSet<>(Set.of(medicoBack)));
        assertThat(centroSalud.getMedicos()).containsOnly(medicoBack);
        assertThat(medicoBack.getCentroSalud()).isEqualTo(centroSalud);

        centroSalud.setMedicos(new HashSet<>());
        assertThat(centroSalud.getMedicos()).doesNotContain(medicoBack);
        assertThat(medicoBack.getCentroSalud()).isNull();
    }
}
