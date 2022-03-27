package com.battap.vpn.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TunnelMapperTest {

    private TunnelMapper tunnelMapper;

    @BeforeEach
    public void setUp() {
        tunnelMapper = new TunnelMapperImpl();
    }
}
