package com.battap.vpn.service;

import com.battap.vpn.domain.VirServer;
import com.battap.vpn.repository.VirServerRepository;
import com.battap.vpn.service.dto.VirServerDTO;
import com.battap.vpn.service.mapper.VirServerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link VirServer}.
 */
@Service
public class VirServerService {

    private final Logger log = LoggerFactory.getLogger(VirServerService.class);

    private final VirServerRepository virServerRepository;

    private final VirServerMapper virServerMapper;

    public VirServerService(VirServerRepository virServerRepository, VirServerMapper virServerMapper) {
        this.virServerRepository = virServerRepository;
        this.virServerMapper = virServerMapper;
    }

    /**
     * Save a virServer.
     *
     * @param virServerDTO the entity to save.
     * @return the persisted entity.
     */
    public VirServerDTO save(VirServerDTO virServerDTO) {
        log.debug("Request to save VirServer : {}", virServerDTO);
        VirServer virServer = virServerMapper.toEntity(virServerDTO);
        virServer = virServerRepository.save(virServer);
        return virServerMapper.toDto(virServer);
    }

    /**
     * Partially update a virServer.
     *
     * @param virServerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VirServerDTO> partialUpdate(VirServerDTO virServerDTO) {
        log.debug("Request to partially update VirServer : {}", virServerDTO);

        return virServerRepository
            .findById(virServerDTO.getId())
            .map(existingVirServer -> {
                virServerMapper.partialUpdate(existingVirServer, virServerDTO);

                return existingVirServer;
            })
            .map(virServerRepository::save)
            .map(virServerMapper::toDto);
    }

    /**
     * Get all the virServers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<VirServerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VirServers");
        return virServerRepository.findAll(pageable).map(virServerMapper::toDto);
    }

    /**
     * Get one virServer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<VirServerDTO> findOne(String id) {
        log.debug("Request to get VirServer : {}", id);
        return virServerRepository.findById(id).map(virServerMapper::toDto);
    }

    /**
     * Delete the virServer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete VirServer : {}", id);
        virServerRepository.deleteById(id);
    }
}
