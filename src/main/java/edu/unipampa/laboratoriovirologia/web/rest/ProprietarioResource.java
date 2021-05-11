package edu.unipampa.laboratoriovirologia.web.rest;

import edu.unipampa.laboratoriovirologia.repository.ProprietarioRepository;
import edu.unipampa.laboratoriovirologia.service.ProprietarioQueryService;
import edu.unipampa.laboratoriovirologia.service.ProprietarioService;
import edu.unipampa.laboratoriovirologia.service.criteria.ProprietarioCriteria;
import edu.unipampa.laboratoriovirologia.service.dto.ProprietarioDTO;
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
 * REST controller for managing {@link edu.unipampa.laboratoriovirologia.domain.Proprietario}.
 */
@RestController
@RequestMapping("/api")
public class ProprietarioResource {

    private final Logger log = LoggerFactory.getLogger(ProprietarioResource.class);

    private static final String ENTITY_NAME = "proprietario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProprietarioService proprietarioService;

    private final ProprietarioRepository proprietarioRepository;

    private final ProprietarioQueryService proprietarioQueryService;

    public ProprietarioResource(
        ProprietarioService proprietarioService,
        ProprietarioRepository proprietarioRepository,
        ProprietarioQueryService proprietarioQueryService
    ) {
        this.proprietarioService = proprietarioService;
        this.proprietarioRepository = proprietarioRepository;
        this.proprietarioQueryService = proprietarioQueryService;
    }

    /**
     * {@code POST  /proprietarios} : Create a new proprietario.
     *
     * @param proprietarioDTO the proprietarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proprietarioDTO, or with status {@code 400 (Bad Request)} if the proprietario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proprietarios")
    public ResponseEntity<ProprietarioDTO> createProprietario(@RequestBody ProprietarioDTO proprietarioDTO) throws URISyntaxException {
        log.debug("REST request to save Proprietario : {}", proprietarioDTO);
        if (proprietarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new proprietario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProprietarioDTO result = proprietarioService.save(proprietarioDTO);
        return ResponseEntity
            .created(new URI("/api/proprietarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proprietarios/:id} : Updates an existing proprietario.
     *
     * @param id the id of the proprietarioDTO to save.
     * @param proprietarioDTO the proprietarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proprietarioDTO,
     * or with status {@code 400 (Bad Request)} if the proprietarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proprietarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proprietarios/{id}")
    public ResponseEntity<ProprietarioDTO> updateProprietario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProprietarioDTO proprietarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Proprietario : {}, {}", id, proprietarioDTO);
        if (proprietarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proprietarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!proprietarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProprietarioDTO result = proprietarioService.save(proprietarioDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, proprietarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /proprietarios/:id} : Partial updates given fields of an existing proprietario, field will ignore if it is null
     *
     * @param id the id of the proprietarioDTO to save.
     * @param proprietarioDTO the proprietarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proprietarioDTO,
     * or with status {@code 400 (Bad Request)} if the proprietarioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the proprietarioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the proprietarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/proprietarios/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProprietarioDTO> partialUpdateProprietario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProprietarioDTO proprietarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Proprietario partially : {}, {}", id, proprietarioDTO);
        if (proprietarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proprietarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!proprietarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProprietarioDTO> result = proprietarioService.partialUpdate(proprietarioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, proprietarioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /proprietarios} : get all the proprietarios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proprietarios in body.
     */
    @GetMapping("/proprietarios")
    public ResponseEntity<List<ProprietarioDTO>> getAllProprietarios(ProprietarioCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Proprietarios by criteria: {}", criteria);
        Page<ProprietarioDTO> page = proprietarioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /proprietarios/count} : count all the proprietarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/proprietarios/count")
    public ResponseEntity<Long> countProprietarios(ProprietarioCriteria criteria) {
        log.debug("REST request to count Proprietarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(proprietarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /proprietarios/:id} : get the "id" proprietario.
     *
     * @param id the id of the proprietarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proprietarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proprietarios/{id}")
    public ResponseEntity<ProprietarioDTO> getProprietario(@PathVariable Long id) {
        log.debug("REST request to get Proprietario : {}", id);
        Optional<ProprietarioDTO> proprietarioDTO = proprietarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proprietarioDTO);
    }

    /**
     * {@code DELETE  /proprietarios/:id} : delete the "id" proprietario.
     *
     * @param id the id of the proprietarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proprietarios/{id}")
    public ResponseEntity<Void> deleteProprietario(@PathVariable Long id) {
        log.debug("REST request to delete Proprietario : {}", id);
        proprietarioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
