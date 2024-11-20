package com.aiep.prueba2.domain;

import static com.aiep.prueba2.domain.CentroSaludTestSamples.*;
import static com.aiep.prueba2.domain.MedicoTestSamples.*;
import static com.aiep.prueba2.domain.PacienteTestSamples.*;
import static com.aiep.prueba2.domain.ReservaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.prueba2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReservaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reserva.class);
        Reserva reserva1 = getReservaSample1();
        Reserva reserva2 = new Reserva();
        assertThat(reserva1).isNotEqualTo(reserva2);

        reserva2.setId(reserva1.getId());
        assertThat(reserva1).isEqualTo(reserva2);

        reserva2 = getReservaSample2();
        assertThat(reserva1).isNotEqualTo(reserva2);
    }

    @Test
    void pacienteTest() {
        Reserva reserva = getReservaRandomSampleGenerator();
        Paciente pacienteBack = getPacienteRandomSampleGenerator();

        reserva.setPaciente(pacienteBack);
        assertThat(reserva.getPaciente()).isEqualTo(pacienteBack);

        reserva.paciente(null);
        assertThat(reserva.getPaciente()).isNull();
    }

    @Test
    void medicoTest() {
        Reserva reserva = getReservaRandomSampleGenerator();
        Medico medicoBack = getMedicoRandomSampleGenerator();

        reserva.setMedico(medicoBack);
        assertThat(reserva.getMedico()).isEqualTo(medicoBack);

        reserva.medico(null);
        assertThat(reserva.getMedico()).isNull();
    }

    @Test
    void centroSaludTest() {
        Reserva reserva = getReservaRandomSampleGenerator();
        CentroSalud centroSaludBack = getCentroSaludRandomSampleGenerator();

        reserva.setCentroSalud(centroSaludBack);
        assertThat(reserva.getCentroSalud()).isEqualTo(centroSaludBack);

        reserva.centroSalud(null);
        assertThat(reserva.getCentroSalud()).isNull();
    }
}
