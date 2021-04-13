package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.*;
import edu.unipampa.laboratoriovirologia.service.dto.PropriedadeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Propriedade} and its DTO {@link PropriedadeDTO}.
 */
@Mapper(componentModel = "spring", uses = { EnderecoMapper.class })
public interface PropriedadeMapper extends EntityMapper<PropriedadeDTO, Propriedade> {
    @Mapping(target = "endereco", source = "endereco", qualifiedByName = "endereco")
    PropriedadeDTO toDto(Propriedade s);

    @Named("tipo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "tipo", source = "tipo")
    PropriedadeDTO toDtoTipo(Propriedade propriedade);
}
