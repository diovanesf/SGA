package edu.unipampa.laboratoriovirologia.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.unipampa.laboratoriovirologia.IntegrationTest;
import edu.unipampa.laboratoriovirologia.domain.Midia;
import edu.unipampa.laboratoriovirologia.repository.MidiaRepository;
import edu.unipampa.laboratoriovirologia.service.dto.MidiaDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.MidiaMapper;
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
 * Integration tests for the {@link MidiaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MidiaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/midias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MidiaRepository midiaRepository;

    @Autowired
    private MidiaMapper midiaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMidiaMockMvc;

    private Midia midia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Midia createEntity(EntityManager em) {
        Midia midia = new Midia()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return midia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Midia createUpdatedEntity(EntityManager em) {
        Midia midia = new Midia()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        return midia;
    }

    @BeforeEach
    public void initTest() {
        midia = createEntity(em);
    }

    @Test
    @Transactional
    void createMidia() throws Exception {
        int databaseSizeBeforeCreate = midiaRepository.findAll().size();
        // Create the Midia
        MidiaDTO midiaDTO = midiaMapper.toDto(midia);
        restMidiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(midiaDTO)))
            .andExpect(status().isCreated());

        // Validate the Midia in the database
        List<Midia> midiaList = midiaRepository.findAll();
        assertThat(midiaList).hasSize(databaseSizeBeforeCreate + 1);
        Midia testMidia = midiaList.get(midiaList.size() - 1);
        assertThat(testMidia.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMidia.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testMidia.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testMidia.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createMidiaWithExistingId() throws Exception {
        // Create the Midia with an existing ID
        midia.setId(1L);
        MidiaDTO midiaDTO = midiaMapper.toDto(midia);

        int databaseSizeBeforeCreate = midiaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMidiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(midiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Midia in the database
        List<Midia> midiaList = midiaRepository.findAll();
        assertThat(midiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMidias() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        // Get all the midiaList
        restMidiaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(midia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    void getMidia() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        // Get the midia
        restMidiaMockMvc
            .perform(get(ENTITY_API_URL_ID, midia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(midia.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    void getNonExistingMidia() throws Exception {
        // Get the midia
        restMidiaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMidia() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        int databaseSizeBeforeUpdate = midiaRepository.findAll().size();

        // Update the midia
        Midia updatedMidia = midiaRepository.findById(midia.getId()).get();
        // Disconnect from session so that the updates on updatedMidia are not directly saved in db
        em.detach(updatedMidia);
        updatedMidia.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).file(UPDATED_FILE).fileContentType(UPDATED_FILE_CONTENT_TYPE);
        MidiaDTO midiaDTO = midiaMapper.toDto(updatedMidia);

        restMidiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, midiaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(midiaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Midia in the database
        List<Midia> midiaList = midiaRepository.findAll();
        assertThat(midiaList).hasSize(databaseSizeBeforeUpdate);
        Midia testMidia = midiaList.get(midiaList.size() - 1);
        assertThat(testMidia.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMidia.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testMidia.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testMidia.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingMidia() throws Exception {
        int databaseSizeBeforeUpdate = midiaRepository.findAll().size();
        midia.setId(count.incrementAndGet());

        // Create the Midia
        MidiaDTO midiaDTO = midiaMapper.toDto(midia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMidiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, midiaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(midiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Midia in the database
        List<Midia> midiaList = midiaRepository.findAll();
        assertThat(midiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMidia() throws Exception {
        int databaseSizeBeforeUpdate = midiaRepository.findAll().size();
        midia.setId(count.incrementAndGet());

        // Create the Midia
        MidiaDTO midiaDTO = midiaMapper.toDto(midia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMidiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(midiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Midia in the database
        List<Midia> midiaList = midiaRepository.findAll();
        assertThat(midiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMidia() throws Exception {
        int databaseSizeBeforeUpdate = midiaRepository.findAll().size();
        midia.setId(count.incrementAndGet());

        // Create the Midia
        MidiaDTO midiaDTO = midiaMapper.toDto(midia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMidiaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(midiaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Midia in the database
        List<Midia> midiaList = midiaRepository.findAll();
        assertThat(midiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMidiaWithPatch() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        int databaseSizeBeforeUpdate = midiaRepository.findAll().size();

        // Update the midia using partial update
        Midia partialUpdatedMidia = new Midia();
        partialUpdatedMidia.setId(midia.getId());

        restMidiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMidia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMidia))
            )
            .andExpect(status().isOk());

        // Validate the Midia in the database
        List<Midia> midiaList = midiaRepository.findAll();
        assertThat(midiaList).hasSize(databaseSizeBeforeUpdate);
        Midia testMidia = midiaList.get(midiaList.size() - 1);
        assertThat(testMidia.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMidia.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testMidia.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testMidia.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateMidiaWithPatch() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        int databaseSizeBeforeUpdate = midiaRepository.findAll().size();

        // Update the midia using partial update
        Midia partialUpdatedMidia = new Midia();
        partialUpdatedMidia.setId(midia.getId());

        partialUpdatedMidia.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).file(UPDATED_FILE).fileContentType(UPDATED_FILE_CONTENT_TYPE);

        restMidiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMidia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMidia))
            )
            .andExpect(status().isOk());

        // Validate the Midia in the database
        List<Midia> midiaList = midiaRepository.findAll();
        assertThat(midiaList).hasSize(databaseSizeBeforeUpdate);
        Midia testMidia = midiaList.get(midiaList.size() - 1);
        assertThat(testMidia.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMidia.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testMidia.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testMidia.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingMidia() throws Exception {
        int databaseSizeBeforeUpdate = midiaRepository.findAll().size();
        midia.setId(count.incrementAndGet());

        // Create the Midia
        MidiaDTO midiaDTO = midiaMapper.toDto(midia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMidiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, midiaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(midiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Midia in the database
        List<Midia> midiaList = midiaRepository.findAll();
        assertThat(midiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMidia() throws Exception {
        int databaseSizeBeforeUpdate = midiaRepository.findAll().size();
        midia.setId(count.incrementAndGet());

        // Create the Midia
        MidiaDTO midiaDTO = midiaMapper.toDto(midia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMidiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(midiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Midia in the database
        List<Midia> midiaList = midiaRepository.findAll();
        assertThat(midiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMidia() throws Exception {
        int databaseSizeBeforeUpdate = midiaRepository.findAll().size();
        midia.setId(count.incrementAndGet());

        // Create the Midia
        MidiaDTO midiaDTO = midiaMapper.toDto(midia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMidiaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(midiaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Midia in the database
        List<Midia> midiaList = midiaRepository.findAll();
        assertThat(midiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMidia() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        int databaseSizeBeforeDelete = midiaRepository.findAll().size();

        // Delete the midia
        restMidiaMockMvc
            .perform(delete(ENTITY_API_URL_ID, midia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Midia> midiaList = midiaRepository.findAll();
        assertThat(midiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
