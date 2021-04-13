package edu.unipampa.laboratoriovirologia.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link edu.unipampa.laboratoriovirologia.domain.Midia} entity.
 */
public class MidiaDTO implements Serializable {

    private Long id;

    private String nome;

    private String descricao;

    @Lob
    private byte[] file;

    private String fileContentType;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
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
        if (!(o instanceof MidiaDTO)) {
            return false;
        }

        MidiaDTO midiaDTO = (MidiaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, midiaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MidiaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", file='" + getFile() + "'" +
            ", amostra=" + getAmostra() +
            "}";
    }
}
