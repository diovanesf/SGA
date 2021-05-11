package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.domain.Medicoveterinario;
import edu.unipampa.laboratoriovirologia.repository.MedicoveterinarioRepository;
import edu.unipampa.laboratoriovirologia.service.dto.MedicoveterinarioDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.MedicoveterinarioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Medicoveterinario}.
 */
@Service
@Transactional
public class MedicoveterinarioService {

    private final Logger log = LoggerFactory.getLogger(MedicoveterinarioService.class);

    private final MedicoveterinarioRepository medicoveterinarioRepository;

    private final MedicoveterinarioMapper medicoveterinarioMapper;

    public MedicoveterinarioService(
        MedicoveterinarioRepository medicoveterinarioRepository,
        MedicoveterinarioMapper medicoveterinarioMapper
    ) {
        this.medicoveterinarioRepository = medicoveterinarioRepository;
        this.medicoveterinarioMapper = medicoveterinarioMapper;
    }

    /**
     * Save a medicoveterinario.
     *
     * @param medicoveterinarioDTO the entity to save.
     * @return the persisted entity.
     */
    public MedicoveterinarioDTO save(MedicoveterinarioDTO medicoveterinarioDTO) {
        log.debug("Request to save Medicoveterinario : {}", medicoveterinarioDTO);
        Medicoveterinario medicoveterinario = medicoveterinarioMapper.toEntity(medicoveterinarioDTO);
        medicoveterinario = medicoveterinarioRepository.save(medicoveterinario);
        return medicoveterinarioMapper.toDto(medicoveterinario);
    }

    /**
     * Partially update a medicoveterinario.
     *
     * @param medicoveterinarioDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MedicoveterinarioDTO> partialUpdate(MedicoveterinarioDTO medicoveterinarioDTO) {
        log.debug("Request to partially update Medicoveterinario : {}", medicoveterinarioDTO);

        return medicoveterinarioRepository
            .findById(medicoveterinarioDTO.getId())
            .map(
                existingMedicoveterinario -> {
                    medicoveterinarioMapper.partialUpdate(existingMedicoveterinario, medicoveterinarioDTO);
                    return existingMedicoveterinario;
                }
            )
            .map(medicoveterinarioRepository::save)
            .map(medicoveterinarioMapper::toDto);
    }

    /**
     * Get all the medicoveterinarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MedicoveterinarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Medicoveterinarios");
        return medicoveterinarioRepository.findAll(pageable).map(medicoveterinarioMapper::toDto);
    }

    /**
     * Get one medicoveterinario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MedicoveterinarioDTO> findOne(Long id) {
        log.debug("Request to get Medicoveterinario : {}", id);
        return medicoveterinarioRepository.findById(id).map(medicoveterinarioMapper::toDto);
    }

    /**
     * Delete the medicoveterinario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Medicoveterinario : {}", id);
        medicoveterinarioRepository.deleteById(id);
    }
}
