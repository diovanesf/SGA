package edu.unipampa.laboratoriovirologia.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link edu.unipampa.laboratoriovirologia.domain.Propriedade} entity.
 */
public class PropriedadeDTO implements Serializable {

    private Long id;

    private String tipo;

    private Integer numeroAnimais;

    private String acometidos;

    private String observacoes;

    private String pricipalSuspeita;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getNumeroAnimais() {
        return numeroAnimais;
    }

    public void setNumeroAnimais(Integer numeroAnimais) {
        this.numeroAnimais = numeroAnimais;
    }

    public String getAcometidos() {
        return acometidos;
    }

    public void setAcometidos(String acometidos) {
        this.acometidos = acometidos;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getPricipalSuspeita() {
        return pricipalSuspeita;
    }

    public void setPricipalSuspeita(String pricipalSuspeita) {
        this.pricipalSuspeita = pricipalSuspeita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PropriedadeDTO)) {
            return false;
        }

        PropriedadeDTO propriedadeDTO = (PropriedadeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, propriedadeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PropriedadeDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", numeroAnimais=" + getNumeroAnimais() +
            ", acometidos='" + getAcometidos() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", pricipalSuspeita='" + getPricipalSuspeita() + "'" +
            "}";
    }
}
