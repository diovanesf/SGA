package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.domain.*; // for static metamodels
import edu.unipampa.laboratoriovirologia.domain.Midia;
import edu.unipampa.laboratoriovirologia.repository.MidiaRepository;
import edu.unipampa.laboratoriovirologia.service.criteria.MidiaCriteria;
import edu.unipampa.laboratoriovirologia.service.dto.MidiaDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.MidiaMapper;
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
 * Service for executing complex queries for {@link Midia} entities in the database.
 * The main input is a {@link MidiaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MidiaDTO} or a {@link Page} of {@link MidiaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MidiaQueryService extends QueryService<Midia> {

    private final Logger log = LoggerFactory.getLogger(MidiaQueryService.class);

    private final MidiaRepository midiaRepository;

    private final MidiaMapper midiaMapper;

    public MidiaQueryService(MidiaRepository midiaRepository, MidiaMapper midiaMapper) {
        this.midiaRepository = midiaRepository;
        this.midiaMapper = midiaMapper;
    }

    /**
     * Return a {@link List} of {@link MidiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MidiaDTO> findByCriteria(MidiaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Midia> specification = createSpecification(criteria);
        return midiaMapper.toDto(midiaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MidiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MidiaDTO> findByCriteria(MidiaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Midia> specification = createSpecification(criteria);
        return midiaRepository.findAll(specification, page).map(midiaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MidiaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Midia> specification = createSpecification(criteria);
        return midiaRepository.count(specification);
    }

    /**
     * Function to convert {@link MidiaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Midia> createSpecification(MidiaCriteria criteria) {
        Specification<Midia> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Midia_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Midia_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Midia_.descricao));
            }
            if (criteria.getAmostraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAmostraId(), root -> root.join(Midia_.amostra, JoinType.LEFT).get(Amostra_.id))
                    );
            }
        }
        return specification;
    }
}
