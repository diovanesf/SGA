package edu.unipampa.laboratoriovirologia.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Propriedade.
 */
@Entity
@Table(name = "propriedade")
public class Propriedade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "numero_animais")
    private Integer numeroAnimais;

    @Column(name = "acometidos")
    private String acometidos;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "pricipal_suspeita")
    private String pricipalSuspeita;

    @OneToOne
    @JoinColumn(unique = true)
    private Endereco endereco;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Propriedade id(Long id) {
        this.id = id;
        return this;
    }

    public String getTipo() {
        return this.tipo;
    }

    public Propriedade tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getNumeroAnimais() {
        return this.numeroAnimais;
    }

    public Propriedade numeroAnimais(Integer numeroAnimais) {
        this.numeroAnimais = numeroAnimais;
        return this;
    }

    public void setNumeroAnimais(Integer numeroAnimais) {
        this.numeroAnimais = numeroAnimais;
    }

    public String getAcometidos() {
        return this.acometidos;
    }

    public Propriedade acometidos(String acometidos) {
        this.acometidos = acometidos;
        return this;
    }

    public void setAcometidos(String acometidos) {
        this.acometidos = acometidos;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public Propriedade observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getPricipalSuspeita() {
        return this.pricipalSuspeita;
    }

    public Propriedade pricipalSuspeita(String pricipalSuspeita) {
        this.pricipalSuspeita = pricipalSuspeita;
        return this;
    }

    public void setPricipalSuspeita(String pricipalSuspeita) {
        this.pricipalSuspeita = pricipalSuspeita;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public Propriedade endereco(Endereco endereco) {
        this.setEndereco(endereco);
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Propriedade)) {
            return false;
        }
        return id != null && id.equals(((Propriedade) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Propriedade{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", numeroAnimais=" + getNumeroAnimais() +
            ", acometidos='" + getAcometidos() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", pricipalSuspeita='" + getPricipalSuspeita() + "'" +
            "}";
    }
}
