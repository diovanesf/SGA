package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.*;
import edu.unipampa.laboratoriovirologia.service.dto.MidiaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Midia} and its DTO {@link MidiaDTO}.
 */
@Mapper(componentModel = "spring", uses = { AmostraMapper.class })
public interface MidiaMapper extends EntityMapper<MidiaDTO, Midia> {
    @Mapping(target = "amostra", source = "amostra", qualifiedByName = "protocolo")
    MidiaDTO toDto(Midia s);
}
