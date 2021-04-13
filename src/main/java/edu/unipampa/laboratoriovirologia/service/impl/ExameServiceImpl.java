package edu.unipampa.laboratoriovirologia.service.impl;

import edu.unipampa.laboratoriovirologia.domain.Exame;
import edu.unipampa.laboratoriovirologia.repository.ExameRepository;
import edu.unipampa.laboratoriovirologia.service.ExameService;
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
public class ExameServiceImpl implements ExameService {

    private final Logger log = LoggerFactory.getLogger(ExameServiceImpl.class);

    private final ExameRepository exameRepository;

    private final ExameMapper exameMapper;

    public ExameServiceImpl(ExameRepository exameRepository, ExameMapper exameMapper) {
        this.exameRepository = exameRepository;
        this.exameMapper = exameMapper;
    }

    @Override
    public ExameDTO save(ExameDTO exameDTO) {
        log.debug("Request to save Exame : {}", exameDTO);
        Exame exame = exameMapper.toEntity(exameDTO);
        exame = exameRepository.save(exame);
        return exameMapper.toDto(exame);
    }

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public List<ExameDTO> findAll() {
        log.debug("Request to get all Exames");
        return exameRepository.findAll().stream().map(exameMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExameDTO> findOne(Long id) {
        log.debug("Request to get Exame : {}", id);
        return exameRepository.findById(id).map(exameMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Exame : {}", id);
        exameRepository.deleteById(id);
    }
}
