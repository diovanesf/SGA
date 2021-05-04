package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.*;
import edu.unipampa.laboratoriovirologia.service.dto.VacinaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vacina} and its DTO {@link VacinaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VacinaMapper extends EntityMapper<VacinaDTO, Vacina> {
    @Named("status")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "status", source = "status")
    VacinaDTO toDtoStatus(Vacina vacina);
}
