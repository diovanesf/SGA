package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.domain.Midia;
import edu.unipampa.laboratoriovirologia.repository.MidiaRepository;
import edu.unipampa.laboratoriovirologia.service.dto.MidiaDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.MidiaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Midia}.
 */
@Service
@Transactional
public class MidiaService {

    private final Logger log = LoggerFactory.getLogger(MidiaService.class);

    private final MidiaRepository midiaRepository;

    private final MidiaMapper midiaMapper;

    public MidiaService(MidiaRepository midiaRepository, MidiaMapper midiaMapper) {
        this.midiaRepository = midiaRepository;
        this.midiaMapper = midiaMapper;
    }

    /**
     * Save a midia.
     *
     * @param midiaDTO the entity to save.
     * @return the persisted entity.
     */
    public MidiaDTO save(MidiaDTO midiaDTO) {
        log.debug("Request to save Midia : {}", midiaDTO);
        Midia midia = midiaMapper.toEntity(midiaDTO);
        midia = midiaRepository.save(midia);
        return midiaMapper.toDto(midia);
    }

    /**
     * Partially update a midia.
     *
     * @param midiaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MidiaDTO> partialUpdate(MidiaDTO midiaDTO) {
        log.debug("Request to partially update Midia : {}", midiaDTO);

        return midiaRepository
            .findById(midiaDTO.getId())
            .map(
                existingMidia -> {
                    midiaMapper.partialUpdate(existingMidia, midiaDTO);
                    return existingMidia;
                }
            )
            .map(midiaRepository::save)
            .map(midiaMapper::toDto);
    }

    /**
     * Get all the midias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MidiaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Midias");
        return midiaRepository.findAll(pageable).map(midiaMapper::toDto);
    }

    /**
     * Get one midia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MidiaDTO> findOne(Long id) {
        log.debug("Request to get Midia : {}", id);
        return midiaRepository.findById(id).map(midiaMapper::toDto);
    }

    /**
     * Delete the midia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Midia : {}", id);
        midiaRepository.deleteById(id);
    }
}
