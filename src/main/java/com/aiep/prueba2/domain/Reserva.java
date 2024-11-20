package com.aiep.prueba2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Reserva.
 */
@Entity
@Table(name = "reserva")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "medico", "centroSalud" }, allowSetters = true)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "pacientes", "centroSalud" }, allowSetters = true)
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "pacientes", "medicos" }, allowSetters = true)
    private CentroSalud centroSalud;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reserva id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public Reserva date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Reserva paciente(Paciente paciente) {
        this.setPaciente(paciente);
        return this;
    }

    public Medico getMedico() {
        return this.medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Reserva medico(Medico medico) {
        this.setMedico(medico);
        return this;
    }

    public CentroSalud getCentroSalud() {
        return this.centroSalud;
    }

    public void setCentroSalud(CentroSalud centroSalud) {
        this.centroSalud = centroSalud;
    }

    public Reserva centroSalud(CentroSalud centroSalud) {
        this.setCentroSalud(centroSalud);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reserva)) {
            return false;
        }
        return getId() != null && getId().equals(((Reserva) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reserva{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
