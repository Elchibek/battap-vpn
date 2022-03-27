package com.battap.vpn.web.rest;

import com.battap.vpn.repository.WgRepository;
import com.battap.vpn.service.WgService;
import com.battap.vpn.service.dto.WgDTO;
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
 * REST controller for managing {@link com.battap.vpn.domain.Wg}.
 */
@RestController
@RequestMapping("/api")
public class WgResource {

    private final Logger log = LoggerFactory.getLogger(WgResource.class);

    private static final String ENTITY_NAME = "wg";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WgService wgService;

    private final WgRepository wgRepository;

    public WgResource(WgService wgService, WgRepository wgRepository) {
        this.wgService = wgService;
        this.wgRepository = wgRepository;
    }

    /**
     * {@code POST  /wgs} : Create a new wg.
     *
     * @param wgDTO the wgDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wgDTO, or with status {@code 400 (Bad Request)} if the wg has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wgs")
    public ResponseEntity<WgDTO> createWg(@Valid @RequestBody WgDTO wgDTO) throws URISyntaxException {
        log.debug("REST request to save Wg : {}", wgDTO);
        if (wgDTO.getId() != null) {
            throw new BadRequestAlertException("A new wg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WgDTO result = wgService.save(wgDTO, false);
        return ResponseEntity
            .created(new URI("/api/wgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /wgs/:id} : Updates an existing wg.
     *
     * @param id the id of the wgDTO to save.
     * @param wgDTO the wgDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wgDTO,
     * or with status {@code 400 (Bad Request)} if the wgDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wgDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wgs/{id}")
    public ResponseEntity<WgDTO> updateWg(@PathVariable(value = "id", required = false) final String id, @Valid @RequestBody WgDTO wgDTO)
        throws URISyntaxException {
        log.debug("REST request to update Wg : {}, {}", id, wgDTO);
        if (wgDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wgDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wgRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WgDTO result = wgService.save(wgDTO, true);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wgDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /wgs/:id} : Partial updates given fields of an existing wg, field will ignore if it is null
     *
     * @param id the id of the wgDTO to save.
     * @param wgDTO the wgDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wgDTO,
     * or with status {@code 400 (Bad Request)} if the wgDTO is not valid,
     * or with status {@code 404 (Not Found)} if the wgDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the wgDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/wgs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WgDTO> partialUpdateWg(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody WgDTO wgDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Wg partially : {}, {}", id, wgDTO);
        if (wgDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wgDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wgRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WgDTO> result = wgService.partialUpdate(wgDTO);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wgDTO.getId()));
    }

    /**
     * {@code GET  /wgs} : get all the wgs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wgs in body.
     */
    @GetMapping("/wgs")
    public ResponseEntity<List<WgDTO>> getAllWgs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Wgs");
        Page<WgDTO> page = wgService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wgs/:id} : get the "id" wg.
     *
     * @param id the id of the wgDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wgDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wgs/{id}")
    public ResponseEntity<WgDTO> getWg(@PathVariable String id) {
        log.debug("REST request to get Wg : {}", id);
        Optional<WgDTO> wgDTO = wgService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wgDTO);
    }

    /**
     * {@code DELETE  /wgs/:id} : delete the "id" wg.
     *
     * @param id the id of the wgDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wgs/{id}")
    public ResponseEntity<Void> deleteWg(@PathVariable String id) {
        log.debug("REST request to delete Wg : {}", id);
        wgService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
