package edu.unipampa.laboratoriovirologia.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Column(name = "tipo_propriedade")
    private String tipoPropriedade;

    @Column(name = "tipo_criacao")
    private String tipoCriacao;

    @ManyToOne
    @JsonIgnoreProperties(value = { "propriedades" }, allowSetters = true)
    private Proprietario proprietario;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
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

    public String getTipoPropriedade() {
        return this.tipoPropriedade;
    }

    public Propriedade tipoPropriedade(String tipoPropriedade) {
        this.tipoPropriedade = tipoPropriedade;
        return this;
    }

    public void setTipoPropriedade(String tipoPropriedade) {
        this.tipoPropriedade = tipoPropriedade;
    }

    public String getTipoCriacao() {
        return this.tipoCriacao;
    }

    public Propriedade tipoCriacao(String tipoCriacao) {
        this.tipoCriacao = tipoCriacao;
        return this;
    }

    public void setTipoCriacao(String tipoCriacao) {
        this.tipoCriacao = tipoCriacao;
    }

    public Proprietario getProprietario() {
        return this.proprietario;
    }

    public Propriedade proprietario(Proprietario proprietario) {
        this.setProprietario(proprietario);
        return this;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
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
            ", tipoPropriedade='" + getTipoPropriedade() + "'" +
            ", tipoCriacao='" + getTipoCriacao() + "'" +
            "}";
    }
}
