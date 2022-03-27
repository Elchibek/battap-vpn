package com.battap.vpn.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.battap.vpn.IntegrationTest;
import com.battap.vpn.domain.VirServer;
import com.battap.vpn.repository.VirServerRepository;
import com.battap.vpn.service.dto.VirServerDTO;
import com.battap.vpn.service.mapper.VirServerMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link VirServerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VirServerResourceIT {

    private static final String DEFAULT_VPS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VPS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REMOTE_HOST = "AAAAAAAAAA";
    private static final String UPDATED_REMOTE_HOST = "BBBBBBBBBB";

    private static final String DEFAULT_REMOTE_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REMOTE_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REMOTE_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_REMOTE_PASSWORD = "BBBBBBBBBB";

    private static final Integer DEFAULT_REMOTE_PORT = 1;
    private static final Integer UPDATED_REMOTE_PORT = 2;

    private static final Integer DEFAULT_SESSION_TIME_OUT = 1;
    private static final Integer UPDATED_SESSION_TIME_OUT = 2;

    private static final Integer DEFAULT_CHANEL_TIME_OUT = 1;
    private static final Integer UPDATED_CHANEL_TIME_OUT = 2;

    private static final String ENTITY_API_URL = "/api/vir-servers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private VirServerRepository virServerRepository;

    @Autowired
    private VirServerMapper virServerMapper;

    @Autowired
    private MockMvc restVirServerMockMvc;

    private VirServer virServer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VirServer createEntity() {
        VirServer virServer = new VirServer()
            .vpsName(DEFAULT_VPS_NAME)
            .remoteHost(DEFAULT_REMOTE_HOST)
            .remoteUserName(DEFAULT_REMOTE_USER_NAME)
            .remotePassword(DEFAULT_REMOTE_PASSWORD)
            .remotePort(DEFAULT_REMOTE_PORT)
            .sessionTimeOut(DEFAULT_SESSION_TIME_OUT)
            .chanelTimeOut(DEFAULT_CHANEL_TIME_OUT);
        return virServer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VirServer createUpdatedEntity() {
        VirServer virServer = new VirServer()
            .vpsName(UPDATED_VPS_NAME)
            .remoteHost(UPDATED_REMOTE_HOST)
            .remoteUserName(UPDATED_REMOTE_USER_NAME)
            .remotePassword(UPDATED_REMOTE_PASSWORD)
            .remotePort(UPDATED_REMOTE_PORT)
            .sessionTimeOut(UPDATED_SESSION_TIME_OUT)
            .chanelTimeOut(UPDATED_CHANEL_TIME_OUT);
        return virServer;
    }

    @BeforeEach
    public void initTest() {
        virServerRepository.deleteAll();
        virServer = createEntity();
    }

    @Test
    void createVirServer() throws Exception {
        int databaseSizeBeforeCreate = virServerRepository.findAll().size();
        // Create the VirServer
        VirServerDTO virServerDTO = virServerMapper.toDto(virServer);
        restVirServerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(virServerDTO)))
            .andExpect(status().isCreated());

        // Validate the VirServer in the database
        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeCreate + 1);
        VirServer testVirServer = virServerList.get(virServerList.size() - 1);
        assertThat(testVirServer.getVpsName()).isEqualTo(DEFAULT_VPS_NAME);
        assertThat(testVirServer.getRemoteHost()).isEqualTo(DEFAULT_REMOTE_HOST);
        assertThat(testVirServer.getRemoteUserName()).isEqualTo(DEFAULT_REMOTE_USER_NAME);
        assertThat(testVirServer.getRemotePassword()).isEqualTo(DEFAULT_REMOTE_PASSWORD);
        assertThat(testVirServer.getRemotePort()).isEqualTo(DEFAULT_REMOTE_PORT);
        assertThat(testVirServer.getSessionTimeOut()).isEqualTo(DEFAULT_SESSION_TIME_OUT);
        assertThat(testVirServer.getChanelTimeOut()).isEqualTo(DEFAULT_CHANEL_TIME_OUT);
    }

    @Test
    void createVirServerWithExistingId() throws Exception {
        // Create the VirServer with an existing ID
        virServer.setId("existing_id");
        VirServerDTO virServerDTO = virServerMapper.toDto(virServer);

        int databaseSizeBeforeCreate = virServerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVirServerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(virServerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VirServer in the database
        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkVpsNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = virServerRepository.findAll().size();
        // set the field null
        virServer.setVpsName(null);

        // Create the VirServer, which fails.
        VirServerDTO virServerDTO = virServerMapper.toDto(virServer);

        restVirServerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(virServerDTO)))
            .andExpect(status().isBadRequest());

        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkRemoteHostIsRequired() throws Exception {
        int databaseSizeBeforeTest = virServerRepository.findAll().size();
        // set the field null
        virServer.setRemoteHost(null);

        // Create the VirServer, which fails.
        VirServerDTO virServerDTO = virServerMapper.toDto(virServer);

        restVirServerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(virServerDTO)))
            .andExpect(status().isBadRequest());

        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkRemoteUserNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = virServerRepository.findAll().size();
        // set the field null
        virServer.setRemoteUserName(null);

        // Create the VirServer, which fails.
        VirServerDTO virServerDTO = virServerMapper.toDto(virServer);

        restVirServerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(virServerDTO)))
            .andExpect(status().isBadRequest());

        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkRemotePasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = virServerRepository.findAll().size();
        // set the field null
        virServer.setRemotePassword(null);

        // Create the VirServer, which fails.
        VirServerDTO virServerDTO = virServerMapper.toDto(virServer);

        restVirServerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(virServerDTO)))
            .andExpect(status().isBadRequest());

        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkRemotePortIsRequired() throws Exception {
        int databaseSizeBeforeTest = virServerRepository.findAll().size();
        // set the field null
        virServer.setRemotePort(null);

        // Create the VirServer, which fails.
        VirServerDTO virServerDTO = virServerMapper.toDto(virServer);

        restVirServerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(virServerDTO)))
            .andExpect(status().isBadRequest());

        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllVirServers() throws Exception {
        // Initialize the database
        virServerRepository.save(virServer);

        // Get all the virServerList
        restVirServerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(virServer.getId())))
            .andExpect(jsonPath("$.[*].vpsName").value(hasItem(DEFAULT_VPS_NAME)))
            .andExpect(jsonPath("$.[*].remoteHost").value(hasItem(DEFAULT_REMOTE_HOST)))
            .andExpect(jsonPath("$.[*].remoteUserName").value(hasItem(DEFAULT_REMOTE_USER_NAME)))
            .andExpect(jsonPath("$.[*].remotePassword").value(hasItem(DEFAULT_REMOTE_PASSWORD)))
            .andExpect(jsonPath("$.[*].remotePort").value(hasItem(DEFAULT_REMOTE_PORT)))
            .andExpect(jsonPath("$.[*].sessionTimeOut").value(hasItem(DEFAULT_SESSION_TIME_OUT)))
            .andExpect(jsonPath("$.[*].chanelTimeOut").value(hasItem(DEFAULT_CHANEL_TIME_OUT)));
    }

    @Test
    void getVirServer() throws Exception {
        // Initialize the database
        virServerRepository.save(virServer);

        // Get the virServer
        restVirServerMockMvc
            .perform(get(ENTITY_API_URL_ID, virServer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(virServer.getId()))
            .andExpect(jsonPath("$.vpsName").value(DEFAULT_VPS_NAME))
            .andExpect(jsonPath("$.remoteHost").value(DEFAULT_REMOTE_HOST))
            .andExpect(jsonPath("$.remoteUserName").value(DEFAULT_REMOTE_USER_NAME))
            .andExpect(jsonPath("$.remotePassword").value(DEFAULT_REMOTE_PASSWORD))
            .andExpect(jsonPath("$.remotePort").value(DEFAULT_REMOTE_PORT))
            .andExpect(jsonPath("$.sessionTimeOut").value(DEFAULT_SESSION_TIME_OUT))
            .andExpect(jsonPath("$.chanelTimeOut").value(DEFAULT_CHANEL_TIME_OUT));
    }

    @Test
    void getNonExistingVirServer() throws Exception {
        // Get the virServer
        restVirServerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewVirServer() throws Exception {
        // Initialize the database
        virServerRepository.save(virServer);

        int databaseSizeBeforeUpdate = virServerRepository.findAll().size();

        // Update the virServer
        VirServer updatedVirServer = virServerRepository.findById(virServer.getId()).get();
        updatedVirServer
            .vpsName(UPDATED_VPS_NAME)
            .remoteHost(UPDATED_REMOTE_HOST)
            .remoteUserName(UPDATED_REMOTE_USER_NAME)
            .remotePassword(UPDATED_REMOTE_PASSWORD)
            .remotePort(UPDATED_REMOTE_PORT)
            .sessionTimeOut(UPDATED_SESSION_TIME_OUT)
            .chanelTimeOut(UPDATED_CHANEL_TIME_OUT);
        VirServerDTO virServerDTO = virServerMapper.toDto(updatedVirServer);

        restVirServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, virServerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(virServerDTO))
            )
            .andExpect(status().isOk());

        // Validate the VirServer in the database
        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeUpdate);
        VirServer testVirServer = virServerList.get(virServerList.size() - 1);
        assertThat(testVirServer.getVpsName()).isEqualTo(UPDATED_VPS_NAME);
        assertThat(testVirServer.getRemoteHost()).isEqualTo(UPDATED_REMOTE_HOST);
        assertThat(testVirServer.getRemoteUserName()).isEqualTo(UPDATED_REMOTE_USER_NAME);
        assertThat(testVirServer.getRemotePassword()).isEqualTo(UPDATED_REMOTE_PASSWORD);
        assertThat(testVirServer.getRemotePort()).isEqualTo(UPDATED_REMOTE_PORT);
        assertThat(testVirServer.getSessionTimeOut()).isEqualTo(UPDATED_SESSION_TIME_OUT);
        assertThat(testVirServer.getChanelTimeOut()).isEqualTo(UPDATED_CHANEL_TIME_OUT);
    }

    @Test
    void putNonExistingVirServer() throws Exception {
        int databaseSizeBeforeUpdate = virServerRepository.findAll().size();
        virServer.setId(UUID.randomUUID().toString());

        // Create the VirServer
        VirServerDTO virServerDTO = virServerMapper.toDto(virServer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVirServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, virServerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(virServerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VirServer in the database
        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchVirServer() throws Exception {
        int databaseSizeBeforeUpdate = virServerRepository.findAll().size();
        virServer.setId(UUID.randomUUID().toString());

        // Create the VirServer
        VirServerDTO virServerDTO = virServerMapper.toDto(virServer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVirServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(virServerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VirServer in the database
        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamVirServer() throws Exception {
        int databaseSizeBeforeUpdate = virServerRepository.findAll().size();
        virServer.setId(UUID.randomUUID().toString());

        // Create the VirServer
        VirServerDTO virServerDTO = virServerMapper.toDto(virServer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVirServerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(virServerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VirServer in the database
        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateVirServerWithPatch() throws Exception {
        // Initialize the database
        virServerRepository.save(virServer);

        int databaseSizeBeforeUpdate = virServerRepository.findAll().size();

        // Update the virServer using partial update
        VirServer partialUpdatedVirServer = new VirServer();
        partialUpdatedVirServer.setId(virServer.getId());

        partialUpdatedVirServer.vpsName(UPDATED_VPS_NAME).remoteHost(UPDATED_REMOTE_HOST).chanelTimeOut(UPDATED_CHANEL_TIME_OUT);

        restVirServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVirServer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVirServer))
            )
            .andExpect(status().isOk());

        // Validate the VirServer in the database
        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeUpdate);
        VirServer testVirServer = virServerList.get(virServerList.size() - 1);
        assertThat(testVirServer.getVpsName()).isEqualTo(UPDATED_VPS_NAME);
        assertThat(testVirServer.getRemoteHost()).isEqualTo(UPDATED_REMOTE_HOST);
        assertThat(testVirServer.getRemoteUserName()).isEqualTo(DEFAULT_REMOTE_USER_NAME);
        assertThat(testVirServer.getRemotePassword()).isEqualTo(DEFAULT_REMOTE_PASSWORD);
        assertThat(testVirServer.getRemotePort()).isEqualTo(DEFAULT_REMOTE_PORT);
        assertThat(testVirServer.getSessionTimeOut()).isEqualTo(DEFAULT_SESSION_TIME_OUT);
        assertThat(testVirServer.getChanelTimeOut()).isEqualTo(UPDATED_CHANEL_TIME_OUT);
    }

    @Test
    void fullUpdateVirServerWithPatch() throws Exception {
        // Initialize the database
        virServerRepository.save(virServer);

        int databaseSizeBeforeUpdate = virServerRepository.findAll().size();

        // Update the virServer using partial update
        VirServer partialUpdatedVirServer = new VirServer();
        partialUpdatedVirServer.setId(virServer.getId());

        partialUpdatedVirServer
            .vpsName(UPDATED_VPS_NAME)
            .remoteHost(UPDATED_REMOTE_HOST)
            .remoteUserName(UPDATED_REMOTE_USER_NAME)
            .remotePassword(UPDATED_REMOTE_PASSWORD)
            .remotePort(UPDATED_REMOTE_PORT)
            .sessionTimeOut(UPDATED_SESSION_TIME_OUT)
            .chanelTimeOut(UPDATED_CHANEL_TIME_OUT);

        restVirServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVirServer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVirServer))
            )
            .andExpect(status().isOk());

        // Validate the VirServer in the database
        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeUpdate);
        VirServer testVirServer = virServerList.get(virServerList.size() - 1);
        assertThat(testVirServer.getVpsName()).isEqualTo(UPDATED_VPS_NAME);
        assertThat(testVirServer.getRemoteHost()).isEqualTo(UPDATED_REMOTE_HOST);
        assertThat(testVirServer.getRemoteUserName()).isEqualTo(UPDATED_REMOTE_USER_NAME);
        assertThat(testVirServer.getRemotePassword()).isEqualTo(UPDATED_REMOTE_PASSWORD);
        assertThat(testVirServer.getRemotePort()).isEqualTo(UPDATED_REMOTE_PORT);
        assertThat(testVirServer.getSessionTimeOut()).isEqualTo(UPDATED_SESSION_TIME_OUT);
        assertThat(testVirServer.getChanelTimeOut()).isEqualTo(UPDATED_CHANEL_TIME_OUT);
    }

    @Test
    void patchNonExistingVirServer() throws Exception {
        int databaseSizeBeforeUpdate = virServerRepository.findAll().size();
        virServer.setId(UUID.randomUUID().toString());

        // Create the VirServer
        VirServerDTO virServerDTO = virServerMapper.toDto(virServer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVirServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, virServerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(virServerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VirServer in the database
        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchVirServer() throws Exception {
        int databaseSizeBeforeUpdate = virServerRepository.findAll().size();
        virServer.setId(UUID.randomUUID().toString());

        // Create the VirServer
        VirServerDTO virServerDTO = virServerMapper.toDto(virServer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVirServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(virServerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VirServer in the database
        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamVirServer() throws Exception {
        int databaseSizeBeforeUpdate = virServerRepository.findAll().size();
        virServer.setId(UUID.randomUUID().toString());

        // Create the VirServer
        VirServerDTO virServerDTO = virServerMapper.toDto(virServer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVirServerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(virServerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VirServer in the database
        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteVirServer() throws Exception {
        // Initialize the database
        virServerRepository.save(virServer);

        int databaseSizeBeforeDelete = virServerRepository.findAll().size();

        // Delete the virServer
        restVirServerMockMvc
            .perform(delete(ENTITY_API_URL_ID, virServer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VirServer> virServerList = virServerRepository.findAll();
        assertThat(virServerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
