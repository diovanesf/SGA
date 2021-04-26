package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.domain.*; // for static metamodels
import edu.unipampa.laboratoriovirologia.domain.Proprietario;
import edu.unipampa.laboratoriovirologia.repository.ProprietarioRepository;
import edu.unipampa.laboratoriovirologia.service.criteria.ProprietarioCriteria;
import edu.unipampa.laboratoriovirologia.service.dto.ProprietarioDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.ProprietarioMapper;
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
 * Service for executing complex queries for {@link Proprietario} entities in the database.
 * The main input is a {@link ProprietarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProprietarioDTO} or a {@link Page} of {@link ProprietarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProprietarioQueryService extends QueryService<Proprietario> {

    private final Logger log = LoggerFactory.getLogger(ProprietarioQueryService.class);

    private final ProprietarioRepository proprietarioRepository;

    private final ProprietarioMapper proprietarioMapper;

    public ProprietarioQueryService(ProprietarioRepository proprietarioRepository, ProprietarioMapper proprietarioMapper) {
        this.proprietarioRepository = proprietarioRepository;
        this.proprietarioMapper = proprietarioMapper;
    }

    /**
     * Return a {@link List} of {@link ProprietarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProprietarioDTO> findByCriteria(ProprietarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Proprietario> specification = createSpecification(criteria);
        return proprietarioMapper.toDto(proprietarioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProprietarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProprietarioDTO> findByCriteria(ProprietarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Proprietario> specification = createSpecification(criteria);
        return proprietarioRepository.findAll(specification, page).map(proprietarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProprietarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Proprietario> specification = createSpecification(criteria);
        return proprietarioRepository.count(specification);
    }

    /**
     * Function to convert {@link ProprietarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Proprietario> createSpecification(ProprietarioCriteria criteria) {
        Specification<Proprietario> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Proprietario_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Proprietario_.nome));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone(), Proprietario_.telefone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Proprietario_.email));
            }
            if (criteria.getEnviarLaudo() != null) {
                specification = specification.and(buildSpecification(criteria.getEnviarLaudo(), Proprietario_.enviarLaudo));
            }
            if (criteria.getPropriedadeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPropriedadeId(),
                            root -> root.join(Proprietario_.propriedades, JoinType.LEFT).get(Propriedade_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
