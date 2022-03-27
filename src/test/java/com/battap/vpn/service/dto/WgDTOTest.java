package com.battap.vpn.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.battap.vpn.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WgDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WgDTO.class);
        WgDTO wgDTO1 = new WgDTO();
        wgDTO1.setId("id1");
        WgDTO wgDTO2 = new WgDTO();
        assertThat(wgDTO1).isNotEqualTo(wgDTO2);
        wgDTO2.setId(wgDTO1.getId());
        assertThat(wgDTO1).isEqualTo(wgDTO2);
        wgDTO2.setId("id2");
        assertThat(wgDTO1).isNotEqualTo(wgDTO2);
        wgDTO1.setId(null);
        assertThat(wgDTO1).isNotEqualTo(wgDTO2);
    }
}
