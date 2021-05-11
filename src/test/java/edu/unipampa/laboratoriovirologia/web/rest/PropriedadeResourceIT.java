package edu.unipampa.laboratoriovirologia.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.unipampa.laboratoriovirologia.IntegrationTest;
import edu.unipampa.laboratoriovirologia.domain.Endereco;
import edu.unipampa.laboratoriovirologia.domain.Propriedade;
import edu.unipampa.laboratoriovirologia.domain.Proprietario;
import edu.unipampa.laboratoriovirologia.repository.PropriedadeRepository;
import edu.unipampa.laboratoriovirologia.service.criteria.PropriedadeCriteria;
import edu.unipampa.laboratoriovirologia.service.dto.PropriedadeDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.PropriedadeMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PropriedadeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PropriedadeResourceIT {

    private static final String DEFAULT_TIPO_PROPRIEDADE = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_PROPRIEDADE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_CRIACAO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CRIACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/propriedades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private PropriedadeMapper propriedadeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPropriedadeMockMvc;

    private Propriedade propriedade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Propriedade createEntity(EntityManager em) {
        Propriedade propriedade = new Propriedade().tipoPropriedade(DEFAULT_TIPO_PROPRIEDADE).tipoCriacao(DEFAULT_TIPO_CRIACAO);
        return propriedade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Propriedade createUpdatedEntity(EntityManager em) {
        Propriedade propriedade = new Propriedade().tipoPropriedade(UPDATED_TIPO_PROPRIEDADE).tipoCriacao(UPDATED_TIPO_CRIACAO);
        return propriedade;
    }

    @BeforeEach
    public void initTest() {
        propriedade = createEntity(em);
    }

    @Test
    @Transactional
    void createPropriedade() throws Exception {
        int databaseSizeBeforeCreate = propriedadeRepository.findAll().size();
        // Create the Propriedade
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(propriedade);
        restPropriedadeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(propriedadeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Propriedade in the database
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeCreate + 1);
        Propriedade testPropriedade = propriedadeList.get(propriedadeList.size() - 1);
        assertThat(testPropriedade.getTipoPropriedade()).isEqualTo(DEFAULT_TIPO_PROPRIEDADE);
        assertThat(testPropriedade.getTipoCriacao()).isEqualTo(DEFAULT_TIPO_CRIACAO);
    }

    @Test
    @Transactional
    void createPropriedadeWithExistingId() throws Exception {
        // Create the Propriedade with an existing ID
        propriedade.setId(1L);
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(propriedade);

        int databaseSizeBeforeCreate = propriedadeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropriedadeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(propriedadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Propriedade in the database
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPropriedades() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList
        restPropriedadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propriedade.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoPropriedade").value(hasItem(DEFAULT_TIPO_PROPRIEDADE)))
            .andExpect(jsonPath("$.[*].tipoCriacao").value(hasItem(DEFAULT_TIPO_CRIACAO)));
    }

    @Test
    @Transactional
    void getPropriedade() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get the propriedade
        restPropriedadeMockMvc
            .perform(get(ENTITY_API_URL_ID, propriedade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(propriedade.getId().intValue()))
            .andExpect(jsonPath("$.tipoPropriedade").value(DEFAULT_TIPO_PROPRIEDADE))
            .andExpect(jsonPath("$.tipoCriacao").value(DEFAULT_TIPO_CRIACAO));
    }

    @Test
    @Transactional
    void getPropriedadesByIdFiltering() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        Long id = propriedade.getId();

        defaultPropriedadeShouldBeFound("id.equals=" + id);
        defaultPropriedadeShouldNotBeFound("id.notEquals=" + id);

        defaultPropriedadeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPropriedadeShouldNotBeFound("id.greaterThan=" + id);

        defaultPropriedadeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPropriedadeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPropriedadesByTipoPropriedadeIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where tipoPropriedade equals to DEFAULT_TIPO_PROPRIEDADE
        defaultPropriedadeShouldBeFound("tipoPropriedade.equals=" + DEFAULT_TIPO_PROPRIEDADE);

        // Get all the propriedadeList where tipoPropriedade equals to UPDATED_TIPO_PROPRIEDADE
        defaultPropriedadeShouldNotBeFound("tipoPropriedade.equals=" + UPDATED_TIPO_PROPRIEDADE);
    }

    @Test
    @Transactional
    void getAllPropriedadesByTipoPropriedadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where tipoPropriedade not equals to DEFAULT_TIPO_PROPRIEDADE
        defaultPropriedadeShouldNotBeFound("tipoPropriedade.notEquals=" + DEFAULT_TIPO_PROPRIEDADE);

        // Get all the propriedadeList where tipoPropriedade not equals to UPDATED_TIPO_PROPRIEDADE
        defaultPropriedadeShouldBeFound("tipoPropriedade.notEquals=" + UPDATED_TIPO_PROPRIEDADE);
    }

    @Test
    @Transactional
    void getAllPropriedadesByTipoPropriedadeIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where tipoPropriedade in DEFAULT_TIPO_PROPRIEDADE or UPDATED_TIPO_PROPRIEDADE
        defaultPropriedadeShouldBeFound("tipoPropriedade.in=" + DEFAULT_TIPO_PROPRIEDADE + "," + UPDATED_TIPO_PROPRIEDADE);

        // Get all the propriedadeList where tipoPropriedade equals to UPDATED_TIPO_PROPRIEDADE
        defaultPropriedadeShouldNotBeFound("tipoPropriedade.in=" + UPDATED_TIPO_PROPRIEDADE);
    }

    @Test
    @Transactional
    void getAllPropriedadesByTipoPropriedadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where tipoPropriedade is not null
        defaultPropriedadeShouldBeFound("tipoPropriedade.specified=true");

        // Get all the propriedadeList where tipoPropriedade is null
        defaultPropriedadeShouldNotBeFound("tipoPropriedade.specified=false");
    }

    @Test
    @Transactional
    void getAllPropriedadesByTipoPropriedadeContainsSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where tipoPropriedade contains DEFAULT_TIPO_PROPRIEDADE
        defaultPropriedadeShouldBeFound("tipoPropriedade.contains=" + DEFAULT_TIPO_PROPRIEDADE);

        // Get all the propriedadeList where tipoPropriedade contains UPDATED_TIPO_PROPRIEDADE
        defaultPropriedadeShouldNotBeFound("tipoPropriedade.contains=" + UPDATED_TIPO_PROPRIEDADE);
    }

    @Test
    @Transactional
    void getAllPropriedadesByTipoPropriedadeNotContainsSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where tipoPropriedade does not contain DEFAULT_TIPO_PROPRIEDADE
        defaultPropriedadeShouldNotBeFound("tipoPropriedade.doesNotContain=" + DEFAULT_TIPO_PROPRIEDADE);

        // Get all the propriedadeList where tipoPropriedade does not contain UPDATED_TIPO_PROPRIEDADE
        defaultPropriedadeShouldBeFound("tipoPropriedade.doesNotContain=" + UPDATED_TIPO_PROPRIEDADE);
    }

    @Test
    @Transactional
    void getAllPropriedadesByTipoCriacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where tipoCriacao equals to DEFAULT_TIPO_CRIACAO
        defaultPropriedadeShouldBeFound("tipoCriacao.equals=" + DEFAULT_TIPO_CRIACAO);

        // Get all the propriedadeList where tipoCriacao equals to UPDATED_TIPO_CRIACAO
        defaultPropriedadeShouldNotBeFound("tipoCriacao.equals=" + UPDATED_TIPO_CRIACAO);
    }

    @Test
    @Transactional
    void getAllPropriedadesByTipoCriacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where tipoCriacao not equals to DEFAULT_TIPO_CRIACAO
        defaultPropriedadeShouldNotBeFound("tipoCriacao.notEquals=" + DEFAULT_TIPO_CRIACAO);

        // Get all the propriedadeList where tipoCriacao not equals to UPDATED_TIPO_CRIACAO
        defaultPropriedadeShouldBeFound("tipoCriacao.notEquals=" + UPDATED_TIPO_CRIACAO);
    }

    @Test
    @Transactional
    void getAllPropriedadesByTipoCriacaoIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where tipoCriacao in DEFAULT_TIPO_CRIACAO or UPDATED_TIPO_CRIACAO
        defaultPropriedadeShouldBeFound("tipoCriacao.in=" + DEFAULT_TIPO_CRIACAO + "," + UPDATED_TIPO_CRIACAO);

        // Get all the propriedadeList where tipoCriacao equals to UPDATED_TIPO_CRIACAO
        defaultPropriedadeShouldNotBeFound("tipoCriacao.in=" + UPDATED_TIPO_CRIACAO);
    }

    @Test
    @Transactional
    void getAllPropriedadesByTipoCriacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where tipoCriacao is not null
        defaultPropriedadeShouldBeFound("tipoCriacao.specified=true");

        // Get all the propriedadeList where tipoCriacao is null
        defaultPropriedadeShouldNotBeFound("tipoCriacao.specified=false");
    }

    @Test
    @Transactional
    void getAllPropriedadesByTipoCriacaoContainsSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where tipoCriacao contains DEFAULT_TIPO_CRIACAO
        defaultPropriedadeShouldBeFound("tipoCriacao.contains=" + DEFAULT_TIPO_CRIACAO);

        // Get all the propriedadeList where tipoCriacao contains UPDATED_TIPO_CRIACAO
        defaultPropriedadeShouldNotBeFound("tipoCriacao.contains=" + UPDATED_TIPO_CRIACAO);
    }

    @Test
    @Transactional
    void getAllPropriedadesByTipoCriacaoNotContainsSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where tipoCriacao does not contain DEFAULT_TIPO_CRIACAO
        defaultPropriedadeShouldNotBeFound("tipoCriacao.doesNotContain=" + DEFAULT_TIPO_CRIACAO);

        // Get all the propriedadeList where tipoCriacao does not contain UPDATED_TIPO_CRIACAO
        defaultPropriedadeShouldBeFound("tipoCriacao.doesNotContain=" + UPDATED_TIPO_CRIACAO);
    }

    @Test
    @Transactional
    void getAllPropriedadesByProprietarioIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);
        Proprietario proprietario = ProprietarioResourceIT.createEntity(em);
        em.persist(proprietario);
        em.flush();
        propriedade.setProprietario(proprietario);
        propriedadeRepository.saveAndFlush(propriedade);
        Long proprietarioId = proprietario.getId();

        // Get all the propriedadeList where proprietario equals to proprietarioId
        defaultPropriedadeShouldBeFound("proprietarioId.equals=" + proprietarioId);

        // Get all the propriedadeList where proprietario equals to (proprietarioId + 1)
        defaultPropriedadeShouldNotBeFound("proprietarioId.equals=" + (proprietarioId + 1));
    }

    @Test
    @Transactional
    void getAllPropriedadesByEnderecoIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);
        Endereco endereco = EnderecoResourceIT.createEntity(em);
        em.persist(endereco);
        em.flush();
        propriedade.setEndereco(endereco);
        propriedadeRepository.saveAndFlush(propriedade);
        Long enderecoId = endereco.getId();

        // Get all the propriedadeList where endereco equals to enderecoId
        defaultPropriedadeShouldBeFound("enderecoId.equals=" + enderecoId);

        // Get all the propriedadeList where endereco equals to (enderecoId + 1)
        defaultPropriedadeShouldNotBeFound("enderecoId.equals=" + (enderecoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPropriedadeShouldBeFound(String filter) throws Exception {
        restPropriedadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propriedade.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoPropriedade").value(hasItem(DEFAULT_TIPO_PROPRIEDADE)))
            .andExpect(jsonPath("$.[*].tipoCriacao").value(hasItem(DEFAULT_TIPO_CRIACAO)));

        // Check, that the count call also returns 1
        restPropriedadeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPropriedadeShouldNotBeFound(String filter) throws Exception {
        restPropriedadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPropriedadeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPropriedade() throws Exception {
        // Get the propriedade
        restPropriedadeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPropriedade() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        int databaseSizeBeforeUpdate = propriedadeRepository.findAll().size();

        // Update the propriedade
        Propriedade updatedPropriedade = propriedadeRepository.findById(propriedade.getId()).get();
        // Disconnect from session so that the updates on updatedPropriedade are not directly saved in db
        em.detach(updatedPropriedade);
        updatedPropriedade.tipoPropriedade(UPDATED_TIPO_PROPRIEDADE).tipoCriacao(UPDATED_TIPO_CRIACAO);
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(updatedPropriedade);

        restPropriedadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, propriedadeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(propriedadeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Propriedade in the database
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeUpdate);
        Propriedade testPropriedade = propriedadeList.get(propriedadeList.size() - 1);
        assertThat(testPropriedade.getTipoPropriedade()).isEqualTo(UPDATED_TIPO_PROPRIEDADE);
        assertThat(testPropriedade.getTipoCriacao()).isEqualTo(UPDATED_TIPO_CRIACAO);
    }

    @Test
    @Transactional
    void putNonExistingPropriedade() throws Exception {
        int databaseSizeBeforeUpdate = propriedadeRepository.findAll().size();
        propriedade.setId(count.incrementAndGet());

        // Create the Propriedade
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(propriedade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropriedadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, propriedadeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(propriedadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Propriedade in the database
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPropriedade() throws Exception {
        int databaseSizeBeforeUpdate = propriedadeRepository.findAll().size();
        propriedade.setId(count.incrementAndGet());

        // Create the Propriedade
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(propriedade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropriedadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(propriedadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Propriedade in the database
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPropriedade() throws Exception {
        int databaseSizeBeforeUpdate = propriedadeRepository.findAll().size();
        propriedade.setId(count.incrementAndGet());

        // Create the Propriedade
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(propriedade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropriedadeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(propriedadeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Propriedade in the database
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePropriedadeWithPatch() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        int databaseSizeBeforeUpdate = propriedadeRepository.findAll().size();

        // Update the propriedade using partial update
        Propriedade partialUpdatedPropriedade = new Propriedade();
        partialUpdatedPropriedade.setId(propriedade.getId());

        partialUpdatedPropriedade.tipoPropriedade(UPDATED_TIPO_PROPRIEDADE).tipoCriacao(UPDATED_TIPO_CRIACAO);

        restPropriedadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPropriedade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPropriedade))
            )
            .andExpect(status().isOk());

        // Validate the Propriedade in the database
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeUpdate);
        Propriedade testPropriedade = propriedadeList.get(propriedadeList.size() - 1);
        assertThat(testPropriedade.getTipoPropriedade()).isEqualTo(UPDATED_TIPO_PROPRIEDADE);
        assertThat(testPropriedade.getTipoCriacao()).isEqualTo(UPDATED_TIPO_CRIACAO);
    }

    @Test
    @Transactional
    void fullUpdatePropriedadeWithPatch() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        int databaseSizeBeforeUpdate = propriedadeRepository.findAll().size();

        // Update the propriedade using partial update
        Propriedade partialUpdatedPropriedade = new Propriedade();
        partialUpdatedPropriedade.setId(propriedade.getId());

        partialUpdatedPropriedade.tipoPropriedade(UPDATED_TIPO_PROPRIEDADE).tipoCriacao(UPDATED_TIPO_CRIACAO);

        restPropriedadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPropriedade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPropriedade))
            )
            .andExpect(status().isOk());

        // Validate the Propriedade in the database
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeUpdate);
        Propriedade testPropriedade = propriedadeList.get(propriedadeList.size() - 1);
        assertThat(testPropriedade.getTipoPropriedade()).isEqualTo(UPDATED_TIPO_PROPRIEDADE);
        assertThat(testPropriedade.getTipoCriacao()).isEqualTo(UPDATED_TIPO_CRIACAO);
    }

    @Test
    @Transactional
    void patchNonExistingPropriedade() throws Exception {
        int databaseSizeBeforeUpdate = propriedadeRepository.findAll().size();
        propriedade.setId(count.incrementAndGet());

        // Create the Propriedade
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(propriedade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropriedadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, propriedadeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(propriedadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Propriedade in the database
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPropriedade() throws Exception {
        int databaseSizeBeforeUpdate = propriedadeRepository.findAll().size();
        propriedade.setId(count.incrementAndGet());

        // Create the Propriedade
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(propriedade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropriedadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(propriedadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Propriedade in the database
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPropriedade() throws Exception {
        int databaseSizeBeforeUpdate = propriedadeRepository.findAll().size();
        propriedade.setId(count.incrementAndGet());

        // Create the Propriedade
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(propriedade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropriedadeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(propriedadeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Propriedade in the database
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePropriedade() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        int databaseSizeBeforeDelete = propriedadeRepository.findAll().size();

        // Delete the propriedade
        restPropriedadeMockMvc
            .perform(delete(ENTITY_API_URL_ID, propriedade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
