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

import com.unipampa.rp6.backend.domain.Proprietario;
import com.unipampa.rp6.backend.repository.ProprietarioRepository;

@RestController
@RequestMapping("/api")
@Transactional
public class ProprietarioResource {

    private final Logger log = LoggerFactory.getLogger(ProprietarioResource.class);

    private static final String ENTITY_NAME = "proprietario";

    private final ProprietarioRepository proprietarioRepository;

    public ProprietarioResource(ProprietarioRepository proprietarioRepository) {
        this.proprietarioRepository = proprietarioRepository;
    }

    /**
     * {@code POST  /proprietarios} : Create a new proprietario.
     *
     * @param proprietario the proprietario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proprietario, or with status {@code 400 (Bad Request)} if the proprietario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proprietarios")
    public Proprietario createProprietario(@RequestBody Proprietario proprietario) throws URISyntaxException {
        log.debug("REST request to save Proprietario : {}", proprietario);
        Proprietario result = proprietarioRepository.save(proprietario);
        return result;
        		}

    @PutMapping("/proprietarios/{id}")
    public Proprietario updateProprietario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Proprietario proprietario
    ) throws URISyntaxException {
        log.debug("REST request to update Proprietario : {}, {}", id, proprietario);

        Proprietario result = proprietarioRepository.save(proprietario);
        return result;
    }

    @PatchMapping(value = "/proprietarios/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Proprietario> partialUpdateProprietario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Proprietario proprietario
    ) throws URISyntaxException {
        log.debug("REST request to partial update Proprietario partially : {}, {}", id, proprietario);

        Optional<Proprietario> result = proprietarioRepository
            .findById(proprietario.getId())
            .map(
                existingProprietario -> {
                    if (proprietario.getNome() != null) {
                        existingProprietario.setNome(proprietario.getNome());
                    }
                    if (proprietario.getTelefone() != null) {
                        existingProprietario.setTelefone(proprietario.getTelefone());
                    }
                    if (proprietario.getEmail() != null) {
                        existingProprietario.setEmail(proprietario.getEmail());
                    }

                    return existingProprietario;
                }
            )
            .map(proprietarioRepository::save);

        return null;
    }

    @GetMapping("/proprietarios")
    public List<Proprietario> getAllProprietarios() {
        log.debug("REST request to get all Proprietarios");
        return proprietarioRepository.findAll();
    }

    @GetMapping("/proprietarios/{id}")
    public Optional<Proprietario> getProprietario(@PathVariable Long id) {
        log.debug("REST request to get Proprietario : {}", id);
        Optional<Proprietario> proprietario = proprietarioRepository.findById(id);
        return proprietario;
    }

    @DeleteMapping("/proprietarios/{id}")
    public void deleteProprietario(@PathVariable Long id) {
        log.debug("REST request to delete Proprietario : {}", id);
        proprietarioRepository.deleteById(id);
    }
}
