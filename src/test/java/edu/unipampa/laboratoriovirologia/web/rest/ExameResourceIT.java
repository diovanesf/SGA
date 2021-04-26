package edu.unipampa.laboratoriovirologia.web.rest;

import static edu.unipampa.laboratoriovirologia.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.unipampa.laboratoriovirologia.IntegrationTest;
import edu.unipampa.laboratoriovirologia.domain.Exame;
import edu.unipampa.laboratoriovirologia.repository.ExameRepository;
import edu.unipampa.laboratoriovirologia.service.dto.ExameDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.ExameMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ExameResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExameResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_PESO_MATERIAL = "AAAAAAAAAA";
    private static final String UPDATED_PESO_MATERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_ESTIMATIVA_VACINAS = "AAAAAAAAAA";
    private static final String UPDATED_ESTIMATIVA_VACINAS = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTADO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_TESTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_TESTE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_LEITURA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_LEITURA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PREENCHIMENTO_ESPELHO = "AAAAAAAAAA";
    private static final String UPDATED_PREENCHIMENTO_ESPELHO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/exames";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExameRepository exameRepository;

    @Autowired
    private ExameMapper exameMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExameMockMvc;

    private Exame exame;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exame createEntity(EntityManager em) {
        Exame exame = new Exame()
            .nome(DEFAULT_NOME)
            .tipo(DEFAULT_TIPO)
            .pesoMaterial(DEFAULT_PESO_MATERIAL)
            .estimativaVacinas(DEFAULT_ESTIMATIVA_VACINAS)
            .resultado(DEFAULT_RESULTADO)
            .dataTeste(DEFAULT_DATA_TESTE)
            .dataLeitura(DEFAULT_DATA_LEITURA)
            .preenchimentoEspelho(DEFAULT_PREENCHIMENTO_ESPELHO)
            .observacoes(DEFAULT_OBSERVACOES)
            .valor(DEFAULT_VALOR);
        return exame;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exame createUpdatedEntity(EntityManager em) {
        Exame exame = new Exame()
            .nome(UPDATED_NOME)
            .tipo(UPDATED_TIPO)
            .pesoMaterial(UPDATED_PESO_MATERIAL)
            .estimativaVacinas(UPDATED_ESTIMATIVA_VACINAS)
            .resultado(UPDATED_RESULTADO)
            .dataTeste(UPDATED_DATA_TESTE)
            .dataLeitura(UPDATED_DATA_LEITURA)
            .preenchimentoEspelho(UPDATED_PREENCHIMENTO_ESPELHO)
            .observacoes(UPDATED_OBSERVACOES)
            .valor(UPDATED_VALOR);
        return exame;
    }

    @BeforeEach
    public void initTest() {
        exame = createEntity(em);
    }

    @Test
    @Transactional
    void createExame() throws Exception {
        int databaseSizeBeforeCreate = exameRepository.findAll().size();
        // Create the Exame
        ExameDTO exameDTO = exameMapper.toDto(exame);
        restExameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exameDTO)))
            .andExpect(status().isCreated());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeCreate + 1);
        Exame testExame = exameList.get(exameList.size() - 1);
        assertThat(testExame.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testExame.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testExame.getPesoMaterial()).isEqualTo(DEFAULT_PESO_MATERIAL);
        assertThat(testExame.getEstimativaVacinas()).isEqualTo(DEFAULT_ESTIMATIVA_VACINAS);
        assertThat(testExame.getResultado()).isEqualTo(DEFAULT_RESULTADO);
        assertThat(testExame.getDataTeste()).isEqualTo(DEFAULT_DATA_TESTE);
        assertThat(testExame.getDataLeitura()).isEqualTo(DEFAULT_DATA_LEITURA);
        assertThat(testExame.getPreenchimentoEspelho()).isEqualTo(DEFAULT_PREENCHIMENTO_ESPELHO);
        assertThat(testExame.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testExame.getValor()).isEqualByComparingTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void createExameWithExistingId() throws Exception {
        // Create the Exame with an existing ID
        exame.setId(1L);
        ExameDTO exameDTO = exameMapper.toDto(exame);

        int databaseSizeBeforeCreate = exameRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllExames() throws Exception {
        // Initialize the database
        exameRepository.saveAndFlush(exame);

        // Get all the exameList
        restExameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exame.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].pesoMaterial").value(hasItem(DEFAULT_PESO_MATERIAL)))
            .andExpect(jsonPath("$.[*].estimativaVacinas").value(hasItem(DEFAULT_ESTIMATIVA_VACINAS)))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO)))
            .andExpect(jsonPath("$.[*].dataTeste").value(hasItem(DEFAULT_DATA_TESTE.toString())))
            .andExpect(jsonPath("$.[*].dataLeitura").value(hasItem(DEFAULT_DATA_LEITURA.toString())))
            .andExpect(jsonPath("$.[*].preenchimentoEspelho").value(hasItem(DEFAULT_PREENCHIMENTO_ESPELHO.toString())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(sameNumber(DEFAULT_VALOR))));
    }

    @Test
    @Transactional
    void getExame() throws Exception {
        // Initialize the database
        exameRepository.saveAndFlush(exame);

        // Get the exame
        restExameMockMvc
            .perform(get(ENTITY_API_URL_ID, exame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exame.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.pesoMaterial").value(DEFAULT_PESO_MATERIAL))
            .andExpect(jsonPath("$.estimativaVacinas").value(DEFAULT_ESTIMATIVA_VACINAS))
            .andExpect(jsonPath("$.resultado").value(DEFAULT_RESULTADO))
            .andExpect(jsonPath("$.dataTeste").value(DEFAULT_DATA_TESTE.toString()))
            .andExpect(jsonPath("$.dataLeitura").value(DEFAULT_DATA_LEITURA.toString()))
            .andExpect(jsonPath("$.preenchimentoEspelho").value(DEFAULT_PREENCHIMENTO_ESPELHO.toString()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()))
            .andExpect(jsonPath("$.valor").value(sameNumber(DEFAULT_VALOR)));
    }

    @Test
    @Transactional
    void getNonExistingExame() throws Exception {
        // Get the exame
        restExameMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewExame() throws Exception {
        // Initialize the database
        exameRepository.saveAndFlush(exame);

        int databaseSizeBeforeUpdate = exameRepository.findAll().size();

        // Update the exame
        Exame updatedExame = exameRepository.findById(exame.getId()).get();
        // Disconnect from session so that the updates on updatedExame are not directly saved in db
        em.detach(updatedExame);
        updatedExame
            .nome(UPDATED_NOME)
            .tipo(UPDATED_TIPO)
            .pesoMaterial(UPDATED_PESO_MATERIAL)
            .estimativaVacinas(UPDATED_ESTIMATIVA_VACINAS)
            .resultado(UPDATED_RESULTADO)
            .dataTeste(UPDATED_DATA_TESTE)
            .dataLeitura(UPDATED_DATA_LEITURA)
            .preenchimentoEspelho(UPDATED_PREENCHIMENTO_ESPELHO)
            .observacoes(UPDATED_OBSERVACOES)
            .valor(UPDATED_VALOR);
        ExameDTO exameDTO = exameMapper.toDto(updatedExame);

        restExameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, exameDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exameDTO))
            )
            .andExpect(status().isOk());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeUpdate);
        Exame testExame = exameList.get(exameList.size() - 1);
        assertThat(testExame.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testExame.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testExame.getPesoMaterial()).isEqualTo(UPDATED_PESO_MATERIAL);
        assertThat(testExame.getEstimativaVacinas()).isEqualTo(UPDATED_ESTIMATIVA_VACINAS);
        assertThat(testExame.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testExame.getDataTeste()).isEqualTo(UPDATED_DATA_TESTE);
        assertThat(testExame.getDataLeitura()).isEqualTo(UPDATED_DATA_LEITURA);
        assertThat(testExame.getPreenchimentoEspelho()).isEqualTo(UPDATED_PREENCHIMENTO_ESPELHO);
        assertThat(testExame.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testExame.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void putNonExistingExame() throws Exception {
        int databaseSizeBeforeUpdate = exameRepository.findAll().size();
        exame.setId(count.incrementAndGet());

        // Create the Exame
        ExameDTO exameDTO = exameMapper.toDto(exame);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, exameDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExame() throws Exception {
        int databaseSizeBeforeUpdate = exameRepository.findAll().size();
        exame.setId(count.incrementAndGet());

        // Create the Exame
        ExameDTO exameDTO = exameMapper.toDto(exame);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExame() throws Exception {
        int databaseSizeBeforeUpdate = exameRepository.findAll().size();
        exame.setId(count.incrementAndGet());

        // Create the Exame
        ExameDTO exameDTO = exameMapper.toDto(exame);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExameMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exameDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExameWithPatch() throws Exception {
        // Initialize the database
        exameRepository.saveAndFlush(exame);

        int databaseSizeBeforeUpdate = exameRepository.findAll().size();

        // Update the exame using partial update
        Exame partialUpdatedExame = new Exame();
        partialUpdatedExame.setId(exame.getId());

        partialUpdatedExame
            .nome(UPDATED_NOME)
            .pesoMaterial(UPDATED_PESO_MATERIAL)
            .resultado(UPDATED_RESULTADO)
            .dataTeste(UPDATED_DATA_TESTE);

        restExameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExame.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExame))
            )
            .andExpect(status().isOk());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeUpdate);
        Exame testExame = exameList.get(exameList.size() - 1);
        assertThat(testExame.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testExame.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testExame.getPesoMaterial()).isEqualTo(UPDATED_PESO_MATERIAL);
        assertThat(testExame.getEstimativaVacinas()).isEqualTo(DEFAULT_ESTIMATIVA_VACINAS);
        assertThat(testExame.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testExame.getDataTeste()).isEqualTo(UPDATED_DATA_TESTE);
        assertThat(testExame.getDataLeitura()).isEqualTo(DEFAULT_DATA_LEITURA);
        assertThat(testExame.getPreenchimentoEspelho()).isEqualTo(DEFAULT_PREENCHIMENTO_ESPELHO);
        assertThat(testExame.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testExame.getValor()).isEqualByComparingTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void fullUpdateExameWithPatch() throws Exception {
        // Initialize the database
        exameRepository.saveAndFlush(exame);

        int databaseSizeBeforeUpdate = exameRepository.findAll().size();

        // Update the exame using partial update
        Exame partialUpdatedExame = new Exame();
        partialUpdatedExame.setId(exame.getId());

        partialUpdatedExame
            .nome(UPDATED_NOME)
            .tipo(UPDATED_TIPO)
            .pesoMaterial(UPDATED_PESO_MATERIAL)
            .estimativaVacinas(UPDATED_ESTIMATIVA_VACINAS)
            .resultado(UPDATED_RESULTADO)
            .dataTeste(UPDATED_DATA_TESTE)
            .dataLeitura(UPDATED_DATA_LEITURA)
            .preenchimentoEspelho(UPDATED_PREENCHIMENTO_ESPELHO)
            .observacoes(UPDATED_OBSERVACOES)
            .valor(UPDATED_VALOR);

        restExameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExame.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExame))
            )
            .andExpect(status().isOk());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeUpdate);
        Exame testExame = exameList.get(exameList.size() - 1);
        assertThat(testExame.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testExame.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testExame.getPesoMaterial()).isEqualTo(UPDATED_PESO_MATERIAL);
        assertThat(testExame.getEstimativaVacinas()).isEqualTo(UPDATED_ESTIMATIVA_VACINAS);
        assertThat(testExame.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testExame.getDataTeste()).isEqualTo(UPDATED_DATA_TESTE);
        assertThat(testExame.getDataLeitura()).isEqualTo(UPDATED_DATA_LEITURA);
        assertThat(testExame.getPreenchimentoEspelho()).isEqualTo(UPDATED_PREENCHIMENTO_ESPELHO);
        assertThat(testExame.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testExame.getValor()).isEqualByComparingTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void patchNonExistingExame() throws Exception {
        int databaseSizeBeforeUpdate = exameRepository.findAll().size();
        exame.setId(count.incrementAndGet());

        // Create the Exame
        ExameDTO exameDTO = exameMapper.toDto(exame);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, exameDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExame() throws Exception {
        int databaseSizeBeforeUpdate = exameRepository.findAll().size();
        exame.setId(count.incrementAndGet());

        // Create the Exame
        ExameDTO exameDTO = exameMapper.toDto(exame);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExame() throws Exception {
        int databaseSizeBeforeUpdate = exameRepository.findAll().size();
        exame.setId(count.incrementAndGet());

        // Create the Exame
        ExameDTO exameDTO = exameMapper.toDto(exame);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExameMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(exameDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExame() throws Exception {
        // Initialize the database
        exameRepository.saveAndFlush(exame);

        int databaseSizeBeforeDelete = exameRepository.findAll().size();

        // Delete the exame
        restExameMockMvc
            .perform(delete(ENTITY_API_URL_ID, exame.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
