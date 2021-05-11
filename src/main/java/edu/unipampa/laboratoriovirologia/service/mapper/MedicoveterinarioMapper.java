package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.*;
import edu.unipampa.laboratoriovirologia.service.dto.MedicoveterinarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Medicoveterinario} and its DTO {@link MedicoveterinarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MedicoveterinarioMapper extends EntityMapper<MedicoveterinarioDTO, Medicoveterinario> {
    @Named("nome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    MedicoveterinarioDTO toDtoNome(Medicoveterinario medicoveterinario);
}
