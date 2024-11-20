package com.aiep.prueba2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Medico.
 */
@Entity
@Table(name = "medico")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Medico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "specialty")
    private String specialty;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medico")
    @JsonIgnoreProperties(value = { "medico", "centroSalud" }, allowSetters = true)
    private Set<Paciente> pacientes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "pacientes", "medicos" }, allowSetters = true)
    private CentroSalud centroSalud;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Medico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Medico name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return this.specialty;
    }

    public Medico specialty(String specialty) {
        this.setSpecialty(specialty);
        return this;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Set<Paciente> getPacientes() {
        return this.pacientes;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        if (this.pacientes != null) {
            this.pacientes.forEach(i -> i.setMedico(null));
        }
        if (pacientes != null) {
            pacientes.forEach(i -> i.setMedico(this));
        }
        this.pacientes = pacientes;
    }

    public Medico pacientes(Set<Paciente> pacientes) {
        this.setPacientes(pacientes);
        return this;
    }

    public Medico addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.setMedico(this);
        return this;
    }

    public Medico removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.setMedico(null);
        return this;
    }

    public CentroSalud getCentroSalud() {
        return this.centroSalud;
    }

    public void setCentroSalud(CentroSalud centroSalud) {
        this.centroSalud = centroSalud;
    }

    public Medico centroSalud(CentroSalud centroSalud) {
        this.setCentroSalud(centroSalud);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medico)) {
            return false;
        }
        return getId() != null && getId().equals(((Medico) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Medico{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", specialty='" + getSpecialty() + "'" +
            "}";
    }
}
