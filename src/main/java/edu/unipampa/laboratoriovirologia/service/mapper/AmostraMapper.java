package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.*;
import edu.unipampa.laboratoriovirologia.service.dto.AmostraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Amostra} and its DTO {@link AmostraDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AmostraMapper extends EntityMapper<AmostraDTO, Amostra> {}
