package edu.unipampa.laboratoriovirologia.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import edu.unipampa.laboratoriovirologia.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MidiaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MidiaDTO.class);
        MidiaDTO midiaDTO1 = new MidiaDTO();
        midiaDTO1.setId(1L);
        MidiaDTO midiaDTO2 = new MidiaDTO();
        assertThat(midiaDTO1).isNotEqualTo(midiaDTO2);
        midiaDTO2.setId(midiaDTO1.getId());
        assertThat(midiaDTO1).isEqualTo(midiaDTO2);
        midiaDTO2.setId(2L);
        assertThat(midiaDTO1).isNotEqualTo(midiaDTO2);
        midiaDTO1.setId(null);
        assertThat(midiaDTO1).isNotEqualTo(midiaDTO2);
    }
}
