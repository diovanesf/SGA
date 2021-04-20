package edu.unipampa.laboratoriovirologia.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Proprietario.
 */
@Entity
@Table(name = "proprietario")
public class Proprietario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @Column(name = "enviar_laudo")
    private Boolean enviarLaudo;

    @JsonIgnoreProperties(value = { "endereco" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Propriedade propriedade;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Proprietario id(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return this.nome;
    }

    public Proprietario nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Proprietario telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public Proprietario email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnviarLaudo() {
        return this.enviarLaudo;
    }

    public Proprietario enviarLaudo(Boolean enviarLaudo) {
        this.enviarLaudo = enviarLaudo;
        return this;
    }

    public void setEnviarLaudo(Boolean enviarLaudo) {
        this.enviarLaudo = enviarLaudo;
    }

    public Propriedade getPropriedade() {
        return this.propriedade;
    }

    public Proprietario propriedade(Propriedade propriedade) {
        this.setPropriedade(propriedade);
        return this;
    }

    public void setPropriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proprietario)) {
            return false;
        }
        return id != null && id.equals(((Proprietario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Proprietario{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            ", enviarLaudo='" + getEnviarLaudo() + "'" +
            "}";
    }
}
