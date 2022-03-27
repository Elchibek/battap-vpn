package com.battap.vpn.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.battap.vpn.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WgTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wg.class);
        Wg wg1 = new Wg();
        wg1.setId("id1");
        Wg wg2 = new Wg();
        wg2.setId(wg1.getId());
        assertThat(wg1).isEqualTo(wg2);
        wg2.setId("id2");
        assertThat(wg1).isNotEqualTo(wg2);
        wg1.setId(null);
        assertThat(wg1).isNotEqualTo(wg2);
    }
}
