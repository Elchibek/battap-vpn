package com.battap.vpn.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.battap.vpn.IntegrationTest;
import com.battap.vpn.domain.Client;
import com.battap.vpn.domain.enumeration.Platform;
import com.battap.vpn.repository.ClientRepository;
import com.battap.vpn.service.dto.ClientDTO;
import com.battap.vpn.service.mapper.ClientMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_IP = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_IP = "BBBBBBBBBB";

    private static final byte[] DEFAULT_QR_CODE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_QR_CODE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_QR_CODE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_QR_CODE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Platform DEFAULT_PLATFORM = Platform.ANDROID;
    private static final Platform UPDATED_PLATFORM = Platform.IOS;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BYTES_RECEIVED = "AAAAAAAAAA";
    private static final String UPDATED_BYTES_RECEIVED = "BBBBBBBBBB";

    private static final String DEFAULT_BYTES_SENT = "AAAAAAAAAA";
    private static final String UPDATED_BYTES_SENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity() {
        Client client = new Client()
            .name(DEFAULT_NAME)
            .clientIP(DEFAULT_CLIENT_IP)
            .qrCode(DEFAULT_QR_CODE)
            .qrCodeContentType(DEFAULT_QR_CODE_CONTENT_TYPE)
            .email(DEFAULT_EMAIL)
            .status(DEFAULT_STATUS)
            .platform(DEFAULT_PLATFORM)
            .description(DEFAULT_DESCRIPTION)
            .bytesReceived(DEFAULT_BYTES_RECEIVED)
            .bytesSent(DEFAULT_BYTES_SENT)
            .startDate(DEFAULT_START_DATE)
            .lastUpdateDate(DEFAULT_LAST_UPDATE_DATE);
        return client;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity() {
        Client client = new Client()
            .name(UPDATED_NAME)
            .clientIP(UPDATED_CLIENT_IP)
            .qrCode(UPDATED_QR_CODE)
            .qrCodeContentType(UPDATED_QR_CODE_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .status(UPDATED_STATUS)
            .platform(UPDATED_PLATFORM)
            .description(UPDATED_DESCRIPTION)
            .bytesReceived(UPDATED_BYTES_RECEIVED)
            .bytesSent(UPDATED_BYTES_SENT)
            .startDate(UPDATED_START_DATE)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE);
        return client;
    }

    @BeforeEach
    public void initTest() {
        clientRepository.deleteAll();
        client = createEntity();
    }

    @Test
    void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();
        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);
        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClient.getClientIP()).isEqualTo(DEFAULT_CLIENT_IP);
        assertThat(testClient.getQrCode()).isEqualTo(DEFAULT_QR_CODE);
        assertThat(testClient.getQrCodeContentType()).isEqualTo(DEFAULT_QR_CODE_CONTENT_TYPE);
        assertThat(testClient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClient.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testClient.getPlatform()).isEqualTo(DEFAULT_PLATFORM);
        assertThat(testClient.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClient.getBytesReceived()).isEqualTo(DEFAULT_BYTES_RECEIVED);
        assertThat(testClient.getBytesSent()).isEqualTo(DEFAULT_BYTES_SENT);
        assertThat(testClient.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testClient.getLastUpdateDate()).isEqualTo(DEFAULT_LAST_UPDATE_DATE);
    }

    @Test
    void createClientWithExistingId() throws Exception {
        // Create the Client with an existing ID
        client.setId("existing_id");
        ClientDTO clientDTO = clientMapper.toDto(client);

        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setName(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setStatus(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setStartDate(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        // Get all the clientList
        restClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].clientIP").value(hasItem(DEFAULT_CLIENT_IP)))
            .andExpect(jsonPath("$.[*].qrCodeContentType").value(hasItem(DEFAULT_QR_CODE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].qrCode").value(hasItem(Base64Utils.encodeToString(DEFAULT_QR_CODE))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].platform").value(hasItem(DEFAULT_PLATFORM.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].bytesReceived").value(hasItem(DEFAULT_BYTES_RECEIVED)))
            .andExpect(jsonPath("$.[*].bytesSent").value(hasItem(DEFAULT_BYTES_SENT)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateDate").value(hasItem(DEFAULT_LAST_UPDATE_DATE.toString())));
    }

    @Test
    void getClient() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        // Get the client
        restClientMockMvc
            .perform(get(ENTITY_API_URL_ID, client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.clientIP").value(DEFAULT_CLIENT_IP))
            .andExpect(jsonPath("$.qrCodeContentType").value(DEFAULT_QR_CODE_CONTENT_TYPE))
            .andExpect(jsonPath("$.qrCode").value(Base64Utils.encodeToString(DEFAULT_QR_CODE)))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.platform").value(DEFAULT_PLATFORM.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.bytesReceived").value(DEFAULT_BYTES_RECEIVED))
            .andExpect(jsonPath("$.bytesSent").value(DEFAULT_BYTES_SENT))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdateDate").value(DEFAULT_LAST_UPDATE_DATE.toString()));
    }

    @Test
    void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewClient() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        updatedClient
            .name(UPDATED_NAME)
            .clientIP(UPDATED_CLIENT_IP)
            .qrCode(UPDATED_QR_CODE)
            .qrCodeContentType(UPDATED_QR_CODE_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .status(UPDATED_STATUS)
            .platform(UPDATED_PLATFORM)
            .description(UPDATED_DESCRIPTION)
            .bytesReceived(UPDATED_BYTES_RECEIVED)
            .bytesSent(UPDATED_BYTES_SENT)
            .startDate(UPDATED_START_DATE)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE);
        ClientDTO clientDTO = clientMapper.toDto(updatedClient);

        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientDTO))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClient.getClientIP()).isEqualTo(UPDATED_CLIENT_IP);
        assertThat(testClient.getQrCode()).isEqualTo(UPDATED_QR_CODE);
        assertThat(testClient.getQrCodeContentType()).isEqualTo(UPDATED_QR_CODE_CONTENT_TYPE);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClient.getPlatform()).isEqualTo(UPDATED_PLATFORM);
        assertThat(testClient.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClient.getBytesReceived()).isEqualTo(UPDATED_BYTES_RECEIVED);
        assertThat(testClient.getBytesSent()).isEqualTo(UPDATED_BYTES_SENT);
        assertThat(testClient.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testClient.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    void putNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .name(UPDATED_NAME)
            .clientIP(UPDATED_CLIENT_IP)
            .qrCode(UPDATED_QR_CODE)
            .qrCodeContentType(UPDATED_QR_CODE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .bytesSent(UPDATED_BYTES_SENT)
            .startDate(UPDATED_START_DATE)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClient.getClientIP()).isEqualTo(UPDATED_CLIENT_IP);
        assertThat(testClient.getQrCode()).isEqualTo(UPDATED_QR_CODE);
        assertThat(testClient.getQrCodeContentType()).isEqualTo(UPDATED_QR_CODE_CONTENT_TYPE);
        assertThat(testClient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClient.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testClient.getPlatform()).isEqualTo(DEFAULT_PLATFORM);
        assertThat(testClient.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClient.getBytesReceived()).isEqualTo(DEFAULT_BYTES_RECEIVED);
        assertThat(testClient.getBytesSent()).isEqualTo(UPDATED_BYTES_SENT);
        assertThat(testClient.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testClient.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    void fullUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .name(UPDATED_NAME)
            .clientIP(UPDATED_CLIENT_IP)
            .qrCode(UPDATED_QR_CODE)
            .qrCodeContentType(UPDATED_QR_CODE_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .status(UPDATED_STATUS)
            .platform(UPDATED_PLATFORM)
            .description(UPDATED_DESCRIPTION)
            .bytesReceived(UPDATED_BYTES_RECEIVED)
            .bytesSent(UPDATED_BYTES_SENT)
            .startDate(UPDATED_START_DATE)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClient.getClientIP()).isEqualTo(UPDATED_CLIENT_IP);
        assertThat(testClient.getQrCode()).isEqualTo(UPDATED_QR_CODE);
        assertThat(testClient.getQrCodeContentType()).isEqualTo(UPDATED_QR_CODE_CONTENT_TYPE);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClient.getPlatform()).isEqualTo(UPDATED_PLATFORM);
        assertThat(testClient.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClient.getBytesReceived()).isEqualTo(UPDATED_BYTES_RECEIVED);
        assertThat(testClient.getBytesSent()).isEqualTo(UPDATED_BYTES_SENT);
        assertThat(testClient.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testClient.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    void patchNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clientDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, client.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
