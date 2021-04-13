package edu.unipampa.laboratoriovirologia.web.rest;

import edu.unipampa.laboratoriovirologia.repository.AmostraRepository;
import edu.unipampa.laboratoriovirologia.service.AmostraService;
import edu.unipampa.laboratoriovirologia.service.dto.AmostraDTO;
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
 * REST controller for managing {@link edu.unipampa.laboratoriovirologia.domain.Amostra}.
 */
@RestController
@RequestMapping("/api")
public class AmostraResource {

    private final Logger log = LoggerFactory.getLogger(AmostraResource.class);

    private static final String ENTITY_NAME = "amostra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmostraService amostraService;

    private final AmostraRepository amostraRepository;

    public AmostraResource(AmostraService amostraService, AmostraRepository amostraRepository) {
        this.amostraService = amostraService;
        this.amostraRepository = amostraRepository;
    }

    /**
     * {@code POST  /amostras} : Create a new amostra.
     *
     * @param amostraDTO the amostraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amostraDTO, or with status {@code 400 (Bad Request)} if the amostra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amostras")
    public ResponseEntity<AmostraDTO> createAmostra(@RequestBody AmostraDTO amostraDTO) throws URISyntaxException {
        log.debug("REST request to save Amostra : {}", amostraDTO);
        if (amostraDTO.getId() != null) {
            throw new BadRequestAlertException("A new amostra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AmostraDTO result = amostraService.save(amostraDTO);
        return ResponseEntity
            .created(new URI("/api/amostras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /amostras/:id} : Updates an existing amostra.
     *
     * @param id the id of the amostraDTO to save.
     * @param amostraDTO the amostraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amostraDTO,
     * or with status {@code 400 (Bad Request)} if the amostraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the amostraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amostras/{id}")
    public ResponseEntity<AmostraDTO> updateAmostra(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AmostraDTO amostraDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Amostra : {}, {}", id, amostraDTO);
        if (amostraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, amostraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!amostraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AmostraDTO result = amostraService.save(amostraDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, amostraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /amostras/:id} : Partial updates given fields of an existing amostra, field will ignore if it is null
     *
     * @param id the id of the amostraDTO to save.
     * @param amostraDTO the amostraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amostraDTO,
     * or with status {@code 400 (Bad Request)} if the amostraDTO is not valid,
     * or with status {@code 404 (Not Found)} if the amostraDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the amostraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/amostras/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AmostraDTO> partialUpdateAmostra(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AmostraDTO amostraDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Amostra partially : {}, {}", id, amostraDTO);
        if (amostraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, amostraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!amostraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AmostraDTO> result = amostraService.partialUpdate(amostraDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, amostraDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /amostras} : get all the amostras.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amostras in body.
     */
    @GetMapping("/amostras")
    public List<AmostraDTO> getAllAmostras(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Amostras");
        return amostraService.findAll();
    }

    /**
     * {@code GET  /amostras/:id} : get the "id" amostra.
     *
     * @param id the id of the amostraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amostraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/amostras/{id}")
    public ResponseEntity<AmostraDTO> getAmostra(@PathVariable Long id) {
        log.debug("REST request to get Amostra : {}", id);
        Optional<AmostraDTO> amostraDTO = amostraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(amostraDTO);
    }

    /**
     * {@code DELETE  /amostras/:id} : delete the "id" amostra.
     *
     * @param id the id of the amostraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amostras/{id}")
    public ResponseEntity<Void> deleteAmostra(@PathVariable Long id) {
        log.debug("REST request to delete Amostra : {}", id);
        amostraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
