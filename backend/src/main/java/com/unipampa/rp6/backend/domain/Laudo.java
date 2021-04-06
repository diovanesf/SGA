package com.unipampa.rp6.backend.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Laudo.
 */
@Entity
@Table(name = "laudo")
public class Laudo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "dados")
    private String dados;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Laudo id(Long id) {
        this.id = id;
        return this;
    }

    public String getDados() {
        return this.dados;
    }

    public Laudo dados(String dados) {
        this.dados = dados;
        return this;
    }

    public void setDados(String dados) {
        this.dados = dados;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Laudo)) {
            return false;
        }
        return id != null && id.equals(((Laudo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Laudo{" +
            "id=" + getId() +
            ", dados='" + getDados() + "'" +
            "}";
    }
}
