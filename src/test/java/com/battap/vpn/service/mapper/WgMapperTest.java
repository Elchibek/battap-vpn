package com.battap.vpn.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WgMapperTest {

    private WgMapper wgMapper;

    @BeforeEach
    public void setUp() {
        wgMapper = new WgMapperImpl();
    }
}
