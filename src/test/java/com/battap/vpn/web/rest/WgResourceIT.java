package com.battap.vpn.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.battap.vpn.IntegrationTest;
import com.battap.vpn.domain.Wg;
import com.battap.vpn.repository.WgRepository;
import com.battap.vpn.service.dto.WgDTO;
import com.battap.vpn.service.mapper.WgMapper;
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
 * Integration tests for the {@link WgResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WgResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRIVATE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PRIVATE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLIC_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PUBLIC_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_MTU = 1;
    private static final Integer UPDATED_MTU = 2;

    private static final Integer DEFAULT_LISTEN_PORT = 1;
    private static final Integer UPDATED_LISTEN_PORT = 2;

    private static final String DEFAULT_POST_UP = "AAAAAAAAAA";
    private static final String UPDATED_POST_UP = "BBBBBBBBBB";

    private static final String DEFAULT_POST_DOWN = "AAAAAAAAAA";
    private static final String UPDATED_POST_DOWN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wgs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private WgRepository wgRepository;

    @Autowired
    private WgMapper wgMapper;

    @Autowired
    private MockMvc restWgMockMvc;

    private Wg wg;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wg createEntity() {
        Wg wg = new Wg()
            .name(DEFAULT_NAME)
            .privateKey(DEFAULT_PRIVATE_KEY)
            .publicKey(DEFAULT_PUBLIC_KEY)
            .address(DEFAULT_ADDRESS)
            .mtu(DEFAULT_MTU)
            .listenPort(DEFAULT_LISTEN_PORT)
            .postUp(DEFAULT_POST_UP)
            .postDown(DEFAULT_POST_DOWN);
        return wg;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wg createUpdatedEntity() {
        Wg wg = new Wg()
            .name(UPDATED_NAME)
            .privateKey(UPDATED_PRIVATE_KEY)
            .publicKey(UPDATED_PUBLIC_KEY)
            .address(UPDATED_ADDRESS)
            .mtu(UPDATED_MTU)
            .listenPort(UPDATED_LISTEN_PORT)
            .postUp(UPDATED_POST_UP)
            .postDown(UPDATED_POST_DOWN);
        return wg;
    }

    @BeforeEach
    public void initTest() {
        wgRepository.deleteAll();
        wg = createEntity();
    }

    @Test
    void createWg() throws Exception {
        int databaseSizeBeforeCreate = wgRepository.findAll().size();
        // Create the Wg
        WgDTO wgDTO = wgMapper.toDto(wg);
        restWgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wgDTO)))
            .andExpect(status().isCreated());

        // Validate the Wg in the database
        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeCreate + 1);
        Wg testWg = wgList.get(wgList.size() - 1);
        assertThat(testWg.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWg.getPrivateKey()).isEqualTo(DEFAULT_PRIVATE_KEY);
        assertThat(testWg.getPublicKey()).isEqualTo(DEFAULT_PUBLIC_KEY);
        assertThat(testWg.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testWg.getMtu()).isEqualTo(DEFAULT_MTU);
        assertThat(testWg.getListenPort()).isEqualTo(DEFAULT_LISTEN_PORT);
        assertThat(testWg.getPostUp()).isEqualTo(DEFAULT_POST_UP);
        assertThat(testWg.getPostDown()).isEqualTo(DEFAULT_POST_DOWN);
    }

    @Test
    void createWgWithExistingId() throws Exception {
        // Create the Wg with an existing ID
        wg.setId("existing_id");
        WgDTO wgDTO = wgMapper.toDto(wg);

        int databaseSizeBeforeCreate = wgRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wgDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wg in the database
        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = wgRepository.findAll().size();
        // set the field null
        wg.setName(null);

        // Create the Wg, which fails.
        WgDTO wgDTO = wgMapper.toDto(wg);

        restWgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wgDTO)))
            .andExpect(status().isBadRequest());

        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPrivateKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = wgRepository.findAll().size();
        // set the field null
        wg.setPrivateKey(null);

        // Create the Wg, which fails.
        WgDTO wgDTO = wgMapper.toDto(wg);

        restWgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wgDTO)))
            .andExpect(status().isBadRequest());

        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPublicKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = wgRepository.findAll().size();
        // set the field null
        wg.setPublicKey(null);

        // Create the Wg, which fails.
        WgDTO wgDTO = wgMapper.toDto(wg);

        restWgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wgDTO)))
            .andExpect(status().isBadRequest());

        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = wgRepository.findAll().size();
        // set the field null
        wg.setAddress(null);

        // Create the Wg, which fails.
        WgDTO wgDTO = wgMapper.toDto(wg);

        restWgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wgDTO)))
            .andExpect(status().isBadRequest());

        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkListenPortIsRequired() throws Exception {
        int databaseSizeBeforeTest = wgRepository.findAll().size();
        // set the field null
        wg.setListenPort(null);

        // Create the Wg, which fails.
        WgDTO wgDTO = wgMapper.toDto(wg);

        restWgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wgDTO)))
            .andExpect(status().isBadRequest());

        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllWgs() throws Exception {
        // Initialize the database
        wgRepository.save(wg);

        // Get all the wgList
        restWgMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wg.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].privateKey").value(hasItem(DEFAULT_PRIVATE_KEY)))
            .andExpect(jsonPath("$.[*].publicKey").value(hasItem(DEFAULT_PUBLIC_KEY)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].mtu").value(hasItem(DEFAULT_MTU)))
            .andExpect(jsonPath("$.[*].listenPort").value(hasItem(DEFAULT_LISTEN_PORT)))
            .andExpect(jsonPath("$.[*].postUp").value(hasItem(DEFAULT_POST_UP)))
            .andExpect(jsonPath("$.[*].postDown").value(hasItem(DEFAULT_POST_DOWN)));
    }

    @Test
    void getWg() throws Exception {
        // Initialize the database
        wgRepository.save(wg);

        // Get the wg
        restWgMockMvc
            .perform(get(ENTITY_API_URL_ID, wg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wg.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.privateKey").value(DEFAULT_PRIVATE_KEY))
            .andExpect(jsonPath("$.publicKey").value(DEFAULT_PUBLIC_KEY))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.mtu").value(DEFAULT_MTU))
            .andExpect(jsonPath("$.listenPort").value(DEFAULT_LISTEN_PORT))
            .andExpect(jsonPath("$.postUp").value(DEFAULT_POST_UP))
            .andExpect(jsonPath("$.postDown").value(DEFAULT_POST_DOWN));
    }

    @Test
    void getNonExistingWg() throws Exception {
        // Get the wg
        restWgMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewWg() throws Exception {
        // Initialize the database
        wgRepository.save(wg);

        int databaseSizeBeforeUpdate = wgRepository.findAll().size();

        // Update the wg
        Wg updatedWg = wgRepository.findById(wg.getId()).get();
        updatedWg
            .name(UPDATED_NAME)
            .privateKey(UPDATED_PRIVATE_KEY)
            .publicKey(UPDATED_PUBLIC_KEY)
            .address(UPDATED_ADDRESS)
            .mtu(UPDATED_MTU)
            .listenPort(UPDATED_LISTEN_PORT)
            .postUp(UPDATED_POST_UP)
            .postDown(UPDATED_POST_DOWN);
        WgDTO wgDTO = wgMapper.toDto(updatedWg);

        restWgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wgDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wgDTO))
            )
            .andExpect(status().isOk());

        // Validate the Wg in the database
        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeUpdate);
        Wg testWg = wgList.get(wgList.size() - 1);
        assertThat(testWg.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWg.getPrivateKey()).isEqualTo(UPDATED_PRIVATE_KEY);
        assertThat(testWg.getPublicKey()).isEqualTo(UPDATED_PUBLIC_KEY);
        assertThat(testWg.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWg.getMtu()).isEqualTo(UPDATED_MTU);
        assertThat(testWg.getListenPort()).isEqualTo(UPDATED_LISTEN_PORT);
        assertThat(testWg.getPostUp()).isEqualTo(UPDATED_POST_UP);
        assertThat(testWg.getPostDown()).isEqualTo(UPDATED_POST_DOWN);
    }

    @Test
    void putNonExistingWg() throws Exception {
        int databaseSizeBeforeUpdate = wgRepository.findAll().size();
        wg.setId(UUID.randomUUID().toString());

        // Create the Wg
        WgDTO wgDTO = wgMapper.toDto(wg);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wgDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wg in the database
        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchWg() throws Exception {
        int databaseSizeBeforeUpdate = wgRepository.findAll().size();
        wg.setId(UUID.randomUUID().toString());

        // Create the Wg
        WgDTO wgDTO = wgMapper.toDto(wg);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wg in the database
        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamWg() throws Exception {
        int databaseSizeBeforeUpdate = wgRepository.findAll().size();
        wg.setId(UUID.randomUUID().toString());

        // Create the Wg
        WgDTO wgDTO = wgMapper.toDto(wg);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWgMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wgDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wg in the database
        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateWgWithPatch() throws Exception {
        // Initialize the database
        wgRepository.save(wg);

        int databaseSizeBeforeUpdate = wgRepository.findAll().size();

        // Update the wg using partial update
        Wg partialUpdatedWg = new Wg();
        partialUpdatedWg.setId(wg.getId());

        partialUpdatedWg.publicKey(UPDATED_PUBLIC_KEY).mtu(UPDATED_MTU);

        restWgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWg.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWg))
            )
            .andExpect(status().isOk());

        // Validate the Wg in the database
        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeUpdate);
        Wg testWg = wgList.get(wgList.size() - 1);
        assertThat(testWg.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWg.getPrivateKey()).isEqualTo(DEFAULT_PRIVATE_KEY);
        assertThat(testWg.getPublicKey()).isEqualTo(UPDATED_PUBLIC_KEY);
        assertThat(testWg.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testWg.getMtu()).isEqualTo(UPDATED_MTU);
        assertThat(testWg.getListenPort()).isEqualTo(DEFAULT_LISTEN_PORT);
        assertThat(testWg.getPostUp()).isEqualTo(DEFAULT_POST_UP);
        assertThat(testWg.getPostDown()).isEqualTo(DEFAULT_POST_DOWN);
    }

    @Test
    void fullUpdateWgWithPatch() throws Exception {
        // Initialize the database
        wgRepository.save(wg);

        int databaseSizeBeforeUpdate = wgRepository.findAll().size();

        // Update the wg using partial update
        Wg partialUpdatedWg = new Wg();
        partialUpdatedWg.setId(wg.getId());

        partialUpdatedWg
            .name(UPDATED_NAME)
            .privateKey(UPDATED_PRIVATE_KEY)
            .publicKey(UPDATED_PUBLIC_KEY)
            .address(UPDATED_ADDRESS)
            .mtu(UPDATED_MTU)
            .listenPort(UPDATED_LISTEN_PORT)
            .postUp(UPDATED_POST_UP)
            .postDown(UPDATED_POST_DOWN);

        restWgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWg.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWg))
            )
            .andExpect(status().isOk());

        // Validate the Wg in the database
        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeUpdate);
        Wg testWg = wgList.get(wgList.size() - 1);
        assertThat(testWg.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWg.getPrivateKey()).isEqualTo(UPDATED_PRIVATE_KEY);
        assertThat(testWg.getPublicKey()).isEqualTo(UPDATED_PUBLIC_KEY);
        assertThat(testWg.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWg.getMtu()).isEqualTo(UPDATED_MTU);
        assertThat(testWg.getListenPort()).isEqualTo(UPDATED_LISTEN_PORT);
        assertThat(testWg.getPostUp()).isEqualTo(UPDATED_POST_UP);
        assertThat(testWg.getPostDown()).isEqualTo(UPDATED_POST_DOWN);
    }

    @Test
    void patchNonExistingWg() throws Exception {
        int databaseSizeBeforeUpdate = wgRepository.findAll().size();
        wg.setId(UUID.randomUUID().toString());

        // Create the Wg
        WgDTO wgDTO = wgMapper.toDto(wg);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wgDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wg in the database
        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchWg() throws Exception {
        int databaseSizeBeforeUpdate = wgRepository.findAll().size();
        wg.setId(UUID.randomUUID().toString());

        // Create the Wg
        WgDTO wgDTO = wgMapper.toDto(wg);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wg in the database
        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamWg() throws Exception {
        int databaseSizeBeforeUpdate = wgRepository.findAll().size();
        wg.setId(UUID.randomUUID().toString());

        // Create the Wg
        WgDTO wgDTO = wgMapper.toDto(wg);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWgMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(wgDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wg in the database
        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteWg() throws Exception {
        // Initialize the database
        wgRepository.save(wg);

        int databaseSizeBeforeDelete = wgRepository.findAll().size();

        // Delete the wg
        restWgMockMvc.perform(delete(ENTITY_API_URL_ID, wg.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wg> wgList = wgRepository.findAll();
        assertThat(wgList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
