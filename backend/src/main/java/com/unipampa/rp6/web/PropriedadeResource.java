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

import com.unipampa.rp6.backend.domain.Propriedade;
import com.unipampa.rp6.backend.repository.PropriedadeRepository;

@RestController
@RequestMapping("/api")
@Transactional
public class PropriedadeResource {

    private final Logger log = LoggerFactory.getLogger(PropriedadeResource.class);

    private static final String ENTITY_NAME = "propriedade";

    private final PropriedadeRepository propriedadeRepository;

    public PropriedadeResource(PropriedadeRepository propriedadeRepository) {
        this.propriedadeRepository = propriedadeRepository;
    }

    @PostMapping("/propriedades")
    public Propriedade createPropriedade(@RequestBody Propriedade propriedade) throws URISyntaxException {
        log.debug("REST request to save Propriedade : {}", propriedade);
        Propriedade result = propriedadeRepository.save(propriedade);
        return result;
    }

    @PutMapping("/propriedades/{id}")
    public Propriedade updatePropriedade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Propriedade propriedade
    ) throws URISyntaxException {
        log.debug("REST request to update Propriedade : {}, {}", id, propriedade);
    
        Propriedade result = propriedadeRepository.save(propriedade);
        return result;
    }

    @PatchMapping(value = "/propriedades/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Propriedade> partialUpdatePropriedade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Propriedade propriedade
    ) throws URISyntaxException {
        log.debug("REST request to partial update Propriedade partially : {}, {}", id, propriedade);
    
        Optional<Propriedade> result = propriedadeRepository
            .findById(propriedade.getId())
            .map(
                existingPropriedade -> {
                    if (propriedade.getTipo() != null) {
                        existingPropriedade.setTipo(propriedade.getTipo());
                    }
                    if (propriedade.getNumeroAnimais() != null) {
                        existingPropriedade.setNumeroAnimais(propriedade.getNumeroAnimais());
                    }
                    if (propriedade.getAcometidos() != null) {
                        existingPropriedade.setAcometidos(propriedade.getAcometidos());
                    }
                    if (propriedade.getObservacoes() != null) {
                        existingPropriedade.setObservacoes(propriedade.getObservacoes());
                    }
                    if (propriedade.getPrincipalSuspeita() != null) {
                        existingPropriedade.setPrincipalSuspeita(propriedade.getPrincipalSuspeita());
                    }

                    return existingPropriedade;
                }
            )
            .map(propriedadeRepository::save);

        return null;
    }

    @GetMapping("/propriedades")
    public List<Propriedade> getAllPropriedades() {
        log.debug("REST request to get all Propriedades");
        return propriedadeRepository.findAll();
    }

    @GetMapping("/propriedades/{id}")
    public Optional<Propriedade> getPropriedade(@PathVariable Long id) {
        log.debug("REST request to get Propriedade : {}", id);
        Optional<Propriedade> propriedade = propriedadeRepository.findById(id);
        return propriedade;
    }

    @DeleteMapping("/propriedades/{id}")
    public void deletePropriedade(@PathVariable Long id) {
        log.debug("REST request to delete Propriedade : {}", id);
        propriedadeRepository.deleteById(id);
    }
}
