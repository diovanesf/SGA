package edu.unipampa.laboratoriovirologia.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link edu.unipampa.laboratoriovirologia.domain.Amostra} entity.
 */
public class AmostraDTO implements Serializable {

    private Long id;

    private String protocolo;

    private String formaEnvio;

    private Integer numeroAmostras;

    private String especie;

    private String materialRecebido;

    private String acondicionamento;

    private String condicaoMaterial;

    private String status;

    private Set<UserDTO> users = new HashSet<>();

    private ProprietarioDTO proprietario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getFormaEnvio() {
        return formaEnvio;
    }

    public void setFormaEnvio(String formaEnvio) {
        this.formaEnvio = formaEnvio;
    }

    public Integer getNumeroAmostras() {
        return numeroAmostras;
    }

    public void setNumeroAmostras(Integer numeroAmostras) {
        this.numeroAmostras = numeroAmostras;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getMaterialRecebido() {
        return materialRecebido;
    }

    public void setMaterialRecebido(String materialRecebido) {
        this.materialRecebido = materialRecebido;
    }

    public String getAcondicionamento() {
        return acondicionamento;
    }

    public void setAcondicionamento(String acondicionamento) {
        this.acondicionamento = acondicionamento;
    }

    public String getCondicaoMaterial() {
        return condicaoMaterial;
    }

    public void setCondicaoMaterial(String condicaoMaterial) {
        this.condicaoMaterial = condicaoMaterial;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public ProprietarioDTO getProprietario() {
        return proprietario;
    }

    public void setProprietario(ProprietarioDTO proprietario) {
        this.proprietario = proprietario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AmostraDTO)) {
            return false;
        }

        AmostraDTO amostraDTO = (AmostraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, amostraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AmostraDTO{" +
            "id=" + getId() +
            ", protocolo='" + getProtocolo() + "'" +
            ", formaEnvio='" + getFormaEnvio() + "'" +
            ", numeroAmostras=" + getNumeroAmostras() +
            ", especie='" + getEspecie() + "'" +
            ", materialRecebido='" + getMaterialRecebido() + "'" +
            ", acondicionamento='" + getAcondicionamento() + "'" +
            ", condicaoMaterial='" + getCondicaoMaterial() + "'" +
            ", status='" + getStatus() + "'" +
            ", users=" + getUsers() +
            ", proprietario=" + getProprietario() +
            "}";
    }
}
