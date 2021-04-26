package edu.unipampa.laboratoriovirologia.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.unipampa.laboratoriovirologia.IntegrationTest;
import edu.unipampa.laboratoriovirologia.domain.Amostra;
import edu.unipampa.laboratoriovirologia.domain.Midia;
import edu.unipampa.laboratoriovirologia.repository.MidiaRepository;
import edu.unipampa.laboratoriovirologia.service.criteria.MidiaCriteria;
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
    void getMidiasByIdFiltering() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        Long id = midia.getId();

        defaultMidiaShouldBeFound("id.equals=" + id);
        defaultMidiaShouldNotBeFound("id.notEquals=" + id);

        defaultMidiaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMidiaShouldNotBeFound("id.greaterThan=" + id);

        defaultMidiaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMidiaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMidiasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        // Get all the midiaList where nome equals to DEFAULT_NOME
        defaultMidiaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the midiaList where nome equals to UPDATED_NOME
        defaultMidiaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllMidiasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        // Get all the midiaList where nome not equals to DEFAULT_NOME
        defaultMidiaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the midiaList where nome not equals to UPDATED_NOME
        defaultMidiaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllMidiasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        // Get all the midiaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultMidiaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the midiaList where nome equals to UPDATED_NOME
        defaultMidiaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllMidiasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        // Get all the midiaList where nome is not null
        defaultMidiaShouldBeFound("nome.specified=true");

        // Get all the midiaList where nome is null
        defaultMidiaShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllMidiasByNomeContainsSomething() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        // Get all the midiaList where nome contains DEFAULT_NOME
        defaultMidiaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the midiaList where nome contains UPDATED_NOME
        defaultMidiaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllMidiasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        // Get all the midiaList where nome does not contain DEFAULT_NOME
        defaultMidiaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the midiaList where nome does not contain UPDATED_NOME
        defaultMidiaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllMidiasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        // Get all the midiaList where descricao equals to DEFAULT_DESCRICAO
        defaultMidiaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the midiaList where descricao equals to UPDATED_DESCRICAO
        defaultMidiaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllMidiasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        // Get all the midiaList where descricao not equals to DEFAULT_DESCRICAO
        defaultMidiaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the midiaList where descricao not equals to UPDATED_DESCRICAO
        defaultMidiaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllMidiasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        // Get all the midiaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultMidiaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the midiaList where descricao equals to UPDATED_DESCRICAO
        defaultMidiaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllMidiasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        // Get all the midiaList where descricao is not null
        defaultMidiaShouldBeFound("descricao.specified=true");

        // Get all the midiaList where descricao is null
        defaultMidiaShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllMidiasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        // Get all the midiaList where descricao contains DEFAULT_DESCRICAO
        defaultMidiaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the midiaList where descricao contains UPDATED_DESCRICAO
        defaultMidiaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllMidiasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);

        // Get all the midiaList where descricao does not contain DEFAULT_DESCRICAO
        defaultMidiaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the midiaList where descricao does not contain UPDATED_DESCRICAO
        defaultMidiaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllMidiasByAmostraIsEqualToSomething() throws Exception {
        // Initialize the database
        midiaRepository.saveAndFlush(midia);
        Amostra amostra = AmostraResourceIT.createEntity(em);
        em.persist(amostra);
        em.flush();
        midia.setAmostra(amostra);
        midiaRepository.saveAndFlush(midia);
        Long amostraId = amostra.getId();

        // Get all the midiaList where amostra equals to amostraId
        defaultMidiaShouldBeFound("amostraId.equals=" + amostraId);

        // Get all the midiaList where amostra equals to (amostraId + 1)
        defaultMidiaShouldNotBeFound("amostraId.equals=" + (amostraId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMidiaShouldBeFound(String filter) throws Exception {
        restMidiaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(midia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));

        // Check, that the count call also returns 1
        restMidiaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMidiaShouldNotBeFound(String filter) throws Exception {
        restMidiaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMidiaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
