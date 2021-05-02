package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.domain.Subamostra;
import edu.unipampa.laboratoriovirologia.repository.SubamostraRepository;
import edu.unipampa.laboratoriovirologia.service.dto.SubamostraDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.SubamostraMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Subamostra}.
 */
@Service
@Transactional
public class SubamostraService {

    private final Logger log = LoggerFactory.getLogger(SubamostraService.class);

    private final SubamostraRepository subamostraRepository;

    private final SubamostraMapper subamostraMapper;

    public SubamostraService(SubamostraRepository subamostraRepository, SubamostraMapper subamostraMapper) {
        this.subamostraRepository = subamostraRepository;
        this.subamostraMapper = subamostraMapper;
    }

    /**
     * Save a subamostra.
     *
     * @param subamostraDTO the entity to save.
     * @return the persisted entity.
     */
    public SubamostraDTO save(SubamostraDTO subamostraDTO) {
        log.debug("Request to save Subamostra : {}", subamostraDTO);
        Subamostra subamostra = subamostraMapper.toEntity(subamostraDTO);
        subamostra = subamostraRepository.save(subamostra);
        return subamostraMapper.toDto(subamostra);
    }

    /**
     * Partially update a subamostra.
     *
     * @param subamostraDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubamostraDTO> partialUpdate(SubamostraDTO subamostraDTO) {
        log.debug("Request to partially update Subamostra : {}", subamostraDTO);

        return subamostraRepository
            .findById(subamostraDTO.getId())
            .map(
                existingSubamostra -> {
                    subamostraMapper.partialUpdate(existingSubamostra, subamostraDTO);
                    return existingSubamostra;
                }
            )
            .map(subamostraRepository::save)
            .map(subamostraMapper::toDto);
    }

    /**
     * Get all the subamostras.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubamostraDTO> findAll() {
        log.debug("Request to get all Subamostras");
        return subamostraRepository.findAll().stream().map(subamostraMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one subamostra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubamostraDTO> findOne(Long id) {
        log.debug("Request to get Subamostra : {}", id);
        return subamostraRepository.findById(id).map(subamostraMapper::toDto);
    }

    /**
     * Delete the subamostra by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Subamostra : {}", id);
        subamostraRepository.deleteById(id);
    }
}
