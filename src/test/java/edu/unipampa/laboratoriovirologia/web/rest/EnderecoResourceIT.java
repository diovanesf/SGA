package edu.unipampa.laboratoriovirologia.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.unipampa.laboratoriovirologia.IntegrationTest;
import edu.unipampa.laboratoriovirologia.domain.Endereco;
import edu.unipampa.laboratoriovirologia.repository.EnderecoRepository;
import edu.unipampa.laboratoriovirologia.service.criteria.EnderecoCriteria;
import edu.unipampa.laboratoriovirologia.service.dto.EnderecoDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.EnderecoMapper;
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
 * Integration tests for the {@link EnderecoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnderecoResourceIT {

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_COORDENADAS_GPS = "AAAAAAAAAA";
    private static final String UPDATED_COORDENADAS_GPS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/enderecos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnderecoMockMvc;

    private Endereco endereco;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Endereco createEntity(EntityManager em) {
        Endereco endereco = new Endereco()
            .endereco(DEFAULT_ENDERECO)
            .cep(DEFAULT_CEP)
            .cidade(DEFAULT_CIDADE)
            .estado(DEFAULT_ESTADO)
            .coordenadasGps(DEFAULT_COORDENADAS_GPS);
        return endereco;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Endereco createUpdatedEntity(EntityManager em) {
        Endereco endereco = new Endereco()
            .endereco(UPDATED_ENDERECO)
            .cep(UPDATED_CEP)
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO)
            .coordenadasGps(UPDATED_COORDENADAS_GPS);
        return endereco;
    }

    @BeforeEach
    public void initTest() {
        endereco = createEntity(em);
    }

    @Test
    @Transactional
    void createEndereco() throws Exception {
        int databaseSizeBeforeCreate = enderecoRepository.findAll().size();
        // Create the Endereco
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(endereco);
        restEnderecoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoDTO)))
            .andExpect(status().isCreated());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeCreate + 1);
        Endereco testEndereco = enderecoList.get(enderecoList.size() - 1);
        assertThat(testEndereco.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testEndereco.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testEndereco.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testEndereco.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testEndereco.getCoordenadasGps()).isEqualTo(DEFAULT_COORDENADAS_GPS);
    }

    @Test
    @Transactional
    void createEnderecoWithExistingId() throws Exception {
        // Create the Endereco with an existing ID
        endereco.setId(1L);
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(endereco);

        int databaseSizeBeforeCreate = enderecoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnderecoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnderecos() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList
        restEnderecoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endereco.getId().intValue())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].coordenadasGps").value(hasItem(DEFAULT_COORDENADAS_GPS)));
    }

    @Test
    @Transactional
    void getEndereco() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get the endereco
        restEnderecoMockMvc
            .perform(get(ENTITY_API_URL_ID, endereco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(endereco.getId().intValue()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.coordenadasGps").value(DEFAULT_COORDENADAS_GPS));
    }

    @Test
    @Transactional
    void getEnderecosByIdFiltering() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        Long id = endereco.getId();

        defaultEnderecoShouldBeFound("id.equals=" + id);
        defaultEnderecoShouldNotBeFound("id.notEquals=" + id);

        defaultEnderecoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEnderecoShouldNotBeFound("id.greaterThan=" + id);

        defaultEnderecoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEnderecoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEnderecosByEnderecoIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where endereco equals to DEFAULT_ENDERECO
        defaultEnderecoShouldBeFound("endereco.equals=" + DEFAULT_ENDERECO);

        // Get all the enderecoList where endereco equals to UPDATED_ENDERECO
        defaultEnderecoShouldNotBeFound("endereco.equals=" + UPDATED_ENDERECO);
    }

    @Test
    @Transactional
    void getAllEnderecosByEnderecoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where endereco not equals to DEFAULT_ENDERECO
        defaultEnderecoShouldNotBeFound("endereco.notEquals=" + DEFAULT_ENDERECO);

        // Get all the enderecoList where endereco not equals to UPDATED_ENDERECO
        defaultEnderecoShouldBeFound("endereco.notEquals=" + UPDATED_ENDERECO);
    }

    @Test
    @Transactional
    void getAllEnderecosByEnderecoIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where endereco in DEFAULT_ENDERECO or UPDATED_ENDERECO
        defaultEnderecoShouldBeFound("endereco.in=" + DEFAULT_ENDERECO + "," + UPDATED_ENDERECO);

        // Get all the enderecoList where endereco equals to UPDATED_ENDERECO
        defaultEnderecoShouldNotBeFound("endereco.in=" + UPDATED_ENDERECO);
    }

    @Test
    @Transactional
    void getAllEnderecosByEnderecoIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where endereco is not null
        defaultEnderecoShouldBeFound("endereco.specified=true");

        // Get all the enderecoList where endereco is null
        defaultEnderecoShouldNotBeFound("endereco.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecosByEnderecoContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where endereco contains DEFAULT_ENDERECO
        defaultEnderecoShouldBeFound("endereco.contains=" + DEFAULT_ENDERECO);

        // Get all the enderecoList where endereco contains UPDATED_ENDERECO
        defaultEnderecoShouldNotBeFound("endereco.contains=" + UPDATED_ENDERECO);
    }

    @Test
    @Transactional
    void getAllEnderecosByEnderecoNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where endereco does not contain DEFAULT_ENDERECO
        defaultEnderecoShouldNotBeFound("endereco.doesNotContain=" + DEFAULT_ENDERECO);

        // Get all the enderecoList where endereco does not contain UPDATED_ENDERECO
        defaultEnderecoShouldBeFound("endereco.doesNotContain=" + UPDATED_ENDERECO);
    }

    @Test
    @Transactional
    void getAllEnderecosByCepIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cep equals to DEFAULT_CEP
        defaultEnderecoShouldBeFound("cep.equals=" + DEFAULT_CEP);

        // Get all the enderecoList where cep equals to UPDATED_CEP
        defaultEnderecoShouldNotBeFound("cep.equals=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllEnderecosByCepIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cep not equals to DEFAULT_CEP
        defaultEnderecoShouldNotBeFound("cep.notEquals=" + DEFAULT_CEP);

        // Get all the enderecoList where cep not equals to UPDATED_CEP
        defaultEnderecoShouldBeFound("cep.notEquals=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllEnderecosByCepIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cep in DEFAULT_CEP or UPDATED_CEP
        defaultEnderecoShouldBeFound("cep.in=" + DEFAULT_CEP + "," + UPDATED_CEP);

        // Get all the enderecoList where cep equals to UPDATED_CEP
        defaultEnderecoShouldNotBeFound("cep.in=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllEnderecosByCepIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cep is not null
        defaultEnderecoShouldBeFound("cep.specified=true");

        // Get all the enderecoList where cep is null
        defaultEnderecoShouldNotBeFound("cep.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecosByCepContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cep contains DEFAULT_CEP
        defaultEnderecoShouldBeFound("cep.contains=" + DEFAULT_CEP);

        // Get all the enderecoList where cep contains UPDATED_CEP
        defaultEnderecoShouldNotBeFound("cep.contains=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllEnderecosByCepNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cep does not contain DEFAULT_CEP
        defaultEnderecoShouldNotBeFound("cep.doesNotContain=" + DEFAULT_CEP);

        // Get all the enderecoList where cep does not contain UPDATED_CEP
        defaultEnderecoShouldBeFound("cep.doesNotContain=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllEnderecosByCidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cidade equals to DEFAULT_CIDADE
        defaultEnderecoShouldBeFound("cidade.equals=" + DEFAULT_CIDADE);

        // Get all the enderecoList where cidade equals to UPDATED_CIDADE
        defaultEnderecoShouldNotBeFound("cidade.equals=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    void getAllEnderecosByCidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cidade not equals to DEFAULT_CIDADE
        defaultEnderecoShouldNotBeFound("cidade.notEquals=" + DEFAULT_CIDADE);

        // Get all the enderecoList where cidade not equals to UPDATED_CIDADE
        defaultEnderecoShouldBeFound("cidade.notEquals=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    void getAllEnderecosByCidadeIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cidade in DEFAULT_CIDADE or UPDATED_CIDADE
        defaultEnderecoShouldBeFound("cidade.in=" + DEFAULT_CIDADE + "," + UPDATED_CIDADE);

        // Get all the enderecoList where cidade equals to UPDATED_CIDADE
        defaultEnderecoShouldNotBeFound("cidade.in=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    void getAllEnderecosByCidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cidade is not null
        defaultEnderecoShouldBeFound("cidade.specified=true");

        // Get all the enderecoList where cidade is null
        defaultEnderecoShouldNotBeFound("cidade.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecosByCidadeContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cidade contains DEFAULT_CIDADE
        defaultEnderecoShouldBeFound("cidade.contains=" + DEFAULT_CIDADE);

        // Get all the enderecoList where cidade contains UPDATED_CIDADE
        defaultEnderecoShouldNotBeFound("cidade.contains=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    void getAllEnderecosByCidadeNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cidade does not contain DEFAULT_CIDADE
        defaultEnderecoShouldNotBeFound("cidade.doesNotContain=" + DEFAULT_CIDADE);

        // Get all the enderecoList where cidade does not contain UPDATED_CIDADE
        defaultEnderecoShouldBeFound("cidade.doesNotContain=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    void getAllEnderecosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where estado equals to DEFAULT_ESTADO
        defaultEnderecoShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the enderecoList where estado equals to UPDATED_ESTADO
        defaultEnderecoShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllEnderecosByEstadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where estado not equals to DEFAULT_ESTADO
        defaultEnderecoShouldNotBeFound("estado.notEquals=" + DEFAULT_ESTADO);

        // Get all the enderecoList where estado not equals to UPDATED_ESTADO
        defaultEnderecoShouldBeFound("estado.notEquals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllEnderecosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultEnderecoShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the enderecoList where estado equals to UPDATED_ESTADO
        defaultEnderecoShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllEnderecosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where estado is not null
        defaultEnderecoShouldBeFound("estado.specified=true");

        // Get all the enderecoList where estado is null
        defaultEnderecoShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecosByEstadoContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where estado contains DEFAULT_ESTADO
        defaultEnderecoShouldBeFound("estado.contains=" + DEFAULT_ESTADO);

        // Get all the enderecoList where estado contains UPDATED_ESTADO
        defaultEnderecoShouldNotBeFound("estado.contains=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllEnderecosByEstadoNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where estado does not contain DEFAULT_ESTADO
        defaultEnderecoShouldNotBeFound("estado.doesNotContain=" + DEFAULT_ESTADO);

        // Get all the enderecoList where estado does not contain UPDATED_ESTADO
        defaultEnderecoShouldBeFound("estado.doesNotContain=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllEnderecosByCoordenadasGpsIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where coordenadasGps equals to DEFAULT_COORDENADAS_GPS
        defaultEnderecoShouldBeFound("coordenadasGps.equals=" + DEFAULT_COORDENADAS_GPS);

        // Get all the enderecoList where coordenadasGps equals to UPDATED_COORDENADAS_GPS
        defaultEnderecoShouldNotBeFound("coordenadasGps.equals=" + UPDATED_COORDENADAS_GPS);
    }

    @Test
    @Transactional
    void getAllEnderecosByCoordenadasGpsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where coordenadasGps not equals to DEFAULT_COORDENADAS_GPS
        defaultEnderecoShouldNotBeFound("coordenadasGps.notEquals=" + DEFAULT_COORDENADAS_GPS);

        // Get all the enderecoList where coordenadasGps not equals to UPDATED_COORDENADAS_GPS
        defaultEnderecoShouldBeFound("coordenadasGps.notEquals=" + UPDATED_COORDENADAS_GPS);
    }

    @Test
    @Transactional
    void getAllEnderecosByCoordenadasGpsIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where coordenadasGps in DEFAULT_COORDENADAS_GPS or UPDATED_COORDENADAS_GPS
        defaultEnderecoShouldBeFound("coordenadasGps.in=" + DEFAULT_COORDENADAS_GPS + "," + UPDATED_COORDENADAS_GPS);

        // Get all the enderecoList where coordenadasGps equals to UPDATED_COORDENADAS_GPS
        defaultEnderecoShouldNotBeFound("coordenadasGps.in=" + UPDATED_COORDENADAS_GPS);
    }

    @Test
    @Transactional
    void getAllEnderecosByCoordenadasGpsIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where coordenadasGps is not null
        defaultEnderecoShouldBeFound("coordenadasGps.specified=true");

        // Get all the enderecoList where coordenadasGps is null
        defaultEnderecoShouldNotBeFound("coordenadasGps.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecosByCoordenadasGpsContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where coordenadasGps contains DEFAULT_COORDENADAS_GPS
        defaultEnderecoShouldBeFound("coordenadasGps.contains=" + DEFAULT_COORDENADAS_GPS);

        // Get all the enderecoList where coordenadasGps contains UPDATED_COORDENADAS_GPS
        defaultEnderecoShouldNotBeFound("coordenadasGps.contains=" + UPDATED_COORDENADAS_GPS);
    }

    @Test
    @Transactional
    void getAllEnderecosByCoordenadasGpsNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where coordenadasGps does not contain DEFAULT_COORDENADAS_GPS
        defaultEnderecoShouldNotBeFound("coordenadasGps.doesNotContain=" + DEFAULT_COORDENADAS_GPS);

        // Get all the enderecoList where coordenadasGps does not contain UPDATED_COORDENADAS_GPS
        defaultEnderecoShouldBeFound("coordenadasGps.doesNotContain=" + UPDATED_COORDENADAS_GPS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEnderecoShouldBeFound(String filter) throws Exception {
        restEnderecoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endereco.getId().intValue())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].coordenadasGps").value(hasItem(DEFAULT_COORDENADAS_GPS)));

        // Check, that the count call also returns 1
        restEnderecoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEnderecoShouldNotBeFound(String filter) throws Exception {
        restEnderecoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnderecoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEndereco() throws Exception {
        // Get the endereco
        restEnderecoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEndereco() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        int databaseSizeBeforeUpdate = enderecoRepository.findAll().size();

        // Update the endereco
        Endereco updatedEndereco = enderecoRepository.findById(endereco.getId()).get();
        // Disconnect from session so that the updates on updatedEndereco are not directly saved in db
        em.detach(updatedEndereco);
        updatedEndereco
            .endereco(UPDATED_ENDERECO)
            .cep(UPDATED_CEP)
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO)
            .coordenadasGps(UPDATED_COORDENADAS_GPS);
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(updatedEndereco);

        restEnderecoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enderecoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enderecoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeUpdate);
        Endereco testEndereco = enderecoList.get(enderecoList.size() - 1);
        assertThat(testEndereco.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testEndereco.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testEndereco.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testEndereco.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testEndereco.getCoordenadasGps()).isEqualTo(UPDATED_COORDENADAS_GPS);
    }

    @Test
    @Transactional
    void putNonExistingEndereco() throws Exception {
        int databaseSizeBeforeUpdate = enderecoRepository.findAll().size();
        endereco.setId(count.incrementAndGet());

        // Create the Endereco
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(endereco);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enderecoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enderecoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEndereco() throws Exception {
        int databaseSizeBeforeUpdate = enderecoRepository.findAll().size();
        endereco.setId(count.incrementAndGet());

        // Create the Endereco
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(endereco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enderecoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEndereco() throws Exception {
        int databaseSizeBeforeUpdate = enderecoRepository.findAll().size();
        endereco.setId(count.incrementAndGet());

        // Create the Endereco
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(endereco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnderecoWithPatch() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        int databaseSizeBeforeUpdate = enderecoRepository.findAll().size();

        // Update the endereco using partial update
        Endereco partialUpdatedEndereco = new Endereco();
        partialUpdatedEndereco.setId(endereco.getId());

        partialUpdatedEndereco.endereco(UPDATED_ENDERECO).cidade(UPDATED_CIDADE);

        restEnderecoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEndereco.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEndereco))
            )
            .andExpect(status().isOk());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeUpdate);
        Endereco testEndereco = enderecoList.get(enderecoList.size() - 1);
        assertThat(testEndereco.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testEndereco.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testEndereco.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testEndereco.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testEndereco.getCoordenadasGps()).isEqualTo(DEFAULT_COORDENADAS_GPS);
    }

    @Test
    @Transactional
    void fullUpdateEnderecoWithPatch() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        int databaseSizeBeforeUpdate = enderecoRepository.findAll().size();

        // Update the endereco using partial update
        Endereco partialUpdatedEndereco = new Endereco();
        partialUpdatedEndereco.setId(endereco.getId());

        partialUpdatedEndereco
            .endereco(UPDATED_ENDERECO)
            .cep(UPDATED_CEP)
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO)
            .coordenadasGps(UPDATED_COORDENADAS_GPS);

        restEnderecoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEndereco.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEndereco))
            )
            .andExpect(status().isOk());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeUpdate);
        Endereco testEndereco = enderecoList.get(enderecoList.size() - 1);
        assertThat(testEndereco.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testEndereco.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testEndereco.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testEndereco.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testEndereco.getCoordenadasGps()).isEqualTo(UPDATED_COORDENADAS_GPS);
    }

    @Test
    @Transactional
    void patchNonExistingEndereco() throws Exception {
        int databaseSizeBeforeUpdate = enderecoRepository.findAll().size();
        endereco.setId(count.incrementAndGet());

        // Create the Endereco
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(endereco);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enderecoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enderecoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEndereco() throws Exception {
        int databaseSizeBeforeUpdate = enderecoRepository.findAll().size();
        endereco.setId(count.incrementAndGet());

        // Create the Endereco
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(endereco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enderecoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEndereco() throws Exception {
        int databaseSizeBeforeUpdate = enderecoRepository.findAll().size();
        endereco.setId(count.incrementAndGet());

        // Create the Endereco
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(endereco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(enderecoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEndereco() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        int databaseSizeBeforeDelete = enderecoRepository.findAll().size();

        // Delete the endereco
        restEnderecoMockMvc
            .perform(delete(ENTITY_API_URL_ID, endereco.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
