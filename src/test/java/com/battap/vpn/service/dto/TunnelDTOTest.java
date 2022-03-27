package com.battap.vpn.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.battap.vpn.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TunnelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TunnelDTO.class);
        TunnelDTO tunnelDTO1 = new TunnelDTO();
        tunnelDTO1.setId("id1");
        TunnelDTO tunnelDTO2 = new TunnelDTO();
        assertThat(tunnelDTO1).isNotEqualTo(tunnelDTO2);
        tunnelDTO2.setId(tunnelDTO1.getId());
        assertThat(tunnelDTO1).isEqualTo(tunnelDTO2);
        tunnelDTO2.setId("id2");
        assertThat(tunnelDTO1).isNotEqualTo(tunnelDTO2);
        tunnelDTO1.setId(null);
        assertThat(tunnelDTO1).isNotEqualTo(tunnelDTO2);
    }
}
