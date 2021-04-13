package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.*;
import edu.unipampa.laboratoriovirologia.service.dto.ProprietarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Proprietario} and its DTO {@link ProprietarioDTO}.
 */
@Mapper(componentModel = "spring", uses = { EnderecoMapper.class, PropriedadeMapper.class })
public interface ProprietarioMapper extends EntityMapper<ProprietarioDTO, Proprietario> {
    @Mapping(target = "endereco", source = "endereco", qualifiedByName = "endereco")
    @Mapping(target = "propriedade", source = "propriedade", qualifiedByName = "tipo")
    ProprietarioDTO toDto(Proprietario s);

    @Named("nome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    ProprietarioDTO toDtoNome(Proprietario proprietario);
}
