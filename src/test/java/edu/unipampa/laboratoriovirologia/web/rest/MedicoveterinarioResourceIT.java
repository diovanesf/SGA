package edu.unipampa.laboratoriovirologia.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.unipampa.laboratoriovirologia.IntegrationTest;
import edu.unipampa.laboratoriovirologia.domain.Medicoveterinario;
import edu.unipampa.laboratoriovirologia.repository.MedicoveterinarioRepository;
import edu.unipampa.laboratoriovirologia.service.dto.MedicoveterinarioDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.MedicoveterinarioMapper;
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
 * Integration tests for the {@link MedicoveterinarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MedicoveterinarioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CRMV = "AAAAAAAAAA";
    private static final String UPDATED_CRMV = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENVIAR_LAUDO = false;
    private static final Boolean UPDATED_ENVIAR_LAUDO = true;

    private static final String ENTITY_API_URL = "/api/medicoveterinarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MedicoveterinarioRepository medicoveterinarioRepository;

    @Autowired
    private MedicoveterinarioMapper medicoveterinarioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicoveterinarioMockMvc;

    private Medicoveterinario medicoveterinario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicoveterinario createEntity(EntityManager em) {
        Medicoveterinario medicoveterinario = new Medicoveterinario()
            .nome(DEFAULT_NOME)
            .telefone(DEFAULT_TELEFONE)
            .email(DEFAULT_EMAIL)
            .CRMV(DEFAULT_CRMV)
            .enviarLaudo(DEFAULT_ENVIAR_LAUDO);
        return medicoveterinario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicoveterinario createUpdatedEntity(EntityManager em) {
        Medicoveterinario medicoveterinario = new Medicoveterinario()
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .CRMV(UPDATED_CRMV)
            .enviarLaudo(UPDATED_ENVIAR_LAUDO);
        return medicoveterinario;
    }

    @BeforeEach
    public void initTest() {
        medicoveterinario = createEntity(em);
    }

    @Test
    @Transactional
    void createMedicoveterinario() throws Exception {
        int databaseSizeBeforeCreate = medicoveterinarioRepository.findAll().size();
        // Create the Medicoveterinario
        MedicoveterinarioDTO medicoveterinarioDTO = medicoveterinarioMapper.toDto(medicoveterinario);
        restMedicoveterinarioMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicoveterinarioDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Medicoveterinario in the database
        List<Medicoveterinario> medicoveterinarioList = medicoveterinarioRepository.findAll();
        assertThat(medicoveterinarioList).hasSize(databaseSizeBeforeCreate + 1);
        Medicoveterinario testMedicoveterinario = medicoveterinarioList.get(medicoveterinarioList.size() - 1);
        assertThat(testMedicoveterinario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMedicoveterinario.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testMedicoveterinario.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMedicoveterinario.getCRMV()).isEqualTo(DEFAULT_CRMV);
        assertThat(testMedicoveterinario.getEnviarLaudo()).isEqualTo(DEFAULT_ENVIAR_LAUDO);
    }

    @Test
    @Transactional
    void createMedicoveterinarioWithExistingId() throws Exception {
        // Create the Medicoveterinario with an existing ID
        medicoveterinario.setId(1L);
        MedicoveterinarioDTO medicoveterinarioDTO = medicoveterinarioMapper.toDto(medicoveterinario);

        int databaseSizeBeforeCreate = medicoveterinarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicoveterinarioMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicoveterinarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicoveterinario in the database
        List<Medicoveterinario> medicoveterinarioList = medicoveterinarioRepository.findAll();
        assertThat(medicoveterinarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMedicoveterinarios() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList
        restMedicoveterinarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicoveterinario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].CRMV").value(hasItem(DEFAULT_CRMV)))
            .andExpect(jsonPath("$.[*].enviarLaudo").value(hasItem(DEFAULT_ENVIAR_LAUDO.booleanValue())));
    }

    @Test
    @Transactional
    void getMedicoveterinario() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get the medicoveterinario
        restMedicoveterinarioMockMvc
            .perform(get(ENTITY_API_URL_ID, medicoveterinario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicoveterinario.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.CRMV").value(DEFAULT_CRMV))
            .andExpect(jsonPath("$.enviarLaudo").value(DEFAULT_ENVIAR_LAUDO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMedicoveterinario() throws Exception {
        // Get the medicoveterinario
        restMedicoveterinarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMedicoveterinario() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        int databaseSizeBeforeUpdate = medicoveterinarioRepository.findAll().size();

        // Update the medicoveterinario
        Medicoveterinario updatedMedicoveterinario = medicoveterinarioRepository.findById(medicoveterinario.getId()).get();
        // Disconnect from session so that the updates on updatedMedicoveterinario are not directly saved in db
        em.detach(updatedMedicoveterinario);
        updatedMedicoveterinario
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .CRMV(UPDATED_CRMV)
            .enviarLaudo(UPDATED_ENVIAR_LAUDO);
        MedicoveterinarioDTO medicoveterinarioDTO = medicoveterinarioMapper.toDto(updatedMedicoveterinario);

        restMedicoveterinarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medicoveterinarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicoveterinarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Medicoveterinario in the database
        List<Medicoveterinario> medicoveterinarioList = medicoveterinarioRepository.findAll();
        assertThat(medicoveterinarioList).hasSize(databaseSizeBeforeUpdate);
        Medicoveterinario testMedicoveterinario = medicoveterinarioList.get(medicoveterinarioList.size() - 1);
        assertThat(testMedicoveterinario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMedicoveterinario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testMedicoveterinario.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMedicoveterinario.getCRMV()).isEqualTo(UPDATED_CRMV);
        assertThat(testMedicoveterinario.getEnviarLaudo()).isEqualTo(UPDATED_ENVIAR_LAUDO);
    }

    @Test
    @Transactional
    void putNonExistingMedicoveterinario() throws Exception {
        int databaseSizeBeforeUpdate = medicoveterinarioRepository.findAll().size();
        medicoveterinario.setId(count.incrementAndGet());

        // Create the Medicoveterinario
        MedicoveterinarioDTO medicoveterinarioDTO = medicoveterinarioMapper.toDto(medicoveterinario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicoveterinarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medicoveterinarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicoveterinarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicoveterinario in the database
        List<Medicoveterinario> medicoveterinarioList = medicoveterinarioRepository.findAll();
        assertThat(medicoveterinarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedicoveterinario() throws Exception {
        int databaseSizeBeforeUpdate = medicoveterinarioRepository.findAll().size();
        medicoveterinario.setId(count.incrementAndGet());

        // Create the Medicoveterinario
        MedicoveterinarioDTO medicoveterinarioDTO = medicoveterinarioMapper.toDto(medicoveterinario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicoveterinarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicoveterinarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicoveterinario in the database
        List<Medicoveterinario> medicoveterinarioList = medicoveterinarioRepository.findAll();
        assertThat(medicoveterinarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedicoveterinario() throws Exception {
        int databaseSizeBeforeUpdate = medicoveterinarioRepository.findAll().size();
        medicoveterinario.setId(count.incrementAndGet());

        // Create the Medicoveterinario
        MedicoveterinarioDTO medicoveterinarioDTO = medicoveterinarioMapper.toDto(medicoveterinario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicoveterinarioMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medicoveterinarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medicoveterinario in the database
        List<Medicoveterinario> medicoveterinarioList = medicoveterinarioRepository.findAll();
        assertThat(medicoveterinarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMedicoveterinarioWithPatch() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        int databaseSizeBeforeUpdate = medicoveterinarioRepository.findAll().size();

        // Update the medicoveterinario using partial update
        Medicoveterinario partialUpdatedMedicoveterinario = new Medicoveterinario();
        partialUpdatedMedicoveterinario.setId(medicoveterinario.getId());

        partialUpdatedMedicoveterinario.telefone(UPDATED_TELEFONE).email(UPDATED_EMAIL);

        restMedicoveterinarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicoveterinario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedicoveterinario))
            )
            .andExpect(status().isOk());

        // Validate the Medicoveterinario in the database
        List<Medicoveterinario> medicoveterinarioList = medicoveterinarioRepository.findAll();
        assertThat(medicoveterinarioList).hasSize(databaseSizeBeforeUpdate);
        Medicoveterinario testMedicoveterinario = medicoveterinarioList.get(medicoveterinarioList.size() - 1);
        assertThat(testMedicoveterinario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMedicoveterinario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testMedicoveterinario.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMedicoveterinario.getCRMV()).isEqualTo(DEFAULT_CRMV);
        assertThat(testMedicoveterinario.getEnviarLaudo()).isEqualTo(DEFAULT_ENVIAR_LAUDO);
    }

    @Test
    @Transactional
    void fullUpdateMedicoveterinarioWithPatch() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        int databaseSizeBeforeUpdate = medicoveterinarioRepository.findAll().size();

        // Update the medicoveterinario using partial update
        Medicoveterinario partialUpdatedMedicoveterinario = new Medicoveterinario();
        partialUpdatedMedicoveterinario.setId(medicoveterinario.getId());

        partialUpdatedMedicoveterinario
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .CRMV(UPDATED_CRMV)
            .enviarLaudo(UPDATED_ENVIAR_LAUDO);

        restMedicoveterinarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicoveterinario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedicoveterinario))
            )
            .andExpect(status().isOk());

        // Validate the Medicoveterinario in the database
        List<Medicoveterinario> medicoveterinarioList = medicoveterinarioRepository.findAll();
        assertThat(medicoveterinarioList).hasSize(databaseSizeBeforeUpdate);
        Medicoveterinario testMedicoveterinario = medicoveterinarioList.get(medicoveterinarioList.size() - 1);
        assertThat(testMedicoveterinario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMedicoveterinario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testMedicoveterinario.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMedicoveterinario.getCRMV()).isEqualTo(UPDATED_CRMV);
        assertThat(testMedicoveterinario.getEnviarLaudo()).isEqualTo(UPDATED_ENVIAR_LAUDO);
    }

    @Test
    @Transactional
    void patchNonExistingMedicoveterinario() throws Exception {
        int databaseSizeBeforeUpdate = medicoveterinarioRepository.findAll().size();
        medicoveterinario.setId(count.incrementAndGet());

        // Create the Medicoveterinario
        MedicoveterinarioDTO medicoveterinarioDTO = medicoveterinarioMapper.toDto(medicoveterinario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicoveterinarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, medicoveterinarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medicoveterinarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicoveterinario in the database
        List<Medicoveterinario> medicoveterinarioList = medicoveterinarioRepository.findAll();
        assertThat(medicoveterinarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedicoveterinario() throws Exception {
        int databaseSizeBeforeUpdate = medicoveterinarioRepository.findAll().size();
        medicoveterinario.setId(count.incrementAndGet());

        // Create the Medicoveterinario
        MedicoveterinarioDTO medicoveterinarioDTO = medicoveterinarioMapper.toDto(medicoveterinario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicoveterinarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medicoveterinarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicoveterinario in the database
        List<Medicoveterinario> medicoveterinarioList = medicoveterinarioRepository.findAll();
        assertThat(medicoveterinarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedicoveterinario() throws Exception {
        int databaseSizeBeforeUpdate = medicoveterinarioRepository.findAll().size();
        medicoveterinario.setId(count.incrementAndGet());

        // Create the Medicoveterinario
        MedicoveterinarioDTO medicoveterinarioDTO = medicoveterinarioMapper.toDto(medicoveterinario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicoveterinarioMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medicoveterinarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medicoveterinario in the database
        List<Medicoveterinario> medicoveterinarioList = medicoveterinarioRepository.findAll();
        assertThat(medicoveterinarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedicoveterinario() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        int databaseSizeBeforeDelete = medicoveterinarioRepository.findAll().size();

        // Delete the medicoveterinario
        restMedicoveterinarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, medicoveterinario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Medicoveterinario> medicoveterinarioList = medicoveterinarioRepository.findAll();
        assertThat(medicoveterinarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
