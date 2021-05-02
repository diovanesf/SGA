package edu.unipampa.laboratoriovirologia.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link edu.unipampa.laboratoriovirologia.domain.Vacina} entity.
 */
public class VacinaDTO implements Serializable {

    private Long id;

    private String status;

    private String pesoMaterial;

    private String estimativaVacinas;

    private LocalDate dataConclusao;

    @Lob
    private String observacoes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPesoMaterial() {
        return pesoMaterial;
    }

    public void setPesoMaterial(String pesoMaterial) {
        this.pesoMaterial = pesoMaterial;
    }

    public String getEstimativaVacinas() {
        return estimativaVacinas;
    }

    public void setEstimativaVacinas(String estimativaVacinas) {
        this.estimativaVacinas = estimativaVacinas;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VacinaDTO)) {
            return false;
        }

        VacinaDTO vacinaDTO = (VacinaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vacinaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VacinaDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", pesoMaterial='" + getPesoMaterial() + "'" +
            ", estimativaVacinas='" + getEstimativaVacinas() + "'" +
            ", dataConclusao='" + getDataConclusao() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            "}";
    }
}
