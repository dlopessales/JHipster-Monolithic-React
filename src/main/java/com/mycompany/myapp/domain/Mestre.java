package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Mestre.
 */
@Entity
@Table(name = "mestre")
public class Mestre implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @OneToMany(mappedBy = "mestre")
    private Set<Detalhe> detalhes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Mestre descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Detalhe> getDetalhes() {
        return detalhes;
    }

    public Mestre detalhes(Set<Detalhe> detalhes) {
        this.detalhes = detalhes;
        return this;
    }

    public Mestre addDetalhe(Detalhe detalhe) {
        this.detalhes.add(detalhe);
        detalhe.setMestre(this);
        return this;
    }

    public Mestre removeDetalhe(Detalhe detalhe) {
        this.detalhes.remove(detalhe);
        detalhe.setMestre(null);
        return this;
    }

    public void setDetalhes(Set<Detalhe> detalhes) {
        this.detalhes = detalhes;
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
        Mestre mestre = (Mestre) o;
        if (mestre.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mestre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mestre{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
