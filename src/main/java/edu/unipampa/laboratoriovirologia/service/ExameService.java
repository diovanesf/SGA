package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.service.dto.ExameDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link edu.unipampa.laboratoriovirologia.domain.Exame}.
 */
public interface ExameService {
    /**
     * Save a exame.
     *
     * @param exameDTO the entity to save.
     * @return the persisted entity.
     */
    ExameDTO save(ExameDTO exameDTO);

    /**
     * Partially updates a exame.
     *
     * @param exameDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExameDTO> partialUpdate(ExameDTO exameDTO);

    /**
     * Get all the exames.
     *
     * @return the list of entities.
     */
    List<ExameDTO> findAll();

    /**
     * Get the "id" exame.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExameDTO> findOne(Long id);

    /**
     * Delete the "id" exame.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
