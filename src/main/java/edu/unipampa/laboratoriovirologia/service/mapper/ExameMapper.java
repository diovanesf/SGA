package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.*;
import edu.unipampa.laboratoriovirologia.service.dto.ExameDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exame} and its DTO {@link ExameDTO}.
 */
@Mapper(componentModel = "spring", uses = { AmostraMapper.class, SubamostraMapper.class })
public interface ExameMapper extends EntityMapper<ExameDTO, Exame> {
    @Mapping(target = "amostra", source = "amostra", qualifiedByName = "protocolo")
    @Mapping(target = "subamostra", source = "subamostra")
    ExameDTO toDto(Exame s);
}
