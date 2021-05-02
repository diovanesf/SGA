package edu.unipampa.laboratoriovirologia.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link edu.unipampa.laboratoriovirologia.domain.Subamostra} entity.
 */
public class SubamostraDTO implements Serializable {

    private Long id;

    private String subAmostra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubAmostra() {
        return subAmostra;
    }

    public void setSubAmostra(String subAmostra) {
        this.subAmostra = subAmostra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubamostraDTO)) {
            return false;
        }

        SubamostraDTO subamostraDTO = (SubamostraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subamostraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubamostraDTO{" +
            "id=" + getId() +
            ", subAmostra='" + getSubAmostra() + "'" +
            "}";
    }
}
