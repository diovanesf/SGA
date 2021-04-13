package edu.unipampa.laboratoriovirologia.service.impl;

import edu.unipampa.laboratoriovirologia.domain.Propriedade;
import edu.unipampa.laboratoriovirologia.repository.PropriedadeRepository;
import edu.unipampa.laboratoriovirologia.service.PropriedadeService;
import edu.unipampa.laboratoriovirologia.service.dto.PropriedadeDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.PropriedadeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Propriedade}.
 */
@Service
@Transactional
public class PropriedadeServiceImpl implements PropriedadeService {

    private final Logger log = LoggerFactory.getLogger(PropriedadeServiceImpl.class);

    private final PropriedadeRepository propriedadeRepository;

    private final PropriedadeMapper propriedadeMapper;

    public PropriedadeServiceImpl(PropriedadeRepository propriedadeRepository, PropriedadeMapper propriedadeMapper) {
        this.propriedadeRepository = propriedadeRepository;
        this.propriedadeMapper = propriedadeMapper;
    }

    @Override
    public PropriedadeDTO save(PropriedadeDTO propriedadeDTO) {
        log.debug("Request to save Propriedade : {}", propriedadeDTO);
        Propriedade propriedade = propriedadeMapper.toEntity(propriedadeDTO);
        propriedade = propriedadeRepository.save(propriedade);
        return propriedadeMapper.toDto(propriedade);
    }

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public List<PropriedadeDTO> findAll() {
        log.debug("Request to get all Propriedades");
        return propriedadeRepository.findAll().stream().map(propriedadeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PropriedadeDTO> findOne(Long id) {
        log.debug("Request to get Propriedade : {}", id);
        return propriedadeRepository.findById(id).map(propriedadeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Propriedade : {}", id);
        propriedadeRepository.deleteById(id);
    }
}
