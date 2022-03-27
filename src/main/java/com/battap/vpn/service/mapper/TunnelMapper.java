package com.battap.vpn.service.mapper;

import com.battap.vpn.domain.Tunnel;
import com.battap.vpn.service.dto.TunnelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tunnel} and its DTO {@link TunnelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TunnelMapper extends EntityMapper<TunnelDTO, Tunnel> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TunnelDTO toDtoId(Tunnel tunnel);
}
