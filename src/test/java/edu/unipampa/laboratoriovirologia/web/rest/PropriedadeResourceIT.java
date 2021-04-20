package edu.unipampa.laboratoriovirologia.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.unipampa.laboratoriovirologia.IntegrationTest;
import edu.unipampa.laboratoriovirologia.domain.Propriedade;
import edu.unipampa.laboratoriovirologia.repository.PropriedadeRepository;
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

    private static final String DEFAULT_TIPO_CRIACAO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CRIACAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_ANIMAIS = 1;
    private static final Integer UPDATED_NUMERO_ANIMAIS = 2;

    private static final String DEFAULT_ACOMETIDOS = "AAAAAAAAAA";
    private static final String UPDATED_ACOMETIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final String DEFAULT_PRICIPAL_SUSPEITA = "AAAAAAAAAA";
    private static final String UPDATED_PRICIPAL_SUSPEITA = "BBBBBBBBBB";

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
            .tipoCriação(DEFAULT_TIPO_CRIACAO)
            .numeroAnimais(DEFAULT_NUMERO_ANIMAIS)
            .acometidos(DEFAULT_ACOMETIDOS)
            .observacoes(DEFAULT_OBSERVACOES)
            .pricipalSuspeita(DEFAULT_PRICIPAL_SUSPEITA);
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
            .tipoCriação(UPDATED_TIPO_CRIACAO)
            .numeroAnimais(UPDATED_NUMERO_ANIMAIS)
            .acometidos(UPDATED_ACOMETIDOS)
            .observacoes(UPDATED_OBSERVACOES)
            .pricipalSuspeita(UPDATED_PRICIPAL_SUSPEITA);
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
        assertThat(testPropriedade.getTipoCriação()).isEqualTo(DEFAULT_TIPO_CRIACAO);
        assertThat(testPropriedade.getNumeroAnimais()).isEqualTo(DEFAULT_NUMERO_ANIMAIS);
        assertThat(testPropriedade.getAcometidos()).isEqualTo(DEFAULT_ACOMETIDOS);
        assertThat(testPropriedade.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testPropriedade.getPricipalSuspeita()).isEqualTo(DEFAULT_PRICIPAL_SUSPEITA);
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
            .andExpect(jsonPath("$.[*].tipoCriação").value(hasItem(DEFAULT_TIPO_CRIACAO)))
            .andExpect(jsonPath("$.[*].numeroAnimais").value(hasItem(DEFAULT_NUMERO_ANIMAIS)))
            .andExpect(jsonPath("$.[*].acometidos").value(hasItem(DEFAULT_ACOMETIDOS)))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].pricipalSuspeita").value(hasItem(DEFAULT_PRICIPAL_SUSPEITA)));
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
            .andExpect(jsonPath("$.tipoCriação").value(DEFAULT_TIPO_CRIACAO))
            .andExpect(jsonPath("$.numeroAnimais").value(DEFAULT_NUMERO_ANIMAIS))
            .andExpect(jsonPath("$.acometidos").value(DEFAULT_ACOMETIDOS))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()))
            .andExpect(jsonPath("$.pricipalSuspeita").value(DEFAULT_PRICIPAL_SUSPEITA));
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
            .tipoCriação(UPDATED_TIPO_CRIACAO)
            .numeroAnimais(UPDATED_NUMERO_ANIMAIS)
            .acometidos(UPDATED_ACOMETIDOS)
            .observacoes(UPDATED_OBSERVACOES)
            .pricipalSuspeita(UPDATED_PRICIPAL_SUSPEITA);
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
        assertThat(testPropriedade.getTipoCriação()).isEqualTo(UPDATED_TIPO_CRIACAO);
        assertThat(testPropriedade.getNumeroAnimais()).isEqualTo(UPDATED_NUMERO_ANIMAIS);
        assertThat(testPropriedade.getAcometidos()).isEqualTo(UPDATED_ACOMETIDOS);
        assertThat(testPropriedade.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testPropriedade.getPricipalSuspeita()).isEqualTo(UPDATED_PRICIPAL_SUSPEITA);
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
            .tipoCriação(UPDATED_TIPO_CRIACAO)
            .numeroAnimais(UPDATED_NUMERO_ANIMAIS)
            .acometidos(UPDATED_ACOMETIDOS)
            .observacoes(UPDATED_OBSERVACOES)
            .pricipalSuspeita(UPDATED_PRICIPAL_SUSPEITA);

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
        assertThat(testPropriedade.getTipoCriação()).isEqualTo(UPDATED_TIPO_CRIACAO);
        assertThat(testPropriedade.getNumeroAnimais()).isEqualTo(UPDATED_NUMERO_ANIMAIS);
        assertThat(testPropriedade.getAcometidos()).isEqualTo(UPDATED_ACOMETIDOS);
        assertThat(testPropriedade.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testPropriedade.getPricipalSuspeita()).isEqualTo(UPDATED_PRICIPAL_SUSPEITA);
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
            .tipoCriação(UPDATED_TIPO_CRIACAO)
            .numeroAnimais(UPDATED_NUMERO_ANIMAIS)
            .acometidos(UPDATED_ACOMETIDOS)
            .observacoes(UPDATED_OBSERVACOES)
            .pricipalSuspeita(UPDATED_PRICIPAL_SUSPEITA);

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
        assertThat(testPropriedade.getTipoCriação()).isEqualTo(UPDATED_TIPO_CRIACAO);
        assertThat(testPropriedade.getNumeroAnimais()).isEqualTo(UPDATED_NUMERO_ANIMAIS);
        assertThat(testPropriedade.getAcometidos()).isEqualTo(UPDATED_ACOMETIDOS);
        assertThat(testPropriedade.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testPropriedade.getPricipalSuspeita()).isEqualTo(UPDATED_PRICIPAL_SUSPEITA);
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
