package edu.unipampa.laboratoriovirologia.service;

import edu.unipampa.laboratoriovirologia.domain.*; // for static metamodels
import edu.unipampa.laboratoriovirologia.domain.Amostra;
import edu.unipampa.laboratoriovirologia.repository.AmostraRepository;
import edu.unipampa.laboratoriovirologia.service.criteria.AmostraCriteria;
import edu.unipampa.laboratoriovirologia.service.dto.AmostraDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.AmostraMapper;
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
 * Service for executing complex queries for {@link Amostra} entities in the database.
 * The main input is a {@link AmostraCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AmostraDTO} or a {@link Page} of {@link AmostraDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AmostraQueryService extends QueryService<Amostra> {

    private final Logger log = LoggerFactory.getLogger(AmostraQueryService.class);

    private final AmostraRepository amostraRepository;

    private final AmostraMapper amostraMapper;

    public AmostraQueryService(AmostraRepository amostraRepository, AmostraMapper amostraMapper) {
        this.amostraRepository = amostraRepository;
        this.amostraMapper = amostraMapper;
    }

    /**
     * Return a {@link List} of {@link AmostraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AmostraDTO> findByCriteria(AmostraCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Amostra> specification = createSpecification(criteria);
        return amostraMapper.toDto(amostraRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AmostraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AmostraDTO> findByCriteria(AmostraCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Amostra> specification = createSpecification(criteria);
        return amostraRepository.findAll(specification, page).map(amostraMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AmostraCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Amostra> specification = createSpecification(criteria);
        return amostraRepository.count(specification);
    }

    /**
     * Function to convert {@link AmostraCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Amostra> createSpecification(AmostraCriteria criteria) {
        Specification<Amostra> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Amostra_.id));
            }
            if (criteria.getProtocolo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProtocolo(), Amostra_.protocolo));
            }
            if (criteria.getFormaEnvio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormaEnvio(), Amostra_.formaEnvio));
            }
            if (criteria.getNumeroAmostras() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroAmostras(), Amostra_.numeroAmostras));
            }
            if (criteria.getEspecie() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEspecie(), Amostra_.especie));
            }
            if (criteria.getNumeroAnimais() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroAnimais(), Amostra_.numeroAnimais));
            }
            if (criteria.getAcometidos() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcometidos(), Amostra_.acometidos));
            }
            if (criteria.getPricipalSuspeita() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPricipalSuspeita(), Amostra_.pricipalSuspeita));
            }
            if (criteria.getDataInicial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataInicial(), Amostra_.dataInicial));
            }
            if (criteria.getMaterialRecebido() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaterialRecebido(), Amostra_.materialRecebido));
            }
            if (criteria.getAcondicionamento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcondicionamento(), Amostra_.acondicionamento));
            }
            if (criteria.getCondicaoMaterial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCondicaoMaterial(), Amostra_.condicaoMaterial));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Amostra_.status));
            }
            if (criteria.getTipoMedVet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipoMedVet(), Amostra_.tipoMedVet));
            }
            if (criteria.getValorTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorTotal(), Amostra_.valorTotal));
            }
            if (criteria.getTipoPagamento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipoPagamento(), Amostra_.tipoPagamento));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), Amostra_.tipo));
            }
            if (criteria.getSituacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSituacao(), Amostra_.situacao));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(Amostra_.users, JoinType.LEFT).get(User_.id))
                    );
            }
            if (criteria.getMidiaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getMidiaId(), root -> root.join(Amostra_.midias, JoinType.LEFT).get(Midia_.id))
                    );
            }
            if (criteria.getSubamostraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSubamostraId(),
                            root -> root.join(Amostra_.subamostras, JoinType.LEFT).get(Subamostra_.id)
                        )
                    );
            }
            if (criteria.getExameId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getExameId(), root -> root.join(Amostra_.exames, JoinType.LEFT).get(Exame_.id))
                    );
            }
            if (criteria.getPropriedadeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPropriedadeId(),
                            root -> root.join(Amostra_.propriedade, JoinType.LEFT).get(Propriedade_.id)
                        )
                    );
            }
            if (criteria.getMedicoveterinarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMedicoveterinarioId(),
                            root -> root.join(Amostra_.medicoveterinario, JoinType.LEFT).get(Medicoveterinario_.id)
                        )
                    );
            }
            if (criteria.getVacinaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVacinaId(), root -> root.join(Amostra_.vacina, JoinType.LEFT).get(Vacina_.id))
                    );
            }
        }
        return specification;
    }
}
