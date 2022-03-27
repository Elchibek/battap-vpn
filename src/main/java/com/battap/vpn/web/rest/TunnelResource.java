package com.battap.vpn.web.rest;

import com.battap.vpn.repository.TunnelRepository;
import com.battap.vpn.service.TunnelService;
import com.battap.vpn.service.dto.TunnelDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.battap.vpn.domain.Tunnel}.
 */
@RestController
@RequestMapping("/api")
public class TunnelResource {

    private final Logger log = LoggerFactory.getLogger(TunnelResource.class);

    private static final String ENTITY_NAME = "tunnel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TunnelService tunnelService;

    private final TunnelRepository tunnelRepository;

    public TunnelResource(TunnelService tunnelService, TunnelRepository tunnelRepository) {
        this.tunnelService = tunnelService;
        this.tunnelRepository = tunnelRepository;
    }

    /**
     * {@code POST  /tunnels} : Create a new tunnel.
     *
     * @param tunnelDTO the tunnelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tunnelDTO, or with status {@code 400 (Bad Request)} if the tunnel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tunnels")
    public ResponseEntity<TunnelDTO> createTunnel(@Valid @RequestBody TunnelDTO tunnelDTO) throws URISyntaxException {
        log.debug("REST request to save Tunnel : {}", tunnelDTO);
        if (tunnelDTO.getId() != null) {
            throw new BadRequestAlertException("A new tunnel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TunnelDTO result = tunnelService.save(tunnelDTO);
        return ResponseEntity
            .created(new URI("/api/tunnels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /tunnels/:id} : Updates an existing tunnel.
     *
     * @param id the id of the tunnelDTO to save.
     * @param tunnelDTO the tunnelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tunnelDTO,
     * or with status {@code 400 (Bad Request)} if the tunnelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tunnelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tunnels/{id}")
    public ResponseEntity<TunnelDTO> updateTunnel(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody TunnelDTO tunnelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tunnel : {}, {}", id, tunnelDTO);
        if (tunnelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tunnelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tunnelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TunnelDTO result = tunnelService.save(tunnelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tunnelDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /tunnels/:id} : Partial updates given fields of an existing tunnel, field will ignore if it is null
     *
     * @param id the id of the tunnelDTO to save.
     * @param tunnelDTO the tunnelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tunnelDTO,
     * or with status {@code 400 (Bad Request)} if the tunnelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tunnelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tunnelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tunnels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TunnelDTO> partialUpdateTunnel(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody TunnelDTO tunnelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tunnel partially : {}, {}", id, tunnelDTO);
        if (tunnelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tunnelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tunnelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TunnelDTO> result = tunnelService.partialUpdate(tunnelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tunnelDTO.getId())
        );
    }

    /**
     * {@code GET  /tunnels} : get all the tunnels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tunnels in body.
     */
    @GetMapping("/tunnels")
    public List<TunnelDTO> getAllTunnels() {
        log.debug("REST request to get all Tunnels");
        return tunnelService.findAll();
    }

    /**
     * {@code GET  /tunnels/:id} : get the "id" tunnel.
     *
     * @param id the id of the tunnelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tunnelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tunnels/{id}")
    public ResponseEntity<TunnelDTO> getTunnel(@PathVariable String id) {
        log.debug("REST request to get Tunnel : {}", id);
        Optional<TunnelDTO> tunnelDTO = tunnelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tunnelDTO);
    }

    /**
     * {@code DELETE  /tunnels/:id} : delete the "id" tunnel.
     *
     * @param id the id of the tunnelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tunnels/{id}")
    public ResponseEntity<Void> deleteTunnel(@PathVariable String id) {
        log.debug("REST request to delete Tunnel : {}", id);
        tunnelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
