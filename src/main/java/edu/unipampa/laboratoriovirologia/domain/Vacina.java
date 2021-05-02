package edu.unipampa.laboratoriovirologia.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Type;

/**
 * A Vacina.
 */
@Entity
@Table(name = "vacina")
public class Vacina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "peso_material")
    private String pesoMaterial;

    @Column(name = "estimativa_vacinas")
    private String estimativaVacinas;

    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacoes")
    private String observacoes;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vacina id(Long id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public Vacina status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPesoMaterial() {
        return this.pesoMaterial;
    }

    public Vacina pesoMaterial(String pesoMaterial) {
        this.pesoMaterial = pesoMaterial;
        return this;
    }

    public void setPesoMaterial(String pesoMaterial) {
        this.pesoMaterial = pesoMaterial;
    }

    public String getEstimativaVacinas() {
        return this.estimativaVacinas;
    }

    public Vacina estimativaVacinas(String estimativaVacinas) {
        this.estimativaVacinas = estimativaVacinas;
        return this;
    }

    public void setEstimativaVacinas(String estimativaVacinas) {
        this.estimativaVacinas = estimativaVacinas;
    }

    public LocalDate getDataConclusao() {
        return this.dataConclusao;
    }

    public Vacina dataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
        return this;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public Vacina observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vacina)) {
            return false;
        }
        return id != null && id.equals(((Vacina) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vacina{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", pesoMaterial='" + getPesoMaterial() + "'" +
            ", estimativaVacinas='" + getEstimativaVacinas() + "'" +
            ", dataConclusao='" + getDataConclusao() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            "}";
    }
}
