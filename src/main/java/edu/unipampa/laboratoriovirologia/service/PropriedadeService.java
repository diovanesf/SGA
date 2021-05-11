package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.domain.Propriedade;
import edu.unipampa.laboratoriovirologia.repository.PropriedadeRepository;
import edu.unipampa.laboratoriovirologia.service.dto.PropriedadeDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.PropriedadeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Propriedade}.
 */
@Service
@Transactional
public class PropriedadeService {

    private final Logger log = LoggerFactory.getLogger(PropriedadeService.class);

    private final PropriedadeRepository propriedadeRepository;

    private final PropriedadeMapper propriedadeMapper;

    public PropriedadeService(PropriedadeRepository propriedadeRepository, PropriedadeMapper propriedadeMapper) {
        this.propriedadeRepository = propriedadeRepository;
        this.propriedadeMapper = propriedadeMapper;
    }

    /**
     * Save a propriedade.
     *
     * @param propriedadeDTO the entity to save.
     * @return the persisted entity.
     */
    public PropriedadeDTO save(PropriedadeDTO propriedadeDTO) {
        log.debug("Request to save Propriedade : {}", propriedadeDTO);
        Propriedade propriedade = propriedadeMapper.toEntity(propriedadeDTO);
        propriedade = propriedadeRepository.save(propriedade);
        return propriedadeMapper.toDto(propriedade);
    }

    /**
     * Partially update a propriedade.
     *
     * @param propriedadeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PropriedadeDTO> partialUpdate(PropriedadeDTO propriedadeDTO) {
        log.debug("Request to partially update Propriedade : {}", propriedadeDTO);

        return propriedadeRepository
            .findById(propriedadeDTO.getId())
            .map(
                existingPropriedade -> {
                    propriedadeMapper.partialUpdate(existingPropriedade, propriedadeDTO);
                    return existingPropriedade;
                }
            )
            .map(propriedadeRepository::save)
            .map(propriedadeMapper::toDto);
    }

    /**
     * Get all the propriedades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PropriedadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Propriedades");
        return propriedadeRepository.findAll(pageable).map(propriedadeMapper::toDto);
    }

    /**
     * Get one propriedade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PropriedadeDTO> findOne(Long id) {
        log.debug("Request to get Propriedade : {}", id);
        return propriedadeRepository.findById(id).map(propriedadeMapper::toDto);
    }

    /**
     * Delete the propriedade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Propriedade : {}", id);
        propriedadeRepository.deleteById(id);
    }
}
