package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.domain.Amostra;
import edu.unipampa.laboratoriovirologia.repository.AmostraRepository;
import edu.unipampa.laboratoriovirologia.service.dto.AmostraDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.AmostraMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Amostra}.
 */
@Service
@Transactional
public class AmostraService {

    private final Logger log = LoggerFactory.getLogger(AmostraService.class);

    private final AmostraRepository amostraRepository;

    private final AmostraMapper amostraMapper;

    public AmostraService(AmostraRepository amostraRepository, AmostraMapper amostraMapper) {
        this.amostraRepository = amostraRepository;
        this.amostraMapper = amostraMapper;
    }

    /**
     * Save a amostra.
     *
     * @param amostraDTO the entity to save.
     * @return the persisted entity.
     */
    public AmostraDTO save(AmostraDTO amostraDTO) {
        log.debug("Request to save Amostra : {}", amostraDTO);
        Amostra amostra = amostraMapper.toEntity(amostraDTO);
        amostra = amostraRepository.save(amostra);
        return amostraMapper.toDto(amostra);
    }

    /**
     * Partially update a amostra.
     *
     * @param amostraDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AmostraDTO> partialUpdate(AmostraDTO amostraDTO) {
        log.debug("Request to partially update Amostra : {}", amostraDTO);

        return amostraRepository
            .findById(amostraDTO.getId())
            .map(
                existingAmostra -> {
                    amostraMapper.partialUpdate(existingAmostra, amostraDTO);
                    return existingAmostra;
                }
            )
            .map(amostraRepository::save)
            .map(amostraMapper::toDto);
    }

    /**
     * Get all the amostras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AmostraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Amostras");
        return amostraRepository.findAll(pageable).map(amostraMapper::toDto);
    }

    /**
     * Get all the amostras with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AmostraDTO> findAllWithEagerRelationships(Pageable pageable) {
        return amostraRepository.findAllWithEagerRelationships(pageable).map(amostraMapper::toDto);
    }

    /**
     * Get one amostra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AmostraDTO> findOne(Long id) {
        log.debug("Request to get Amostra : {}", id);
        return amostraRepository.findOneWithEagerRelationships(id).map(amostraMapper::toDto);
    }

    /**
     * Delete the amostra by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Amostra : {}", id);
        amostraRepository.deleteById(id);
    }
}
