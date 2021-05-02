package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.*;
import edu.unipampa.laboratoriovirologia.service.dto.SubamostraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subamostra} and its DTO {@link SubamostraDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SubamostraMapper extends EntityMapper<SubamostraDTO, Subamostra> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubamostraDTO toDtoId(Subamostra subamostra);
}
