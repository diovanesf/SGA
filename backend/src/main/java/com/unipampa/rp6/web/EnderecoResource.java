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

import com.unipampa.rp6.backend.domain.Endereco;
import com.unipampa.rp6.backend.repository.EnderecoRepository;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Endereco}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EnderecoResource {

    private final Logger log = LoggerFactory.getLogger(EnderecoResource.class);

    private static final String ENTITY_NAME = "endereco";

    private final EnderecoRepository enderecoRepository;

    public EnderecoResource(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @PostMapping("/enderecos")
    public Endereco createEndereco(@RequestBody Endereco endereco) throws URISyntaxException {
        log.debug("REST request to save Endereco : {}", endereco);
        Endereco result = enderecoRepository.save(endereco);
        return result;
        }

    @PutMapping("/enderecos/{id}")
    public Endereco updateEndereco(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Endereco endereco
    ) throws URISyntaxException {
        log.debug("REST request to update Endereco : {}, {}", id, endereco);
    
        Endereco result = enderecoRepository.save(endereco);
        return result;
    }

    @PatchMapping(value = "/enderecos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Endereco> partialUpdateEndereco(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Endereco endereco
    ) throws URISyntaxException {
        log.debug("REST request to partial update Endereco partially : {}, {}", id, endereco);
    
        Optional<Endereco> result = enderecoRepository
            .findById(endereco.getId())
            .map(
                existingEndereco -> {
                    if (endereco.getEndereco() != null) {
                        existingEndereco.setEndereco(endereco.getEndereco());
                    }
                    if (endereco.getCep() != null) {
                        existingEndereco.setCep(endereco.getCep());
                    }
                    if (endereco.getCidade() != null) {
                        existingEndereco.setCidade(endereco.getCidade());
                    }
                    if (endereco.getEstado() != null) {
                        existingEndereco.setEstado(endereco.getEstado());
                    }
                    if (endereco.getCoodernadasGps() != null) {
                        existingEndereco.setCoodernadasGps(endereco.getCoodernadasGps());
                    }

                    return existingEndereco;
                }
            )
            .map(enderecoRepository::save);

        return null;
    }

    @GetMapping("/enderecos")
    public List<Endereco> getAllEnderecos() {
        log.debug("REST request to get all Enderecos");
        return enderecoRepository.findAll();
    }

    @GetMapping("/enderecos/{id}")
    public Optional<Endereco> getEndereco(@PathVariable Long id) {
        log.debug("REST request to get Endereco : {}", id);
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        return endereco;
    }

    @DeleteMapping("/enderecos/{id}")
    public void deleteEndereco(@PathVariable Long id) {
        log.debug("REST request to delete Endereco : {}", id);
        enderecoRepository.deleteById(id);
    }
}
