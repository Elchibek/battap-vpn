package com.battap.vpn.service.mapper;

import com.battap.vpn.domain.VirServer;
import com.battap.vpn.service.dto.VirServerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VirServer} and its DTO {@link VirServerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VirServerMapper extends EntityMapper<VirServerDTO, VirServer> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VirServerDTO toDtoId(VirServer virServer);
}
