package edu.unipampa.laboratoriovirologia.web.rest;

import edu.unipampa.laboratoriovirologia.repository.SubamostraRepository;
import edu.unipampa.laboratoriovirologia.service.SubamostraService;
import edu.unipampa.laboratoriovirologia.service.dto.SubamostraDTO;
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
 * REST controller for managing {@link edu.unipampa.laboratoriovirologia.domain.Subamostra}.
 */
@RestController
@RequestMapping("/api")
public class SubamostraResource {

    private final Logger log = LoggerFactory.getLogger(SubamostraResource.class);

    private static final String ENTITY_NAME = "subamostra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubamostraService subamostraService;

    private final SubamostraRepository subamostraRepository;

    public SubamostraResource(SubamostraService subamostraService, SubamostraRepository subamostraRepository) {
        this.subamostraService = subamostraService;
        this.subamostraRepository = subamostraRepository;
    }

    /**
     * {@code POST  /subamostras} : Create a new subamostra.
     *
     * @param subamostraDTO the subamostraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subamostraDTO, or with status {@code 400 (Bad Request)} if the subamostra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subamostras")
    public ResponseEntity<SubamostraDTO> createSubamostra(@RequestBody SubamostraDTO subamostraDTO) throws URISyntaxException {
        log.debug("REST request to save Subamostra : {}", subamostraDTO);
        if (subamostraDTO.getId() != null) {
            throw new BadRequestAlertException("A new subamostra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubamostraDTO result = subamostraService.save(subamostraDTO);
        return ResponseEntity
            .created(new URI("/api/subamostras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subamostras/:id} : Updates an existing subamostra.
     *
     * @param id the id of the subamostraDTO to save.
     * @param subamostraDTO the subamostraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subamostraDTO,
     * or with status {@code 400 (Bad Request)} if the subamostraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subamostraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subamostras/{id}")
    public ResponseEntity<SubamostraDTO> updateSubamostra(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubamostraDTO subamostraDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Subamostra : {}, {}", id, subamostraDTO);
        if (subamostraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subamostraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subamostraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubamostraDTO result = subamostraService.save(subamostraDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subamostraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /subamostras/:id} : Partial updates given fields of an existing subamostra, field will ignore if it is null
     *
     * @param id the id of the subamostraDTO to save.
     * @param subamostraDTO the subamostraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subamostraDTO,
     * or with status {@code 400 (Bad Request)} if the subamostraDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subamostraDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subamostraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/subamostras/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SubamostraDTO> partialUpdateSubamostra(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubamostraDTO subamostraDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Subamostra partially : {}, {}", id, subamostraDTO);
        if (subamostraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subamostraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subamostraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubamostraDTO> result = subamostraService.partialUpdate(subamostraDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subamostraDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /subamostras} : get all the subamostras.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subamostras in body.
     */
    @GetMapping("/subamostras")
    public List<SubamostraDTO> getAllSubamostras() {
        log.debug("REST request to get all Subamostras");
        return subamostraService.findAll();
    }

    /**
     * {@code GET  /subamostras/:id} : get the "id" subamostra.
     *
     * @param id the id of the subamostraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subamostraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subamostras/{id}")
    public ResponseEntity<SubamostraDTO> getSubamostra(@PathVariable Long id) {
        log.debug("REST request to get Subamostra : {}", id);
        Optional<SubamostraDTO> subamostraDTO = subamostraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subamostraDTO);
    }

    /**
     * {@code DELETE  /subamostras/:id} : delete the "id" subamostra.
     *
     * @param id the id of the subamostraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subamostras/{id}")
    public ResponseEntity<Void> deleteSubamostra(@PathVariable Long id) {
        log.debug("REST request to delete Subamostra : {}", id);
        subamostraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
