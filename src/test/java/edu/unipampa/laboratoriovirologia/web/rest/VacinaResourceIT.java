package edu.unipampa.laboratoriovirologia.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.unipampa.laboratoriovirologia.IntegrationTest;
import edu.unipampa.laboratoriovirologia.domain.Vacina;
import edu.unipampa.laboratoriovirologia.repository.VacinaRepository;
import edu.unipampa.laboratoriovirologia.service.dto.VacinaDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.VacinaMapper;
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
 * Integration tests for the {@link VacinaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VacinaResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PESO_MATERIAL = "AAAAAAAAAA";
    private static final String UPDATED_PESO_MATERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_ESTIMATIVA_VACINAS = "AAAAAAAAAA";
    private static final String UPDATED_ESTIMATIVA_VACINAS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_CONCLUSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CONCLUSAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vacinas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private VacinaMapper vacinaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVacinaMockMvc;

    private Vacina vacina;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vacina createEntity(EntityManager em) {
        Vacina vacina = new Vacina()
            .status(DEFAULT_STATUS)
            .pesoMaterial(DEFAULT_PESO_MATERIAL)
            .estimativaVacinas(DEFAULT_ESTIMATIVA_VACINAS)
            .dataConclusao(DEFAULT_DATA_CONCLUSAO)
            .observacoes(DEFAULT_OBSERVACOES);
        return vacina;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vacina createUpdatedEntity(EntityManager em) {
        Vacina vacina = new Vacina()
            .status(UPDATED_STATUS)
            .pesoMaterial(UPDATED_PESO_MATERIAL)
            .estimativaVacinas(UPDATED_ESTIMATIVA_VACINAS)
            .dataConclusao(UPDATED_DATA_CONCLUSAO)
            .observacoes(UPDATED_OBSERVACOES);
        return vacina;
    }

    @BeforeEach
    public void initTest() {
        vacina = createEntity(em);
    }

    @Test
    @Transactional
    void createVacina() throws Exception {
        int databaseSizeBeforeCreate = vacinaRepository.findAll().size();
        // Create the Vacina
        VacinaDTO vacinaDTO = vacinaMapper.toDto(vacina);
        restVacinaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vacinaDTO)))
            .andExpect(status().isCreated());

        // Validate the Vacina in the database
        List<Vacina> vacinaList = vacinaRepository.findAll();
        assertThat(vacinaList).hasSize(databaseSizeBeforeCreate + 1);
        Vacina testVacina = vacinaList.get(vacinaList.size() - 1);
        assertThat(testVacina.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testVacina.getPesoMaterial()).isEqualTo(DEFAULT_PESO_MATERIAL);
        assertThat(testVacina.getEstimativaVacinas()).isEqualTo(DEFAULT_ESTIMATIVA_VACINAS);
        assertThat(testVacina.getDataConclusao()).isEqualTo(DEFAULT_DATA_CONCLUSAO);
        assertThat(testVacina.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    void createVacinaWithExistingId() throws Exception {
        // Create the Vacina with an existing ID
        vacina.setId(1L);
        VacinaDTO vacinaDTO = vacinaMapper.toDto(vacina);

        int databaseSizeBeforeCreate = vacinaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVacinaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vacinaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vacina in the database
        List<Vacina> vacinaList = vacinaRepository.findAll();
        assertThat(vacinaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVacinas() throws Exception {
        // Initialize the database
        vacinaRepository.saveAndFlush(vacina);

        // Get all the vacinaList
        restVacinaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vacina.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].pesoMaterial").value(hasItem(DEFAULT_PESO_MATERIAL)))
            .andExpect(jsonPath("$.[*].estimativaVacinas").value(hasItem(DEFAULT_ESTIMATIVA_VACINAS)))
            .andExpect(jsonPath("$.[*].dataConclusao").value(hasItem(DEFAULT_DATA_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())));
    }

    @Test
    @Transactional
    void getVacina() throws Exception {
        // Initialize the database
        vacinaRepository.saveAndFlush(vacina);

        // Get the vacina
        restVacinaMockMvc
            .perform(get(ENTITY_API_URL_ID, vacina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vacina.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.pesoMaterial").value(DEFAULT_PESO_MATERIAL))
            .andExpect(jsonPath("$.estimativaVacinas").value(DEFAULT_ESTIMATIVA_VACINAS))
            .andExpect(jsonPath("$.dataConclusao").value(DEFAULT_DATA_CONCLUSAO.toString()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()));
    }

    @Test
    @Transactional
    void getNonExistingVacina() throws Exception {
        // Get the vacina
        restVacinaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVacina() throws Exception {
        // Initialize the database
        vacinaRepository.saveAndFlush(vacina);

        int databaseSizeBeforeUpdate = vacinaRepository.findAll().size();

        // Update the vacina
        Vacina updatedVacina = vacinaRepository.findById(vacina.getId()).get();
        // Disconnect from session so that the updates on updatedVacina are not directly saved in db
        em.detach(updatedVacina);
        updatedVacina
            .status(UPDATED_STATUS)
            .pesoMaterial(UPDATED_PESO_MATERIAL)
            .estimativaVacinas(UPDATED_ESTIMATIVA_VACINAS)
            .dataConclusao(UPDATED_DATA_CONCLUSAO)
            .observacoes(UPDATED_OBSERVACOES);
        VacinaDTO vacinaDTO = vacinaMapper.toDto(updatedVacina);

        restVacinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vacinaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vacinaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Vacina in the database
        List<Vacina> vacinaList = vacinaRepository.findAll();
        assertThat(vacinaList).hasSize(databaseSizeBeforeUpdate);
        Vacina testVacina = vacinaList.get(vacinaList.size() - 1);
        assertThat(testVacina.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testVacina.getPesoMaterial()).isEqualTo(UPDATED_PESO_MATERIAL);
        assertThat(testVacina.getEstimativaVacinas()).isEqualTo(UPDATED_ESTIMATIVA_VACINAS);
        assertThat(testVacina.getDataConclusao()).isEqualTo(UPDATED_DATA_CONCLUSAO);
        assertThat(testVacina.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void putNonExistingVacina() throws Exception {
        int databaseSizeBeforeUpdate = vacinaRepository.findAll().size();
        vacina.setId(count.incrementAndGet());

        // Create the Vacina
        VacinaDTO vacinaDTO = vacinaMapper.toDto(vacina);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVacinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vacinaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vacinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vacina in the database
        List<Vacina> vacinaList = vacinaRepository.findAll();
        assertThat(vacinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVacina() throws Exception {
        int databaseSizeBeforeUpdate = vacinaRepository.findAll().size();
        vacina.setId(count.incrementAndGet());

        // Create the Vacina
        VacinaDTO vacinaDTO = vacinaMapper.toDto(vacina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVacinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vacinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vacina in the database
        List<Vacina> vacinaList = vacinaRepository.findAll();
        assertThat(vacinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVacina() throws Exception {
        int databaseSizeBeforeUpdate = vacinaRepository.findAll().size();
        vacina.setId(count.incrementAndGet());

        // Create the Vacina
        VacinaDTO vacinaDTO = vacinaMapper.toDto(vacina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVacinaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vacinaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vacina in the database
        List<Vacina> vacinaList = vacinaRepository.findAll();
        assertThat(vacinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVacinaWithPatch() throws Exception {
        // Initialize the database
        vacinaRepository.saveAndFlush(vacina);

        int databaseSizeBeforeUpdate = vacinaRepository.findAll().size();

        // Update the vacina using partial update
        Vacina partialUpdatedVacina = new Vacina();
        partialUpdatedVacina.setId(vacina.getId());

        partialUpdatedVacina.pesoMaterial(UPDATED_PESO_MATERIAL).observacoes(UPDATED_OBSERVACOES);

        restVacinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVacina.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVacina))
            )
            .andExpect(status().isOk());

        // Validate the Vacina in the database
        List<Vacina> vacinaList = vacinaRepository.findAll();
        assertThat(vacinaList).hasSize(databaseSizeBeforeUpdate);
        Vacina testVacina = vacinaList.get(vacinaList.size() - 1);
        assertThat(testVacina.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testVacina.getPesoMaterial()).isEqualTo(UPDATED_PESO_MATERIAL);
        assertThat(testVacina.getEstimativaVacinas()).isEqualTo(DEFAULT_ESTIMATIVA_VACINAS);
        assertThat(testVacina.getDataConclusao()).isEqualTo(DEFAULT_DATA_CONCLUSAO);
        assertThat(testVacina.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void fullUpdateVacinaWithPatch() throws Exception {
        // Initialize the database
        vacinaRepository.saveAndFlush(vacina);

        int databaseSizeBeforeUpdate = vacinaRepository.findAll().size();

        // Update the vacina using partial update
        Vacina partialUpdatedVacina = new Vacina();
        partialUpdatedVacina.setId(vacina.getId());

        partialUpdatedVacina
            .status(UPDATED_STATUS)
            .pesoMaterial(UPDATED_PESO_MATERIAL)
            .estimativaVacinas(UPDATED_ESTIMATIVA_VACINAS)
            .dataConclusao(UPDATED_DATA_CONCLUSAO)
            .observacoes(UPDATED_OBSERVACOES);

        restVacinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVacina.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVacina))
            )
            .andExpect(status().isOk());

        // Validate the Vacina in the database
        List<Vacina> vacinaList = vacinaRepository.findAll();
        assertThat(vacinaList).hasSize(databaseSizeBeforeUpdate);
        Vacina testVacina = vacinaList.get(vacinaList.size() - 1);
        assertThat(testVacina.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testVacina.getPesoMaterial()).isEqualTo(UPDATED_PESO_MATERIAL);
        assertThat(testVacina.getEstimativaVacinas()).isEqualTo(UPDATED_ESTIMATIVA_VACINAS);
        assertThat(testVacina.getDataConclusao()).isEqualTo(UPDATED_DATA_CONCLUSAO);
        assertThat(testVacina.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void patchNonExistingVacina() throws Exception {
        int databaseSizeBeforeUpdate = vacinaRepository.findAll().size();
        vacina.setId(count.incrementAndGet());

        // Create the Vacina
        VacinaDTO vacinaDTO = vacinaMapper.toDto(vacina);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVacinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vacinaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vacinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vacina in the database
        List<Vacina> vacinaList = vacinaRepository.findAll();
        assertThat(vacinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVacina() throws Exception {
        int databaseSizeBeforeUpdate = vacinaRepository.findAll().size();
        vacina.setId(count.incrementAndGet());

        // Create the Vacina
        VacinaDTO vacinaDTO = vacinaMapper.toDto(vacina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVacinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vacinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vacina in the database
        List<Vacina> vacinaList = vacinaRepository.findAll();
        assertThat(vacinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVacina() throws Exception {
        int databaseSizeBeforeUpdate = vacinaRepository.findAll().size();
        vacina.setId(count.incrementAndGet());

        // Create the Vacina
        VacinaDTO vacinaDTO = vacinaMapper.toDto(vacina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVacinaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vacinaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vacina in the database
        List<Vacina> vacinaList = vacinaRepository.findAll();
        assertThat(vacinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVacina() throws Exception {
        // Initialize the database
        vacinaRepository.saveAndFlush(vacina);

        int databaseSizeBeforeDelete = vacinaRepository.findAll().size();

        // Delete the vacina
        restVacinaMockMvc
            .perform(delete(ENTITY_API_URL_ID, vacina.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vacina> vacinaList = vacinaRepository.findAll();
        assertThat(vacinaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
