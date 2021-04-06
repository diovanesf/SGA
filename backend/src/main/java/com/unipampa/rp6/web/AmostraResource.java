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

import com.unipampa.rp6.backend.domain.Amostra;
import com.unipampa.rp6.backend.repository.AmostraRepository;

@RestController
@RequestMapping("/api")
@Transactional
public class AmostraResource {

    private final Logger log = LoggerFactory.getLogger(AmostraResource.class);

    private static final String ENTITY_NAME = "amostra";

    private final AmostraRepository amostraRepository;

    public AmostraResource(AmostraRepository amostraRepository) {
        this.amostraRepository = amostraRepository;
    }

    /**
     * {@code POST  /amostras} : Create a new amostra.
     *
     * @param amostra the amostra to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amostra, or with status {@code 400 (Bad Request)} if the amostra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amostras")
    public Amostra createAmostra(@RequestBody Amostra amostra) throws URISyntaxException {
        log.debug("REST request to save Amostra : {}", amostra);
        
        Amostra result = amostraRepository.save(amostra);
        return result;
    }

    @PutMapping("/amostras/{id}")
    public Amostra updateAmostra(@PathVariable(value = "id", required = false) final Long id, @RequestBody Amostra amostra)
        throws URISyntaxException {
        log.debug("REST request to update Amostra : {}, {}", id, amostra);
        

        Amostra result = amostraRepository.save(amostra);
        return result;
    }

    @PatchMapping(value = "/amostras/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Amostra> partialUpdateAmostra(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Amostra amostra
    ) throws URISyntaxException {
        log.debug("REST request to partial update Amostra partially : {}, {}", id, amostra);
    
        Optional<Amostra> result = amostraRepository
            .findById(amostra.getId())
            .map(
                existingAmostra -> {
                    if (amostra.getProtocolo() != null) {
                        existingAmostra.setProtocolo(amostra.getProtocolo());
                    }
                    if (amostra.getFormaEnvio() != null) {
                        existingAmostra.setFormaEnvio(amostra.getFormaEnvio());
                    }
                    if (amostra.getNumeroAmostras() != null) {
                        existingAmostra.setNumeroAmostras(amostra.getNumeroAmostras());
                    }
                    if (amostra.getEspecie() != null) {
                        existingAmostra.setEspecie(amostra.getEspecie());
                    }
                    if (amostra.getMaterialRecebido() != null) {
                        existingAmostra.setMaterialRecebido(amostra.getMaterialRecebido());
                    }
                    if (amostra.getAcondicionamento() != null) {
                        existingAmostra.setAcondicionamento(amostra.getAcondicionamento());
                    }
                    if (amostra.getCondicaoMaterial() != null) {
                        existingAmostra.setCondicaoMaterial(amostra.getCondicaoMaterial());
                    }
                    if (amostra.getStatus() != null) {
                        existingAmostra.setStatus(amostra.getStatus());
                    }

                    return existingAmostra;
                }
            )
            .map(amostraRepository::save);

        return null;
    }

    @GetMapping("/amostras")
    public List<Amostra> getAllAmostras() {
        log.debug("REST request to get all Amostras");
        return amostraRepository.findAll();
    }

    @GetMapping("/amostras/{id}")
    public Optional<Amostra> getAmostra(@PathVariable Long id) {
        log.debug("REST request to get Amostra : {}", id);
        Optional<Amostra> amostra = amostraRepository.findById(id);
        return amostra;
    }

    @DeleteMapping("/amostras/{id}")
    public void deleteAmostra(@PathVariable Long id) {
        log.debug("REST request to delete Amostra : {}", id);
        amostraRepository.deleteById(id);
    }
}
