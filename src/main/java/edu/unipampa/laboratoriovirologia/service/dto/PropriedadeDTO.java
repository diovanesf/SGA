package edu.unipampa.laboratoriovirologia.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link edu.unipampa.laboratoriovirologia.domain.Propriedade} entity.
 */
public class PropriedadeDTO implements Serializable {

    private Long id;

    private String tipoPropriedade;

    private String tipoCriação;

    private Integer numeroAnimais;

    private String acometidos;

    @Lob
    private String observacoes;

    private String pricipalSuspeita;

    private EnderecoDTO endereco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoPropriedade() {
        return tipoPropriedade;
    }

    public void setTipoPropriedade(String tipoPropriedade) {
        this.tipoPropriedade = tipoPropriedade;
    }

    public String getTipoCriação() {
        return tipoCriação;
    }

    public void setTipoCriação(String tipoCriação) {
        this.tipoCriação = tipoCriação;
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

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
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
            ", tipoPropriedade='" + getTipoPropriedade() + "'" +
            ", tipoCriação='" + getTipoCriação() + "'" +
            ", numeroAnimais=" + getNumeroAnimais() +
            ", acometidos='" + getAcometidos() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", pricipalSuspeita='" + getPricipalSuspeita() + "'" +
            ", endereco=" + getEndereco() +
            "}";
    }
}
