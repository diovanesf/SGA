package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.service.dto.PropriedadeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link edu.unipampa.laboratoriovirologia.domain.Propriedade}.
 */
public interface PropriedadeService {
    /**
     * Save a propriedade.
     *
     * @param propriedadeDTO the entity to save.
     * @return the persisted entity.
     */
    PropriedadeDTO save(PropriedadeDTO propriedadeDTO);

    /**
     * Partially updates a propriedade.
     *
     * @param propriedadeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PropriedadeDTO> partialUpdate(PropriedadeDTO propriedadeDTO);

    /**
     * Get all the propriedades.
     *
     * @return the list of entities.
     */
    List<PropriedadeDTO> findAll();

    /**
     * Get the "id" propriedade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PropriedadeDTO> findOne(Long id);

    /**
     * Delete the "id" propriedade.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
