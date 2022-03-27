package com.battap.vpn.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.battap.vpn.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VirServerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VirServer.class);
        VirServer virServer1 = new VirServer();
        virServer1.setId("id1");
        VirServer virServer2 = new VirServer();
        virServer2.setId(virServer1.getId());
        assertThat(virServer1).isEqualTo(virServer2);
        virServer2.setId("id2");
        assertThat(virServer1).isNotEqualTo(virServer2);
        virServer1.setId(null);
        assertThat(virServer1).isNotEqualTo(virServer2);
    }
}
