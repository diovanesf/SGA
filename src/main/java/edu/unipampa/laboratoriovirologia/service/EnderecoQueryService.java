package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.domain.*; // for static metamodels
import edu.unipampa.laboratoriovirologia.domain.Endereco;
import edu.unipampa.laboratoriovirologia.repository.EnderecoRepository;
import edu.unipampa.laboratoriovirologia.service.criteria.EnderecoCriteria;
import edu.unipampa.laboratoriovirologia.service.dto.EnderecoDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.EnderecoMapper;
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
 * Service for executing complex queries for {@link Endereco} entities in the database.
 * The main input is a {@link EnderecoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EnderecoDTO} or a {@link Page} of {@link EnderecoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnderecoQueryService extends QueryService<Endereco> {

    private final Logger log = LoggerFactory.getLogger(EnderecoQueryService.class);

    private final EnderecoRepository enderecoRepository;

    private final EnderecoMapper enderecoMapper;

    public EnderecoQueryService(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    /**
     * Return a {@link List} of {@link EnderecoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EnderecoDTO> findByCriteria(EnderecoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Endereco> specification = createSpecification(criteria);
        return enderecoMapper.toDto(enderecoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EnderecoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnderecoDTO> findByCriteria(EnderecoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Endereco> specification = createSpecification(criteria);
        return enderecoRepository.findAll(specification, page).map(enderecoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnderecoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Endereco> specification = createSpecification(criteria);
        return enderecoRepository.count(specification);
    }

    /**
     * Function to convert {@link EnderecoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Endereco> createSpecification(EnderecoCriteria criteria) {
        Specification<Endereco> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Endereco_.id));
            }
            if (criteria.getEndereco() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndereco(), Endereco_.endereco));
            }
            if (criteria.getCep() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCep(), Endereco_.cep));
            }
            if (criteria.getCidade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCidade(), Endereco_.cidade));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstado(), Endereco_.estado));
            }
            if (criteria.getCoordenadasGps() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCoordenadasGps(), Endereco_.coordenadasGps));
            }
        }
        return specification;
    }
}
