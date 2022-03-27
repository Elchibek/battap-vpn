package com.battap.vpn.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VirServerMapperTest {

    private VirServerMapper virServerMapper;

    @BeforeEach
    public void setUp() {
        virServerMapper = new VirServerMapperImpl();
    }
}
