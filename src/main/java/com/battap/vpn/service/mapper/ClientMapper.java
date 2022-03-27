package com.battap.vpn.service.mapper;

import com.battap.vpn.domain.Client;
import com.battap.vpn.service.dto.ClientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring", uses = { TunnelMapper.class, WgMapper.class })
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
    @Mapping(target = "tunnel", source = "tunnel", qualifiedByName = "id")
    @Mapping(target = "wg", source = "wg", qualifiedByName = "id")
    ClientDTO toDto(Client s);
}
