package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.domain.*; // for static metamodels
import edu.unipampa.laboratoriovirologia.domain.Propriedade;
import edu.unipampa.laboratoriovirologia.repository.PropriedadeRepository;
import edu.unipampa.laboratoriovirologia.service.criteria.PropriedadeCriteria;
import edu.unipampa.laboratoriovirologia.service.dto.PropriedadeDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.PropriedadeMapper;
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
 * Service for executing complex queries for {@link Propriedade} entities in the database.
 * The main input is a {@link PropriedadeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PropriedadeDTO} or a {@link Page} of {@link PropriedadeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PropriedadeQueryService extends QueryService<Propriedade> {

    private final Logger log = LoggerFactory.getLogger(PropriedadeQueryService.class);

    private final PropriedadeRepository propriedadeRepository;

    private final PropriedadeMapper propriedadeMapper;

    public PropriedadeQueryService(PropriedadeRepository propriedadeRepository, PropriedadeMapper propriedadeMapper) {
        this.propriedadeRepository = propriedadeRepository;
        this.propriedadeMapper = propriedadeMapper;
    }

    /**
     * Return a {@link List} of {@link PropriedadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PropriedadeDTO> findByCriteria(PropriedadeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Propriedade> specification = createSpecification(criteria);
        return propriedadeMapper.toDto(propriedadeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PropriedadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PropriedadeDTO> findByCriteria(PropriedadeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Propriedade> specification = createSpecification(criteria);
        return propriedadeRepository.findAll(specification, page).map(propriedadeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PropriedadeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Propriedade> specification = createSpecification(criteria);
        return propriedadeRepository.count(specification);
    }

    /**
     * Function to convert {@link PropriedadeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Propriedade> createSpecification(PropriedadeCriteria criteria) {
        Specification<Propriedade> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Propriedade_.id));
            }
            if (criteria.getTipoPropriedade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipoPropriedade(), Propriedade_.tipoPropriedade));
            }
            if (criteria.getTipoCriacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipoCriacao(), Propriedade_.tipoCriacao));
            }
            if (criteria.getProprietarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProprietarioId(),
                            root -> root.join(Propriedade_.proprietario, JoinType.LEFT).get(Proprietario_.id)
                        )
                    );
            }
            if (criteria.getEnderecoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEnderecoId(),
                            root -> root.join(Propriedade_.endereco, JoinType.LEFT).get(Endereco_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
