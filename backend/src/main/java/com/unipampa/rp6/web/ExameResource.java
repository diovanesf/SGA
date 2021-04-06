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

import com.unipampa.rp6.backend.domain.Exame;
import com.unipampa.rp6.backend.repository.ExameRepository;

@RestController
@RequestMapping("/api")
@Transactional
public class ExameResource {

    private final Logger log = LoggerFactory.getLogger(ExameResource.class);

    private static final String ENTITY_NAME = "exame";

    private final ExameRepository exameRepository;

    public ExameResource(ExameRepository exameRepository) {
        this.exameRepository = exameRepository;
    }

    @PostMapping("/exames")
    public Exame createExame(@RequestBody Exame exame) throws URISyntaxException {
        log.debug("REST request to save Exame : {}", exame);
        Exame result = exameRepository.save(exame);
        return result;
    }

    @PutMapping("/exames/{id}")
    public Exame updateExame(@PathVariable(value = "id", required = false) final Long id, @RequestBody Exame exame)
        throws URISyntaxException {
        log.debug("REST request to update Exame : {}, {}", id, exame);
    
        Exame result = exameRepository.save(exame);
        return result;
    }

    @PatchMapping(value = "/exames/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Exame> partialUpdateExame(@PathVariable(value = "id", required = false) final Long id, @RequestBody Exame exame)
        throws URISyntaxException {
        log.debug("REST request to partial update Exame partially : {}, {}", id, exame);
    
        Optional<Exame> result = exameRepository
            .findById(exame.getId())
            .map(
                existingExame -> {
                    if (exame.getNome() != null) {
                        existingExame.setNome(exame.getNome());
                    }
                    if (exame.getTipo() != null) {
                        existingExame.setTipo(exame.getTipo());
                    }

                    return existingExame;
                }
            )
            .map(exameRepository::save);

        return null;
    }

    @GetMapping("/exames")
    public List<Exame> getAllExames() {
        log.debug("REST request to get all Exames");
        return exameRepository.findAll();
    }

    @GetMapping("/exames/{id}")
    public Optional<Exame> getExame(@PathVariable Long id) {
        log.debug("REST request to get Exame : {}", id);
        Optional<Exame> exame = exameRepository.findById(id);
        return exame;
    }

    @DeleteMapping("/exames/{id}")
    public void deleteExame(@PathVariable Long id) {
        log.debug("REST request to delete Exame : {}", id);
        exameRepository.deleteById(id);
    }
}
