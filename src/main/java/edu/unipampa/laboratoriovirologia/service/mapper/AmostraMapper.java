package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.*;
import edu.unipampa.laboratoriovirologia.service.dto.AmostraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Amostra} and its DTO {@link AmostraDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { UserMapper.class, PropriedadeMapper.class, MedicoveterinarioMapper.class, SubamostraMapper.class, VacinaMapper.class }
)
public interface AmostraMapper extends EntityMapper<AmostraDTO, Amostra> {
    @Mapping(target = "users", source = "users", qualifiedByName = "loginSet")
    @Mapping(target = "propriedade", source = "propriedade", qualifiedByName = "tipoPropriedade")
    @Mapping(target = "medicoveterinario", source = "medicoveterinario", qualifiedByName = "nome")
    @Mapping(target = "subamostra", source = "subamostra", qualifiedByName = "subamostra")
    @Mapping(target = "vacina", source = "vacina", qualifiedByName = "status")
    AmostraDTO toDto(Amostra s);

    @Mapping(target = "removeUser", ignore = true)
    Amostra toEntity(AmostraDTO amostraDTO);

    @Named("protocolo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "protocolo", source = "protocolo")
    AmostraDTO toDtoProtocolo(Amostra amostra);
}
