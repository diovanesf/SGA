package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.service.dto.ProprietarioDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link edu.unipampa.laboratoriovirologia.domain.Proprietario}.
 */
public interface ProprietarioService {
    /**
     * Save a proprietario.
     *
     * @param proprietarioDTO the entity to save.
     * @return the persisted entity.
     */
    ProprietarioDTO save(ProprietarioDTO proprietarioDTO);

    /**
     * Partially updates a proprietario.
     *
     * @param proprietarioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProprietarioDTO> partialUpdate(ProprietarioDTO proprietarioDTO);

    /**
     * Get all the proprietarios.
     *
     * @return the list of entities.
     */
    List<ProprietarioDTO> findAll();

    /**
     * Get the "id" proprietario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProprietarioDTO> findOne(Long id);

    /**
     * Delete the "id" proprietario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
