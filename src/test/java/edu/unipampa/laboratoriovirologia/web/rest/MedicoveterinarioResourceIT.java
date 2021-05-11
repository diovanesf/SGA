package edu.unipampa.laboratoriovirologia.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.unipampa.laboratoriovirologia.IntegrationTest;
import edu.unipampa.laboratoriovirologia.domain.Medicoveterinario;
import edu.unipampa.laboratoriovirologia.repository.MedicoveterinarioRepository;
import edu.unipampa.laboratoriovirologia.service.criteria.MedicoveterinarioCriteria;
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
            .crmv(DEFAULT_CRMV)
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
            .crmv(UPDATED_CRMV)
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
        assertThat(testMedicoveterinario.getCrmv()).isEqualTo(DEFAULT_CRMV);
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
            .andExpect(jsonPath("$.[*].crmv").value(hasItem(DEFAULT_CRMV)))
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
            .andExpect(jsonPath("$.crmv").value(DEFAULT_CRMV))
            .andExpect(jsonPath("$.enviarLaudo").value(DEFAULT_ENVIAR_LAUDO.booleanValue()));
    }

    @Test
    @Transactional
    void getMedicoveterinariosByIdFiltering() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        Long id = medicoveterinario.getId();

        defaultMedicoveterinarioShouldBeFound("id.equals=" + id);
        defaultMedicoveterinarioShouldNotBeFound("id.notEquals=" + id);

        defaultMedicoveterinarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMedicoveterinarioShouldNotBeFound("id.greaterThan=" + id);

        defaultMedicoveterinarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMedicoveterinarioShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where nome equals to DEFAULT_NOME
        defaultMedicoveterinarioShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the medicoveterinarioList where nome equals to UPDATED_NOME
        defaultMedicoveterinarioShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where nome not equals to DEFAULT_NOME
        defaultMedicoveterinarioShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the medicoveterinarioList where nome not equals to UPDATED_NOME
        defaultMedicoveterinarioShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultMedicoveterinarioShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the medicoveterinarioList where nome equals to UPDATED_NOME
        defaultMedicoveterinarioShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where nome is not null
        defaultMedicoveterinarioShouldBeFound("nome.specified=true");

        // Get all the medicoveterinarioList where nome is null
        defaultMedicoveterinarioShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByNomeContainsSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where nome contains DEFAULT_NOME
        defaultMedicoveterinarioShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the medicoveterinarioList where nome contains UPDATED_NOME
        defaultMedicoveterinarioShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where nome does not contain DEFAULT_NOME
        defaultMedicoveterinarioShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the medicoveterinarioList where nome does not contain UPDATED_NOME
        defaultMedicoveterinarioShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where telefone equals to DEFAULT_TELEFONE
        defaultMedicoveterinarioShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the medicoveterinarioList where telefone equals to UPDATED_TELEFONE
        defaultMedicoveterinarioShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByTelefoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where telefone not equals to DEFAULT_TELEFONE
        defaultMedicoveterinarioShouldNotBeFound("telefone.notEquals=" + DEFAULT_TELEFONE);

        // Get all the medicoveterinarioList where telefone not equals to UPDATED_TELEFONE
        defaultMedicoveterinarioShouldBeFound("telefone.notEquals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultMedicoveterinarioShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the medicoveterinarioList where telefone equals to UPDATED_TELEFONE
        defaultMedicoveterinarioShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where telefone is not null
        defaultMedicoveterinarioShouldBeFound("telefone.specified=true");

        // Get all the medicoveterinarioList where telefone is null
        defaultMedicoveterinarioShouldNotBeFound("telefone.specified=false");
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByTelefoneContainsSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where telefone contains DEFAULT_TELEFONE
        defaultMedicoveterinarioShouldBeFound("telefone.contains=" + DEFAULT_TELEFONE);

        // Get all the medicoveterinarioList where telefone contains UPDATED_TELEFONE
        defaultMedicoveterinarioShouldNotBeFound("telefone.contains=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByTelefoneNotContainsSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where telefone does not contain DEFAULT_TELEFONE
        defaultMedicoveterinarioShouldNotBeFound("telefone.doesNotContain=" + DEFAULT_TELEFONE);

        // Get all the medicoveterinarioList where telefone does not contain UPDATED_TELEFONE
        defaultMedicoveterinarioShouldBeFound("telefone.doesNotContain=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where email equals to DEFAULT_EMAIL
        defaultMedicoveterinarioShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the medicoveterinarioList where email equals to UPDATED_EMAIL
        defaultMedicoveterinarioShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where email not equals to DEFAULT_EMAIL
        defaultMedicoveterinarioShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the medicoveterinarioList where email not equals to UPDATED_EMAIL
        defaultMedicoveterinarioShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultMedicoveterinarioShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the medicoveterinarioList where email equals to UPDATED_EMAIL
        defaultMedicoveterinarioShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where email is not null
        defaultMedicoveterinarioShouldBeFound("email.specified=true");

        // Get all the medicoveterinarioList where email is null
        defaultMedicoveterinarioShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByEmailContainsSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where email contains DEFAULT_EMAIL
        defaultMedicoveterinarioShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the medicoveterinarioList where email contains UPDATED_EMAIL
        defaultMedicoveterinarioShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where email does not contain DEFAULT_EMAIL
        defaultMedicoveterinarioShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the medicoveterinarioList where email does not contain UPDATED_EMAIL
        defaultMedicoveterinarioShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByCrmvIsEqualToSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where crmv equals to DEFAULT_CRMV
        defaultMedicoveterinarioShouldBeFound("crmv.equals=" + DEFAULT_CRMV);

        // Get all the medicoveterinarioList where crmv equals to UPDATED_CRMV
        defaultMedicoveterinarioShouldNotBeFound("crmv.equals=" + UPDATED_CRMV);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByCrmvIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where crmv not equals to DEFAULT_CRMV
        defaultMedicoveterinarioShouldNotBeFound("crmv.notEquals=" + DEFAULT_CRMV);

        // Get all the medicoveterinarioList where crmv not equals to UPDATED_CRMV
        defaultMedicoveterinarioShouldBeFound("crmv.notEquals=" + UPDATED_CRMV);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByCrmvIsInShouldWork() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where crmv in DEFAULT_CRMV or UPDATED_CRMV
        defaultMedicoveterinarioShouldBeFound("crmv.in=" + DEFAULT_CRMV + "," + UPDATED_CRMV);

        // Get all the medicoveterinarioList where crmv equals to UPDATED_CRMV
        defaultMedicoveterinarioShouldNotBeFound("crmv.in=" + UPDATED_CRMV);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByCrmvIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where crmv is not null
        defaultMedicoveterinarioShouldBeFound("crmv.specified=true");

        // Get all the medicoveterinarioList where crmv is null
        defaultMedicoveterinarioShouldNotBeFound("crmv.specified=false");
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByCrmvContainsSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where crmv contains DEFAULT_CRMV
        defaultMedicoveterinarioShouldBeFound("crmv.contains=" + DEFAULT_CRMV);

        // Get all the medicoveterinarioList where crmv contains UPDATED_CRMV
        defaultMedicoveterinarioShouldNotBeFound("crmv.contains=" + UPDATED_CRMV);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByCrmvNotContainsSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where crmv does not contain DEFAULT_CRMV
        defaultMedicoveterinarioShouldNotBeFound("crmv.doesNotContain=" + DEFAULT_CRMV);

        // Get all the medicoveterinarioList where crmv does not contain UPDATED_CRMV
        defaultMedicoveterinarioShouldBeFound("crmv.doesNotContain=" + UPDATED_CRMV);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByEnviarLaudoIsEqualToSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where enviarLaudo equals to DEFAULT_ENVIAR_LAUDO
        defaultMedicoveterinarioShouldBeFound("enviarLaudo.equals=" + DEFAULT_ENVIAR_LAUDO);

        // Get all the medicoveterinarioList where enviarLaudo equals to UPDATED_ENVIAR_LAUDO
        defaultMedicoveterinarioShouldNotBeFound("enviarLaudo.equals=" + UPDATED_ENVIAR_LAUDO);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByEnviarLaudoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where enviarLaudo not equals to DEFAULT_ENVIAR_LAUDO
        defaultMedicoveterinarioShouldNotBeFound("enviarLaudo.notEquals=" + DEFAULT_ENVIAR_LAUDO);

        // Get all the medicoveterinarioList where enviarLaudo not equals to UPDATED_ENVIAR_LAUDO
        defaultMedicoveterinarioShouldBeFound("enviarLaudo.notEquals=" + UPDATED_ENVIAR_LAUDO);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByEnviarLaudoIsInShouldWork() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where enviarLaudo in DEFAULT_ENVIAR_LAUDO or UPDATED_ENVIAR_LAUDO
        defaultMedicoveterinarioShouldBeFound("enviarLaudo.in=" + DEFAULT_ENVIAR_LAUDO + "," + UPDATED_ENVIAR_LAUDO);

        // Get all the medicoveterinarioList where enviarLaudo equals to UPDATED_ENVIAR_LAUDO
        defaultMedicoveterinarioShouldNotBeFound("enviarLaudo.in=" + UPDATED_ENVIAR_LAUDO);
    }

    @Test
    @Transactional
    void getAllMedicoveterinariosByEnviarLaudoIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicoveterinarioRepository.saveAndFlush(medicoveterinario);

        // Get all the medicoveterinarioList where enviarLaudo is not null
        defaultMedicoveterinarioShouldBeFound("enviarLaudo.specified=true");

        // Get all the medicoveterinarioList where enviarLaudo is null
        defaultMedicoveterinarioShouldNotBeFound("enviarLaudo.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMedicoveterinarioShouldBeFound(String filter) throws Exception {
        restMedicoveterinarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicoveterinario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].crmv").value(hasItem(DEFAULT_CRMV)))
            .andExpect(jsonPath("$.[*].enviarLaudo").value(hasItem(DEFAULT_ENVIAR_LAUDO.booleanValue())));

        // Check, that the count call also returns 1
        restMedicoveterinarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMedicoveterinarioShouldNotBeFound(String filter) throws Exception {
        restMedicoveterinarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMedicoveterinarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
            .crmv(UPDATED_CRMV)
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
        assertThat(testMedicoveterinario.getCrmv()).isEqualTo(UPDATED_CRMV);
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
        assertThat(testMedicoveterinario.getCrmv()).isEqualTo(DEFAULT_CRMV);
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
            .crmv(UPDATED_CRMV)
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
        assertThat(testMedicoveterinario.getCrmv()).isEqualTo(UPDATED_CRMV);
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
