package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.domain.Vacina;
import edu.unipampa.laboratoriovirologia.repository.VacinaRepository;
import edu.unipampa.laboratoriovirologia.service.dto.VacinaDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.VacinaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Vacina}.
 */
@Service
@Transactional
public class VacinaService {

    private final Logger log = LoggerFactory.getLogger(VacinaService.class);

    private final VacinaRepository vacinaRepository;

    private final VacinaMapper vacinaMapper;

    public VacinaService(VacinaRepository vacinaRepository, VacinaMapper vacinaMapper) {
        this.vacinaRepository = vacinaRepository;
        this.vacinaMapper = vacinaMapper;
    }

    /**
     * Save a vacina.
     *
     * @param vacinaDTO the entity to save.
     * @return the persisted entity.
     */
    public VacinaDTO save(VacinaDTO vacinaDTO) {
        log.debug("Request to save Vacina : {}", vacinaDTO);
        Vacina vacina = vacinaMapper.toEntity(vacinaDTO);
        vacina = vacinaRepository.save(vacina);
        return vacinaMapper.toDto(vacina);
    }

    /**
     * Partially update a vacina.
     *
     * @param vacinaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VacinaDTO> partialUpdate(VacinaDTO vacinaDTO) {
        log.debug("Request to partially update Vacina : {}", vacinaDTO);

        return vacinaRepository
            .findById(vacinaDTO.getId())
            .map(
                existingVacina -> {
                    vacinaMapper.partialUpdate(existingVacina, vacinaDTO);
                    return existingVacina;
                }
            )
            .map(vacinaRepository::save)
            .map(vacinaMapper::toDto);
    }

    /**
     * Get all the vacinas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VacinaDTO> findAll() {
        log.debug("Request to get all Vacinas");
        return vacinaRepository.findAll().stream().map(vacinaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one vacina by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VacinaDTO> findOne(Long id) {
        log.debug("Request to get Vacina : {}", id);
        return vacinaRepository.findById(id).map(vacinaMapper::toDto);
    }

    /**
     * Delete the vacina by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Vacina : {}", id);
        vacinaRepository.deleteById(id);
    }
}
