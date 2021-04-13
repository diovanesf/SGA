package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.service.dto.MidiaDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link edu.unipampa.laboratoriovirologia.domain.Midia}.
 */
public interface MidiaService {
    /**
     * Save a midia.
     *
     * @param midiaDTO the entity to save.
     * @return the persisted entity.
     */
    MidiaDTO save(MidiaDTO midiaDTO);

    /**
     * Partially updates a midia.
     *
     * @param midiaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MidiaDTO> partialUpdate(MidiaDTO midiaDTO);

    /**
     * Get all the midias.
     *
     * @return the list of entities.
     */
    List<MidiaDTO> findAll();

    /**
     * Get the "id" midia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MidiaDTO> findOne(Long id);

    /**
     * Delete the "id" midia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
