package com.aiep.prueba2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CentroSalud.
 */
@Entity
@Table(name = "centro_salud")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CentroSalud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "centroSalud")
    @JsonIgnoreProperties(value = { "medico", "centroSalud" }, allowSetters = true)
    private Set<Paciente> pacientes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "centroSalud")
    @JsonIgnoreProperties(value = { "pacientes", "centroSalud" }, allowSetters = true)
    private Set<Medico> medicos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CentroSalud id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CentroSalud name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public CentroSalud address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Paciente> getPacientes() {
        return this.pacientes;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        if (this.pacientes != null) {
            this.pacientes.forEach(i -> i.setCentroSalud(null));
        }
        if (pacientes != null) {
            pacientes.forEach(i -> i.setCentroSalud(this));
        }
        this.pacientes = pacientes;
    }

    public CentroSalud pacientes(Set<Paciente> pacientes) {
        this.setPacientes(pacientes);
        return this;
    }

    public CentroSalud addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.setCentroSalud(this);
        return this;
    }

    public CentroSalud removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.setCentroSalud(null);
        return this;
    }

    public Set<Medico> getMedicos() {
        return this.medicos;
    }

    public void setMedicos(Set<Medico> medicos) {
        if (this.medicos != null) {
            this.medicos.forEach(i -> i.setCentroSalud(null));
        }
        if (medicos != null) {
            medicos.forEach(i -> i.setCentroSalud(this));
        }
        this.medicos = medicos;
    }

    public CentroSalud medicos(Set<Medico> medicos) {
        this.setMedicos(medicos);
        return this;
    }

    public CentroSalud addMedico(Medico medico) {
        this.medicos.add(medico);
        medico.setCentroSalud(this);
        return this;
    }

    public CentroSalud removeMedico(Medico medico) {
        this.medicos.remove(medico);
        medico.setCentroSalud(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CentroSalud)) {
            return false;
        }
        return getId() != null && getId().equals(((CentroSalud) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CentroSalud{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
