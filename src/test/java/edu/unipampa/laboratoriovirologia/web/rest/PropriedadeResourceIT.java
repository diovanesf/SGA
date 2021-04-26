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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PropriedadeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PropriedadeResourceIT {

    private static final String DEFAULT_TIPO_PROPRIEDADE = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_PROPRIEDADE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_ANIMAIS = 1;
    private static final Integer UPDATED_NUMERO_ANIMAIS = 2;
    private static final Integer SMALLER_NUMERO_ANIMAIS = 1 - 1;

    private static final String DEFAULT_ACOMETIDOS = "AAAAAAAAAA";
    private static final String UPDATED_ACOMETIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final String DEFAULT_PRICIPAL_SUSPEITA = "AAAAAAAAAA";
    private static final String UPDATED_PRICIPAL_SUSPEITA = "BBBBBBBBBB";

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
        Propriedade propriedade = new Propriedade()
            .tipoPropriedade(DEFAULT_TIPO_PROPRIEDADE)
            .numeroAnimais(DEFAULT_NUMERO_ANIMAIS)
            .acometidos(DEFAULT_ACOMETIDOS)
            .observacoes(DEFAULT_OBSERVACOES)
            .pricipalSuspeita(DEFAULT_PRICIPAL_SUSPEITA)
            .tipoCriacao(DEFAULT_TIPO_CRIACAO);
        return propriedade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Propriedade createUpdatedEntity(EntityManager em) {
        Propriedade propriedade = new Propriedade()
            .tipoPropriedade(UPDATED_TIPO_PROPRIEDADE)
            .numeroAnimais(UPDATED_NUMERO_ANIMAIS)
            .acometidos(UPDATED_ACOMETIDOS)
            .observacoes(UPDATED_OBSERVACOES)
            .pricipalSuspeita(UPDATED_PRICIPAL_SUSPEITA)
            .tipoCriacao(UPDATED_TIPO_CRIACAO);
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
        assertThat(testPropriedade.getNumeroAnimais()).isEqualTo(DEFAULT_NUMERO_ANIMAIS);
        assertThat(testPropriedade.getAcometidos()).isEqualTo(DEFAULT_ACOMETIDOS);
        assertThat(testPropriedade.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testPropriedade.getPricipalSuspeita()).isEqualTo(DEFAULT_PRICIPAL_SUSPEITA);
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
            .andExpect(jsonPath("$.[*].numeroAnimais").value(hasItem(DEFAULT_NUMERO_ANIMAIS)))
            .andExpect(jsonPath("$.[*].acometidos").value(hasItem(DEFAULT_ACOMETIDOS)))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].pricipalSuspeita").value(hasItem(DEFAULT_PRICIPAL_SUSPEITA)))
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
            .andExpect(jsonPath("$.numeroAnimais").value(DEFAULT_NUMERO_ANIMAIS))
            .andExpect(jsonPath("$.acometidos").value(DEFAULT_ACOMETIDOS))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()))
            .andExpect(jsonPath("$.pricipalSuspeita").value(DEFAULT_PRICIPAL_SUSPEITA))
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
    void getAllPropriedadesByNumeroAnimaisIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where numeroAnimais equals to DEFAULT_NUMERO_ANIMAIS
        defaultPropriedadeShouldBeFound("numeroAnimais.equals=" + DEFAULT_NUMERO_ANIMAIS);

        // Get all the propriedadeList where numeroAnimais equals to UPDATED_NUMERO_ANIMAIS
        defaultPropriedadeShouldNotBeFound("numeroAnimais.equals=" + UPDATED_NUMERO_ANIMAIS);
    }

    @Test
    @Transactional
    void getAllPropriedadesByNumeroAnimaisIsNotEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where numeroAnimais not equals to DEFAULT_NUMERO_ANIMAIS
        defaultPropriedadeShouldNotBeFound("numeroAnimais.notEquals=" + DEFAULT_NUMERO_ANIMAIS);

        // Get all the propriedadeList where numeroAnimais not equals to UPDATED_NUMERO_ANIMAIS
        defaultPropriedadeShouldBeFound("numeroAnimais.notEquals=" + UPDATED_NUMERO_ANIMAIS);
    }

    @Test
    @Transactional
    void getAllPropriedadesByNumeroAnimaisIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where numeroAnimais in DEFAULT_NUMERO_ANIMAIS or UPDATED_NUMERO_ANIMAIS
        defaultPropriedadeShouldBeFound("numeroAnimais.in=" + DEFAULT_NUMERO_ANIMAIS + "," + UPDATED_NUMERO_ANIMAIS);

        // Get all the propriedadeList where numeroAnimais equals to UPDATED_NUMERO_ANIMAIS
        defaultPropriedadeShouldNotBeFound("numeroAnimais.in=" + UPDATED_NUMERO_ANIMAIS);
    }

    @Test
    @Transactional
    void getAllPropriedadesByNumeroAnimaisIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where numeroAnimais is not null
        defaultPropriedadeShouldBeFound("numeroAnimais.specified=true");

        // Get all the propriedadeList where numeroAnimais is null
        defaultPropriedadeShouldNotBeFound("numeroAnimais.specified=false");
    }

    @Test
    @Transactional
    void getAllPropriedadesByNumeroAnimaisIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where numeroAnimais is greater than or equal to DEFAULT_NUMERO_ANIMAIS
        defaultPropriedadeShouldBeFound("numeroAnimais.greaterThanOrEqual=" + DEFAULT_NUMERO_ANIMAIS);

        // Get all the propriedadeList where numeroAnimais is greater than or equal to UPDATED_NUMERO_ANIMAIS
        defaultPropriedadeShouldNotBeFound("numeroAnimais.greaterThanOrEqual=" + UPDATED_NUMERO_ANIMAIS);
    }

    @Test
    @Transactional
    void getAllPropriedadesByNumeroAnimaisIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where numeroAnimais is less than or equal to DEFAULT_NUMERO_ANIMAIS
        defaultPropriedadeShouldBeFound("numeroAnimais.lessThanOrEqual=" + DEFAULT_NUMERO_ANIMAIS);

        // Get all the propriedadeList where numeroAnimais is less than or equal to SMALLER_NUMERO_ANIMAIS
        defaultPropriedadeShouldNotBeFound("numeroAnimais.lessThanOrEqual=" + SMALLER_NUMERO_ANIMAIS);
    }

    @Test
    @Transactional
    void getAllPropriedadesByNumeroAnimaisIsLessThanSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where numeroAnimais is less than DEFAULT_NUMERO_ANIMAIS
        defaultPropriedadeShouldNotBeFound("numeroAnimais.lessThan=" + DEFAULT_NUMERO_ANIMAIS);

        // Get all the propriedadeList where numeroAnimais is less than UPDATED_NUMERO_ANIMAIS
        defaultPropriedadeShouldBeFound("numeroAnimais.lessThan=" + UPDATED_NUMERO_ANIMAIS);
    }

    @Test
    @Transactional
    void getAllPropriedadesByNumeroAnimaisIsGreaterThanSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where numeroAnimais is greater than DEFAULT_NUMERO_ANIMAIS
        defaultPropriedadeShouldNotBeFound("numeroAnimais.greaterThan=" + DEFAULT_NUMERO_ANIMAIS);

        // Get all the propriedadeList where numeroAnimais is greater than SMALLER_NUMERO_ANIMAIS
        defaultPropriedadeShouldBeFound("numeroAnimais.greaterThan=" + SMALLER_NUMERO_ANIMAIS);
    }

    @Test
    @Transactional
    void getAllPropriedadesByAcometidosIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where acometidos equals to DEFAULT_ACOMETIDOS
        defaultPropriedadeShouldBeFound("acometidos.equals=" + DEFAULT_ACOMETIDOS);

        // Get all the propriedadeList where acometidos equals to UPDATED_ACOMETIDOS
        defaultPropriedadeShouldNotBeFound("acometidos.equals=" + UPDATED_ACOMETIDOS);
    }

    @Test
    @Transactional
    void getAllPropriedadesByAcometidosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where acometidos not equals to DEFAULT_ACOMETIDOS
        defaultPropriedadeShouldNotBeFound("acometidos.notEquals=" + DEFAULT_ACOMETIDOS);

        // Get all the propriedadeList where acometidos not equals to UPDATED_ACOMETIDOS
        defaultPropriedadeShouldBeFound("acometidos.notEquals=" + UPDATED_ACOMETIDOS);
    }

    @Test
    @Transactional
    void getAllPropriedadesByAcometidosIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where acometidos in DEFAULT_ACOMETIDOS or UPDATED_ACOMETIDOS
        defaultPropriedadeShouldBeFound("acometidos.in=" + DEFAULT_ACOMETIDOS + "," + UPDATED_ACOMETIDOS);

        // Get all the propriedadeList where acometidos equals to UPDATED_ACOMETIDOS
        defaultPropriedadeShouldNotBeFound("acometidos.in=" + UPDATED_ACOMETIDOS);
    }

    @Test
    @Transactional
    void getAllPropriedadesByAcometidosIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where acometidos is not null
        defaultPropriedadeShouldBeFound("acometidos.specified=true");

        // Get all the propriedadeList where acometidos is null
        defaultPropriedadeShouldNotBeFound("acometidos.specified=false");
    }

    @Test
    @Transactional
    void getAllPropriedadesByAcometidosContainsSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where acometidos contains DEFAULT_ACOMETIDOS
        defaultPropriedadeShouldBeFound("acometidos.contains=" + DEFAULT_ACOMETIDOS);

        // Get all the propriedadeList where acometidos contains UPDATED_ACOMETIDOS
        defaultPropriedadeShouldNotBeFound("acometidos.contains=" + UPDATED_ACOMETIDOS);
    }

    @Test
    @Transactional
    void getAllPropriedadesByAcometidosNotContainsSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where acometidos does not contain DEFAULT_ACOMETIDOS
        defaultPropriedadeShouldNotBeFound("acometidos.doesNotContain=" + DEFAULT_ACOMETIDOS);

        // Get all the propriedadeList where acometidos does not contain UPDATED_ACOMETIDOS
        defaultPropriedadeShouldBeFound("acometidos.doesNotContain=" + UPDATED_ACOMETIDOS);
    }

    @Test
    @Transactional
    void getAllPropriedadesByPricipalSuspeitaIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where pricipalSuspeita equals to DEFAULT_PRICIPAL_SUSPEITA
        defaultPropriedadeShouldBeFound("pricipalSuspeita.equals=" + DEFAULT_PRICIPAL_SUSPEITA);

        // Get all the propriedadeList where pricipalSuspeita equals to UPDATED_PRICIPAL_SUSPEITA
        defaultPropriedadeShouldNotBeFound("pricipalSuspeita.equals=" + UPDATED_PRICIPAL_SUSPEITA);
    }

    @Test
    @Transactional
    void getAllPropriedadesByPricipalSuspeitaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where pricipalSuspeita not equals to DEFAULT_PRICIPAL_SUSPEITA
        defaultPropriedadeShouldNotBeFound("pricipalSuspeita.notEquals=" + DEFAULT_PRICIPAL_SUSPEITA);

        // Get all the propriedadeList where pricipalSuspeita not equals to UPDATED_PRICIPAL_SUSPEITA
        defaultPropriedadeShouldBeFound("pricipalSuspeita.notEquals=" + UPDATED_PRICIPAL_SUSPEITA);
    }

    @Test
    @Transactional
    void getAllPropriedadesByPricipalSuspeitaIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where pricipalSuspeita in DEFAULT_PRICIPAL_SUSPEITA or UPDATED_PRICIPAL_SUSPEITA
        defaultPropriedadeShouldBeFound("pricipalSuspeita.in=" + DEFAULT_PRICIPAL_SUSPEITA + "," + UPDATED_PRICIPAL_SUSPEITA);

        // Get all the propriedadeList where pricipalSuspeita equals to UPDATED_PRICIPAL_SUSPEITA
        defaultPropriedadeShouldNotBeFound("pricipalSuspeita.in=" + UPDATED_PRICIPAL_SUSPEITA);
    }

    @Test
    @Transactional
    void getAllPropriedadesByPricipalSuspeitaIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where pricipalSuspeita is not null
        defaultPropriedadeShouldBeFound("pricipalSuspeita.specified=true");

        // Get all the propriedadeList where pricipalSuspeita is null
        defaultPropriedadeShouldNotBeFound("pricipalSuspeita.specified=false");
    }

    @Test
    @Transactional
    void getAllPropriedadesByPricipalSuspeitaContainsSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where pricipalSuspeita contains DEFAULT_PRICIPAL_SUSPEITA
        defaultPropriedadeShouldBeFound("pricipalSuspeita.contains=" + DEFAULT_PRICIPAL_SUSPEITA);

        // Get all the propriedadeList where pricipalSuspeita contains UPDATED_PRICIPAL_SUSPEITA
        defaultPropriedadeShouldNotBeFound("pricipalSuspeita.contains=" + UPDATED_PRICIPAL_SUSPEITA);
    }

    @Test
    @Transactional
    void getAllPropriedadesByPricipalSuspeitaNotContainsSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where pricipalSuspeita does not contain DEFAULT_PRICIPAL_SUSPEITA
        defaultPropriedadeShouldNotBeFound("pricipalSuspeita.doesNotContain=" + DEFAULT_PRICIPAL_SUSPEITA);

        // Get all the propriedadeList where pricipalSuspeita does not contain UPDATED_PRICIPAL_SUSPEITA
        defaultPropriedadeShouldBeFound("pricipalSuspeita.doesNotContain=" + UPDATED_PRICIPAL_SUSPEITA);
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
            .andExpect(jsonPath("$.[*].numeroAnimais").value(hasItem(DEFAULT_NUMERO_ANIMAIS)))
            .andExpect(jsonPath("$.[*].acometidos").value(hasItem(DEFAULT_ACOMETIDOS)))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].pricipalSuspeita").value(hasItem(DEFAULT_PRICIPAL_SUSPEITA)))
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
        updatedPropriedade
            .tipoPropriedade(UPDATED_TIPO_PROPRIEDADE)
            .numeroAnimais(UPDATED_NUMERO_ANIMAIS)
            .acometidos(UPDATED_ACOMETIDOS)
            .observacoes(UPDATED_OBSERVACOES)
            .pricipalSuspeita(UPDATED_PRICIPAL_SUSPEITA)
            .tipoCriacao(UPDATED_TIPO_CRIACAO);
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
        assertThat(testPropriedade.getNumeroAnimais()).isEqualTo(UPDATED_NUMERO_ANIMAIS);
        assertThat(testPropriedade.getAcometidos()).isEqualTo(UPDATED_ACOMETIDOS);
        assertThat(testPropriedade.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testPropriedade.getPricipalSuspeita()).isEqualTo(UPDATED_PRICIPAL_SUSPEITA);
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

        partialUpdatedPropriedade
            .tipoPropriedade(UPDATED_TIPO_PROPRIEDADE)
            .numeroAnimais(UPDATED_NUMERO_ANIMAIS)
            .acometidos(UPDATED_ACOMETIDOS)
            .observacoes(UPDATED_OBSERVACOES)
            .pricipalSuspeita(UPDATED_PRICIPAL_SUSPEITA)
            .tipoCriacao(UPDATED_TIPO_CRIACAO);

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
        assertThat(testPropriedade.getNumeroAnimais()).isEqualTo(UPDATED_NUMERO_ANIMAIS);
        assertThat(testPropriedade.getAcometidos()).isEqualTo(UPDATED_ACOMETIDOS);
        assertThat(testPropriedade.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testPropriedade.getPricipalSuspeita()).isEqualTo(UPDATED_PRICIPAL_SUSPEITA);
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

        partialUpdatedPropriedade
            .tipoPropriedade(UPDATED_TIPO_PROPRIEDADE)
            .numeroAnimais(UPDATED_NUMERO_ANIMAIS)
            .acometidos(UPDATED_ACOMETIDOS)
            .observacoes(UPDATED_OBSERVACOES)
            .pricipalSuspeita(UPDATED_PRICIPAL_SUSPEITA)
            .tipoCriacao(UPDATED_TIPO_CRIACAO);

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
        assertThat(testPropriedade.getNumeroAnimais()).isEqualTo(UPDATED_NUMERO_ANIMAIS);
        assertThat(testPropriedade.getAcometidos()).isEqualTo(UPDATED_ACOMETIDOS);
        assertThat(testPropriedade.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testPropriedade.getPricipalSuspeita()).isEqualTo(UPDATED_PRICIPAL_SUSPEITA);
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
