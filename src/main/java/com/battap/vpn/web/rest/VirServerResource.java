package com.battap.vpn.web.rest;

import com.battap.vpn.repository.VirServerRepository;
import com.battap.vpn.service.VirServerService;
import com.battap.vpn.service.dto.VirServerDTO;
import com.battap.vpn.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.battap.vpn.domain.VirServer}.
 */
@RestController
@RequestMapping("/api")
public class VirServerResource {

    private final Logger log = LoggerFactory.getLogger(VirServerResource.class);

    private static final String ENTITY_NAME = "virServer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VirServerService virServerService;

    private final VirServerRepository virServerRepository;

    public VirServerResource(VirServerService virServerService, VirServerRepository virServerRepository) {
        this.virServerService = virServerService;
        this.virServerRepository = virServerRepository;
    }

    /**
     * {@code POST  /vir-servers} : Create a new virServer.
     *
     * @param virServerDTO the virServerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new virServerDTO, or with status {@code 400 (Bad Request)} if the virServer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vir-servers")
    public ResponseEntity<VirServerDTO> createVirServer(@Valid @RequestBody VirServerDTO virServerDTO) throws URISyntaxException {
        log.debug("REST request to save VirServer : {}", virServerDTO);
        if (virServerDTO.getId() != null) {
            throw new BadRequestAlertException("A new virServer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VirServerDTO result = virServerService.save(virServerDTO);
        return ResponseEntity
            .created(new URI("/api/vir-servers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /vir-servers/:id} : Updates an existing virServer.
     *
     * @param id the id of the virServerDTO to save.
     * @param virServerDTO the virServerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated virServerDTO,
     * or with status {@code 400 (Bad Request)} if the virServerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the virServerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vir-servers/{id}")
    public ResponseEntity<VirServerDTO> updateVirServer(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody VirServerDTO virServerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VirServer : {}, {}", id, virServerDTO);
        if (virServerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, virServerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!virServerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VirServerDTO result = virServerService.save(virServerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, virServerDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /vir-servers/:id} : Partial updates given fields of an existing virServer, field will ignore if it is null
     *
     * @param id the id of the virServerDTO to save.
     * @param virServerDTO the virServerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated virServerDTO,
     * or with status {@code 400 (Bad Request)} if the virServerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the virServerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the virServerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vir-servers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VirServerDTO> partialUpdateVirServer(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody VirServerDTO virServerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VirServer partially : {}, {}", id, virServerDTO);
        if (virServerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, virServerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!virServerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VirServerDTO> result = virServerService.partialUpdate(virServerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, virServerDTO.getId())
        );
    }

    /**
     * {@code GET  /vir-servers} : get all the virServers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of virServers in body.
     */
    @GetMapping("/vir-servers")
    public ResponseEntity<List<VirServerDTO>> getAllVirServers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of VirServers");
        Page<VirServerDTO> page = virServerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vir-servers/:id} : get the "id" virServer.
     *
     * @param id the id of the virServerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the virServerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vir-servers/{id}")
    public ResponseEntity<VirServerDTO> getVirServer(@PathVariable String id) {
        log.debug("REST request to get VirServer : {}", id);
        Optional<VirServerDTO> virServerDTO = virServerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(virServerDTO);
    }

    /**
     * {@code DELETE  /vir-servers/:id} : delete the "id" virServer.
     *
     * @param id the id of the virServerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vir-servers/{id}")
    public ResponseEntity<Void> deleteVirServer(@PathVariable String id) {
        log.debug("REST request to delete VirServer : {}", id);
        virServerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
