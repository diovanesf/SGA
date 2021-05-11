package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.domain.Proprietario;
import edu.unipampa.laboratoriovirologia.repository.ProprietarioRepository;
import edu.unipampa.laboratoriovirologia.service.dto.ProprietarioDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.ProprietarioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Proprietario}.
 */
@Service
@Transactional
public class ProprietarioService {

    private final Logger log = LoggerFactory.getLogger(ProprietarioService.class);

    private final ProprietarioRepository proprietarioRepository;

    private final ProprietarioMapper proprietarioMapper;

    public ProprietarioService(ProprietarioRepository proprietarioRepository, ProprietarioMapper proprietarioMapper) {
        this.proprietarioRepository = proprietarioRepository;
        this.proprietarioMapper = proprietarioMapper;
    }

    /**
     * Save a proprietario.
     *
     * @param proprietarioDTO the entity to save.
     * @return the persisted entity.
     */
    public ProprietarioDTO save(ProprietarioDTO proprietarioDTO) {
        log.debug("Request to save Proprietario : {}", proprietarioDTO);
        Proprietario proprietario = proprietarioMapper.toEntity(proprietarioDTO);
        proprietario = proprietarioRepository.save(proprietario);
        return proprietarioMapper.toDto(proprietario);
    }

    /**
     * Partially update a proprietario.
     *
     * @param proprietarioDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProprietarioDTO> partialUpdate(ProprietarioDTO proprietarioDTO) {
        log.debug("Request to partially update Proprietario : {}", proprietarioDTO);

        return proprietarioRepository
            .findById(proprietarioDTO.getId())
            .map(
                existingProprietario -> {
                    proprietarioMapper.partialUpdate(existingProprietario, proprietarioDTO);
                    return existingProprietario;
                }
            )
            .map(proprietarioRepository::save)
            .map(proprietarioMapper::toDto);
    }

    /**
     * Get all the proprietarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProprietarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Proprietarios");
        return proprietarioRepository.findAll(pageable).map(proprietarioMapper::toDto);
    }

    /**
     * Get one proprietario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProprietarioDTO> findOne(Long id) {
        log.debug("Request to get Proprietario : {}", id);
        return proprietarioRepository.findById(id).map(proprietarioMapper::toDto);
    }

    /**
     * Delete the proprietario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Proprietario : {}", id);
        proprietarioRepository.deleteById(id);
    }
}
