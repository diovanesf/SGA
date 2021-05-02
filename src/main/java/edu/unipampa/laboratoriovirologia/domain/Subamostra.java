package edu.unipampa.laboratoriovirologia.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Subamostra.
 */
@Entity
@Table(name = "subamostra")
public class Subamostra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sub_amostra")
    private String subAmostra;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subamostra id(Long id) {
        this.id = id;
        return this;
    }

    public String getSubAmostra() {
        return this.subAmostra;
    }

    public Subamostra subAmostra(String subAmostra) {
        this.subAmostra = subAmostra;
        return this;
    }

    public void setSubAmostra(String subAmostra) {
        this.subAmostra = subAmostra;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subamostra)) {
            return false;
        }
        return id != null && id.equals(((Subamostra) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Subamostra{" +
            "id=" + getId() +
            ", subAmostra='" + getSubAmostra() + "'" +
            "}";
    }
}
