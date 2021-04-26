package edu.unipampa.laboratoriovirologia.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.unipampa.laboratoriovirologia.IntegrationTest;
import edu.unipampa.laboratoriovirologia.domain.Propriedade;
import edu.unipampa.laboratoriovirologia.domain.Proprietario;
import edu.unipampa.laboratoriovirologia.repository.ProprietarioRepository;
import edu.unipampa.laboratoriovirologia.service.criteria.ProprietarioCriteria;
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

    private static final Boolean DEFAULT_ENVIAR_LAUDO = false;
    private static final Boolean UPDATED_ENVIAR_LAUDO = true;

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
        Proprietario proprietario = new Proprietario()
            .nome(DEFAULT_NOME)
            .telefone(DEFAULT_TELEFONE)
            .email(DEFAULT_EMAIL)
            .enviarLaudo(DEFAULT_ENVIAR_LAUDO);
        return proprietario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proprietario createUpdatedEntity(EntityManager em) {
        Proprietario proprietario = new Proprietario()
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .enviarLaudo(UPDATED_ENVIAR_LAUDO);
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
        assertThat(testProprietario.getEnviarLaudo()).isEqualTo(DEFAULT_ENVIAR_LAUDO);
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
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].enviarLaudo").value(hasItem(DEFAULT_ENVIAR_LAUDO.booleanValue())));
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
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.enviarLaudo").value(DEFAULT_ENVIAR_LAUDO.booleanValue()));
    }

    @Test
    @Transactional
    void getProprietariosByIdFiltering() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        Long id = proprietario.getId();

        defaultProprietarioShouldBeFound("id.equals=" + id);
        defaultProprietarioShouldNotBeFound("id.notEquals=" + id);

        defaultProprietarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProprietarioShouldNotBeFound("id.greaterThan=" + id);

        defaultProprietarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProprietarioShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProprietariosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where nome equals to DEFAULT_NOME
        defaultProprietarioShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the proprietarioList where nome equals to UPDATED_NOME
        defaultProprietarioShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProprietariosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where nome not equals to DEFAULT_NOME
        defaultProprietarioShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the proprietarioList where nome not equals to UPDATED_NOME
        defaultProprietarioShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProprietariosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultProprietarioShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the proprietarioList where nome equals to UPDATED_NOME
        defaultProprietarioShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProprietariosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where nome is not null
        defaultProprietarioShouldBeFound("nome.specified=true");

        // Get all the proprietarioList where nome is null
        defaultProprietarioShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllProprietariosByNomeContainsSomething() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where nome contains DEFAULT_NOME
        defaultProprietarioShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the proprietarioList where nome contains UPDATED_NOME
        defaultProprietarioShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProprietariosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where nome does not contain DEFAULT_NOME
        defaultProprietarioShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the proprietarioList where nome does not contain UPDATED_NOME
        defaultProprietarioShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProprietariosByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where telefone equals to DEFAULT_TELEFONE
        defaultProprietarioShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the proprietarioList where telefone equals to UPDATED_TELEFONE
        defaultProprietarioShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllProprietariosByTelefoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where telefone not equals to DEFAULT_TELEFONE
        defaultProprietarioShouldNotBeFound("telefone.notEquals=" + DEFAULT_TELEFONE);

        // Get all the proprietarioList where telefone not equals to UPDATED_TELEFONE
        defaultProprietarioShouldBeFound("telefone.notEquals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllProprietariosByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultProprietarioShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the proprietarioList where telefone equals to UPDATED_TELEFONE
        defaultProprietarioShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllProprietariosByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where telefone is not null
        defaultProprietarioShouldBeFound("telefone.specified=true");

        // Get all the proprietarioList where telefone is null
        defaultProprietarioShouldNotBeFound("telefone.specified=false");
    }

    @Test
    @Transactional
    void getAllProprietariosByTelefoneContainsSomething() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where telefone contains DEFAULT_TELEFONE
        defaultProprietarioShouldBeFound("telefone.contains=" + DEFAULT_TELEFONE);

        // Get all the proprietarioList where telefone contains UPDATED_TELEFONE
        defaultProprietarioShouldNotBeFound("telefone.contains=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllProprietariosByTelefoneNotContainsSomething() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where telefone does not contain DEFAULT_TELEFONE
        defaultProprietarioShouldNotBeFound("telefone.doesNotContain=" + DEFAULT_TELEFONE);

        // Get all the proprietarioList where telefone does not contain UPDATED_TELEFONE
        defaultProprietarioShouldBeFound("telefone.doesNotContain=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllProprietariosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where email equals to DEFAULT_EMAIL
        defaultProprietarioShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the proprietarioList where email equals to UPDATED_EMAIL
        defaultProprietarioShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllProprietariosByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where email not equals to DEFAULT_EMAIL
        defaultProprietarioShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the proprietarioList where email not equals to UPDATED_EMAIL
        defaultProprietarioShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllProprietariosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultProprietarioShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the proprietarioList where email equals to UPDATED_EMAIL
        defaultProprietarioShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllProprietariosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where email is not null
        defaultProprietarioShouldBeFound("email.specified=true");

        // Get all the proprietarioList where email is null
        defaultProprietarioShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllProprietariosByEmailContainsSomething() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where email contains DEFAULT_EMAIL
        defaultProprietarioShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the proprietarioList where email contains UPDATED_EMAIL
        defaultProprietarioShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllProprietariosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where email does not contain DEFAULT_EMAIL
        defaultProprietarioShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the proprietarioList where email does not contain UPDATED_EMAIL
        defaultProprietarioShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllProprietariosByEnviarLaudoIsEqualToSomething() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where enviarLaudo equals to DEFAULT_ENVIAR_LAUDO
        defaultProprietarioShouldBeFound("enviarLaudo.equals=" + DEFAULT_ENVIAR_LAUDO);

        // Get all the proprietarioList where enviarLaudo equals to UPDATED_ENVIAR_LAUDO
        defaultProprietarioShouldNotBeFound("enviarLaudo.equals=" + UPDATED_ENVIAR_LAUDO);
    }

    @Test
    @Transactional
    void getAllProprietariosByEnviarLaudoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where enviarLaudo not equals to DEFAULT_ENVIAR_LAUDO
        defaultProprietarioShouldNotBeFound("enviarLaudo.notEquals=" + DEFAULT_ENVIAR_LAUDO);

        // Get all the proprietarioList where enviarLaudo not equals to UPDATED_ENVIAR_LAUDO
        defaultProprietarioShouldBeFound("enviarLaudo.notEquals=" + UPDATED_ENVIAR_LAUDO);
    }

    @Test
    @Transactional
    void getAllProprietariosByEnviarLaudoIsInShouldWork() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where enviarLaudo in DEFAULT_ENVIAR_LAUDO or UPDATED_ENVIAR_LAUDO
        defaultProprietarioShouldBeFound("enviarLaudo.in=" + DEFAULT_ENVIAR_LAUDO + "," + UPDATED_ENVIAR_LAUDO);

        // Get all the proprietarioList where enviarLaudo equals to UPDATED_ENVIAR_LAUDO
        defaultProprietarioShouldNotBeFound("enviarLaudo.in=" + UPDATED_ENVIAR_LAUDO);
    }

    @Test
    @Transactional
    void getAllProprietariosByEnviarLaudoIsNullOrNotNull() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList where enviarLaudo is not null
        defaultProprietarioShouldBeFound("enviarLaudo.specified=true");

        // Get all the proprietarioList where enviarLaudo is null
        defaultProprietarioShouldNotBeFound("enviarLaudo.specified=false");
    }

    @Test
    @Transactional
    void getAllProprietariosByPropriedadeIsEqualToSomething() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);
        Propriedade propriedade = PropriedadeResourceIT.createEntity(em);
        em.persist(propriedade);
        em.flush();
        proprietario.addPropriedade(propriedade);
        proprietarioRepository.saveAndFlush(proprietario);
        Long propriedadeId = propriedade.getId();

        // Get all the proprietarioList where propriedade equals to propriedadeId
        defaultProprietarioShouldBeFound("propriedadeId.equals=" + propriedadeId);

        // Get all the proprietarioList where propriedade equals to (propriedadeId + 1)
        defaultProprietarioShouldNotBeFound("propriedadeId.equals=" + (propriedadeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProprietarioShouldBeFound(String filter) throws Exception {
        restProprietarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proprietario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].enviarLaudo").value(hasItem(DEFAULT_ENVIAR_LAUDO.booleanValue())));

        // Check, that the count call also returns 1
        restProprietarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProprietarioShouldNotBeFound(String filter) throws Exception {
        restProprietarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProprietarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        updatedProprietario.nome(UPDATED_NOME).telefone(UPDATED_TELEFONE).email(UPDATED_EMAIL).enviarLaudo(UPDATED_ENVIAR_LAUDO);
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
        assertThat(testProprietario.getEnviarLaudo()).isEqualTo(UPDATED_ENVIAR_LAUDO);
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

        partialUpdatedProprietario.telefone(UPDATED_TELEFONE).email(UPDATED_EMAIL).enviarLaudo(UPDATED_ENVIAR_LAUDO);

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
        assertThat(testProprietario.getEnviarLaudo()).isEqualTo(UPDATED_ENVIAR_LAUDO);
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

        partialUpdatedProprietario.nome(UPDATED_NOME).telefone(UPDATED_TELEFONE).email(UPDATED_EMAIL).enviarLaudo(UPDATED_ENVIAR_LAUDO);

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
        assertThat(testProprietario.getEnviarLaudo()).isEqualTo(UPDATED_ENVIAR_LAUDO);
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
