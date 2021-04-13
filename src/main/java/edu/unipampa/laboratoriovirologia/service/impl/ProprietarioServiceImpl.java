package edu.unipampa.laboratoriovirologia.service.impl;

import edu.unipampa.laboratoriovirologia.domain.Proprietario;
import edu.unipampa.laboratoriovirologia.repository.ProprietarioRepository;
import edu.unipampa.laboratoriovirologia.service.ProprietarioService;
import edu.unipampa.laboratoriovirologia.service.dto.ProprietarioDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.ProprietarioMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Proprietario}.
 */
@Service
@Transactional
public class ProprietarioServiceImpl implements ProprietarioService {

    private final Logger log = LoggerFactory.getLogger(ProprietarioServiceImpl.class);

    private final ProprietarioRepository proprietarioRepository;

    private final ProprietarioMapper proprietarioMapper;

    public ProprietarioServiceImpl(ProprietarioRepository proprietarioRepository, ProprietarioMapper proprietarioMapper) {
        this.proprietarioRepository = proprietarioRepository;
        this.proprietarioMapper = proprietarioMapper;
    }

    @Override
    public ProprietarioDTO save(ProprietarioDTO proprietarioDTO) {
        log.debug("Request to save Proprietario : {}", proprietarioDTO);
        Proprietario proprietario = proprietarioMapper.toEntity(proprietarioDTO);
        proprietario = proprietarioRepository.save(proprietario);
        return proprietarioMapper.toDto(proprietario);
    }

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public List<ProprietarioDTO> findAll() {
        log.debug("Request to get all Proprietarios");
        return proprietarioRepository.findAll().stream().map(proprietarioMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProprietarioDTO> findOne(Long id) {
        log.debug("Request to get Proprietario : {}", id);
        return proprietarioRepository.findById(id).map(proprietarioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Proprietario : {}", id);
        proprietarioRepository.deleteById(id);
    }
}
