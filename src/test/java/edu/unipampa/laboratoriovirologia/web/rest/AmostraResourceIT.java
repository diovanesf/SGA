package edu.unipampa.laboratoriovirologia.web.rest;

import static edu.unipampa.laboratoriovirologia.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.unipampa.laboratoriovirologia.IntegrationTest;
import edu.unipampa.laboratoriovirologia.domain.Amostra;
import edu.unipampa.laboratoriovirologia.repository.AmostraRepository;
import edu.unipampa.laboratoriovirologia.service.AmostraService;
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

    private static final String DEFAULT_ESPECIE = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_INICIAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INICIAL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_FINAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FINAL = LocalDate.now(ZoneId.systemDefault());

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
