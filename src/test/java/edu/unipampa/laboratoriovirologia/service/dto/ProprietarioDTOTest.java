package edu.unipampa.laboratoriovirologia.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import edu.unipampa.laboratoriovirologia.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProprietarioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProprietarioDTO.class);
        ProprietarioDTO proprietarioDTO1 = new ProprietarioDTO();
        proprietarioDTO1.setId(1L);
        ProprietarioDTO proprietarioDTO2 = new ProprietarioDTO();
        assertThat(proprietarioDTO1).isNotEqualTo(proprietarioDTO2);
        proprietarioDTO2.setId(proprietarioDTO1.getId());
        assertThat(proprietarioDTO1).isEqualTo(proprietarioDTO2);
        proprietarioDTO2.setId(2L);
        assertThat(proprietarioDTO1).isNotEqualTo(proprietarioDTO2);
        proprietarioDTO1.setId(null);
        assertThat(proprietarioDTO1).isNotEqualTo(proprietarioDTO2);
    }
}
