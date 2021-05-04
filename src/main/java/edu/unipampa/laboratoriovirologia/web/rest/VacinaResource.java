package edu.unipampa.laboratoriovirologia.web.rest;

import edu.unipampa.laboratoriovirologia.repository.VacinaRepository;
import edu.unipampa.laboratoriovirologia.service.VacinaService;
import edu.unipampa.laboratoriovirologia.service.dto.VacinaDTO;
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
 * REST controller for managing {@link edu.unipampa.laboratoriovirologia.domain.Vacina}.
 */
@RestController
@RequestMapping("/api")
public class VacinaResource {

    private final Logger log = LoggerFactory.getLogger(VacinaResource.class);

    private static final String ENTITY_NAME = "vacina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VacinaService vacinaService;

    private final VacinaRepository vacinaRepository;

    public VacinaResource(VacinaService vacinaService, VacinaRepository vacinaRepository) {
        this.vacinaService = vacinaService;
        this.vacinaRepository = vacinaRepository;
    }

    /**
     * {@code POST  /vacinas} : Create a new vacina.
     *
     * @param vacinaDTO the vacinaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vacinaDTO, or with status {@code 400 (Bad Request)} if the vacina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vacinas")
    public ResponseEntity<VacinaDTO> createVacina(@RequestBody VacinaDTO vacinaDTO) throws URISyntaxException {
        log.debug("REST request to save Vacina : {}", vacinaDTO);
        if (vacinaDTO.getId() != null) {
            throw new BadRequestAlertException("A new vacina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VacinaDTO result = vacinaService.save(vacinaDTO);
        return ResponseEntity
            .created(new URI("/api/vacinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vacinas/:id} : Updates an existing vacina.
     *
     * @param id the id of the vacinaDTO to save.
     * @param vacinaDTO the vacinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vacinaDTO,
     * or with status {@code 400 (Bad Request)} if the vacinaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vacinaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vacinas/{id}")
    public ResponseEntity<VacinaDTO> updateVacina(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VacinaDTO vacinaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Vacina : {}, {}", id, vacinaDTO);
        if (vacinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vacinaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vacinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VacinaDTO result = vacinaService.save(vacinaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vacinaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vacinas/:id} : Partial updates given fields of an existing vacina, field will ignore if it is null
     *
     * @param id the id of the vacinaDTO to save.
     * @param vacinaDTO the vacinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vacinaDTO,
     * or with status {@code 400 (Bad Request)} if the vacinaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vacinaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vacinaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vacinas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VacinaDTO> partialUpdateVacina(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VacinaDTO vacinaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vacina partially : {}, {}", id, vacinaDTO);
        if (vacinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vacinaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vacinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VacinaDTO> result = vacinaService.partialUpdate(vacinaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vacinaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vacinas} : get all the vacinas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vacinas in body.
     */
    @GetMapping("/vacinas")
    public List<VacinaDTO> getAllVacinas() {
        log.debug("REST request to get all Vacinas");
        return vacinaService.findAll();
    }

    /**
     * {@code GET  /vacinas/:id} : get the "id" vacina.
     *
     * @param id the id of the vacinaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vacinaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vacinas/{id}")
    public ResponseEntity<VacinaDTO> getVacina(@PathVariable Long id) {
        log.debug("REST request to get Vacina : {}", id);
        Optional<VacinaDTO> vacinaDTO = vacinaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vacinaDTO);
    }

    /**
     * {@code DELETE  /vacinas/:id} : delete the "id" vacina.
     *
     * @param id the id of the vacinaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vacinas/{id}")
    public ResponseEntity<Void> deleteVacina(@PathVariable Long id) {
        log.debug("REST request to delete Vacina : {}", id);
        vacinaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
