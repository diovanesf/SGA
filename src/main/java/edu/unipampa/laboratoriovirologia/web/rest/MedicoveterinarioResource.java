package edu.unipampa.laboratoriovirologia.web.rest;

import edu.unipampa.laboratoriovirologia.repository.MedicoveterinarioRepository;
import edu.unipampa.laboratoriovirologia.service.MedicoveterinarioService;
import edu.unipampa.laboratoriovirologia.service.dto.MedicoveterinarioDTO;
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
 * REST controller for managing {@link edu.unipampa.laboratoriovirologia.domain.Medicoveterinario}.
 */
@RestController
@RequestMapping("/api")
public class MedicoveterinarioResource {

    private final Logger log = LoggerFactory.getLogger(MedicoveterinarioResource.class);

    private static final String ENTITY_NAME = "medicoveterinario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicoveterinarioService medicoveterinarioService;

    private final MedicoveterinarioRepository medicoveterinarioRepository;

    public MedicoveterinarioResource(
        MedicoveterinarioService medicoveterinarioService,
        MedicoveterinarioRepository medicoveterinarioRepository
    ) {
        this.medicoveterinarioService = medicoveterinarioService;
        this.medicoveterinarioRepository = medicoveterinarioRepository;
    }

    /**
     * {@code POST  /medicoveterinarios} : Create a new medicoveterinario.
     *
     * @param medicoveterinarioDTO the medicoveterinarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicoveterinarioDTO, or with status {@code 400 (Bad Request)} if the medicoveterinario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medicoveterinarios")
    public ResponseEntity<MedicoveterinarioDTO> createMedicoveterinario(@RequestBody MedicoveterinarioDTO medicoveterinarioDTO)
        throws URISyntaxException {
        log.debug("REST request to save Medicoveterinario : {}", medicoveterinarioDTO);
        if (medicoveterinarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicoveterinario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicoveterinarioDTO result = medicoveterinarioService.save(medicoveterinarioDTO);
        return ResponseEntity
            .created(new URI("/api/medicoveterinarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medicoveterinarios/:id} : Updates an existing medicoveterinario.
     *
     * @param id the id of the medicoveterinarioDTO to save.
     * @param medicoveterinarioDTO the medicoveterinarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicoveterinarioDTO,
     * or with status {@code 400 (Bad Request)} if the medicoveterinarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicoveterinarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medicoveterinarios/{id}")
    public ResponseEntity<MedicoveterinarioDTO> updateMedicoveterinario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MedicoveterinarioDTO medicoveterinarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Medicoveterinario : {}, {}", id, medicoveterinarioDTO);
        if (medicoveterinarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicoveterinarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicoveterinarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MedicoveterinarioDTO result = medicoveterinarioService.save(medicoveterinarioDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medicoveterinarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /medicoveterinarios/:id} : Partial updates given fields of an existing medicoveterinario, field will ignore if it is null
     *
     * @param id the id of the medicoveterinarioDTO to save.
     * @param medicoveterinarioDTO the medicoveterinarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicoveterinarioDTO,
     * or with status {@code 400 (Bad Request)} if the medicoveterinarioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the medicoveterinarioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the medicoveterinarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/medicoveterinarios/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MedicoveterinarioDTO> partialUpdateMedicoveterinario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MedicoveterinarioDTO medicoveterinarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Medicoveterinario partially : {}, {}", id, medicoveterinarioDTO);
        if (medicoveterinarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicoveterinarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicoveterinarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MedicoveterinarioDTO> result = medicoveterinarioService.partialUpdate(medicoveterinarioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medicoveterinarioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /medicoveterinarios} : get all the medicoveterinarios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicoveterinarios in body.
     */
    @GetMapping("/medicoveterinarios")
    public List<MedicoveterinarioDTO> getAllMedicoveterinarios() {
        log.debug("REST request to get all Medicoveterinarios");
        return medicoveterinarioService.findAll();
    }

    /**
     * {@code GET  /medicoveterinarios/:id} : get the "id" medicoveterinario.
     *
     * @param id the id of the medicoveterinarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicoveterinarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medicoveterinarios/{id}")
    public ResponseEntity<MedicoveterinarioDTO> getMedicoveterinario(@PathVariable Long id) {
        log.debug("REST request to get Medicoveterinario : {}", id);
        Optional<MedicoveterinarioDTO> medicoveterinarioDTO = medicoveterinarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicoveterinarioDTO);
    }

    /**
     * {@code DELETE  /medicoveterinarios/:id} : delete the "id" medicoveterinario.
     *
     * @param id the id of the medicoveterinarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medicoveterinarios/{id}")
    public ResponseEntity<Void> deleteMedicoveterinario(@PathVariable Long id) {
        log.debug("REST request to delete Medicoveterinario : {}", id);
        medicoveterinarioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
