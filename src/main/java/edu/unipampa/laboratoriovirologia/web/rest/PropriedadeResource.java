package edu.unipampa.laboratoriovirologia.web.rest;

import edu.unipampa.laboratoriovirologia.repository.PropriedadeRepository;
import edu.unipampa.laboratoriovirologia.service.PropriedadeService;
import edu.unipampa.laboratoriovirologia.service.dto.PropriedadeDTO;
import edu.unipampa.laboratoriovirologia.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link edu.unipampa.laboratoriovirologia.domain.Propriedade}.
 */
@RestController
@RequestMapping("/api")
public class PropriedadeResource {

    private final Logger log = LoggerFactory.getLogger(PropriedadeResource.class);

    private static final String ENTITY_NAME = "propriedade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PropriedadeService propriedadeService;

    private final PropriedadeRepository propriedadeRepository;

    public PropriedadeResource(PropriedadeService propriedadeService, PropriedadeRepository propriedadeRepository) {
        this.propriedadeService = propriedadeService;
        this.propriedadeRepository = propriedadeRepository;
    }

    /**
     * {@code POST  /propriedades} : Create a new propriedade.
     *
     * @param propriedadeDTO the propriedadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new propriedadeDTO, or with status {@code 400 (Bad Request)} if the propriedade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/propriedades")
    public ResponseEntity<PropriedadeDTO> createPropriedade(@RequestBody PropriedadeDTO propriedadeDTO) throws URISyntaxException {
        log.debug("REST request to save Propriedade : {}", propriedadeDTO);
        if (propriedadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new propriedade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropriedadeDTO result = propriedadeService.save(propriedadeDTO);
        return ResponseEntity
            .created(new URI("/api/propriedades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /propriedades/:id} : Updates an existing propriedade.
     *
     * @param id the id of the propriedadeDTO to save.
     * @param propriedadeDTO the propriedadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated propriedadeDTO,
     * or with status {@code 400 (Bad Request)} if the propriedadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the propriedadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/propriedades/{id}")
    public ResponseEntity<PropriedadeDTO> updatePropriedade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PropriedadeDTO propriedadeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Propriedade : {}, {}", id, propriedadeDTO);
        if (propriedadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, propriedadeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!propriedadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PropriedadeDTO result = propriedadeService.save(propriedadeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, propriedadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /propriedades/:id} : Partial updates given fields of an existing propriedade, field will ignore if it is null
     *
     * @param id the id of the propriedadeDTO to save.
     * @param propriedadeDTO the propriedadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated propriedadeDTO,
     * or with status {@code 400 (Bad Request)} if the propriedadeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the propriedadeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the propriedadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/propriedades/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PropriedadeDTO> partialUpdatePropriedade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PropriedadeDTO propriedadeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Propriedade partially : {}, {}", id, propriedadeDTO);
        if (propriedadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, propriedadeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!propriedadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PropriedadeDTO> result = propriedadeService.partialUpdate(propriedadeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, propriedadeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /propriedades} : get all the propriedades.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of propriedades in body.
     */
    @GetMapping("/propriedades")
    public List<PropriedadeDTO> getAllPropriedades() {
        log.debug("REST request to get all Propriedades");
        return propriedadeService.findAll();
    }

    /**
     * {@code GET  /propriedades/:id} : get the "id" propriedade.
     *
     * @param id the id of the propriedadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propriedadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/propriedades/{id}")
    public ResponseEntity<PropriedadeDTO> getPropriedade(@PathVariable Long id) {
        log.debug("REST request to get Propriedade : {}", id);
        Optional<PropriedadeDTO> propriedadeDTO = propriedadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propriedadeDTO);
    }

    /**
     * {@code DELETE  /propriedades/:id} : delete the "id" propriedade.
     *
     * @param id the id of the propriedadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/propriedades/{id}")
    public ResponseEntity<Void> deletePropriedade(@PathVariable Long id) {
        log.debug("REST request to delete Propriedade : {}", id);
        propriedadeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
