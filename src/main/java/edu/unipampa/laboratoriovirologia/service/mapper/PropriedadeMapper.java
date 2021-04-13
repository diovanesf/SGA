package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.*;
import edu.unipampa.laboratoriovirologia.service.dto.PropriedadeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Propriedade} and its DTO {@link PropriedadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PropriedadeMapper extends EntityMapper<PropriedadeDTO, Propriedade> {}
