package com.battap.vpn.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.battap.vpn.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TunnelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tunnel.class);
        Tunnel tunnel1 = new Tunnel();
        tunnel1.setId("id1");
        Tunnel tunnel2 = new Tunnel();
        tunnel2.setId(tunnel1.getId());
        assertThat(tunnel1).isEqualTo(tunnel2);
        tunnel2.setId("id2");
        assertThat(tunnel1).isNotEqualTo(tunnel2);
        tunnel1.setId(null);
        assertThat(tunnel1).isNotEqualTo(tunnel2);
    }
}
