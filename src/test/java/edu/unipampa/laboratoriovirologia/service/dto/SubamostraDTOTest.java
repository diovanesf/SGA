package edu.unipampa.laboratoriovirologia.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import edu.unipampa.laboratoriovirologia.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubamostraDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubamostraDTO.class);
        SubamostraDTO subamostraDTO1 = new SubamostraDTO();
        subamostraDTO1.setId(1L);
        SubamostraDTO subamostraDTO2 = new SubamostraDTO();
        assertThat(subamostraDTO1).isNotEqualTo(subamostraDTO2);
        subamostraDTO2.setId(subamostraDTO1.getId());
        assertThat(subamostraDTO1).isEqualTo(subamostraDTO2);
        subamostraDTO2.setId(2L);
        assertThat(subamostraDTO1).isNotEqualTo(subamostraDTO2);
        subamostraDTO1.setId(null);
        assertThat(subamostraDTO1).isNotEqualTo(subamostraDTO2);
    }
}
