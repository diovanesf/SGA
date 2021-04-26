package edu.unipampa.laboratoriovirologia.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link edu.unipampa.laboratoriovirologia.domain.Proprietario} entity. This class is used
 * in {@link edu.unipampa.laboratoriovirologia.web.rest.ProprietarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /proprietarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProprietarioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter telefone;

    private StringFilter email;

    private BooleanFilter enviarLaudo;

    private LongFilter propriedadeId;

    public ProprietarioCriteria() {}

    public ProprietarioCriteria(ProprietarioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.telefone = other.telefone == null ? null : other.telefone.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.enviarLaudo = other.enviarLaudo == null ? null : other.enviarLaudo.copy();
        this.propriedadeId = other.propriedadeId == null ? null : other.propriedadeId.copy();
    }

    @Override
    public ProprietarioCriteria copy() {
        return new ProprietarioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public StringFilter nome() {
        if (nome == null) {
            nome = new StringFilter();
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getTelefone() {
        return telefone;
    }

    public StringFilter telefone() {
        if (telefone == null) {
            telefone = new StringFilter();
        }
        return telefone;
    }

    public void setTelefone(StringFilter telefone) {
        this.telefone = telefone;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public BooleanFilter getEnviarLaudo() {
        return enviarLaudo;
    }

    public BooleanFilter enviarLaudo() {
        if (enviarLaudo == null) {
            enviarLaudo = new BooleanFilter();
        }
        return enviarLaudo;
    }

    public void setEnviarLaudo(BooleanFilter enviarLaudo) {
        this.enviarLaudo = enviarLaudo;
    }

    public LongFilter getPropriedadeId() {
        return propriedadeId;
    }

    public LongFilter propriedadeId() {
        if (propriedadeId == null) {
            propriedadeId = new LongFilter();
        }
        return propriedadeId;
    }

    public void setPropriedadeId(LongFilter propriedadeId) {
        this.propriedadeId = propriedadeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProprietarioCriteria that = (ProprietarioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(telefone, that.telefone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(enviarLaudo, that.enviarLaudo) &&
            Objects.equals(propriedadeId, that.propriedadeId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, telefone, email, enviarLaudo, propriedadeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProprietarioCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (telefone != null ? "telefone=" + telefone + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (enviarLaudo != null ? "enviarLaudo=" + enviarLaudo + ", " : "") +
            (propriedadeId != null ? "propriedadeId=" + propriedadeId + ", " : "") +
            "}";
    }
}
