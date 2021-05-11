package edu.unipampa.laboratoriovirologia.web.rest;

import edu.unipampa.laboratoriovirologia.repository.MidiaRepository;
import edu.unipampa.laboratoriovirologia.service.MidiaQueryService;
import edu.unipampa.laboratoriovirologia.service.MidiaService;
import edu.unipampa.laboratoriovirologia.service.criteria.MidiaCriteria;
import edu.unipampa.laboratoriovirologia.service.dto.MidiaDTO;
import edu.unipampa.laboratoriovirologia.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link edu.unipampa.laboratoriovirologia.domain.Midia}.
 */
@RestController
@RequestMapping("/api")
public class MidiaResource {

    private final Logger log = LoggerFactory.getLogger(MidiaResource.class);

    private static final String ENTITY_NAME = "midia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MidiaService midiaService;

    private final MidiaRepository midiaRepository;

    private final MidiaQueryService midiaQueryService;

    public MidiaResource(MidiaService midiaService, MidiaRepository midiaRepository, MidiaQueryService midiaQueryService) {
        this.midiaService = midiaService;
        this.midiaRepository = midiaRepository;
        this.midiaQueryService = midiaQueryService;
    }

    /**
     * {@code POST  /midias} : Create a new midia.
     *
     * @param midiaDTO the midiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new midiaDTO, or with status {@code 400 (Bad Request)} if the midia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/midias")
    public ResponseEntity<MidiaDTO> createMidia(@RequestBody MidiaDTO midiaDTO) throws URISyntaxException {
        log.debug("REST request to save Midia : {}", midiaDTO);
        if (midiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new midia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MidiaDTO result = midiaService.save(midiaDTO);
        return ResponseEntity
            .created(new URI("/api/midias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /midias/:id} : Updates an existing midia.
     *
     * @param id the id of the midiaDTO to save.
     * @param midiaDTO the midiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated midiaDTO,
     * or with status {@code 400 (Bad Request)} if the midiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the midiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/midias/{id}")
    public ResponseEntity<MidiaDTO> updateMidia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MidiaDTO midiaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Midia : {}, {}", id, midiaDTO);
        if (midiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, midiaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!midiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MidiaDTO result = midiaService.save(midiaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, midiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /midias/:id} : Partial updates given fields of an existing midia, field will ignore if it is null
     *
     * @param id the id of the midiaDTO to save.
     * @param midiaDTO the midiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated midiaDTO,
     * or with status {@code 400 (Bad Request)} if the midiaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the midiaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the midiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/midias/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MidiaDTO> partialUpdateMidia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MidiaDTO midiaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Midia partially : {}, {}", id, midiaDTO);
        if (midiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, midiaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!midiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MidiaDTO> result = midiaService.partialUpdate(midiaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, midiaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /midias} : get all the midias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of midias in body.
     */
    @GetMapping("/midias")
    public ResponseEntity<List<MidiaDTO>> getAllMidias(MidiaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Midias by criteria: {}", criteria);
        Page<MidiaDTO> page = midiaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /midias/count} : count all the midias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/midias/count")
    public ResponseEntity<Long> countMidias(MidiaCriteria criteria) {
        log.debug("REST request to count Midias by criteria: {}", criteria);
        return ResponseEntity.ok().body(midiaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /midias/:id} : get the "id" midia.
     *
     * @param id the id of the midiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the midiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/midias/{id}")
    public ResponseEntity<MidiaDTO> getMidia(@PathVariable Long id) {
        log.debug("REST request to get Midia : {}", id);
        Optional<MidiaDTO> midiaDTO = midiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(midiaDTO);
    }

    /**
     * {@code DELETE  /midias/:id} : delete the "id" midia.
     *
     * @param id the id of the midiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/midias/{id}")
    public ResponseEntity<Void> deleteMidia(@PathVariable Long id) {
        log.debug("REST request to delete Midia : {}", id);
        midiaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
