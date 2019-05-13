package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Tipo;

/**
 * A Detalhe.
 */
@Entity
@Table(name = "detalhe")
public class Detalhe implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data")
    private Instant data;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private Tipo tipo;

    @ManyToOne
    @JsonIgnoreProperties("detalhes")
    private Mestre mestre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Detalhe nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Instant getData() {
        return data;
    }

    public Detalhe data(Instant data) {
        this.data = data;
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Detalhe tipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Mestre getMestre() {
        return mestre;
    }

    public Detalhe mestre(Mestre mestre) {
        this.mestre = mestre;
        return this;
    }

    public void setMestre(Mestre mestre) {
        this.mestre = mestre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Detalhe detalhe = (Detalhe) o;
        if (detalhe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detalhe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Detalhe{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", data='" + getData() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
