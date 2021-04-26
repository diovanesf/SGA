package edu.unipampa.laboratoriovirologia.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link edu.unipampa.laboratoriovirologia.domain.Exame} entity. This class is used
 * in {@link edu.unipampa.laboratoriovirologia.web.rest.ExameResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /exames?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExameCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter tipo;

    private StringFilter pesoMaterial;

    private StringFilter estimativaVacinas;

    private StringFilter resultado;

    private LocalDateFilter dataTeste;

    private LocalDateFilter dataLeitura;

    private BigDecimalFilter valor;

    private LongFilter amostraId;

    public ExameCriteria() {}

    public ExameCriteria(ExameCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.pesoMaterial = other.pesoMaterial == null ? null : other.pesoMaterial.copy();
        this.estimativaVacinas = other.estimativaVacinas == null ? null : other.estimativaVacinas.copy();
        this.resultado = other.resultado == null ? null : other.resultado.copy();
        this.dataTeste = other.dataTeste == null ? null : other.dataTeste.copy();
        this.dataLeitura = other.dataLeitura == null ? null : other.dataLeitura.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.amostraId = other.amostraId == null ? null : other.amostraId.copy();
    }

    @Override
    public ExameCriteria copy() {
        return new ExameCriteria(this);
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

    public StringFilter getTipo() {
        return tipo;
    }

    public StringFilter tipo() {
        if (tipo == null) {
            tipo = new StringFilter();
        }
        return tipo;
    }

    public void setTipo(StringFilter tipo) {
        this.tipo = tipo;
    }

    public StringFilter getPesoMaterial() {
        return pesoMaterial;
    }

    public StringFilter pesoMaterial() {
        if (pesoMaterial == null) {
            pesoMaterial = new StringFilter();
        }
        return pesoMaterial;
    }

    public void setPesoMaterial(StringFilter pesoMaterial) {
        this.pesoMaterial = pesoMaterial;
    }

    public StringFilter getEstimativaVacinas() {
        return estimativaVacinas;
    }

    public StringFilter estimativaVacinas() {
        if (estimativaVacinas == null) {
            estimativaVacinas = new StringFilter();
        }
        return estimativaVacinas;
    }

    public void setEstimativaVacinas(StringFilter estimativaVacinas) {
        this.estimativaVacinas = estimativaVacinas;
    }

    public StringFilter getResultado() {
        return resultado;
    }

    public StringFilter resultado() {
        if (resultado == null) {
            resultado = new StringFilter();
        }
        return resultado;
    }

    public void setResultado(StringFilter resultado) {
        this.resultado = resultado;
    }

    public LocalDateFilter getDataTeste() {
        return dataTeste;
    }

    public LocalDateFilter dataTeste() {
        if (dataTeste == null) {
            dataTeste = new LocalDateFilter();
        }
        return dataTeste;
    }

    public void setDataTeste(LocalDateFilter dataTeste) {
        this.dataTeste = dataTeste;
    }

    public LocalDateFilter getDataLeitura() {
        return dataLeitura;
    }

    public LocalDateFilter dataLeitura() {
        if (dataLeitura == null) {
            dataLeitura = new LocalDateFilter();
        }
        return dataLeitura;
    }

    public void setDataLeitura(LocalDateFilter dataLeitura) {
        this.dataLeitura = dataLeitura;
    }

    public BigDecimalFilter getValor() {
        return valor;
    }

    public BigDecimalFilter valor() {
        if (valor == null) {
            valor = new BigDecimalFilter();
        }
        return valor;
    }

    public void setValor(BigDecimalFilter valor) {
        this.valor = valor;
    }

    public LongFilter getAmostraId() {
        return amostraId;
    }

    public LongFilter amostraId() {
        if (amostraId == null) {
            amostraId = new LongFilter();
        }
        return amostraId;
    }

    public void setAmostraId(LongFilter amostraId) {
        this.amostraId = amostraId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExameCriteria that = (ExameCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(pesoMaterial, that.pesoMaterial) &&
            Objects.equals(estimativaVacinas, that.estimativaVacinas) &&
            Objects.equals(resultado, that.resultado) &&
            Objects.equals(dataTeste, that.dataTeste) &&
            Objects.equals(dataLeitura, that.dataLeitura) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(amostraId, that.amostraId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, tipo, pesoMaterial, estimativaVacinas, resultado, dataTeste, dataLeitura, valor, amostraId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExameCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (tipo != null ? "tipo=" + tipo + ", " : "") +
            (pesoMaterial != null ? "pesoMaterial=" + pesoMaterial + ", " : "") +
            (estimativaVacinas != null ? "estimativaVacinas=" + estimativaVacinas + ", " : "") +
            (resultado != null ? "resultado=" + resultado + ", " : "") +
            (dataTeste != null ? "dataTeste=" + dataTeste + ", " : "") +
            (dataLeitura != null ? "dataLeitura=" + dataLeitura + ", " : "") +
            (valor != null ? "valor=" + valor + ", " : "") +
            (amostraId != null ? "amostraId=" + amostraId + ", " : "") +
            "}";
    }
}
