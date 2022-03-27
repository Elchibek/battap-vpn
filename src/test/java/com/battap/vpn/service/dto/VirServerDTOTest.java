package com.battap.vpn.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.battap.vpn.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VirServerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VirServerDTO.class);
        VirServerDTO virServerDTO1 = new VirServerDTO();
        virServerDTO1.setId("id1");
        VirServerDTO virServerDTO2 = new VirServerDTO();
        assertThat(virServerDTO1).isNotEqualTo(virServerDTO2);
        virServerDTO2.setId(virServerDTO1.getId());
        assertThat(virServerDTO1).isEqualTo(virServerDTO2);
        virServerDTO2.setId("id2");
        assertThat(virServerDTO1).isNotEqualTo(virServerDTO2);
        virServerDTO1.setId(null);
        assertThat(virServerDTO1).isNotEqualTo(virServerDTO2);
    }
}
