package com.battap.vpn.service.mapper;

import com.battap.vpn.domain.Wg;
import com.battap.vpn.service.dto.WgDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Wg} and its DTO {@link WgDTO}.
 */
@Mapper(componentModel = "spring", uses = { VirServerMapper.class })
public interface WgMapper extends EntityMapper<WgDTO, Wg> {
    @Mapping(target = "virServer", source = "virServer", qualifiedByName = "id")
    WgDTO toDto(Wg s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    WgDTO toDtoId(Wg wg);
}
