package com.battap.vpn.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.battap.vpn.IntegrationTest;
import com.battap.vpn.domain.Tunnel;
import com.battap.vpn.repository.TunnelRepository;
import com.battap.vpn.service.dto.TunnelDTO;
import com.battap.vpn.service.mapper.TunnelMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link TunnelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TunnelResourceIT {

    private static final String DEFAULT_CLIENT_PRIVATE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_PRIVATE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_PUB_KEY = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_PUB_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DNS = "AAAAAAAAAA";
    private static final String UPDATED_DNS = "BBBBBBBBBB";

    private static final String DEFAULT_SERVER_PUBLIC_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SERVER_PUBLIC_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_PRESHARED_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PRESHARED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_ANDPOINT = "AAAAAAAAAA";
    private static final String UPDATED_ANDPOINT = "BBBBBBBBBB";

    private static final String DEFAULT_ALLOWED_I_PS = "AAAAAAAAAA";
    private static final String UPDATED_ALLOWED_I_PS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PERSISTENT_KEEPALIVE = 1;
    private static final Integer UPDATED_PERSISTENT_KEEPALIVE = 2;

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tunnels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TunnelRepository tunnelRepository;

    @Autowired
    private TunnelMapper tunnelMapper;

    @Autowired
    private MockMvc restTunnelMockMvc;

    private Tunnel tunnel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tunnel createEntity() {
        Tunnel tunnel = new Tunnel()
            .clientPrivateKey(DEFAULT_CLIENT_PRIVATE_KEY)
            .clientPubKey(DEFAULT_CLIENT_PUB_KEY)
            .address(DEFAULT_ADDRESS)
            .dns(DEFAULT_DNS)
            .serverPublicKey(DEFAULT_SERVER_PUBLIC_KEY)
            .presharedKey(DEFAULT_PRESHARED_KEY)
            .andpoint(DEFAULT_ANDPOINT)
            .allowedIPs(DEFAULT_ALLOWED_I_PS)
            .persistentKeepalive(DEFAULT_PERSISTENT_KEEPALIVE)
            .text(DEFAULT_TEXT);
        return tunnel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tunnel createUpdatedEntity() {
        Tunnel tunnel = new Tunnel()
            .clientPrivateKey(UPDATED_CLIENT_PRIVATE_KEY)
            .clientPubKey(UPDATED_CLIENT_PUB_KEY)
            .address(UPDATED_ADDRESS)
            .dns(UPDATED_DNS)
            .serverPublicKey(UPDATED_SERVER_PUBLIC_KEY)
            .presharedKey(UPDATED_PRESHARED_KEY)
            .andpoint(UPDATED_ANDPOINT)
            .allowedIPs(UPDATED_ALLOWED_I_PS)
            .persistentKeepalive(UPDATED_PERSISTENT_KEEPALIVE)
            .text(UPDATED_TEXT);
        return tunnel;
    }

    @BeforeEach
    public void initTest() {
        tunnelRepository.deleteAll();
        tunnel = createEntity();
    }

    @Test
    void createTunnel() throws Exception {
        int databaseSizeBeforeCreate = tunnelRepository.findAll().size();
        // Create the Tunnel
        TunnelDTO tunnelDTO = tunnelMapper.toDto(tunnel);
        restTunnelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tunnelDTO)))
            .andExpect(status().isCreated());

        // Validate the Tunnel in the database
        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeCreate + 1);
        Tunnel testTunnel = tunnelList.get(tunnelList.size() - 1);
        assertThat(testTunnel.getClientPrivateKey()).isEqualTo(DEFAULT_CLIENT_PRIVATE_KEY);
        assertThat(testTunnel.getClientPubKey()).isEqualTo(DEFAULT_CLIENT_PUB_KEY);
        assertThat(testTunnel.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testTunnel.getDns()).isEqualTo(DEFAULT_DNS);
        assertThat(testTunnel.getServerPublicKey()).isEqualTo(DEFAULT_SERVER_PUBLIC_KEY);
        assertThat(testTunnel.getPresharedKey()).isEqualTo(DEFAULT_PRESHARED_KEY);
        assertThat(testTunnel.getAndpoint()).isEqualTo(DEFAULT_ANDPOINT);
        assertThat(testTunnel.getAllowedIPs()).isEqualTo(DEFAULT_ALLOWED_I_PS);
        assertThat(testTunnel.getPersistentKeepalive()).isEqualTo(DEFAULT_PERSISTENT_KEEPALIVE);
        assertThat(testTunnel.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    void createTunnelWithExistingId() throws Exception {
        // Create the Tunnel with an existing ID
        tunnel.setId("existing_id");
        TunnelDTO tunnelDTO = tunnelMapper.toDto(tunnel);

        int databaseSizeBeforeCreate = tunnelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTunnelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tunnelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tunnel in the database
        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkClientPrivateKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = tunnelRepository.findAll().size();
        // set the field null
        tunnel.setClientPrivateKey(null);

        // Create the Tunnel, which fails.
        TunnelDTO tunnelDTO = tunnelMapper.toDto(tunnel);

        restTunnelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tunnelDTO)))
            .andExpect(status().isBadRequest());

        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkClientPubKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = tunnelRepository.findAll().size();
        // set the field null
        tunnel.setClientPubKey(null);

        // Create the Tunnel, which fails.
        TunnelDTO tunnelDTO = tunnelMapper.toDto(tunnel);

        restTunnelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tunnelDTO)))
            .andExpect(status().isBadRequest());

        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = tunnelRepository.findAll().size();
        // set the field null
        tunnel.setAddress(null);

        // Create the Tunnel, which fails.
        TunnelDTO tunnelDTO = tunnelMapper.toDto(tunnel);

        restTunnelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tunnelDTO)))
            .andExpect(status().isBadRequest());

        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDnsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tunnelRepository.findAll().size();
        // set the field null
        tunnel.setDns(null);

        // Create the Tunnel, which fails.
        TunnelDTO tunnelDTO = tunnelMapper.toDto(tunnel);

        restTunnelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tunnelDTO)))
            .andExpect(status().isBadRequest());

        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkServerPublicKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = tunnelRepository.findAll().size();
        // set the field null
        tunnel.setServerPublicKey(null);

        // Create the Tunnel, which fails.
        TunnelDTO tunnelDTO = tunnelMapper.toDto(tunnel);

        restTunnelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tunnelDTO)))
            .andExpect(status().isBadRequest());

        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAndpointIsRequired() throws Exception {
        int databaseSizeBeforeTest = tunnelRepository.findAll().size();
        // set the field null
        tunnel.setAndpoint(null);

        // Create the Tunnel, which fails.
        TunnelDTO tunnelDTO = tunnelMapper.toDto(tunnel);

        restTunnelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tunnelDTO)))
            .andExpect(status().isBadRequest());

        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAllowedIPsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tunnelRepository.findAll().size();
        // set the field null
        tunnel.setAllowedIPs(null);

        // Create the Tunnel, which fails.
        TunnelDTO tunnelDTO = tunnelMapper.toDto(tunnel);

        restTunnelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tunnelDTO)))
            .andExpect(status().isBadRequest());

        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllTunnels() throws Exception {
        // Initialize the database
        tunnelRepository.save(tunnel);

        // Get all the tunnelList
        restTunnelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tunnel.getId())))
            .andExpect(jsonPath("$.[*].clientPrivateKey").value(hasItem(DEFAULT_CLIENT_PRIVATE_KEY)))
            .andExpect(jsonPath("$.[*].clientPubKey").value(hasItem(DEFAULT_CLIENT_PUB_KEY)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].dns").value(hasItem(DEFAULT_DNS)))
            .andExpect(jsonPath("$.[*].serverPublicKey").value(hasItem(DEFAULT_SERVER_PUBLIC_KEY)))
            .andExpect(jsonPath("$.[*].presharedKey").value(hasItem(DEFAULT_PRESHARED_KEY)))
            .andExpect(jsonPath("$.[*].andpoint").value(hasItem(DEFAULT_ANDPOINT)))
            .andExpect(jsonPath("$.[*].allowedIPs").value(hasItem(DEFAULT_ALLOWED_I_PS)))
            .andExpect(jsonPath("$.[*].persistentKeepalive").value(hasItem(DEFAULT_PERSISTENT_KEEPALIVE)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())));
    }

    @Test
    void getTunnel() throws Exception {
        // Initialize the database
        tunnelRepository.save(tunnel);

        // Get the tunnel
        restTunnelMockMvc
            .perform(get(ENTITY_API_URL_ID, tunnel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tunnel.getId()))
            .andExpect(jsonPath("$.clientPrivateKey").value(DEFAULT_CLIENT_PRIVATE_KEY))
            .andExpect(jsonPath("$.clientPubKey").value(DEFAULT_CLIENT_PUB_KEY))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.dns").value(DEFAULT_DNS))
            .andExpect(jsonPath("$.serverPublicKey").value(DEFAULT_SERVER_PUBLIC_KEY))
            .andExpect(jsonPath("$.presharedKey").value(DEFAULT_PRESHARED_KEY))
            .andExpect(jsonPath("$.andpoint").value(DEFAULT_ANDPOINT))
            .andExpect(jsonPath("$.allowedIPs").value(DEFAULT_ALLOWED_I_PS))
            .andExpect(jsonPath("$.persistentKeepalive").value(DEFAULT_PERSISTENT_KEEPALIVE))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()));
    }

    @Test
    void getNonExistingTunnel() throws Exception {
        // Get the tunnel
        restTunnelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewTunnel() throws Exception {
        // Initialize the database
        tunnelRepository.save(tunnel);

        int databaseSizeBeforeUpdate = tunnelRepository.findAll().size();

        // Update the tunnel
        Tunnel updatedTunnel = tunnelRepository.findById(tunnel.getId()).get();
        updatedTunnel
            .clientPrivateKey(UPDATED_CLIENT_PRIVATE_KEY)
            .clientPubKey(UPDATED_CLIENT_PUB_KEY)
            .address(UPDATED_ADDRESS)
            .dns(UPDATED_DNS)
            .serverPublicKey(UPDATED_SERVER_PUBLIC_KEY)
            .presharedKey(UPDATED_PRESHARED_KEY)
            .andpoint(UPDATED_ANDPOINT)
            .allowedIPs(UPDATED_ALLOWED_I_PS)
            .persistentKeepalive(UPDATED_PERSISTENT_KEEPALIVE)
            .text(UPDATED_TEXT);
        TunnelDTO tunnelDTO = tunnelMapper.toDto(updatedTunnel);

        restTunnelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tunnelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tunnelDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tunnel in the database
        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeUpdate);
        Tunnel testTunnel = tunnelList.get(tunnelList.size() - 1);
        assertThat(testTunnel.getClientPrivateKey()).isEqualTo(UPDATED_CLIENT_PRIVATE_KEY);
        assertThat(testTunnel.getClientPubKey()).isEqualTo(UPDATED_CLIENT_PUB_KEY);
        assertThat(testTunnel.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTunnel.getDns()).isEqualTo(UPDATED_DNS);
        assertThat(testTunnel.getServerPublicKey()).isEqualTo(UPDATED_SERVER_PUBLIC_KEY);
        assertThat(testTunnel.getPresharedKey()).isEqualTo(UPDATED_PRESHARED_KEY);
        assertThat(testTunnel.getAndpoint()).isEqualTo(UPDATED_ANDPOINT);
        assertThat(testTunnel.getAllowedIPs()).isEqualTo(UPDATED_ALLOWED_I_PS);
        assertThat(testTunnel.getPersistentKeepalive()).isEqualTo(UPDATED_PERSISTENT_KEEPALIVE);
        assertThat(testTunnel.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    void putNonExistingTunnel() throws Exception {
        int databaseSizeBeforeUpdate = tunnelRepository.findAll().size();
        tunnel.setId(UUID.randomUUID().toString());

        // Create the Tunnel
        TunnelDTO tunnelDTO = tunnelMapper.toDto(tunnel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTunnelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tunnelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tunnelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tunnel in the database
        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTunnel() throws Exception {
        int databaseSizeBeforeUpdate = tunnelRepository.findAll().size();
        tunnel.setId(UUID.randomUUID().toString());

        // Create the Tunnel
        TunnelDTO tunnelDTO = tunnelMapper.toDto(tunnel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTunnelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tunnelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tunnel in the database
        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTunnel() throws Exception {
        int databaseSizeBeforeUpdate = tunnelRepository.findAll().size();
        tunnel.setId(UUID.randomUUID().toString());

        // Create the Tunnel
        TunnelDTO tunnelDTO = tunnelMapper.toDto(tunnel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTunnelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tunnelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tunnel in the database
        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTunnelWithPatch() throws Exception {
        // Initialize the database
        tunnelRepository.save(tunnel);

        int databaseSizeBeforeUpdate = tunnelRepository.findAll().size();

        // Update the tunnel using partial update
        Tunnel partialUpdatedTunnel = new Tunnel();
        partialUpdatedTunnel.setId(tunnel.getId());

        partialUpdatedTunnel.address(UPDATED_ADDRESS).serverPublicKey(UPDATED_SERVER_PUBLIC_KEY).presharedKey(UPDATED_PRESHARED_KEY);

        restTunnelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTunnel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTunnel))
            )
            .andExpect(status().isOk());

        // Validate the Tunnel in the database
        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeUpdate);
        Tunnel testTunnel = tunnelList.get(tunnelList.size() - 1);
        assertThat(testTunnel.getClientPrivateKey()).isEqualTo(DEFAULT_CLIENT_PRIVATE_KEY);
        assertThat(testTunnel.getClientPubKey()).isEqualTo(DEFAULT_CLIENT_PUB_KEY);
        assertThat(testTunnel.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTunnel.getDns()).isEqualTo(DEFAULT_DNS);
        assertThat(testTunnel.getServerPublicKey()).isEqualTo(UPDATED_SERVER_PUBLIC_KEY);
        assertThat(testTunnel.getPresharedKey()).isEqualTo(UPDATED_PRESHARED_KEY);
        assertThat(testTunnel.getAndpoint()).isEqualTo(DEFAULT_ANDPOINT);
        assertThat(testTunnel.getAllowedIPs()).isEqualTo(DEFAULT_ALLOWED_I_PS);
        assertThat(testTunnel.getPersistentKeepalive()).isEqualTo(DEFAULT_PERSISTENT_KEEPALIVE);
        assertThat(testTunnel.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    void fullUpdateTunnelWithPatch() throws Exception {
        // Initialize the database
        tunnelRepository.save(tunnel);

        int databaseSizeBeforeUpdate = tunnelRepository.findAll().size();

        // Update the tunnel using partial update
        Tunnel partialUpdatedTunnel = new Tunnel();
        partialUpdatedTunnel.setId(tunnel.getId());

        partialUpdatedTunnel
            .clientPrivateKey(UPDATED_CLIENT_PRIVATE_KEY)
            .clientPubKey(UPDATED_CLIENT_PUB_KEY)
            .address(UPDATED_ADDRESS)
            .dns(UPDATED_DNS)
            .serverPublicKey(UPDATED_SERVER_PUBLIC_KEY)
            .presharedKey(UPDATED_PRESHARED_KEY)
            .andpoint(UPDATED_ANDPOINT)
            .allowedIPs(UPDATED_ALLOWED_I_PS)
            .persistentKeepalive(UPDATED_PERSISTENT_KEEPALIVE)
            .text(UPDATED_TEXT);

        restTunnelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTunnel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTunnel))
            )
            .andExpect(status().isOk());

        // Validate the Tunnel in the database
        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeUpdate);
        Tunnel testTunnel = tunnelList.get(tunnelList.size() - 1);
        assertThat(testTunnel.getClientPrivateKey()).isEqualTo(UPDATED_CLIENT_PRIVATE_KEY);
        assertThat(testTunnel.getClientPubKey()).isEqualTo(UPDATED_CLIENT_PUB_KEY);
        assertThat(testTunnel.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTunnel.getDns()).isEqualTo(UPDATED_DNS);
        assertThat(testTunnel.getServerPublicKey()).isEqualTo(UPDATED_SERVER_PUBLIC_KEY);
        assertThat(testTunnel.getPresharedKey()).isEqualTo(UPDATED_PRESHARED_KEY);
        assertThat(testTunnel.getAndpoint()).isEqualTo(UPDATED_ANDPOINT);
        assertThat(testTunnel.getAllowedIPs()).isEqualTo(UPDATED_ALLOWED_I_PS);
        assertThat(testTunnel.getPersistentKeepalive()).isEqualTo(UPDATED_PERSISTENT_KEEPALIVE);
        assertThat(testTunnel.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    void patchNonExistingTunnel() throws Exception {
        int databaseSizeBeforeUpdate = tunnelRepository.findAll().size();
        tunnel.setId(UUID.randomUUID().toString());

        // Create the Tunnel
        TunnelDTO tunnelDTO = tunnelMapper.toDto(tunnel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTunnelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tunnelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tunnelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tunnel in the database
        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTunnel() throws Exception {
        int databaseSizeBeforeUpdate = tunnelRepository.findAll().size();
        tunnel.setId(UUID.randomUUID().toString());

        // Create the Tunnel
        TunnelDTO tunnelDTO = tunnelMapper.toDto(tunnel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTunnelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tunnelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tunnel in the database
        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTunnel() throws Exception {
        int databaseSizeBeforeUpdate = tunnelRepository.findAll().size();
        tunnel.setId(UUID.randomUUID().toString());

        // Create the Tunnel
        TunnelDTO tunnelDTO = tunnelMapper.toDto(tunnel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTunnelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tunnelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tunnel in the database
        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTunnel() throws Exception {
        // Initialize the database
        tunnelRepository.save(tunnel);

        int databaseSizeBeforeDelete = tunnelRepository.findAll().size();

        // Delete the tunnel
        restTunnelMockMvc
            .perform(delete(ENTITY_API_URL_ID, tunnel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tunnel> tunnelList = tunnelRepository.findAll();
        assertThat(tunnelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
