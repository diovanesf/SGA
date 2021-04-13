package edu.unipampa.laboratoriovirologia.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.unipampa.laboratoriovirologia.IntegrationTest;
import edu.unipampa.laboratoriovirologia.domain.Proprietario;
import edu.unipampa.laboratoriovirologia.repository.ProprietarioRepository;
import edu.unipampa.laboratoriovirologia.service.dto.ProprietarioDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.ProprietarioMapper;
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
 * Integration tests for the {@link ProprietarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProprietarioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/proprietarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @Autowired
    private ProprietarioMapper proprietarioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProprietarioMockMvc;

    private Proprietario proprietario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proprietario createEntity(EntityManager em) {
        Proprietario proprietario = new Proprietario().nome(DEFAULT_NOME).telefone(DEFAULT_TELEFONE).email(DEFAULT_EMAIL);
        return proprietario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proprietario createUpdatedEntity(EntityManager em) {
        Proprietario proprietario = new Proprietario().nome(UPDATED_NOME).telefone(UPDATED_TELEFONE).email(UPDATED_EMAIL);
        return proprietario;
    }

    @BeforeEach
    public void initTest() {
        proprietario = createEntity(em);
    }

    @Test
    @Transactional
    void createProprietario() throws Exception {
        int databaseSizeBeforeCreate = proprietarioRepository.findAll().size();
        // Create the Proprietario
        ProprietarioDTO proprietarioDTO = proprietarioMapper.toDto(proprietario);
        restProprietarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proprietarioDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Proprietario in the database
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeCreate + 1);
        Proprietario testProprietario = proprietarioList.get(proprietarioList.size() - 1);
        assertThat(testProprietario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProprietario.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testProprietario.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createProprietarioWithExistingId() throws Exception {
        // Create the Proprietario with an existing ID
        proprietario.setId(1L);
        ProprietarioDTO proprietarioDTO = proprietarioMapper.toDto(proprietario);

        int databaseSizeBeforeCreate = proprietarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProprietarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proprietarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proprietario in the database
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProprietarios() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList
        restProprietarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proprietario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getProprietario() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get the proprietario
        restProprietarioMockMvc
            .perform(get(ENTITY_API_URL_ID, proprietario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proprietario.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingProprietario() throws Exception {
        // Get the proprietario
        restProprietarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProprietario() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        int databaseSizeBeforeUpdate = proprietarioRepository.findAll().size();

        // Update the proprietario
        Proprietario updatedProprietario = proprietarioRepository.findById(proprietario.getId()).get();
        // Disconnect from session so that the updates on updatedProprietario are not directly saved in db
        em.detach(updatedProprietario);
        updatedProprietario.nome(UPDATED_NOME).telefone(UPDATED_TELEFONE).email(UPDATED_EMAIL);
        ProprietarioDTO proprietarioDTO = proprietarioMapper.toDto(updatedProprietario);

        restProprietarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proprietarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(proprietarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Proprietario in the database
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeUpdate);
        Proprietario testProprietario = proprietarioList.get(proprietarioList.size() - 1);
        assertThat(testProprietario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProprietario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testProprietario.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingProprietario() throws Exception {
        int databaseSizeBeforeUpdate = proprietarioRepository.findAll().size();
        proprietario.setId(count.incrementAndGet());

        // Create the Proprietario
        ProprietarioDTO proprietarioDTO = proprietarioMapper.toDto(proprietario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProprietarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proprietarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(proprietarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proprietario in the database
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProprietario() throws Exception {
        int databaseSizeBeforeUpdate = proprietarioRepository.findAll().size();
        proprietario.setId(count.incrementAndGet());

        // Create the Proprietario
        ProprietarioDTO proprietarioDTO = proprietarioMapper.toDto(proprietario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProprietarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(proprietarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proprietario in the database
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProprietario() throws Exception {
        int databaseSizeBeforeUpdate = proprietarioRepository.findAll().size();
        proprietario.setId(count.incrementAndGet());

        // Create the Proprietario
        ProprietarioDTO proprietarioDTO = proprietarioMapper.toDto(proprietario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProprietarioMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proprietarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Proprietario in the database
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProprietarioWithPatch() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        int databaseSizeBeforeUpdate = proprietarioRepository.findAll().size();

        // Update the proprietario using partial update
        Proprietario partialUpdatedProprietario = new Proprietario();
        partialUpdatedProprietario.setId(proprietario.getId());

        partialUpdatedProprietario.telefone(UPDATED_TELEFONE).email(UPDATED_EMAIL);

        restProprietarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProprietario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProprietario))
            )
            .andExpect(status().isOk());

        // Validate the Proprietario in the database
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeUpdate);
        Proprietario testProprietario = proprietarioList.get(proprietarioList.size() - 1);
        assertThat(testProprietario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProprietario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testProprietario.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateProprietarioWithPatch() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        int databaseSizeBeforeUpdate = proprietarioRepository.findAll().size();

        // Update the proprietario using partial update
        Proprietario partialUpdatedProprietario = new Proprietario();
        partialUpdatedProprietario.setId(proprietario.getId());

        partialUpdatedProprietario.nome(UPDATED_NOME).telefone(UPDATED_TELEFONE).email(UPDATED_EMAIL);

        restProprietarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProprietario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProprietario))
            )
            .andExpect(status().isOk());

        // Validate the Proprietario in the database
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeUpdate);
        Proprietario testProprietario = proprietarioList.get(proprietarioList.size() - 1);
        assertThat(testProprietario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProprietario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testProprietario.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingProprietario() throws Exception {
        int databaseSizeBeforeUpdate = proprietarioRepository.findAll().size();
        proprietario.setId(count.incrementAndGet());

        // Create the Proprietario
        ProprietarioDTO proprietarioDTO = proprietarioMapper.toDto(proprietario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProprietarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, proprietarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(proprietarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proprietario in the database
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProprietario() throws Exception {
        int databaseSizeBeforeUpdate = proprietarioRepository.findAll().size();
        proprietario.setId(count.incrementAndGet());

        // Create the Proprietario
        ProprietarioDTO proprietarioDTO = proprietarioMapper.toDto(proprietario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProprietarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(proprietarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proprietario in the database
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProprietario() throws Exception {
        int databaseSizeBeforeUpdate = proprietarioRepository.findAll().size();
        proprietario.setId(count.incrementAndGet());

        // Create the Proprietario
        ProprietarioDTO proprietarioDTO = proprietarioMapper.toDto(proprietario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProprietarioMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(proprietarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Proprietario in the database
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProprietario() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        int databaseSizeBeforeDelete = proprietarioRepository.findAll().size();

        // Delete the proprietario
        restProprietarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, proprietario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
