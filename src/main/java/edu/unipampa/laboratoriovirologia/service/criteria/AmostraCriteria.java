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
 * Criteria class for the {@link edu.unipampa.laboratoriovirologia.domain.Amostra} entity. This class is used
 * in {@link edu.unipampa.laboratoriovirologia.web.rest.AmostraResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /amostras?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AmostraCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter protocolo;

    private StringFilter formaEnvio;

    private IntegerFilter numeroAmostras;

    private StringFilter especie;

    private IntegerFilter numeroAnimais;

    private StringFilter acometidos;

    private StringFilter pricipalSuspeita;

    private LocalDateFilter dataInicial;

    private StringFilter materialRecebido;

    private StringFilter acondicionamento;

    private StringFilter condicaoMaterial;

    private StringFilter status;

    private StringFilter tipoMedVet;

    private BigDecimalFilter valorTotal;

    private StringFilter tipoPagamento;

    private StringFilter tipo;

    private StringFilter situacao;

    private LongFilter userId;

    private LongFilter midiaId;

    private LongFilter exameId;

    private LongFilter propriedadeId;

    private LongFilter medicoveterinarioId;

    private LongFilter subamostraId;

    private LongFilter vacinaId;

    public AmostraCriteria() {}

    public AmostraCriteria(AmostraCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.protocolo = other.protocolo == null ? null : other.protocolo.copy();
        this.formaEnvio = other.formaEnvio == null ? null : other.formaEnvio.copy();
        this.numeroAmostras = other.numeroAmostras == null ? null : other.numeroAmostras.copy();
        this.especie = other.especie == null ? null : other.especie.copy();
        this.numeroAnimais = other.numeroAnimais == null ? null : other.numeroAnimais.copy();
        this.acometidos = other.acometidos == null ? null : other.acometidos.copy();
        this.pricipalSuspeita = other.pricipalSuspeita == null ? null : other.pricipalSuspeita.copy();
        this.dataInicial = other.dataInicial == null ? null : other.dataInicial.copy();
        this.materialRecebido = other.materialRecebido == null ? null : other.materialRecebido.copy();
        this.acondicionamento = other.acondicionamento == null ? null : other.acondicionamento.copy();
        this.condicaoMaterial = other.condicaoMaterial == null ? null : other.condicaoMaterial.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.tipoMedVet = other.tipoMedVet == null ? null : other.tipoMedVet.copy();
        this.valorTotal = other.valorTotal == null ? null : other.valorTotal.copy();
        this.tipoPagamento = other.tipoPagamento == null ? null : other.tipoPagamento.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.situacao = other.situacao == null ? null : other.situacao.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.midiaId = other.midiaId == null ? null : other.midiaId.copy();
        this.exameId = other.exameId == null ? null : other.exameId.copy();
        this.propriedadeId = other.propriedadeId == null ? null : other.propriedadeId.copy();
        this.medicoveterinarioId = other.medicoveterinarioId == null ? null : other.medicoveterinarioId.copy();
        this.subamostraId = other.subamostraId == null ? null : other.subamostraId.copy();
        this.vacinaId = other.vacinaId == null ? null : other.vacinaId.copy();
    }

    @Override
    public AmostraCriteria copy() {
        return new AmostraCriteria(this);
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

    public StringFilter getProtocolo() {
        return protocolo;
    }

    public StringFilter protocolo() {
        if (protocolo == null) {
            protocolo = new StringFilter();
        }
        return protocolo;
    }

    public void setProtocolo(StringFilter protocolo) {
        this.protocolo = protocolo;
    }

    public StringFilter getFormaEnvio() {
        return formaEnvio;
    }

    public StringFilter formaEnvio() {
        if (formaEnvio == null) {
            formaEnvio = new StringFilter();
        }
        return formaEnvio;
    }

    public void setFormaEnvio(StringFilter formaEnvio) {
        this.formaEnvio = formaEnvio;
    }

    public IntegerFilter getNumeroAmostras() {
        return numeroAmostras;
    }

    public IntegerFilter numeroAmostras() {
        if (numeroAmostras == null) {
            numeroAmostras = new IntegerFilter();
        }
        return numeroAmostras;
    }

    public void setNumeroAmostras(IntegerFilter numeroAmostras) {
        this.numeroAmostras = numeroAmostras;
    }

    public StringFilter getEspecie() {
        return especie;
    }

    public StringFilter especie() {
        if (especie == null) {
            especie = new StringFilter();
        }
        return especie;
    }

    public void setEspecie(StringFilter especie) {
        this.especie = especie;
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

    public LocalDateFilter getDataInicial() {
        return dataInicial;
    }

    public LocalDateFilter dataInicial() {
        if (dataInicial == null) {
            dataInicial = new LocalDateFilter();
        }
        return dataInicial;
    }

    public void setDataInicial(LocalDateFilter dataInicial) {
        this.dataInicial = dataInicial;
    }

    public StringFilter getMaterialRecebido() {
        return materialRecebido;
    }

    public StringFilter materialRecebido() {
        if (materialRecebido == null) {
            materialRecebido = new StringFilter();
        }
        return materialRecebido;
    }

    public void setMaterialRecebido(StringFilter materialRecebido) {
        this.materialRecebido = materialRecebido;
    }

    public StringFilter getAcondicionamento() {
        return acondicionamento;
    }

    public StringFilter acondicionamento() {
        if (acondicionamento == null) {
            acondicionamento = new StringFilter();
        }
        return acondicionamento;
    }

    public void setAcondicionamento(StringFilter acondicionamento) {
        this.acondicionamento = acondicionamento;
    }

    public StringFilter getCondicaoMaterial() {
        return condicaoMaterial;
    }

    public StringFilter condicaoMaterial() {
        if (condicaoMaterial == null) {
            condicaoMaterial = new StringFilter();
        }
        return condicaoMaterial;
    }

    public void setCondicaoMaterial(StringFilter condicaoMaterial) {
        this.condicaoMaterial = condicaoMaterial;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public StringFilter getTipoMedVet() {
        return tipoMedVet;
    }

    public StringFilter tipoMedVet() {
        if (tipoMedVet == null) {
            tipoMedVet = new StringFilter();
        }
        return tipoMedVet;
    }

    public void setTipoMedVet(StringFilter tipoMedVet) {
        this.tipoMedVet = tipoMedVet;
    }

    public BigDecimalFilter getValorTotal() {
        return valorTotal;
    }

    public BigDecimalFilter valorTotal() {
        if (valorTotal == null) {
            valorTotal = new BigDecimalFilter();
        }
        return valorTotal;
    }

    public void setValorTotal(BigDecimalFilter valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StringFilter getTipoPagamento() {
        return tipoPagamento;
    }

    public StringFilter tipoPagamento() {
        if (tipoPagamento == null) {
            tipoPagamento = new StringFilter();
        }
        return tipoPagamento;
    }

    public void setTipoPagamento(StringFilter tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
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

    public StringFilter getSituacao() {
        return situacao;
    }

    public StringFilter situacao() {
        if (situacao == null) {
            situacao = new StringFilter();
        }
        return situacao;
    }

    public void setSituacao(StringFilter situacao) {
        this.situacao = situacao;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getMidiaId() {
        return midiaId;
    }

    public LongFilter midiaId() {
        if (midiaId == null) {
            midiaId = new LongFilter();
        }
        return midiaId;
    }

    public void setMidiaId(LongFilter midiaId) {
        this.midiaId = midiaId;
    }

    public LongFilter getExameId() {
        return exameId;
    }

    public LongFilter exameId() {
        if (exameId == null) {
            exameId = new LongFilter();
        }
        return exameId;
    }

    public void setExameId(LongFilter exameId) {
        this.exameId = exameId;
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

    public LongFilter getMedicoveterinarioId() {
        return medicoveterinarioId;
    }

    public LongFilter medicoveterinarioId() {
        if (medicoveterinarioId == null) {
            medicoveterinarioId = new LongFilter();
        }
        return medicoveterinarioId;
    }

    public void setMedicoveterinarioId(LongFilter medicoveterinarioId) {
        this.medicoveterinarioId = medicoveterinarioId;
    }

    public LongFilter getSubamostraId() {
        return subamostraId;
    }

    public LongFilter subamostraId() {
        if (subamostraId == null) {
            subamostraId = new LongFilter();
        }
        return subamostraId;
    }

    public void setSubamostraId(LongFilter subamostraId) {
        this.subamostraId = subamostraId;
    }

    public LongFilter getVacinaId() {
        return vacinaId;
    }

    public LongFilter vacinaId() {
        if (vacinaId == null) {
            vacinaId = new LongFilter();
        }
        return vacinaId;
    }

    public void setVacinaId(LongFilter vacinaId) {
        this.vacinaId = vacinaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AmostraCriteria that = (AmostraCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(protocolo, that.protocolo) &&
            Objects.equals(formaEnvio, that.formaEnvio) &&
            Objects.equals(numeroAmostras, that.numeroAmostras) &&
            Objects.equals(especie, that.especie) &&
            Objects.equals(numeroAnimais, that.numeroAnimais) &&
            Objects.equals(acometidos, that.acometidos) &&
            Objects.equals(pricipalSuspeita, that.pricipalSuspeita) &&
            Objects.equals(dataInicial, that.dataInicial) &&
            Objects.equals(materialRecebido, that.materialRecebido) &&
            Objects.equals(acondicionamento, that.acondicionamento) &&
            Objects.equals(condicaoMaterial, that.condicaoMaterial) &&
            Objects.equals(status, that.status) &&
            Objects.equals(tipoMedVet, that.tipoMedVet) &&
            Objects.equals(valorTotal, that.valorTotal) &&
            Objects.equals(tipoPagamento, that.tipoPagamento) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(situacao, that.situacao) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(midiaId, that.midiaId) &&
            Objects.equals(exameId, that.exameId) &&
            Objects.equals(propriedadeId, that.propriedadeId) &&
            Objects.equals(medicoveterinarioId, that.medicoveterinarioId) &&
            Objects.equals(subamostraId, that.subamostraId) &&
            Objects.equals(vacinaId, that.vacinaId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            protocolo,
            formaEnvio,
            numeroAmostras,
            especie,
            numeroAnimais,
            acometidos,
            pricipalSuspeita,
            dataInicial,
            materialRecebido,
            acondicionamento,
            condicaoMaterial,
            status,
            tipoMedVet,
            valorTotal,
            tipoPagamento,
            tipo,
            situacao,
            userId,
            midiaId,
            exameId,
            propriedadeId,
            medicoveterinarioId,
            subamostraId,
            vacinaId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AmostraCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (protocolo != null ? "protocolo=" + protocolo + ", " : "") +
            (formaEnvio != null ? "formaEnvio=" + formaEnvio + ", " : "") +
            (numeroAmostras != null ? "numeroAmostras=" + numeroAmostras + ", " : "") +
            (especie != null ? "especie=" + especie + ", " : "") +
            (numeroAnimais != null ? "numeroAnimais=" + numeroAnimais + ", " : "") +
            (acometidos != null ? "acometidos=" + acometidos + ", " : "") +
            (pricipalSuspeita != null ? "pricipalSuspeita=" + pricipalSuspeita + ", " : "") +
            (dataInicial != null ? "dataInicial=" + dataInicial + ", " : "") +
            (materialRecebido != null ? "materialRecebido=" + materialRecebido + ", " : "") +
            (acondicionamento != null ? "acondicionamento=" + acondicionamento + ", " : "") +
            (condicaoMaterial != null ? "condicaoMaterial=" + condicaoMaterial + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (tipoMedVet != null ? "tipoMedVet=" + tipoMedVet + ", " : "") +
            (valorTotal != null ? "valorTotal=" + valorTotal + ", " : "") +
            (tipoPagamento != null ? "tipoPagamento=" + tipoPagamento + ", " : "") +
            (tipo != null ? "tipo=" + tipo + ", " : "") +
            (situacao != null ? "situacao=" + situacao + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (midiaId != null ? "midiaId=" + midiaId + ", " : "") +
            (exameId != null ? "exameId=" + exameId + ", " : "") +
            (propriedadeId != null ? "propriedadeId=" + propriedadeId + ", " : "") +
            (medicoveterinarioId != null ? "medicoveterinarioId=" + medicoveterinarioId + ", " : "") +
            (subamostraId != null ? "subamostraId=" + subamostraId + ", " : "") +
            (vacinaId != null ? "vacinaId=" + vacinaId + ", " : "") +
            "}";
    }
}
