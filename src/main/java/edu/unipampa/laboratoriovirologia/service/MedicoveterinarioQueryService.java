package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.domain.*; // for static metamodels
import edu.unipampa.laboratoriovirologia.domain.Medicoveterinario;
import edu.unipampa.laboratoriovirologia.repository.MedicoveterinarioRepository;
import edu.unipampa.laboratoriovirologia.service.criteria.MedicoveterinarioCriteria;
import edu.unipampa.laboratoriovirologia.service.dto.MedicoveterinarioDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.MedicoveterinarioMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Medicoveterinario} entities in the database.
 * The main input is a {@link MedicoveterinarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MedicoveterinarioDTO} or a {@link Page} of {@link MedicoveterinarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MedicoveterinarioQueryService extends QueryService<Medicoveterinario> {

    private final Logger log = LoggerFactory.getLogger(MedicoveterinarioQueryService.class);

    private final MedicoveterinarioRepository medicoveterinarioRepository;

    private final MedicoveterinarioMapper medicoveterinarioMapper;

    public MedicoveterinarioQueryService(
        MedicoveterinarioRepository medicoveterinarioRepository,
        MedicoveterinarioMapper medicoveterinarioMapper
    ) {
        this.medicoveterinarioRepository = medicoveterinarioRepository;
        this.medicoveterinarioMapper = medicoveterinarioMapper;
    }

    /**
     * Return a {@link List} of {@link MedicoveterinarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MedicoveterinarioDTO> findByCriteria(MedicoveterinarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Medicoveterinario> specification = createSpecification(criteria);
        return medicoveterinarioMapper.toDto(medicoveterinarioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MedicoveterinarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MedicoveterinarioDTO> findByCriteria(MedicoveterinarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Medicoveterinario> specification = createSpecification(criteria);
        return medicoveterinarioRepository.findAll(specification, page).map(medicoveterinarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MedicoveterinarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Medicoveterinario> specification = createSpecification(criteria);
        return medicoveterinarioRepository.count(specification);
    }

    /**
     * Function to convert {@link MedicoveterinarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Medicoveterinario> createSpecification(MedicoveterinarioCriteria criteria) {
        Specification<Medicoveterinario> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Medicoveterinario_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Medicoveterinario_.nome));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone(), Medicoveterinario_.telefone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Medicoveterinario_.email));
            }
            if (criteria.getCrmv() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCrmv(), Medicoveterinario_.crmv));
            }
            if (criteria.getEnviarLaudo() != null) {
                specification = specification.and(buildSpecification(criteria.getEnviarLaudo(), Medicoveterinario_.enviarLaudo));
            }
        }
        return specification;
    }
}
