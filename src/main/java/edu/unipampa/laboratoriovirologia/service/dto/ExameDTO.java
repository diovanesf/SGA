package edu.unipampa.laboratoriovirologia.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link edu.unipampa.laboratoriovirologia.domain.Exame} entity.
 */
public class ExameDTO implements Serializable {

    private Long id;

    private String nome;

    private String tipo;

    private String resultado;

    private LocalDate dataTeste;

    private LocalDate dataLeitura;

    @Lob
    private String preenchimentoEspelho;

    @Lob
    private String observacoes;

    private BigDecimal valor;

    private AmostraDTO amostra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public LocalDate getDataTeste() {
        return dataTeste;
    }

    public void setDataTeste(LocalDate dataTeste) {
        this.dataTeste = dataTeste;
    }

    public LocalDate getDataLeitura() {
        return dataLeitura;
    }

    public void setDataLeitura(LocalDate dataLeitura) {
        this.dataLeitura = dataLeitura;
    }

    public String getPreenchimentoEspelho() {
        return preenchimentoEspelho;
    }

    public void setPreenchimentoEspelho(String preenchimentoEspelho) {
        this.preenchimentoEspelho = preenchimentoEspelho;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public AmostraDTO getAmostra() {
        return amostra;
    }

    public void setAmostra(AmostraDTO amostra) {
        this.amostra = amostra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExameDTO)) {
            return false;
        }

        ExameDTO exameDTO = (ExameDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, exameDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExameDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", resultado='" + getResultado() + "'" +
            ", dataTeste='" + getDataTeste() + "'" +
            ", dataLeitura='" + getDataLeitura() + "'" +
            ", preenchimentoEspelho='" + getPreenchimentoEspelho() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", valor=" + getValor() +
            ", amostra=" + getAmostra() +
            "}";
    }
}
