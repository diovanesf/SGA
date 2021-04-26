package edu.unipampa.laboratoriovirologia.web.rest;

import static edu.unipampa.laboratoriovirologia.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.unipampa.laboratoriovirologia.IntegrationTest;
import edu.unipampa.laboratoriovirologia.domain.Amostra;
import edu.unipampa.laboratoriovirologia.domain.Exame;
import edu.unipampa.laboratoriovirologia.domain.Medicoveterinario;
import edu.unipampa.laboratoriovirologia.domain.Midia;
import edu.unipampa.laboratoriovirologia.domain.Propriedade;
import edu.unipampa.laboratoriovirologia.domain.User;
import edu.unipampa.laboratoriovirologia.repository.AmostraRepository;
import edu.unipampa.laboratoriovirologia.service.AmostraService;
import edu.unipampa.laboratoriovirologia.service.criteria.AmostraCriteria;
import edu.unipampa.laboratoriovirologia.service.dto.AmostraDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.AmostraMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AmostraResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AmostraResourceIT {

    private static final String DEFAULT_PROTOCOLO = "AAAAAAAAAA";
    private static final String UPDATED_PROTOCOLO = "BBBBBBBBBB";

    private static final String DEFAULT_FORMA_ENVIO = "AAAAAAAAAA";
    private static final String UPDATED_FORMA_ENVIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_AMOSTRAS = 1;
    private static final Integer UPDATED_NUMERO_AMOSTRAS = 2;
    private static final Integer SMALLER_NUMERO_AMOSTRAS = 1 - 1;

    private static final String DEFAULT_ESPECIE = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_INICIAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INICIAL = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_INICIAL = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATA_FINAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FINAL = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_FINAL = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_MATERIAL_RECEBIDO = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_RECEBIDO = "BBBBBBBBBB";

    private static final String DEFAULT_ACONDICIONAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_ACONDICIONAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_CONDICAO_MATERIAL = "AAAAAAAAAA";
    private static final String UPDATED_CONDICAO_MATERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_MED_VET = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_MED_VET = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_TOTAL = new BigDecimal(1 - 1);

    private static final String DEFAULT_TIPO_PAGAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_PAGAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_SITUACAO = "AAAAAAAAAA";
    private static final String UPDATED_SITUACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/amostras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AmostraRepository amostraRepository;

    @Mock
    private AmostraRepository amostraRepositoryMock;

    @Autowired
    private AmostraMapper amostraMapper;

    @Mock
    private AmostraService amostraServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAmostraMockMvc;

    private Amostra amostra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Amostra createEntity(EntityManager em) {
        Amostra amostra = new Amostra()
            .protocolo(DEFAULT_PROTOCOLO)
            .formaEnvio(DEFAULT_FORMA_ENVIO)
            .numeroAmostras(DEFAULT_NUMERO_AMOSTRAS)
            .especie(DEFAULT_ESPECIE)
            .dataInicial(DEFAULT_DATA_INICIAL)
            .dataFinal(DEFAULT_DATA_FINAL)
            .materialRecebido(DEFAULT_MATERIAL_RECEBIDO)
            .acondicionamento(DEFAULT_ACONDICIONAMENTO)
            .condicaoMaterial(DEFAULT_CONDICAO_MATERIAL)
            .status(DEFAULT_STATUS)
            .tipoMedVet(DEFAULT_TIPO_MED_VET)
            .valorTotal(DEFAULT_VALOR_TOTAL)
            .tipoPagamento(DEFAULT_TIPO_PAGAMENTO)
            .situacao(DEFAULT_SITUACAO);
        return amostra;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Amostra createUpdatedEntity(EntityManager em) {
        Amostra amostra = new Amostra()
            .protocolo(UPDATED_PROTOCOLO)
            .formaEnvio(UPDATED_FORMA_ENVIO)
            .numeroAmostras(UPDATED_NUMERO_AMOSTRAS)
            .especie(UPDATED_ESPECIE)
            .dataInicial(UPDATED_DATA_INICIAL)
            .dataFinal(UPDATED_DATA_FINAL)
            .materialRecebido(UPDATED_MATERIAL_RECEBIDO)
            .acondicionamento(UPDATED_ACONDICIONAMENTO)
            .condicaoMaterial(UPDATED_CONDICAO_MATERIAL)
            .status(UPDATED_STATUS)
            .tipoMedVet(UPDATED_TIPO_MED_VET)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .tipoPagamento(UPDATED_TIPO_PAGAMENTO)
            .situacao(UPDATED_SITUACAO);
        return amostra;
    }

    @BeforeEach
    public void initTest() {
        amostra = createEntity(em);
    }

    @Test
    @Transactional
    void createAmostra() throws Exception {
        int databaseSizeBeforeCreate = amostraRepository.findAll().size();
        // Create the Amostra
        AmostraDTO amostraDTO = amostraMapper.toDto(amostra);
        restAmostraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(amostraDTO)))
            .andExpect(status().isCreated());

        // Validate the Amostra in the database
        List<Amostra> amostraList = amostraRepository.findAll();
        assertThat(amostraList).hasSize(databaseSizeBeforeCreate + 1);
        Amostra testAmostra = amostraList.get(amostraList.size() - 1);
        assertThat(testAmostra.getProtocolo()).isEqualTo(DEFAULT_PROTOCOLO);
        assertThat(testAmostra.getFormaEnvio()).isEqualTo(DEFAULT_FORMA_ENVIO);
        assertThat(testAmostra.getNumeroAmostras()).isEqualTo(DEFAULT_NUMERO_AMOSTRAS);
        assertThat(testAmostra.getEspecie()).isEqualTo(DEFAULT_ESPECIE);
        assertThat(testAmostra.getDataInicial()).isEqualTo(DEFAULT_DATA_INICIAL);
        assertThat(testAmostra.getDataFinal()).isEqualTo(DEFAULT_DATA_FINAL);
        assertThat(testAmostra.getMaterialRecebido()).isEqualTo(DEFAULT_MATERIAL_RECEBIDO);
        assertThat(testAmostra.getAcondicionamento()).isEqualTo(DEFAULT_ACONDICIONAMENTO);
        assertThat(testAmostra.getCondicaoMaterial()).isEqualTo(DEFAULT_CONDICAO_MATERIAL);
        assertThat(testAmostra.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAmostra.getTipoMedVet()).isEqualTo(DEFAULT_TIPO_MED_VET);
        assertThat(testAmostra.getValorTotal()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL);
        assertThat(testAmostra.getTipoPagamento()).isEqualTo(DEFAULT_TIPO_PAGAMENTO);
        assertThat(testAmostra.getSituacao()).isEqualTo(DEFAULT_SITUACAO);
    }

    @Test
    @Transactional
    void createAmostraWithExistingId() throws Exception {
        // Create the Amostra with an existing ID
        amostra.setId(1L);
        AmostraDTO amostraDTO = amostraMapper.toDto(amostra);

        int databaseSizeBeforeCreate = amostraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmostraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(amostraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Amostra in the database
        List<Amostra> amostraList = amostraRepository.findAll();
        assertThat(amostraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAmostras() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList
        restAmostraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amostra.getId().intValue())))
            .andExpect(jsonPath("$.[*].protocolo").value(hasItem(DEFAULT_PROTOCOLO)))
            .andExpect(jsonPath("$.[*].formaEnvio").value(hasItem(DEFAULT_FORMA_ENVIO)))
            .andExpect(jsonPath("$.[*].numeroAmostras").value(hasItem(DEFAULT_NUMERO_AMOSTRAS)))
            .andExpect(jsonPath("$.[*].especie").value(hasItem(DEFAULT_ESPECIE)))
            .andExpect(jsonPath("$.[*].dataInicial").value(hasItem(DEFAULT_DATA_INICIAL.toString())))
            .andExpect(jsonPath("$.[*].dataFinal").value(hasItem(DEFAULT_DATA_FINAL.toString())))
            .andExpect(jsonPath("$.[*].materialRecebido").value(hasItem(DEFAULT_MATERIAL_RECEBIDO)))
            .andExpect(jsonPath("$.[*].acondicionamento").value(hasItem(DEFAULT_ACONDICIONAMENTO)))
            .andExpect(jsonPath("$.[*].condicaoMaterial").value(hasItem(DEFAULT_CONDICAO_MATERIAL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].tipoMedVet").value(hasItem(DEFAULT_TIPO_MED_VET)))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL))))
            .andExpect(jsonPath("$.[*].tipoPagamento").value(hasItem(DEFAULT_TIPO_PAGAMENTO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAmostrasWithEagerRelationshipsIsEnabled() throws Exception {
        when(amostraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAmostraMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(amostraServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAmostrasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(amostraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAmostraMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(amostraServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getAmostra() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get the amostra
        restAmostraMockMvc
            .perform(get(ENTITY_API_URL_ID, amostra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(amostra.getId().intValue()))
            .andExpect(jsonPath("$.protocolo").value(DEFAULT_PROTOCOLO))
            .andExpect(jsonPath("$.formaEnvio").value(DEFAULT_FORMA_ENVIO))
            .andExpect(jsonPath("$.numeroAmostras").value(DEFAULT_NUMERO_AMOSTRAS))
            .andExpect(jsonPath("$.especie").value(DEFAULT_ESPECIE))
            .andExpect(jsonPath("$.dataInicial").value(DEFAULT_DATA_INICIAL.toString()))
            .andExpect(jsonPath("$.dataFinal").value(DEFAULT_DATA_FINAL.toString()))
            .andExpect(jsonPath("$.materialRecebido").value(DEFAULT_MATERIAL_RECEBIDO))
            .andExpect(jsonPath("$.acondicionamento").value(DEFAULT_ACONDICIONAMENTO))
            .andExpect(jsonPath("$.condicaoMaterial").value(DEFAULT_CONDICAO_MATERIAL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.tipoMedVet").value(DEFAULT_TIPO_MED_VET))
            .andExpect(jsonPath("$.valorTotal").value(sameNumber(DEFAULT_VALOR_TOTAL)))
            .andExpect(jsonPath("$.tipoPagamento").value(DEFAULT_TIPO_PAGAMENTO))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO));
    }

    @Test
    @Transactional
    void getAmostrasByIdFiltering() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        Long id = amostra.getId();

        defaultAmostraShouldBeFound("id.equals=" + id);
        defaultAmostraShouldNotBeFound("id.notEquals=" + id);

        defaultAmostraShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAmostraShouldNotBeFound("id.greaterThan=" + id);

        defaultAmostraShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAmostraShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAmostrasByProtocoloIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where protocolo equals to DEFAULT_PROTOCOLO
        defaultAmostraShouldBeFound("protocolo.equals=" + DEFAULT_PROTOCOLO);

        // Get all the amostraList where protocolo equals to UPDATED_PROTOCOLO
        defaultAmostraShouldNotBeFound("protocolo.equals=" + UPDATED_PROTOCOLO);
    }

    @Test
    @Transactional
    void getAllAmostrasByProtocoloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where protocolo not equals to DEFAULT_PROTOCOLO
        defaultAmostraShouldNotBeFound("protocolo.notEquals=" + DEFAULT_PROTOCOLO);

        // Get all the amostraList where protocolo not equals to UPDATED_PROTOCOLO
        defaultAmostraShouldBeFound("protocolo.notEquals=" + UPDATED_PROTOCOLO);
    }

    @Test
    @Transactional
    void getAllAmostrasByProtocoloIsInShouldWork() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where protocolo in DEFAULT_PROTOCOLO or UPDATED_PROTOCOLO
        defaultAmostraShouldBeFound("protocolo.in=" + DEFAULT_PROTOCOLO + "," + UPDATED_PROTOCOLO);

        // Get all the amostraList where protocolo equals to UPDATED_PROTOCOLO
        defaultAmostraShouldNotBeFound("protocolo.in=" + UPDATED_PROTOCOLO);
    }

    @Test
    @Transactional
    void getAllAmostrasByProtocoloIsNullOrNotNull() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where protocolo is not null
        defaultAmostraShouldBeFound("protocolo.specified=true");

        // Get all the amostraList where protocolo is null
        defaultAmostraShouldNotBeFound("protocolo.specified=false");
    }

    @Test
    @Transactional
    void getAllAmostrasByProtocoloContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where protocolo contains DEFAULT_PROTOCOLO
        defaultAmostraShouldBeFound("protocolo.contains=" + DEFAULT_PROTOCOLO);

        // Get all the amostraList where protocolo contains UPDATED_PROTOCOLO
        defaultAmostraShouldNotBeFound("protocolo.contains=" + UPDATED_PROTOCOLO);
    }

    @Test
    @Transactional
    void getAllAmostrasByProtocoloNotContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where protocolo does not contain DEFAULT_PROTOCOLO
        defaultAmostraShouldNotBeFound("protocolo.doesNotContain=" + DEFAULT_PROTOCOLO);

        // Get all the amostraList where protocolo does not contain UPDATED_PROTOCOLO
        defaultAmostraShouldBeFound("protocolo.doesNotContain=" + UPDATED_PROTOCOLO);
    }

    @Test
    @Transactional
    void getAllAmostrasByFormaEnvioIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where formaEnvio equals to DEFAULT_FORMA_ENVIO
        defaultAmostraShouldBeFound("formaEnvio.equals=" + DEFAULT_FORMA_ENVIO);

        // Get all the amostraList where formaEnvio equals to UPDATED_FORMA_ENVIO
        defaultAmostraShouldNotBeFound("formaEnvio.equals=" + UPDATED_FORMA_ENVIO);
    }

    @Test
    @Transactional
    void getAllAmostrasByFormaEnvioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where formaEnvio not equals to DEFAULT_FORMA_ENVIO
        defaultAmostraShouldNotBeFound("formaEnvio.notEquals=" + DEFAULT_FORMA_ENVIO);

        // Get all the amostraList where formaEnvio not equals to UPDATED_FORMA_ENVIO
        defaultAmostraShouldBeFound("formaEnvio.notEquals=" + UPDATED_FORMA_ENVIO);
    }

    @Test
    @Transactional
    void getAllAmostrasByFormaEnvioIsInShouldWork() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where formaEnvio in DEFAULT_FORMA_ENVIO or UPDATED_FORMA_ENVIO
        defaultAmostraShouldBeFound("formaEnvio.in=" + DEFAULT_FORMA_ENVIO + "," + UPDATED_FORMA_ENVIO);

        // Get all the amostraList where formaEnvio equals to UPDATED_FORMA_ENVIO
        defaultAmostraShouldNotBeFound("formaEnvio.in=" + UPDATED_FORMA_ENVIO);
    }

    @Test
    @Transactional
    void getAllAmostrasByFormaEnvioIsNullOrNotNull() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where formaEnvio is not null
        defaultAmostraShouldBeFound("formaEnvio.specified=true");

        // Get all the amostraList where formaEnvio is null
        defaultAmostraShouldNotBeFound("formaEnvio.specified=false");
    }

    @Test
    @Transactional
    void getAllAmostrasByFormaEnvioContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where formaEnvio contains DEFAULT_FORMA_ENVIO
        defaultAmostraShouldBeFound("formaEnvio.contains=" + DEFAULT_FORMA_ENVIO);

        // Get all the amostraList where formaEnvio contains UPDATED_FORMA_ENVIO
        defaultAmostraShouldNotBeFound("formaEnvio.contains=" + UPDATED_FORMA_ENVIO);
    }

    @Test
    @Transactional
    void getAllAmostrasByFormaEnvioNotContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where formaEnvio does not contain DEFAULT_FORMA_ENVIO
        defaultAmostraShouldNotBeFound("formaEnvio.doesNotContain=" + DEFAULT_FORMA_ENVIO);

        // Get all the amostraList where formaEnvio does not contain UPDATED_FORMA_ENVIO
        defaultAmostraShouldBeFound("formaEnvio.doesNotContain=" + UPDATED_FORMA_ENVIO);
    }

    @Test
    @Transactional
    void getAllAmostrasByNumeroAmostrasIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where numeroAmostras equals to DEFAULT_NUMERO_AMOSTRAS
        defaultAmostraShouldBeFound("numeroAmostras.equals=" + DEFAULT_NUMERO_AMOSTRAS);

        // Get all the amostraList where numeroAmostras equals to UPDATED_NUMERO_AMOSTRAS
        defaultAmostraShouldNotBeFound("numeroAmostras.equals=" + UPDATED_NUMERO_AMOSTRAS);
    }

    @Test
    @Transactional
    void getAllAmostrasByNumeroAmostrasIsNotEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where numeroAmostras not equals to DEFAULT_NUMERO_AMOSTRAS
        defaultAmostraShouldNotBeFound("numeroAmostras.notEquals=" + DEFAULT_NUMERO_AMOSTRAS);

        // Get all the amostraList where numeroAmostras not equals to UPDATED_NUMERO_AMOSTRAS
        defaultAmostraShouldBeFound("numeroAmostras.notEquals=" + UPDATED_NUMERO_AMOSTRAS);
    }

    @Test
    @Transactional
    void getAllAmostrasByNumeroAmostrasIsInShouldWork() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where numeroAmostras in DEFAULT_NUMERO_AMOSTRAS or UPDATED_NUMERO_AMOSTRAS
        defaultAmostraShouldBeFound("numeroAmostras.in=" + DEFAULT_NUMERO_AMOSTRAS + "," + UPDATED_NUMERO_AMOSTRAS);

        // Get all the amostraList where numeroAmostras equals to UPDATED_NUMERO_AMOSTRAS
        defaultAmostraShouldNotBeFound("numeroAmostras.in=" + UPDATED_NUMERO_AMOSTRAS);
    }

    @Test
    @Transactional
    void getAllAmostrasByNumeroAmostrasIsNullOrNotNull() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where numeroAmostras is not null
        defaultAmostraShouldBeFound("numeroAmostras.specified=true");

        // Get all the amostraList where numeroAmostras is null
        defaultAmostraShouldNotBeFound("numeroAmostras.specified=false");
    }

    @Test
    @Transactional
    void getAllAmostrasByNumeroAmostrasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where numeroAmostras is greater than or equal to DEFAULT_NUMERO_AMOSTRAS
        defaultAmostraShouldBeFound("numeroAmostras.greaterThanOrEqual=" + DEFAULT_NUMERO_AMOSTRAS);

        // Get all the amostraList where numeroAmostras is greater than or equal to UPDATED_NUMERO_AMOSTRAS
        defaultAmostraShouldNotBeFound("numeroAmostras.greaterThanOrEqual=" + UPDATED_NUMERO_AMOSTRAS);
    }

    @Test
    @Transactional
    void getAllAmostrasByNumeroAmostrasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where numeroAmostras is less than or equal to DEFAULT_NUMERO_AMOSTRAS
        defaultAmostraShouldBeFound("numeroAmostras.lessThanOrEqual=" + DEFAULT_NUMERO_AMOSTRAS);

        // Get all the amostraList where numeroAmostras is less than or equal to SMALLER_NUMERO_AMOSTRAS
        defaultAmostraShouldNotBeFound("numeroAmostras.lessThanOrEqual=" + SMALLER_NUMERO_AMOSTRAS);
    }

    @Test
    @Transactional
    void getAllAmostrasByNumeroAmostrasIsLessThanSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where numeroAmostras is less than DEFAULT_NUMERO_AMOSTRAS
        defaultAmostraShouldNotBeFound("numeroAmostras.lessThan=" + DEFAULT_NUMERO_AMOSTRAS);

        // Get all the amostraList where numeroAmostras is less than UPDATED_NUMERO_AMOSTRAS
        defaultAmostraShouldBeFound("numeroAmostras.lessThan=" + UPDATED_NUMERO_AMOSTRAS);
    }

    @Test
    @Transactional
    void getAllAmostrasByNumeroAmostrasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where numeroAmostras is greater than DEFAULT_NUMERO_AMOSTRAS
        defaultAmostraShouldNotBeFound("numeroAmostras.greaterThan=" + DEFAULT_NUMERO_AMOSTRAS);

        // Get all the amostraList where numeroAmostras is greater than SMALLER_NUMERO_AMOSTRAS
        defaultAmostraShouldBeFound("numeroAmostras.greaterThan=" + SMALLER_NUMERO_AMOSTRAS);
    }

    @Test
    @Transactional
    void getAllAmostrasByEspecieIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where especie equals to DEFAULT_ESPECIE
        defaultAmostraShouldBeFound("especie.equals=" + DEFAULT_ESPECIE);

        // Get all the amostraList where especie equals to UPDATED_ESPECIE
        defaultAmostraShouldNotBeFound("especie.equals=" + UPDATED_ESPECIE);
    }

    @Test
    @Transactional
    void getAllAmostrasByEspecieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where especie not equals to DEFAULT_ESPECIE
        defaultAmostraShouldNotBeFound("especie.notEquals=" + DEFAULT_ESPECIE);

        // Get all the amostraList where especie not equals to UPDATED_ESPECIE
        defaultAmostraShouldBeFound("especie.notEquals=" + UPDATED_ESPECIE);
    }

    @Test
    @Transactional
    void getAllAmostrasByEspecieIsInShouldWork() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where especie in DEFAULT_ESPECIE or UPDATED_ESPECIE
        defaultAmostraShouldBeFound("especie.in=" + DEFAULT_ESPECIE + "," + UPDATED_ESPECIE);

        // Get all the amostraList where especie equals to UPDATED_ESPECIE
        defaultAmostraShouldNotBeFound("especie.in=" + UPDATED_ESPECIE);
    }

    @Test
    @Transactional
    void getAllAmostrasByEspecieIsNullOrNotNull() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where especie is not null
        defaultAmostraShouldBeFound("especie.specified=true");

        // Get all the amostraList where especie is null
        defaultAmostraShouldNotBeFound("especie.specified=false");
    }

    @Test
    @Transactional
    void getAllAmostrasByEspecieContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where especie contains DEFAULT_ESPECIE
        defaultAmostraShouldBeFound("especie.contains=" + DEFAULT_ESPECIE);

        // Get all the amostraList where especie contains UPDATED_ESPECIE
        defaultAmostraShouldNotBeFound("especie.contains=" + UPDATED_ESPECIE);
    }

    @Test
    @Transactional
    void getAllAmostrasByEspecieNotContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where especie does not contain DEFAULT_ESPECIE
        defaultAmostraShouldNotBeFound("especie.doesNotContain=" + DEFAULT_ESPECIE);

        // Get all the amostraList where especie does not contain UPDATED_ESPECIE
        defaultAmostraShouldBeFound("especie.doesNotContain=" + UPDATED_ESPECIE);
    }

    @Test
    @Transactional
    void getAllAmostrasByDataInicialIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataInicial equals to DEFAULT_DATA_INICIAL
        defaultAmostraShouldBeFound("dataInicial.equals=" + DEFAULT_DATA_INICIAL);

        // Get all the amostraList where dataInicial equals to UPDATED_DATA_INICIAL
        defaultAmostraShouldNotBeFound("dataInicial.equals=" + UPDATED_DATA_INICIAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByDataInicialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataInicial not equals to DEFAULT_DATA_INICIAL
        defaultAmostraShouldNotBeFound("dataInicial.notEquals=" + DEFAULT_DATA_INICIAL);

        // Get all the amostraList where dataInicial not equals to UPDATED_DATA_INICIAL
        defaultAmostraShouldBeFound("dataInicial.notEquals=" + UPDATED_DATA_INICIAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByDataInicialIsInShouldWork() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataInicial in DEFAULT_DATA_INICIAL or UPDATED_DATA_INICIAL
        defaultAmostraShouldBeFound("dataInicial.in=" + DEFAULT_DATA_INICIAL + "," + UPDATED_DATA_INICIAL);

        // Get all the amostraList where dataInicial equals to UPDATED_DATA_INICIAL
        defaultAmostraShouldNotBeFound("dataInicial.in=" + UPDATED_DATA_INICIAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByDataInicialIsNullOrNotNull() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataInicial is not null
        defaultAmostraShouldBeFound("dataInicial.specified=true");

        // Get all the amostraList where dataInicial is null
        defaultAmostraShouldNotBeFound("dataInicial.specified=false");
    }

    @Test
    @Transactional
    void getAllAmostrasByDataInicialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataInicial is greater than or equal to DEFAULT_DATA_INICIAL
        defaultAmostraShouldBeFound("dataInicial.greaterThanOrEqual=" + DEFAULT_DATA_INICIAL);

        // Get all the amostraList where dataInicial is greater than or equal to UPDATED_DATA_INICIAL
        defaultAmostraShouldNotBeFound("dataInicial.greaterThanOrEqual=" + UPDATED_DATA_INICIAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByDataInicialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataInicial is less than or equal to DEFAULT_DATA_INICIAL
        defaultAmostraShouldBeFound("dataInicial.lessThanOrEqual=" + DEFAULT_DATA_INICIAL);

        // Get all the amostraList where dataInicial is less than or equal to SMALLER_DATA_INICIAL
        defaultAmostraShouldNotBeFound("dataInicial.lessThanOrEqual=" + SMALLER_DATA_INICIAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByDataInicialIsLessThanSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataInicial is less than DEFAULT_DATA_INICIAL
        defaultAmostraShouldNotBeFound("dataInicial.lessThan=" + DEFAULT_DATA_INICIAL);

        // Get all the amostraList where dataInicial is less than UPDATED_DATA_INICIAL
        defaultAmostraShouldBeFound("dataInicial.lessThan=" + UPDATED_DATA_INICIAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByDataInicialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataInicial is greater than DEFAULT_DATA_INICIAL
        defaultAmostraShouldNotBeFound("dataInicial.greaterThan=" + DEFAULT_DATA_INICIAL);

        // Get all the amostraList where dataInicial is greater than SMALLER_DATA_INICIAL
        defaultAmostraShouldBeFound("dataInicial.greaterThan=" + SMALLER_DATA_INICIAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByDataFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataFinal equals to DEFAULT_DATA_FINAL
        defaultAmostraShouldBeFound("dataFinal.equals=" + DEFAULT_DATA_FINAL);

        // Get all the amostraList where dataFinal equals to UPDATED_DATA_FINAL
        defaultAmostraShouldNotBeFound("dataFinal.equals=" + UPDATED_DATA_FINAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByDataFinalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataFinal not equals to DEFAULT_DATA_FINAL
        defaultAmostraShouldNotBeFound("dataFinal.notEquals=" + DEFAULT_DATA_FINAL);

        // Get all the amostraList where dataFinal not equals to UPDATED_DATA_FINAL
        defaultAmostraShouldBeFound("dataFinal.notEquals=" + UPDATED_DATA_FINAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByDataFinalIsInShouldWork() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataFinal in DEFAULT_DATA_FINAL or UPDATED_DATA_FINAL
        defaultAmostraShouldBeFound("dataFinal.in=" + DEFAULT_DATA_FINAL + "," + UPDATED_DATA_FINAL);

        // Get all the amostraList where dataFinal equals to UPDATED_DATA_FINAL
        defaultAmostraShouldNotBeFound("dataFinal.in=" + UPDATED_DATA_FINAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByDataFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataFinal is not null
        defaultAmostraShouldBeFound("dataFinal.specified=true");

        // Get all the amostraList where dataFinal is null
        defaultAmostraShouldNotBeFound("dataFinal.specified=false");
    }

    @Test
    @Transactional
    void getAllAmostrasByDataFinalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataFinal is greater than or equal to DEFAULT_DATA_FINAL
        defaultAmostraShouldBeFound("dataFinal.greaterThanOrEqual=" + DEFAULT_DATA_FINAL);

        // Get all the amostraList where dataFinal is greater than or equal to UPDATED_DATA_FINAL
        defaultAmostraShouldNotBeFound("dataFinal.greaterThanOrEqual=" + UPDATED_DATA_FINAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByDataFinalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataFinal is less than or equal to DEFAULT_DATA_FINAL
        defaultAmostraShouldBeFound("dataFinal.lessThanOrEqual=" + DEFAULT_DATA_FINAL);

        // Get all the amostraList where dataFinal is less than or equal to SMALLER_DATA_FINAL
        defaultAmostraShouldNotBeFound("dataFinal.lessThanOrEqual=" + SMALLER_DATA_FINAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByDataFinalIsLessThanSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataFinal is less than DEFAULT_DATA_FINAL
        defaultAmostraShouldNotBeFound("dataFinal.lessThan=" + DEFAULT_DATA_FINAL);

        // Get all the amostraList where dataFinal is less than UPDATED_DATA_FINAL
        defaultAmostraShouldBeFound("dataFinal.lessThan=" + UPDATED_DATA_FINAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByDataFinalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where dataFinal is greater than DEFAULT_DATA_FINAL
        defaultAmostraShouldNotBeFound("dataFinal.greaterThan=" + DEFAULT_DATA_FINAL);

        // Get all the amostraList where dataFinal is greater than SMALLER_DATA_FINAL
        defaultAmostraShouldBeFound("dataFinal.greaterThan=" + SMALLER_DATA_FINAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByMaterialRecebidoIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where materialRecebido equals to DEFAULT_MATERIAL_RECEBIDO
        defaultAmostraShouldBeFound("materialRecebido.equals=" + DEFAULT_MATERIAL_RECEBIDO);

        // Get all the amostraList where materialRecebido equals to UPDATED_MATERIAL_RECEBIDO
        defaultAmostraShouldNotBeFound("materialRecebido.equals=" + UPDATED_MATERIAL_RECEBIDO);
    }

    @Test
    @Transactional
    void getAllAmostrasByMaterialRecebidoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where materialRecebido not equals to DEFAULT_MATERIAL_RECEBIDO
        defaultAmostraShouldNotBeFound("materialRecebido.notEquals=" + DEFAULT_MATERIAL_RECEBIDO);

        // Get all the amostraList where materialRecebido not equals to UPDATED_MATERIAL_RECEBIDO
        defaultAmostraShouldBeFound("materialRecebido.notEquals=" + UPDATED_MATERIAL_RECEBIDO);
    }

    @Test
    @Transactional
    void getAllAmostrasByMaterialRecebidoIsInShouldWork() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where materialRecebido in DEFAULT_MATERIAL_RECEBIDO or UPDATED_MATERIAL_RECEBIDO
        defaultAmostraShouldBeFound("materialRecebido.in=" + DEFAULT_MATERIAL_RECEBIDO + "," + UPDATED_MATERIAL_RECEBIDO);

        // Get all the amostraList where materialRecebido equals to UPDATED_MATERIAL_RECEBIDO
        defaultAmostraShouldNotBeFound("materialRecebido.in=" + UPDATED_MATERIAL_RECEBIDO);
    }

    @Test
    @Transactional
    void getAllAmostrasByMaterialRecebidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where materialRecebido is not null
        defaultAmostraShouldBeFound("materialRecebido.specified=true");

        // Get all the amostraList where materialRecebido is null
        defaultAmostraShouldNotBeFound("materialRecebido.specified=false");
    }

    @Test
    @Transactional
    void getAllAmostrasByMaterialRecebidoContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where materialRecebido contains DEFAULT_MATERIAL_RECEBIDO
        defaultAmostraShouldBeFound("materialRecebido.contains=" + DEFAULT_MATERIAL_RECEBIDO);

        // Get all the amostraList where materialRecebido contains UPDATED_MATERIAL_RECEBIDO
        defaultAmostraShouldNotBeFound("materialRecebido.contains=" + UPDATED_MATERIAL_RECEBIDO);
    }

    @Test
    @Transactional
    void getAllAmostrasByMaterialRecebidoNotContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where materialRecebido does not contain DEFAULT_MATERIAL_RECEBIDO
        defaultAmostraShouldNotBeFound("materialRecebido.doesNotContain=" + DEFAULT_MATERIAL_RECEBIDO);

        // Get all the amostraList where materialRecebido does not contain UPDATED_MATERIAL_RECEBIDO
        defaultAmostraShouldBeFound("materialRecebido.doesNotContain=" + UPDATED_MATERIAL_RECEBIDO);
    }

    @Test
    @Transactional
    void getAllAmostrasByAcondicionamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where acondicionamento equals to DEFAULT_ACONDICIONAMENTO
        defaultAmostraShouldBeFound("acondicionamento.equals=" + DEFAULT_ACONDICIONAMENTO);

        // Get all the amostraList where acondicionamento equals to UPDATED_ACONDICIONAMENTO
        defaultAmostraShouldNotBeFound("acondicionamento.equals=" + UPDATED_ACONDICIONAMENTO);
    }

    @Test
    @Transactional
    void getAllAmostrasByAcondicionamentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where acondicionamento not equals to DEFAULT_ACONDICIONAMENTO
        defaultAmostraShouldNotBeFound("acondicionamento.notEquals=" + DEFAULT_ACONDICIONAMENTO);

        // Get all the amostraList where acondicionamento not equals to UPDATED_ACONDICIONAMENTO
        defaultAmostraShouldBeFound("acondicionamento.notEquals=" + UPDATED_ACONDICIONAMENTO);
    }

    @Test
    @Transactional
    void getAllAmostrasByAcondicionamentoIsInShouldWork() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where acondicionamento in DEFAULT_ACONDICIONAMENTO or UPDATED_ACONDICIONAMENTO
        defaultAmostraShouldBeFound("acondicionamento.in=" + DEFAULT_ACONDICIONAMENTO + "," + UPDATED_ACONDICIONAMENTO);

        // Get all the amostraList where acondicionamento equals to UPDATED_ACONDICIONAMENTO
        defaultAmostraShouldNotBeFound("acondicionamento.in=" + UPDATED_ACONDICIONAMENTO);
    }

    @Test
    @Transactional
    void getAllAmostrasByAcondicionamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where acondicionamento is not null
        defaultAmostraShouldBeFound("acondicionamento.specified=true");

        // Get all the amostraList where acondicionamento is null
        defaultAmostraShouldNotBeFound("acondicionamento.specified=false");
    }

    @Test
    @Transactional
    void getAllAmostrasByAcondicionamentoContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where acondicionamento contains DEFAULT_ACONDICIONAMENTO
        defaultAmostraShouldBeFound("acondicionamento.contains=" + DEFAULT_ACONDICIONAMENTO);

        // Get all the amostraList where acondicionamento contains UPDATED_ACONDICIONAMENTO
        defaultAmostraShouldNotBeFound("acondicionamento.contains=" + UPDATED_ACONDICIONAMENTO);
    }

    @Test
    @Transactional
    void getAllAmostrasByAcondicionamentoNotContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where acondicionamento does not contain DEFAULT_ACONDICIONAMENTO
        defaultAmostraShouldNotBeFound("acondicionamento.doesNotContain=" + DEFAULT_ACONDICIONAMENTO);

        // Get all the amostraList where acondicionamento does not contain UPDATED_ACONDICIONAMENTO
        defaultAmostraShouldBeFound("acondicionamento.doesNotContain=" + UPDATED_ACONDICIONAMENTO);
    }

    @Test
    @Transactional
    void getAllAmostrasByCondicaoMaterialIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where condicaoMaterial equals to DEFAULT_CONDICAO_MATERIAL
        defaultAmostraShouldBeFound("condicaoMaterial.equals=" + DEFAULT_CONDICAO_MATERIAL);

        // Get all the amostraList where condicaoMaterial equals to UPDATED_CONDICAO_MATERIAL
        defaultAmostraShouldNotBeFound("condicaoMaterial.equals=" + UPDATED_CONDICAO_MATERIAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByCondicaoMaterialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where condicaoMaterial not equals to DEFAULT_CONDICAO_MATERIAL
        defaultAmostraShouldNotBeFound("condicaoMaterial.notEquals=" + DEFAULT_CONDICAO_MATERIAL);

        // Get all the amostraList where condicaoMaterial not equals to UPDATED_CONDICAO_MATERIAL
        defaultAmostraShouldBeFound("condicaoMaterial.notEquals=" + UPDATED_CONDICAO_MATERIAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByCondicaoMaterialIsInShouldWork() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where condicaoMaterial in DEFAULT_CONDICAO_MATERIAL or UPDATED_CONDICAO_MATERIAL
        defaultAmostraShouldBeFound("condicaoMaterial.in=" + DEFAULT_CONDICAO_MATERIAL + "," + UPDATED_CONDICAO_MATERIAL);

        // Get all the amostraList where condicaoMaterial equals to UPDATED_CONDICAO_MATERIAL
        defaultAmostraShouldNotBeFound("condicaoMaterial.in=" + UPDATED_CONDICAO_MATERIAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByCondicaoMaterialIsNullOrNotNull() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where condicaoMaterial is not null
        defaultAmostraShouldBeFound("condicaoMaterial.specified=true");

        // Get all the amostraList where condicaoMaterial is null
        defaultAmostraShouldNotBeFound("condicaoMaterial.specified=false");
    }

    @Test
    @Transactional
    void getAllAmostrasByCondicaoMaterialContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where condicaoMaterial contains DEFAULT_CONDICAO_MATERIAL
        defaultAmostraShouldBeFound("condicaoMaterial.contains=" + DEFAULT_CONDICAO_MATERIAL);

        // Get all the amostraList where condicaoMaterial contains UPDATED_CONDICAO_MATERIAL
        defaultAmostraShouldNotBeFound("condicaoMaterial.contains=" + UPDATED_CONDICAO_MATERIAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByCondicaoMaterialNotContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where condicaoMaterial does not contain DEFAULT_CONDICAO_MATERIAL
        defaultAmostraShouldNotBeFound("condicaoMaterial.doesNotContain=" + DEFAULT_CONDICAO_MATERIAL);

        // Get all the amostraList where condicaoMaterial does not contain UPDATED_CONDICAO_MATERIAL
        defaultAmostraShouldBeFound("condicaoMaterial.doesNotContain=" + UPDATED_CONDICAO_MATERIAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where status equals to DEFAULT_STATUS
        defaultAmostraShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the amostraList where status equals to UPDATED_STATUS
        defaultAmostraShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAmostrasByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where status not equals to DEFAULT_STATUS
        defaultAmostraShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the amostraList where status not equals to UPDATED_STATUS
        defaultAmostraShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAmostrasByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAmostraShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the amostraList where status equals to UPDATED_STATUS
        defaultAmostraShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAmostrasByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where status is not null
        defaultAmostraShouldBeFound("status.specified=true");

        // Get all the amostraList where status is null
        defaultAmostraShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllAmostrasByStatusContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where status contains DEFAULT_STATUS
        defaultAmostraShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the amostraList where status contains UPDATED_STATUS
        defaultAmostraShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAmostrasByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where status does not contain DEFAULT_STATUS
        defaultAmostraShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the amostraList where status does not contain UPDATED_STATUS
        defaultAmostraShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAmostrasByTipoMedVetIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where tipoMedVet equals to DEFAULT_TIPO_MED_VET
        defaultAmostraShouldBeFound("tipoMedVet.equals=" + DEFAULT_TIPO_MED_VET);

        // Get all the amostraList where tipoMedVet equals to UPDATED_TIPO_MED_VET
        defaultAmostraShouldNotBeFound("tipoMedVet.equals=" + UPDATED_TIPO_MED_VET);
    }

    @Test
    @Transactional
    void getAllAmostrasByTipoMedVetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where tipoMedVet not equals to DEFAULT_TIPO_MED_VET
        defaultAmostraShouldNotBeFound("tipoMedVet.notEquals=" + DEFAULT_TIPO_MED_VET);

        // Get all the amostraList where tipoMedVet not equals to UPDATED_TIPO_MED_VET
        defaultAmostraShouldBeFound("tipoMedVet.notEquals=" + UPDATED_TIPO_MED_VET);
    }

    @Test
    @Transactional
    void getAllAmostrasByTipoMedVetIsInShouldWork() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where tipoMedVet in DEFAULT_TIPO_MED_VET or UPDATED_TIPO_MED_VET
        defaultAmostraShouldBeFound("tipoMedVet.in=" + DEFAULT_TIPO_MED_VET + "," + UPDATED_TIPO_MED_VET);

        // Get all the amostraList where tipoMedVet equals to UPDATED_TIPO_MED_VET
        defaultAmostraShouldNotBeFound("tipoMedVet.in=" + UPDATED_TIPO_MED_VET);
    }

    @Test
    @Transactional
    void getAllAmostrasByTipoMedVetIsNullOrNotNull() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where tipoMedVet is not null
        defaultAmostraShouldBeFound("tipoMedVet.specified=true");

        // Get all the amostraList where tipoMedVet is null
        defaultAmostraShouldNotBeFound("tipoMedVet.specified=false");
    }

    @Test
    @Transactional
    void getAllAmostrasByTipoMedVetContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where tipoMedVet contains DEFAULT_TIPO_MED_VET
        defaultAmostraShouldBeFound("tipoMedVet.contains=" + DEFAULT_TIPO_MED_VET);

        // Get all the amostraList where tipoMedVet contains UPDATED_TIPO_MED_VET
        defaultAmostraShouldNotBeFound("tipoMedVet.contains=" + UPDATED_TIPO_MED_VET);
    }

    @Test
    @Transactional
    void getAllAmostrasByTipoMedVetNotContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where tipoMedVet does not contain DEFAULT_TIPO_MED_VET
        defaultAmostraShouldNotBeFound("tipoMedVet.doesNotContain=" + DEFAULT_TIPO_MED_VET);

        // Get all the amostraList where tipoMedVet does not contain UPDATED_TIPO_MED_VET
        defaultAmostraShouldBeFound("tipoMedVet.doesNotContain=" + UPDATED_TIPO_MED_VET);
    }

    @Test
    @Transactional
    void getAllAmostrasByValorTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where valorTotal equals to DEFAULT_VALOR_TOTAL
        defaultAmostraShouldBeFound("valorTotal.equals=" + DEFAULT_VALOR_TOTAL);

        // Get all the amostraList where valorTotal equals to UPDATED_VALOR_TOTAL
        defaultAmostraShouldNotBeFound("valorTotal.equals=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByValorTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where valorTotal not equals to DEFAULT_VALOR_TOTAL
        defaultAmostraShouldNotBeFound("valorTotal.notEquals=" + DEFAULT_VALOR_TOTAL);

        // Get all the amostraList where valorTotal not equals to UPDATED_VALOR_TOTAL
        defaultAmostraShouldBeFound("valorTotal.notEquals=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByValorTotalIsInShouldWork() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where valorTotal in DEFAULT_VALOR_TOTAL or UPDATED_VALOR_TOTAL
        defaultAmostraShouldBeFound("valorTotal.in=" + DEFAULT_VALOR_TOTAL + "," + UPDATED_VALOR_TOTAL);

        // Get all the amostraList where valorTotal equals to UPDATED_VALOR_TOTAL
        defaultAmostraShouldNotBeFound("valorTotal.in=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByValorTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where valorTotal is not null
        defaultAmostraShouldBeFound("valorTotal.specified=true");

        // Get all the amostraList where valorTotal is null
        defaultAmostraShouldNotBeFound("valorTotal.specified=false");
    }

    @Test
    @Transactional
    void getAllAmostrasByValorTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where valorTotal is greater than or equal to DEFAULT_VALOR_TOTAL
        defaultAmostraShouldBeFound("valorTotal.greaterThanOrEqual=" + DEFAULT_VALOR_TOTAL);

        // Get all the amostraList where valorTotal is greater than or equal to UPDATED_VALOR_TOTAL
        defaultAmostraShouldNotBeFound("valorTotal.greaterThanOrEqual=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByValorTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where valorTotal is less than or equal to DEFAULT_VALOR_TOTAL
        defaultAmostraShouldBeFound("valorTotal.lessThanOrEqual=" + DEFAULT_VALOR_TOTAL);

        // Get all the amostraList where valorTotal is less than or equal to SMALLER_VALOR_TOTAL
        defaultAmostraShouldNotBeFound("valorTotal.lessThanOrEqual=" + SMALLER_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByValorTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where valorTotal is less than DEFAULT_VALOR_TOTAL
        defaultAmostraShouldNotBeFound("valorTotal.lessThan=" + DEFAULT_VALOR_TOTAL);

        // Get all the amostraList where valorTotal is less than UPDATED_VALOR_TOTAL
        defaultAmostraShouldBeFound("valorTotal.lessThan=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByValorTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where valorTotal is greater than DEFAULT_VALOR_TOTAL
        defaultAmostraShouldNotBeFound("valorTotal.greaterThan=" + DEFAULT_VALOR_TOTAL);

        // Get all the amostraList where valorTotal is greater than SMALLER_VALOR_TOTAL
        defaultAmostraShouldBeFound("valorTotal.greaterThan=" + SMALLER_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllAmostrasByTipoPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where tipoPagamento equals to DEFAULT_TIPO_PAGAMENTO
        defaultAmostraShouldBeFound("tipoPagamento.equals=" + DEFAULT_TIPO_PAGAMENTO);

        // Get all the amostraList where tipoPagamento equals to UPDATED_TIPO_PAGAMENTO
        defaultAmostraShouldNotBeFound("tipoPagamento.equals=" + UPDATED_TIPO_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllAmostrasByTipoPagamentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where tipoPagamento not equals to DEFAULT_TIPO_PAGAMENTO
        defaultAmostraShouldNotBeFound("tipoPagamento.notEquals=" + DEFAULT_TIPO_PAGAMENTO);

        // Get all the amostraList where tipoPagamento not equals to UPDATED_TIPO_PAGAMENTO
        defaultAmostraShouldBeFound("tipoPagamento.notEquals=" + UPDATED_TIPO_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllAmostrasByTipoPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where tipoPagamento in DEFAULT_TIPO_PAGAMENTO or UPDATED_TIPO_PAGAMENTO
        defaultAmostraShouldBeFound("tipoPagamento.in=" + DEFAULT_TIPO_PAGAMENTO + "," + UPDATED_TIPO_PAGAMENTO);

        // Get all the amostraList where tipoPagamento equals to UPDATED_TIPO_PAGAMENTO
        defaultAmostraShouldNotBeFound("tipoPagamento.in=" + UPDATED_TIPO_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllAmostrasByTipoPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where tipoPagamento is not null
        defaultAmostraShouldBeFound("tipoPagamento.specified=true");

        // Get all the amostraList where tipoPagamento is null
        defaultAmostraShouldNotBeFound("tipoPagamento.specified=false");
    }

    @Test
    @Transactional
    void getAllAmostrasByTipoPagamentoContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where tipoPagamento contains DEFAULT_TIPO_PAGAMENTO
        defaultAmostraShouldBeFound("tipoPagamento.contains=" + DEFAULT_TIPO_PAGAMENTO);

        // Get all the amostraList where tipoPagamento contains UPDATED_TIPO_PAGAMENTO
        defaultAmostraShouldNotBeFound("tipoPagamento.contains=" + UPDATED_TIPO_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllAmostrasByTipoPagamentoNotContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where tipoPagamento does not contain DEFAULT_TIPO_PAGAMENTO
        defaultAmostraShouldNotBeFound("tipoPagamento.doesNotContain=" + DEFAULT_TIPO_PAGAMENTO);

        // Get all the amostraList where tipoPagamento does not contain UPDATED_TIPO_PAGAMENTO
        defaultAmostraShouldBeFound("tipoPagamento.doesNotContain=" + UPDATED_TIPO_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllAmostrasBySituacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where situacao equals to DEFAULT_SITUACAO
        defaultAmostraShouldBeFound("situacao.equals=" + DEFAULT_SITUACAO);

        // Get all the amostraList where situacao equals to UPDATED_SITUACAO
        defaultAmostraShouldNotBeFound("situacao.equals=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllAmostrasBySituacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where situacao not equals to DEFAULT_SITUACAO
        defaultAmostraShouldNotBeFound("situacao.notEquals=" + DEFAULT_SITUACAO);

        // Get all the amostraList where situacao not equals to UPDATED_SITUACAO
        defaultAmostraShouldBeFound("situacao.notEquals=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllAmostrasBySituacaoIsInShouldWork() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where situacao in DEFAULT_SITUACAO or UPDATED_SITUACAO
        defaultAmostraShouldBeFound("situacao.in=" + DEFAULT_SITUACAO + "," + UPDATED_SITUACAO);

        // Get all the amostraList where situacao equals to UPDATED_SITUACAO
        defaultAmostraShouldNotBeFound("situacao.in=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllAmostrasBySituacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where situacao is not null
        defaultAmostraShouldBeFound("situacao.specified=true");

        // Get all the amostraList where situacao is null
        defaultAmostraShouldNotBeFound("situacao.specified=false");
    }

    @Test
    @Transactional
    void getAllAmostrasBySituacaoContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where situacao contains DEFAULT_SITUACAO
        defaultAmostraShouldBeFound("situacao.contains=" + DEFAULT_SITUACAO);

        // Get all the amostraList where situacao contains UPDATED_SITUACAO
        defaultAmostraShouldNotBeFound("situacao.contains=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllAmostrasBySituacaoNotContainsSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        // Get all the amostraList where situacao does not contain DEFAULT_SITUACAO
        defaultAmostraShouldNotBeFound("situacao.doesNotContain=" + DEFAULT_SITUACAO);

        // Get all the amostraList where situacao does not contain UPDATED_SITUACAO
        defaultAmostraShouldBeFound("situacao.doesNotContain=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllAmostrasByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        amostra.addUser(user);
        amostraRepository.saveAndFlush(amostra);
        Long userId = user.getId();

        // Get all the amostraList where user equals to userId
        defaultAmostraShouldBeFound("userId.equals=" + userId);

        // Get all the amostraList where user equals to (userId + 1)
        defaultAmostraShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllAmostrasByMidiaIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);
        Midia midia = MidiaResourceIT.createEntity(em);
        em.persist(midia);
        em.flush();
        amostra.addMidia(midia);
        amostraRepository.saveAndFlush(amostra);
        Long midiaId = midia.getId();

        // Get all the amostraList where midia equals to midiaId
        defaultAmostraShouldBeFound("midiaId.equals=" + midiaId);

        // Get all the amostraList where midia equals to (midiaId + 1)
        defaultAmostraShouldNotBeFound("midiaId.equals=" + (midiaId + 1));
    }

    @Test
    @Transactional
    void getAllAmostrasByExameIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);
        Exame exame = ExameResourceIT.createEntity(em);
        em.persist(exame);
        em.flush();
        amostra.addExame(exame);
        amostraRepository.saveAndFlush(amostra);
        Long exameId = exame.getId();

        // Get all the amostraList where exame equals to exameId
        defaultAmostraShouldBeFound("exameId.equals=" + exameId);

        // Get all the amostraList where exame equals to (exameId + 1)
        defaultAmostraShouldNotBeFound("exameId.equals=" + (exameId + 1));
    }

    @Test
    @Transactional
    void getAllAmostrasByPropriedadeIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);
        Propriedade propriedade = PropriedadeResourceIT.createEntity(em);
        em.persist(propriedade);
        em.flush();
        amostra.setPropriedade(propriedade);
        amostraRepository.saveAndFlush(amostra);
        Long propriedadeId = propriedade.getId();

        // Get all the amostraList where propriedade equals to propriedadeId
        defaultAmostraShouldBeFound("propriedadeId.equals=" + propriedadeId);

        // Get all the amostraList where propriedade equals to (propriedadeId + 1)
        defaultAmostraShouldNotBeFound("propriedadeId.equals=" + (propriedadeId + 1));
    }

    @Test
    @Transactional
    void getAllAmostrasByMedicoveterinarioIsEqualToSomething() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);
        Medicoveterinario medicoveterinario = MedicoveterinarioResourceIT.createEntity(em);
        em.persist(medicoveterinario);
        em.flush();
        amostra.setMedicoveterinario(medicoveterinario);
        amostraRepository.saveAndFlush(amostra);
        Long medicoveterinarioId = medicoveterinario.getId();

        // Get all the amostraList where medicoveterinario equals to medicoveterinarioId
        defaultAmostraShouldBeFound("medicoveterinarioId.equals=" + medicoveterinarioId);

        // Get all the amostraList where medicoveterinario equals to (medicoveterinarioId + 1)
        defaultAmostraShouldNotBeFound("medicoveterinarioId.equals=" + (medicoveterinarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAmostraShouldBeFound(String filter) throws Exception {
        restAmostraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amostra.getId().intValue())))
            .andExpect(jsonPath("$.[*].protocolo").value(hasItem(DEFAULT_PROTOCOLO)))
            .andExpect(jsonPath("$.[*].formaEnvio").value(hasItem(DEFAULT_FORMA_ENVIO)))
            .andExpect(jsonPath("$.[*].numeroAmostras").value(hasItem(DEFAULT_NUMERO_AMOSTRAS)))
            .andExpect(jsonPath("$.[*].especie").value(hasItem(DEFAULT_ESPECIE)))
            .andExpect(jsonPath("$.[*].dataInicial").value(hasItem(DEFAULT_DATA_INICIAL.toString())))
            .andExpect(jsonPath("$.[*].dataFinal").value(hasItem(DEFAULT_DATA_FINAL.toString())))
            .andExpect(jsonPath("$.[*].materialRecebido").value(hasItem(DEFAULT_MATERIAL_RECEBIDO)))
            .andExpect(jsonPath("$.[*].acondicionamento").value(hasItem(DEFAULT_ACONDICIONAMENTO)))
            .andExpect(jsonPath("$.[*].condicaoMaterial").value(hasItem(DEFAULT_CONDICAO_MATERIAL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].tipoMedVet").value(hasItem(DEFAULT_TIPO_MED_VET)))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL))))
            .andExpect(jsonPath("$.[*].tipoPagamento").value(hasItem(DEFAULT_TIPO_PAGAMENTO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO)));

        // Check, that the count call also returns 1
        restAmostraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAmostraShouldNotBeFound(String filter) throws Exception {
        restAmostraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAmostraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAmostra() throws Exception {
        // Get the amostra
        restAmostraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAmostra() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        int databaseSizeBeforeUpdate = amostraRepository.findAll().size();

        // Update the amostra
        Amostra updatedAmostra = amostraRepository.findById(amostra.getId()).get();
        // Disconnect from session so that the updates on updatedAmostra are not directly saved in db
        em.detach(updatedAmostra);
        updatedAmostra
            .protocolo(UPDATED_PROTOCOLO)
            .formaEnvio(UPDATED_FORMA_ENVIO)
            .numeroAmostras(UPDATED_NUMERO_AMOSTRAS)
            .especie(UPDATED_ESPECIE)
            .dataInicial(UPDATED_DATA_INICIAL)
            .dataFinal(UPDATED_DATA_FINAL)
            .materialRecebido(UPDATED_MATERIAL_RECEBIDO)
            .acondicionamento(UPDATED_ACONDICIONAMENTO)
            .condicaoMaterial(UPDATED_CONDICAO_MATERIAL)
            .status(UPDATED_STATUS)
            .tipoMedVet(UPDATED_TIPO_MED_VET)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .tipoPagamento(UPDATED_TIPO_PAGAMENTO)
            .situacao(UPDATED_SITUACAO);
        AmostraDTO amostraDTO = amostraMapper.toDto(updatedAmostra);

        restAmostraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, amostraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(amostraDTO))
            )
            .andExpect(status().isOk());

        // Validate the Amostra in the database
        List<Amostra> amostraList = amostraRepository.findAll();
        assertThat(amostraList).hasSize(databaseSizeBeforeUpdate);
        Amostra testAmostra = amostraList.get(amostraList.size() - 1);
        assertThat(testAmostra.getProtocolo()).isEqualTo(UPDATED_PROTOCOLO);
        assertThat(testAmostra.getFormaEnvio()).isEqualTo(UPDATED_FORMA_ENVIO);
        assertThat(testAmostra.getNumeroAmostras()).isEqualTo(UPDATED_NUMERO_AMOSTRAS);
        assertThat(testAmostra.getEspecie()).isEqualTo(UPDATED_ESPECIE);
        assertThat(testAmostra.getDataInicial()).isEqualTo(UPDATED_DATA_INICIAL);
        assertThat(testAmostra.getDataFinal()).isEqualTo(UPDATED_DATA_FINAL);
        assertThat(testAmostra.getMaterialRecebido()).isEqualTo(UPDATED_MATERIAL_RECEBIDO);
        assertThat(testAmostra.getAcondicionamento()).isEqualTo(UPDATED_ACONDICIONAMENTO);
        assertThat(testAmostra.getCondicaoMaterial()).isEqualTo(UPDATED_CONDICAO_MATERIAL);
        assertThat(testAmostra.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAmostra.getTipoMedVet()).isEqualTo(UPDATED_TIPO_MED_VET);
        assertThat(testAmostra.getValorTotal()).isEqualTo(UPDATED_VALOR_TOTAL);
        assertThat(testAmostra.getTipoPagamento()).isEqualTo(UPDATED_TIPO_PAGAMENTO);
        assertThat(testAmostra.getSituacao()).isEqualTo(UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void putNonExistingAmostra() throws Exception {
        int databaseSizeBeforeUpdate = amostraRepository.findAll().size();
        amostra.setId(count.incrementAndGet());

        // Create the Amostra
        AmostraDTO amostraDTO = amostraMapper.toDto(amostra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmostraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, amostraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(amostraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Amostra in the database
        List<Amostra> amostraList = amostraRepository.findAll();
        assertThat(amostraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAmostra() throws Exception {
        int databaseSizeBeforeUpdate = amostraRepository.findAll().size();
        amostra.setId(count.incrementAndGet());

        // Create the Amostra
        AmostraDTO amostraDTO = amostraMapper.toDto(amostra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmostraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(amostraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Amostra in the database
        List<Amostra> amostraList = amostraRepository.findAll();
        assertThat(amostraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAmostra() throws Exception {
        int databaseSizeBeforeUpdate = amostraRepository.findAll().size();
        amostra.setId(count.incrementAndGet());

        // Create the Amostra
        AmostraDTO amostraDTO = amostraMapper.toDto(amostra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmostraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(amostraDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Amostra in the database
        List<Amostra> amostraList = amostraRepository.findAll();
        assertThat(amostraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAmostraWithPatch() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        int databaseSizeBeforeUpdate = amostraRepository.findAll().size();

        // Update the amostra using partial update
        Amostra partialUpdatedAmostra = new Amostra();
        partialUpdatedAmostra.setId(amostra.getId());

        partialUpdatedAmostra
            .protocolo(UPDATED_PROTOCOLO)
            .formaEnvio(UPDATED_FORMA_ENVIO)
            .numeroAmostras(UPDATED_NUMERO_AMOSTRAS)
            .especie(UPDATED_ESPECIE);

        restAmostraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAmostra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAmostra))
            )
            .andExpect(status().isOk());

        // Validate the Amostra in the database
        List<Amostra> amostraList = amostraRepository.findAll();
        assertThat(amostraList).hasSize(databaseSizeBeforeUpdate);
        Amostra testAmostra = amostraList.get(amostraList.size() - 1);
        assertThat(testAmostra.getProtocolo()).isEqualTo(UPDATED_PROTOCOLO);
        assertThat(testAmostra.getFormaEnvio()).isEqualTo(UPDATED_FORMA_ENVIO);
        assertThat(testAmostra.getNumeroAmostras()).isEqualTo(UPDATED_NUMERO_AMOSTRAS);
        assertThat(testAmostra.getEspecie()).isEqualTo(UPDATED_ESPECIE);
        assertThat(testAmostra.getDataInicial()).isEqualTo(DEFAULT_DATA_INICIAL);
        assertThat(testAmostra.getDataFinal()).isEqualTo(DEFAULT_DATA_FINAL);
        assertThat(testAmostra.getMaterialRecebido()).isEqualTo(DEFAULT_MATERIAL_RECEBIDO);
        assertThat(testAmostra.getAcondicionamento()).isEqualTo(DEFAULT_ACONDICIONAMENTO);
        assertThat(testAmostra.getCondicaoMaterial()).isEqualTo(DEFAULT_CONDICAO_MATERIAL);
        assertThat(testAmostra.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAmostra.getTipoMedVet()).isEqualTo(DEFAULT_TIPO_MED_VET);
        assertThat(testAmostra.getValorTotal()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL);
        assertThat(testAmostra.getTipoPagamento()).isEqualTo(DEFAULT_TIPO_PAGAMENTO);
        assertThat(testAmostra.getSituacao()).isEqualTo(DEFAULT_SITUACAO);
    }

    @Test
    @Transactional
    void fullUpdateAmostraWithPatch() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        int databaseSizeBeforeUpdate = amostraRepository.findAll().size();

        // Update the amostra using partial update
        Amostra partialUpdatedAmostra = new Amostra();
        partialUpdatedAmostra.setId(amostra.getId());

        partialUpdatedAmostra
            .protocolo(UPDATED_PROTOCOLO)
            .formaEnvio(UPDATED_FORMA_ENVIO)
            .numeroAmostras(UPDATED_NUMERO_AMOSTRAS)
            .especie(UPDATED_ESPECIE)
            .dataInicial(UPDATED_DATA_INICIAL)
            .dataFinal(UPDATED_DATA_FINAL)
            .materialRecebido(UPDATED_MATERIAL_RECEBIDO)
            .acondicionamento(UPDATED_ACONDICIONAMENTO)
            .condicaoMaterial(UPDATED_CONDICAO_MATERIAL)
            .status(UPDATED_STATUS)
            .tipoMedVet(UPDATED_TIPO_MED_VET)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .tipoPagamento(UPDATED_TIPO_PAGAMENTO)
            .situacao(UPDATED_SITUACAO);

        restAmostraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAmostra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAmostra))
            )
            .andExpect(status().isOk());

        // Validate the Amostra in the database
        List<Amostra> amostraList = amostraRepository.findAll();
        assertThat(amostraList).hasSize(databaseSizeBeforeUpdate);
        Amostra testAmostra = amostraList.get(amostraList.size() - 1);
        assertThat(testAmostra.getProtocolo()).isEqualTo(UPDATED_PROTOCOLO);
        assertThat(testAmostra.getFormaEnvio()).isEqualTo(UPDATED_FORMA_ENVIO);
        assertThat(testAmostra.getNumeroAmostras()).isEqualTo(UPDATED_NUMERO_AMOSTRAS);
        assertThat(testAmostra.getEspecie()).isEqualTo(UPDATED_ESPECIE);
        assertThat(testAmostra.getDataInicial()).isEqualTo(UPDATED_DATA_INICIAL);
        assertThat(testAmostra.getDataFinal()).isEqualTo(UPDATED_DATA_FINAL);
        assertThat(testAmostra.getMaterialRecebido()).isEqualTo(UPDATED_MATERIAL_RECEBIDO);
        assertThat(testAmostra.getAcondicionamento()).isEqualTo(UPDATED_ACONDICIONAMENTO);
        assertThat(testAmostra.getCondicaoMaterial()).isEqualTo(UPDATED_CONDICAO_MATERIAL);
        assertThat(testAmostra.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAmostra.getTipoMedVet()).isEqualTo(UPDATED_TIPO_MED_VET);
        assertThat(testAmostra.getValorTotal()).isEqualByComparingTo(UPDATED_VALOR_TOTAL);
        assertThat(testAmostra.getTipoPagamento()).isEqualTo(UPDATED_TIPO_PAGAMENTO);
        assertThat(testAmostra.getSituacao()).isEqualTo(UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void patchNonExistingAmostra() throws Exception {
        int databaseSizeBeforeUpdate = amostraRepository.findAll().size();
        amostra.setId(count.incrementAndGet());

        // Create the Amostra
        AmostraDTO amostraDTO = amostraMapper.toDto(amostra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmostraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, amostraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(amostraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Amostra in the database
        List<Amostra> amostraList = amostraRepository.findAll();
        assertThat(amostraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAmostra() throws Exception {
        int databaseSizeBeforeUpdate = amostraRepository.findAll().size();
        amostra.setId(count.incrementAndGet());

        // Create the Amostra
        AmostraDTO amostraDTO = amostraMapper.toDto(amostra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmostraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(amostraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Amostra in the database
        List<Amostra> amostraList = amostraRepository.findAll();
        assertThat(amostraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAmostra() throws Exception {
        int databaseSizeBeforeUpdate = amostraRepository.findAll().size();
        amostra.setId(count.incrementAndGet());

        // Create the Amostra
        AmostraDTO amostraDTO = amostraMapper.toDto(amostra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmostraMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(amostraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Amostra in the database
        List<Amostra> amostraList = amostraRepository.findAll();
        assertThat(amostraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAmostra() throws Exception {
        // Initialize the database
        amostraRepository.saveAndFlush(amostra);

        int databaseSizeBeforeDelete = amostraRepository.findAll().size();

        // Delete the amostra
        restAmostraMockMvc
            .perform(delete(ENTITY_API_URL_ID, amostra.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Amostra> amostraList = amostraRepository.findAll();
        assertThat(amostraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
