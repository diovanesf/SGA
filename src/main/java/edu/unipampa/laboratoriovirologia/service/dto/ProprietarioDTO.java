package edu.unipampa.laboratoriovirologia.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link edu.unipampa.laboratoriovirologia.domain.Proprietario} entity.
 */
public class ProprietarioDTO implements Serializable {

    private Long id;

    private String nome;

    private String telefone;

    private String email;

    private EnderecoDTO endereco;

    private PropriedadeDTO propriedade;

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public PropriedadeDTO getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(PropriedadeDTO propriedade) {
        this.propriedade = propriedade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProprietarioDTO)) {
            return false;
        }

        ProprietarioDTO proprietarioDTO = (ProprietarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, proprietarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProprietarioDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            ", endereco=" + getEndereco() +
            ", propriedade=" + getPropriedade() +
            "}";
    }
}
