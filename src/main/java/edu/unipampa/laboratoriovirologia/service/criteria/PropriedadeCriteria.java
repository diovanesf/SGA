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
 * Criteria class for the {@link edu.unipampa.laboratoriovirologia.domain.Propriedade} entity. This class is used
 * in {@link edu.unipampa.laboratoriovirologia.web.rest.PropriedadeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /propriedades?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PropriedadeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tipoPropriedade;

    private IntegerFilter numeroAnimais;

    private StringFilter acometidos;

    private StringFilter pricipalSuspeita;

    private StringFilter tipoCriacao;

    private LongFilter proprietarioId;

    private LongFilter enderecoId;

    public PropriedadeCriteria() {}

    public PropriedadeCriteria(PropriedadeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tipoPropriedade = other.tipoPropriedade == null ? null : other.tipoPropriedade.copy();
        this.numeroAnimais = other.numeroAnimais == null ? null : other.numeroAnimais.copy();
        this.acometidos = other.acometidos == null ? null : other.acometidos.copy();
        this.pricipalSuspeita = other.pricipalSuspeita == null ? null : other.pricipalSuspeita.copy();
        this.tipoCriacao = other.tipoCriacao == null ? null : other.tipoCriacao.copy();
        this.proprietarioId = other.proprietarioId == null ? null : other.proprietarioId.copy();
        this.enderecoId = other.enderecoId == null ? null : other.enderecoId.copy();
    }

    @Override
    public PropriedadeCriteria copy() {
        return new PropriedadeCriteria(this);
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

    public StringFilter getTipoPropriedade() {
        return tipoPropriedade;
    }

    public StringFilter tipoPropriedade() {
        if (tipoPropriedade == null) {
            tipoPropriedade = new StringFilter();
        }
        return tipoPropriedade;
    }

    public void setTipoPropriedade(StringFilter tipoPropriedade) {
        this.tipoPropriedade = tipoPropriedade;
    }

    public IntegerFilter getNumeroAnimais() {
        return numeroAnimais;
    }

    public IntegerFilter numeroAnimais() {
        if (numeroAnimais == null) {
            numeroAnimais = new IntegerFilter();
        }
        return numeroAnimais;
    }

    public void setNumeroAnimais(IntegerFilter numeroAnimais) {
        this.numeroAnimais = numeroAnimais;
    }

    public StringFilter getAcometidos() {
        return acometidos;
    }

    public StringFilter acometidos() {
        if (acometidos == null) {
            acometidos = new StringFilter();
        }
        return acometidos;
    }

    public void setAcometidos(StringFilter acometidos) {
        this.acometidos = acometidos;
    }

    public StringFilter getPricipalSuspeita() {
        return pricipalSuspeita;
    }

    public StringFilter pricipalSuspeita() {
        if (pricipalSuspeita == null) {
            pricipalSuspeita = new StringFilter();
        }
        return pricipalSuspeita;
    }

    public void setPricipalSuspeita(StringFilter pricipalSuspeita) {
        this.pricipalSuspeita = pricipalSuspeita;
    }

    public StringFilter getTipoCriacao() {
        return tipoCriacao;
    }

    public StringFilter tipoCriacao() {
        if (tipoCriacao == null) {
            tipoCriacao = new StringFilter();
        }
        return tipoCriacao;
    }

    public void setTipoCriacao(StringFilter tipoCriacao) {
        this.tipoCriacao = tipoCriacao;
    }

    public LongFilter getProprietarioId() {
        return proprietarioId;
    }

    public LongFilter proprietarioId() {
        if (proprietarioId == null) {
            proprietarioId = new LongFilter();
        }
        return proprietarioId;
    }

    public void setProprietarioId(LongFilter proprietarioId) {
        this.proprietarioId = proprietarioId;
    }

    public LongFilter getEnderecoId() {
        return enderecoId;
    }

    public LongFilter enderecoId() {
        if (enderecoId == null) {
            enderecoId = new LongFilter();
        }
        return enderecoId;
    }

    public void setEnderecoId(LongFilter enderecoId) {
        this.enderecoId = enderecoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PropriedadeCriteria that = (PropriedadeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tipoPropriedade, that.tipoPropriedade) &&
            Objects.equals(numeroAnimais, that.numeroAnimais) &&
            Objects.equals(acometidos, that.acometidos) &&
            Objects.equals(pricipalSuspeita, that.pricipalSuspeita) &&
            Objects.equals(tipoCriacao, that.tipoCriacao) &&
            Objects.equals(proprietarioId, that.proprietarioId) &&
            Objects.equals(enderecoId, that.enderecoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoPropriedade, numeroAnimais, acometidos, pricipalSuspeita, tipoCriacao, proprietarioId, enderecoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PropriedadeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tipoPropriedade != null ? "tipoPropriedade=" + tipoPropriedade + ", " : "") +
            (numeroAnimais != null ? "numeroAnimais=" + numeroAnimais + ", " : "") +
            (acometidos != null ? "acometidos=" + acometidos + ", " : "") +
            (pricipalSuspeita != null ? "pricipalSuspeita=" + pricipalSuspeita + ", " : "") +
            (tipoCriacao != null ? "tipoCriacao=" + tipoCriacao + ", " : "") +
            (proprietarioId != null ? "proprietarioId=" + proprietarioId + ", " : "") +
            (enderecoId != null ? "enderecoId=" + enderecoId + ", " : "") +
            "}";
    }
}
