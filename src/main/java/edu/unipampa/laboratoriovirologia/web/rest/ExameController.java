package edu.unipampa.laboratoriovirologia.web.rest;

import edu.unipampa.laboratoriovirologia.repository.ExameRepository;
import edu.unipampa.laboratoriovirologia.service.ExameService;
import edu.unipampa.laboratoriovirologia.service.dto.ExameDTO;
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
 * REST controller for managing {@link edu.unipampa.laboratoriovirologia.domain.Exame}.
 */
@RestController
@RequestMapping("/api")
public class ExameController {

    private final ExameService exameService;

    public ExameController(ExameService exameService) {
        this.exameService = exameService;
    }

    /**
     * {@code GET  /exames} : get all the exames.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exames in body.
     */
    @GetMapping("/amostra/{amostraId}/exames")
    public List<ExameDTO> getExamesByAmostraId(@PathVariable Long amostraId) {
        return exameService.findExamesByAmostraId(amostraId);
    }

}
