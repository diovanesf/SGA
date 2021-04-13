package edu.unipampa.laboratoriovirologia.service.impl;

import edu.unipampa.laboratoriovirologia.domain.Amostra;
import edu.unipampa.laboratoriovirologia.repository.AmostraRepository;
import edu.unipampa.laboratoriovirologia.service.AmostraService;
import edu.unipampa.laboratoriovirologia.service.dto.AmostraDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.AmostraMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Amostra}.
 */
@Service
@Transactional
public class AmostraServiceImpl implements AmostraService {

    private final Logger log = LoggerFactory.getLogger(AmostraServiceImpl.class);

    private final AmostraRepository amostraRepository;

    private final AmostraMapper amostraMapper;

    public AmostraServiceImpl(AmostraRepository amostraRepository, AmostraMapper amostraMapper) {
        this.amostraRepository = amostraRepository;
        this.amostraMapper = amostraMapper;
    }

    @Override
    public AmostraDTO save(AmostraDTO amostraDTO) {
        log.debug("Request to save Amostra : {}", amostraDTO);
        Amostra amostra = amostraMapper.toEntity(amostraDTO);
        amostra = amostraRepository.save(amostra);
        return amostraMapper.toDto(amostra);
    }

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public List<AmostraDTO> findAll() {
        log.debug("Request to get all Amostras");
        return amostraRepository.findAll().stream().map(amostraMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AmostraDTO> findOne(Long id) {
        log.debug("Request to get Amostra : {}", id);
        return amostraRepository.findById(id).map(amostraMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Amostra : {}", id);
        amostraRepository.deleteById(id);
    }
}
