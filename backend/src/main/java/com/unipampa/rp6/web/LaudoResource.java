package com.unipampa.rp6.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.tomcat.util.http.HeaderUtil;
import org.apache.tomcat.util.http.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unipampa.rp6.backend.domain.Laudo;
import com.unipampa.rp6.backend.repository.LaudoRepository;

@RestController
@RequestMapping("/api")
@Transactional
public class LaudoResource {

    private final Logger log = LoggerFactory.getLogger(LaudoResource.class);

    private static final String ENTITY_NAME = "laudo";

    private final LaudoRepository laudoRepository;

    public LaudoResource(LaudoRepository laudoRepository) {
        this.laudoRepository = laudoRepository;
    }

    /**
     * {@code POST  /laudos} : Create a new laudo.
     *
     * @param laudo the laudo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new laudo, or with status {@code 400 (Bad Request)} if the laudo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/laudos")
    public Laudo createLaudo(@RequestBody Laudo laudo) throws URISyntaxException {
        log.debug("REST request to save Laudo : {}", laudo);
        Laudo result = laudoRepository.save(laudo);
        return result;
    }

    @PutMapping("/laudos/{id}")
    public Laudo updateLaudo(@PathVariable(value = "id", required = false) final Long id, @RequestBody Laudo laudo)
        throws URISyntaxException {
        log.debug("REST request to update Laudo : {}, {}", id, laudo);

        Laudo result = laudoRepository.save(laudo);
        return result;
        		}

    @PatchMapping(value = "/laudos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Laudo> partialUpdateLaudo(@PathVariable(value = "id", required = false) final Long id, @RequestBody Laudo laudo)
        throws URISyntaxException {
        log.debug("REST request to partial update Laudo partially : {}, {}", id, laudo);
    
        Optional<Laudo> result = laudoRepository
            .findById(laudo.getId())
            .map(
                existingLaudo -> {
                    if (laudo.getDados() != null) {
                        existingLaudo.setDados(laudo.getDados());
                    }

                    return existingLaudo;
                }
            )
            .map(laudoRepository::save);

        return null;
    }

    @GetMapping("/laudos")
    public List<Laudo> getAllLaudos() {
        log.debug("REST request to get all Laudos");
        return laudoRepository.findAll();
    }

    @GetMapping("/laudos/{id}")
    public Optional<Laudo> getLaudo(@PathVariable Long id) {
        log.debug("REST request to get Laudo : {}", id);
        Optional<Laudo> laudo = laudoRepository.findById(id);
        return laudo;
    }

    @DeleteMapping("/laudos/{id}")
    public void deleteLaudo(@PathVariable Long id) {
        log.debug("REST request to delete Laudo : {}", id);
        laudoRepository.deleteById(id);
   }
}
