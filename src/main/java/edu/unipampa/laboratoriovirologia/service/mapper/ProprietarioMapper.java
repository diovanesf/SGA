package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.*;
import edu.unipampa.laboratoriovirologia.service.dto.ProprietarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Proprietario} and its DTO {@link ProprietarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProprietarioMapper extends EntityMapper<ProprietarioDTO, Proprietario> {}
