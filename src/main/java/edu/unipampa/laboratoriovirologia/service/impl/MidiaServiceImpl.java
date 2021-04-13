package edu.unipampa.laboratoriovirologia.service.impl;

import edu.unipampa.laboratoriovirologia.domain.Midia;
import edu.unipampa.laboratoriovirologia.repository.MidiaRepository;
import edu.unipampa.laboratoriovirologia.service.MidiaService;
import edu.unipampa.laboratoriovirologia.service.dto.MidiaDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.MidiaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Midia}.
 */
@Service
@Transactional
public class MidiaServiceImpl implements MidiaService {

    private final Logger log = LoggerFactory.getLogger(MidiaServiceImpl.class);

    private final MidiaRepository midiaRepository;

    private final MidiaMapper midiaMapper;

    public MidiaServiceImpl(MidiaRepository midiaRepository, MidiaMapper midiaMapper) {
        this.midiaRepository = midiaRepository;
        this.midiaMapper = midiaMapper;
    }

    @Override
    public MidiaDTO save(MidiaDTO midiaDTO) {
        log.debug("Request to save Midia : {}", midiaDTO);
        Midia midia = midiaMapper.toEntity(midiaDTO);
        midia = midiaRepository.save(midia);
        return midiaMapper.toDto(midia);
    }

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public List<MidiaDTO> findAll() {
        log.debug("Request to get all Midias");
        return midiaRepository.findAll().stream().map(midiaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MidiaDTO> findOne(Long id) {
        log.debug("Request to get Midia : {}", id);
        return midiaRepository.findById(id).map(midiaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Midia : {}", id);
        midiaRepository.deleteById(id);
    }
}
