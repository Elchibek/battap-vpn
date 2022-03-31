package com.battap.vpn.service;

import com.battap.vpn.domain.VirServer;
import com.battap.vpn.domain.Wg;
import com.battap.vpn.repository.WgRepository;
import com.battap.vpn.service.crypto.GenKey;
import com.battap.vpn.service.dto.ClientDTO;
import com.battap.vpn.service.dto.TunnelDTO;
import com.battap.vpn.service.dto.WgDTO;
import com.battap.vpn.service.mapper.WgMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Wg}.
 */
@Service
public class WgService {

    private final Logger log = LoggerFactory.getLogger(WgService.class);

    private final WgRepository wgRepository;

    private final WgMapper wgMapper;

    private final ClientService clientService;

    public WgService(WgRepository wgRepository, WgMapper wgMapper, ClientService clientService) {
        this.wgRepository = wgRepository;
        this.wgMapper = wgMapper;
        this.clientService = clientService;
    }

    public WgDTO save(WgDTO wgDTO, Boolean isUpdate) {
        log.debug("Request to save Wg : {}", wgDTO);

        Wg wg = wgMapper.toEntity(wgDTO);

        if(wg.getName().isEmpty())
            wg.setName("Client: " + wgRepository.count() + 1);

        if(wg.getListenPort() == null)
            wg.setListenPort(51820);

        // ( 10.0.0.0 до 10.0.0.255 )
        if(wgDTO.getAddress().isEmpty())
            wg.setAddress("10.0.0.1/24");

        if(wg.getMtu() == null)
            wg.setMtu(0);

        if(wg.getPostUp().isEmpty())
            wg.setPostUp("iptables -A FORWARD -i %i -j ACCEPT; iptables -t nat -A POSTROUTING -o eth0 -j MASQUERADE");

        if(wg.getPostDown().isEmpty())
            wg.setPostDown("iptables -D FORWARD -i %i -j ACCEPT; iptables -t nat -D POSTROUTING -o eth0 -j MASQUERADE");

        if(!isUpdate){
            GenKey genKey = new GenKey();
            String privateKey = genKey.getPrivateKey().toBase64();
            String pubKey = genKey.getPublicKey().toBase64();
            wg.setPrivateKey(privateKey);
            wg.setPublicKey(pubKey);
            
        } else {
            getWgConfig(wgDTO.getId());
        }
        wg = wgRepository.save(wg);
        return wgMapper.toDto(wg);
    }

    public String getWgConfig(String id) {
        WgDTO wg = wgRepository.findById(id).map(wgMapper::toDto).get();

        String conf = ("[Interface]" + 
        "\nPrivateKey = " + wg.getPrivateKey() +
        "\nAddress = " + wg.getAddress() +
        "\nMTU = " + wg.getMtu() +
        "\nListenPort = " + wg.getListenPort() +
        "\nPostUp = " + wg.getPostUp() +
        "\nPostDown = " + wg.getPostDown());

        for (ClientDTO clientDTO : clientService.findAll())
            if(clientDTO.getWg().getId().equals(id)) 
                conf.concat("\n\n[Peer]"
                + "\nPublicKey = " + clientDTO.getTunnel().getClientPubKey()
                + "\nAllowedIPs = " + clientDTO.getTunnel().getAllowedIPs());

        return null;
    }

    public Optional<WgDTO> partialUpdate(WgDTO wgDTO) {
        log.debug("Request to partially update Wg : {}", wgDTO);

        return wgRepository
            .findById(wgDTO.getId())
            .map(existingWg -> {
                wgMapper.partialUpdate(existingWg, wgDTO);
                return existingWg;
            })
            .map(wgRepository::save)
            .map(wgMapper::toDto);
    }

    public Page<WgDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Wgs");
        // ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny()
        // .withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
        // .withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        // Wg wg = new Wg();
        // wgRepository.findAll(Example.of(wg, customExampleMatcher));

        // Wg wgs = new Wg();
        // VirServer vi =  new VirServer();
        // vi.setId(wg.getVirServer().getId());
        // wgs.setVirServer(vi);
        // List<Wg> list = wgRepository.findAll(Example.of(wgs));

        return wgRepository.findAll(pageable).map(wgMapper::toDto);
    }

    public Optional<WgDTO> findOne(String id) {
        log.debug("Request to get Wg : {}", id);
        return wgRepository.findById(id).map(wgMapper::toDto);
    }

    public void delete(String id) {
        log.debug("Request to delete Wg : {}", id);
        wgRepository.deleteById(id);
    }
}
