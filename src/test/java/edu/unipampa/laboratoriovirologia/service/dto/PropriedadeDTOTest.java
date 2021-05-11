package edu.unipampa.laboratoriovirologia.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import edu.unipampa.laboratoriovirologia.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PropriedadeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropriedadeDTO.class);
        PropriedadeDTO propriedadeDTO1 = new PropriedadeDTO();
        propriedadeDTO1.setId(1L);
        PropriedadeDTO propriedadeDTO2 = new PropriedadeDTO();
        assertThat(propriedadeDTO1).isNotEqualTo(propriedadeDTO2);
        propriedadeDTO2.setId(propriedadeDTO1.getId());
        assertThat(propriedadeDTO1).isEqualTo(propriedadeDTO2);
        propriedadeDTO2.setId(2L);
        assertThat(propriedadeDTO1).isNotEqualTo(propriedadeDTO2);
        propriedadeDTO1.setId(null);
        assertThat(propriedadeDTO1).isNotEqualTo(propriedadeDTO2);
    }
}
