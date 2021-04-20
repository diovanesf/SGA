package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.domain.Exame;
import edu.unipampa.laboratoriovirologia.repository.ExameRepository;
import edu.unipampa.laboratoriovirologia.service.dto.ExameDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.ExameMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Exame}.
 */
@Service
@Transactional
public class ExameService {

    private final Logger log = LoggerFactory.getLogger(ExameService.class);

    private final ExameRepository exameRepository;

    private final ExameMapper exameMapper;

    public ExameService(ExameRepository exameRepository, ExameMapper exameMapper) {
        this.exameRepository = exameRepository;
        this.exameMapper = exameMapper;
    }

    /**
     * Save a exame.
     *
     * @param exameDTO the entity to save.
     * @return the persisted entity.
     */
    public ExameDTO save(ExameDTO exameDTO) {
        log.debug("Request to save Exame : {}", exameDTO);
        Exame exame = exameMapper.toEntity(exameDTO);
        exame = exameRepository.save(exame);
        return exameMapper.toDto(exame);
    }

    /**
     * Partially update a exame.
     *
     * @param exameDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ExameDTO> partialUpdate(ExameDTO exameDTO) {
        log.debug("Request to partially update Exame : {}", exameDTO);

        return exameRepository
            .findById(exameDTO.getId())
            .map(
                existingExame -> {
                    exameMapper.partialUpdate(existingExame, exameDTO);
                    return existingExame;
                }
            )
            .map(exameRepository::save)
            .map(exameMapper::toDto);
    }

    /**
     * Get all the exames.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ExameDTO> findAll() {
        log.debug("Request to get all Exames");
        return exameRepository.findAll().stream().map(exameMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one exame by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExameDTO> findOne(Long id) {
        log.debug("Request to get Exame : {}", id);
        return exameRepository.findById(id).map(exameMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<ExameDTO> findExamesByAmostraId(Long amostraId) {
        return exameRepository.findByAmostraId(amostraId).stream().map(exameMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Delete the exame by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Exame : {}", id);
        exameRepository.deleteById(id);
    }
}
