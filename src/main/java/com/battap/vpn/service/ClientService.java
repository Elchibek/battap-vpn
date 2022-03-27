package com.battap.vpn.service;

import com.battap.vpn.domain.Client;
import com.battap.vpn.repository.ClientRepository;
import com.battap.vpn.service.crypto.GenKey;
import com.battap.vpn.service.dto.ClientDTO;
import com.battap.vpn.service.dto.TunnelDTO;
import com.battap.vpn.service.mapper.ClientMapper;
import com.battap.vpn.service.util.QrCode;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
public class ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final TunnelService tunnelService;
    private final VirServerService virServerService;

    public ClientService(
        ClientRepository clientRepository, 
        ClientMapper clientMapper, 
        TunnelService tunnelService, 
        VirServerService virServerService
    ) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.tunnelService = tunnelService;
        this.virServerService = virServerService;
    }

    public ClientDTO save(ClientDTO clientDTO) {
        log.debug("Request to save Client : {}", clientDTO);
        String remooteIP = virServerService.findOne(clientDTO.getWg().getVirServer().getId()).get().getRemoteHost();
        if(remooteIP.isEmpty())
            throw new RuntimeException();

        TunnelDTO tunnel = new TunnelDTO();
        // [Interface]
        GenKey genKey = new GenKey();
        tunnel.setClientPrivateKey(genKey.getPrivateKey().toBase64());
        tunnel.setClientPubKey(genKey.getPublicKey().toBase64());
        tunnel.setAddress("10.0.0.2/32");
        tunnel.setDns("8.8.8.8");
        // [Peer]
        tunnel.setServerPublicKey(clientDTO.getWg().getPublicKey());
        tunnel.setPresharedKey(new GenKey().getPrivateKey().toBase64());
        tunnel.setAndpoint(remooteIP + ":" + clientDTO.getWg().getListenPort());
        tunnel.setAllowedIPs("0.0.0.0/0, ::0/0");
        tunnel.setPersistentKeepalive(20);

        tunnel.setText("[Interface]"
            + "\nPrivateKey = " + tunnel.getClientPrivateKey()
            + "\nAddress = " + tunnel.getAddress()
            + "\nDNS = " + tunnel.getDns()
            + "\n\n[Peer]"
            + "\nPublicKey = " + tunnel.getServerPublicKey()
            + "\nPresharedKey = " + tunnel.getPresharedKey()
            + "\nEndpoint = " + tunnel.getAndpoint()
            + "\nAllowedIPs = " + tunnel.getAllowedIPs()
            + "\nPersistentKeepalive = " + tunnel.getPersistentKeepalive());

        clientDTO.setQrCode(QrCode.generateQRcode(tunnel.getText()));
        clientDTO.setQrCodeContentType("image/png");
        clientDTO.setStartDate(Instant.now());
        clientDTO.setLastUpdateDate(Instant.now());

        clientDTO.setTunnel(tunnelService.save(tunnel));

        Client client = clientMapper.toEntity(clientDTO);
        client = clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    public Optional<ClientDTO> partialUpdate(ClientDTO clientDTO) {
        log.debug("Request to partially update Client : {}", clientDTO);
        return clientRepository
            .findById(clientDTO.getId())
            .map(existingClient -> {
                clientMapper.partialUpdate(existingClient, clientDTO);

                return existingClient;
            })
            .map(clientRepository::save)
            .map(clientMapper::toDto);
    }

    public List<ClientDTO> findAll() {
        log.debug("Request to get all Clients");
        return clientRepository.findAll().stream().map(clientMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Optional<ClientDTO> findOne(String id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id).map(clientMapper::toDto);
    }

    public void delete(String id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.findById(id).ifPresent(client -> {
            if(!(client.getTunnel() == null || client.getTunnel().getId().isEmpty()))
                tunnelService.delete(client.getTunnel().getId());
        });
        clientRepository.deleteById(id);
    }
}
