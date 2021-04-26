package edu.unipampa.laboratoriovirologia.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Medicoveterinario.
 */
@Entity
@Table(name = "medicoveterinario")
public class Medicoveterinario implements Serializable {

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

    @Column(name = "crmv")
    private String crmv;

    @Column(name = "enviar_laudo")
    private Boolean enviarLaudo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medicoveterinario id(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return this.nome;
    }

    public Medicoveterinario nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Medicoveterinario telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public Medicoveterinario email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCrmv() {
        return this.crmv;
    }

    public Medicoveterinario crmv(String crmv) {
        this.crmv = crmv;
        return this;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public Boolean getEnviarLaudo() {
        return this.enviarLaudo;
    }

    public Medicoveterinario enviarLaudo(Boolean enviarLaudo) {
        this.enviarLaudo = enviarLaudo;
        return this;
    }

    public void setEnviarLaudo(Boolean enviarLaudo) {
        this.enviarLaudo = enviarLaudo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medicoveterinario)) {
            return false;
        }
        return id != null && id.equals(((Medicoveterinario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Medicoveterinario{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            ", crmv='" + getCrmv() + "'" +
            ", enviarLaudo='" + getEnviarLaudo() + "'" +
            "}";
    }
}
