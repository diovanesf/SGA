package edu.unipampa.laboratoriovirologia.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link edu.unipampa.laboratoriovirologia.domain.Propriedade} entity.
 */
public class PropriedadeDTO implements Serializable {

    private Long id;

    private String tipoPropriedade;

    private String tipoCriacao;

    private ProprietarioDTO proprietario;

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

    public String getTipoCriacao() {
        return tipoCriacao;
    }

    public void setTipoCriacao(String tipoCriacao) {
        this.tipoCriacao = tipoCriacao;
    }

    public ProprietarioDTO getProprietario() {
        return proprietario;
    }

    public void setProprietario(ProprietarioDTO proprietario) {
        this.proprietario = proprietario;
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
            ", tipoCriacao='" + getTipoCriacao() + "'" +
            ", proprietario=" + getProprietario() +
            ", endereco=" + getEndereco() +
            "}";
    }
}
