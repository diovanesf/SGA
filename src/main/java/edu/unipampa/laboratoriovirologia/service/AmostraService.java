package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.service.dto.AmostraDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link edu.unipampa.laboratoriovirologia.domain.Amostra}.
 */
public interface AmostraService {
    /**
     * Save a amostra.
     *
     * @param amostraDTO the entity to save.
     * @return the persisted entity.
     */
    AmostraDTO save(AmostraDTO amostraDTO);

    /**
     * Partially updates a amostra.
     *
     * @param amostraDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AmostraDTO> partialUpdate(AmostraDTO amostraDTO);

    /**
     * Get all the amostras.
     *
     * @return the list of entities.
     */
    List<AmostraDTO> findAll();

    /**
     * Get the "id" amostra.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AmostraDTO> findOne(Long id);

    /**
     * Delete the "id" amostra.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
