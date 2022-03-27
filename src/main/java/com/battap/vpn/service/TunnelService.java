package com.battap.vpn.service;

import com.battap.vpn.domain.Tunnel;
import com.battap.vpn.repository.TunnelRepository;
import com.battap.vpn.service.dto.TunnelDTO;
import com.battap.vpn.service.mapper.TunnelMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Tunnel}.
 */
@Service
public class TunnelService {

    private final Logger log = LoggerFactory.getLogger(TunnelService.class);

    private final TunnelRepository tunnelRepository;

    private final TunnelMapper tunnelMapper;

    public TunnelService(TunnelRepository tunnelRepository, TunnelMapper tunnelMapper) {
        this.tunnelRepository = tunnelRepository;
        this.tunnelMapper = tunnelMapper;
    }

    public TunnelDTO save(TunnelDTO tunnelDTO) {
        log.debug("Request to save Tunnel : {}", tunnelDTO);
        Tunnel tunnel = tunnelMapper.toEntity(tunnelDTO);
        tunnel = tunnelRepository.save(tunnel);
        return tunnelMapper.toDto(tunnel);
    }

    public Optional<TunnelDTO> partialUpdate(TunnelDTO tunnelDTO) {
        log.debug("Request to partially update Tunnel : {}", tunnelDTO);

        return tunnelRepository
            .findById(tunnelDTO.getId())
            .map(existingTunnel -> {
                tunnelMapper.partialUpdate(existingTunnel, tunnelDTO);

                return existingTunnel;
            })
            .map(tunnelRepository::save)
            .map(tunnelMapper::toDto);
    }

    public List<TunnelDTO> findAll() {
        log.debug("Request to get all Tunnels");
        return tunnelRepository.findAll().stream().map(tunnelMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Optional<TunnelDTO> findOne(String id) {
        log.debug("Request to get Tunnel : {}", id);
        return tunnelRepository.findById(id).map(tunnelMapper::toDto);
    }

    public void delete(String id) {
        log.debug("Request to delete Tunnel : {}", id);
        tunnelRepository.deleteById(id);
    }
}
