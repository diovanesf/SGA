package edu.unipampa.laboratoriovirologia.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.unipampa.laboratoriovirologia.IntegrationTest;
import edu.unipampa.laboratoriovirologia.domain.Subamostra;
import edu.unipampa.laboratoriovirologia.repository.SubamostraRepository;
import edu.unipampa.laboratoriovirologia.service.dto.SubamostraDTO;
import edu.unipampa.laboratoriovirologia.service.mapper.SubamostraMapper;
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
 * Integration tests for the {@link SubamostraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubamostraResourceIT {

    private static final String DEFAULT_SUB_AMOSTRA = "AAAAAAAAAA";
    private static final String UPDATED_SUB_AMOSTRA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/subamostras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubamostraRepository subamostraRepository;

    @Autowired
    private SubamostraMapper subamostraMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubamostraMockMvc;

    private Subamostra subamostra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subamostra createEntity(EntityManager em) {
        Subamostra subamostra = new Subamostra().subAmostra(DEFAULT_SUB_AMOSTRA);
        return subamostra;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subamostra createUpdatedEntity(EntityManager em) {
        Subamostra subamostra = new Subamostra().subAmostra(UPDATED_SUB_AMOSTRA);
        return subamostra;
    }

    @BeforeEach
    public void initTest() {
        subamostra = createEntity(em);
    }

    @Test
    @Transactional
    void createSubamostra() throws Exception {
        int databaseSizeBeforeCreate = subamostraRepository.findAll().size();
        // Create the Subamostra
        SubamostraDTO subamostraDTO = subamostraMapper.toDto(subamostra);
        restSubamostraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subamostraDTO)))
            .andExpect(status().isCreated());

        // Validate the Subamostra in the database
        List<Subamostra> subamostraList = subamostraRepository.findAll();
        assertThat(subamostraList).hasSize(databaseSizeBeforeCreate + 1);
        Subamostra testSubamostra = subamostraList.get(subamostraList.size() - 1);
        assertThat(testSubamostra.getSubAmostra()).isEqualTo(DEFAULT_SUB_AMOSTRA);
    }

    @Test
    @Transactional
    void createSubamostraWithExistingId() throws Exception {
        // Create the Subamostra with an existing ID
        subamostra.setId(1L);
        SubamostraDTO subamostraDTO = subamostraMapper.toDto(subamostra);

        int databaseSizeBeforeCreate = subamostraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubamostraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subamostraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Subamostra in the database
        List<Subamostra> subamostraList = subamostraRepository.findAll();
        assertThat(subamostraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubamostras() throws Exception {
        // Initialize the database
        subamostraRepository.saveAndFlush(subamostra);

        // Get all the subamostraList
        restSubamostraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subamostra.getId().intValue())))
            .andExpect(jsonPath("$.[*].subAmostra").value(hasItem(DEFAULT_SUB_AMOSTRA)));
    }

    @Test
    @Transactional
    void getSubamostra() throws Exception {
        // Initialize the database
        subamostraRepository.saveAndFlush(subamostra);

        // Get the subamostra
        restSubamostraMockMvc
            .perform(get(ENTITY_API_URL_ID, subamostra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subamostra.getId().intValue()))
            .andExpect(jsonPath("$.subAmostra").value(DEFAULT_SUB_AMOSTRA));
    }

    @Test
    @Transactional
    void getNonExistingSubamostra() throws Exception {
        // Get the subamostra
        restSubamostraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSubamostra() throws Exception {
        // Initialize the database
        subamostraRepository.saveAndFlush(subamostra);

        int databaseSizeBeforeUpdate = subamostraRepository.findAll().size();

        // Update the subamostra
        Subamostra updatedSubamostra = subamostraRepository.findById(subamostra.getId()).get();
        // Disconnect from session so that the updates on updatedSubamostra are not directly saved in db
        em.detach(updatedSubamostra);
        updatedSubamostra.subAmostra(UPDATED_SUB_AMOSTRA);
        SubamostraDTO subamostraDTO = subamostraMapper.toDto(updatedSubamostra);

        restSubamostraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subamostraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subamostraDTO))
            )
            .andExpect(status().isOk());

        // Validate the Subamostra in the database
        List<Subamostra> subamostraList = subamostraRepository.findAll();
        assertThat(subamostraList).hasSize(databaseSizeBeforeUpdate);
        Subamostra testSubamostra = subamostraList.get(subamostraList.size() - 1);
        assertThat(testSubamostra.getSubAmostra()).isEqualTo(UPDATED_SUB_AMOSTRA);
    }

    @Test
    @Transactional
    void putNonExistingSubamostra() throws Exception {
        int databaseSizeBeforeUpdate = subamostraRepository.findAll().size();
        subamostra.setId(count.incrementAndGet());

        // Create the Subamostra
        SubamostraDTO subamostraDTO = subamostraMapper.toDto(subamostra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubamostraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subamostraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subamostraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subamostra in the database
        List<Subamostra> subamostraList = subamostraRepository.findAll();
        assertThat(subamostraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubamostra() throws Exception {
        int databaseSizeBeforeUpdate = subamostraRepository.findAll().size();
        subamostra.setId(count.incrementAndGet());

        // Create the Subamostra
        SubamostraDTO subamostraDTO = subamostraMapper.toDto(subamostra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubamostraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subamostraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subamostra in the database
        List<Subamostra> subamostraList = subamostraRepository.findAll();
        assertThat(subamostraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubamostra() throws Exception {
        int databaseSizeBeforeUpdate = subamostraRepository.findAll().size();
        subamostra.setId(count.incrementAndGet());

        // Create the Subamostra
        SubamostraDTO subamostraDTO = subamostraMapper.toDto(subamostra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubamostraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subamostraDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subamostra in the database
        List<Subamostra> subamostraList = subamostraRepository.findAll();
        assertThat(subamostraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubamostraWithPatch() throws Exception {
        // Initialize the database
        subamostraRepository.saveAndFlush(subamostra);

        int databaseSizeBeforeUpdate = subamostraRepository.findAll().size();

        // Update the subamostra using partial update
        Subamostra partialUpdatedSubamostra = new Subamostra();
        partialUpdatedSubamostra.setId(subamostra.getId());

        restSubamostraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubamostra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubamostra))
            )
            .andExpect(status().isOk());

        // Validate the Subamostra in the database
        List<Subamostra> subamostraList = subamostraRepository.findAll();
        assertThat(subamostraList).hasSize(databaseSizeBeforeUpdate);
        Subamostra testSubamostra = subamostraList.get(subamostraList.size() - 1);
        assertThat(testSubamostra.getSubAmostra()).isEqualTo(DEFAULT_SUB_AMOSTRA);
    }

    @Test
    @Transactional
    void fullUpdateSubamostraWithPatch() throws Exception {
        // Initialize the database
        subamostraRepository.saveAndFlush(subamostra);

        int databaseSizeBeforeUpdate = subamostraRepository.findAll().size();

        // Update the subamostra using partial update
        Subamostra partialUpdatedSubamostra = new Subamostra();
        partialUpdatedSubamostra.setId(subamostra.getId());

        partialUpdatedSubamostra.subAmostra(UPDATED_SUB_AMOSTRA);

        restSubamostraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubamostra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubamostra))
            )
            .andExpect(status().isOk());

        // Validate the Subamostra in the database
        List<Subamostra> subamostraList = subamostraRepository.findAll();
        assertThat(subamostraList).hasSize(databaseSizeBeforeUpdate);
        Subamostra testSubamostra = subamostraList.get(subamostraList.size() - 1);
        assertThat(testSubamostra.getSubAmostra()).isEqualTo(UPDATED_SUB_AMOSTRA);
    }

    @Test
    @Transactional
    void patchNonExistingSubamostra() throws Exception {
        int databaseSizeBeforeUpdate = subamostraRepository.findAll().size();
        subamostra.setId(count.incrementAndGet());

        // Create the Subamostra
        SubamostraDTO subamostraDTO = subamostraMapper.toDto(subamostra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubamostraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subamostraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subamostraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subamostra in the database
        List<Subamostra> subamostraList = subamostraRepository.findAll();
        assertThat(subamostraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubamostra() throws Exception {
        int databaseSizeBeforeUpdate = subamostraRepository.findAll().size();
        subamostra.setId(count.incrementAndGet());

        // Create the Subamostra
        SubamostraDTO subamostraDTO = subamostraMapper.toDto(subamostra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubamostraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subamostraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subamostra in the database
        List<Subamostra> subamostraList = subamostraRepository.findAll();
        assertThat(subamostraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubamostra() throws Exception {
        int databaseSizeBeforeUpdate = subamostraRepository.findAll().size();
        subamostra.setId(count.incrementAndGet());

        // Create the Subamostra
        SubamostraDTO subamostraDTO = subamostraMapper.toDto(subamostra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubamostraMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(subamostraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subamostra in the database
        List<Subamostra> subamostraList = subamostraRepository.findAll();
        assertThat(subamostraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubamostra() throws Exception {
        // Initialize the database
        subamostraRepository.saveAndFlush(subamostra);

        int databaseSizeBeforeDelete = subamostraRepository.findAll().size();

        // Delete the subamostra
        restSubamostraMockMvc
            .perform(delete(ENTITY_API_URL_ID, subamostra.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subamostra> subamostraList = subamostraRepository.findAll();
        assertThat(subamostraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
