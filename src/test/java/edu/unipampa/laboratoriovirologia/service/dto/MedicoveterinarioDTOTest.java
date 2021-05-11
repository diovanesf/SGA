package edu.unipampa.laboratoriovirologia.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import edu.unipampa.laboratoriovirologia.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedicoveterinarioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicoveterinarioDTO.class);
        MedicoveterinarioDTO medicoveterinarioDTO1 = new MedicoveterinarioDTO();
        medicoveterinarioDTO1.setId(1L);
        MedicoveterinarioDTO medicoveterinarioDTO2 = new MedicoveterinarioDTO();
        assertThat(medicoveterinarioDTO1).isNotEqualTo(medicoveterinarioDTO2);
        medicoveterinarioDTO2.setId(medicoveterinarioDTO1.getId());
        assertThat(medicoveterinarioDTO1).isEqualTo(medicoveterinarioDTO2);
        medicoveterinarioDTO2.setId(2L);
        assertThat(medicoveterinarioDTO1).isNotEqualTo(medicoveterinarioDTO2);
        medicoveterinarioDTO1.setId(null);
        assertThat(medicoveterinarioDTO1).isNotEqualTo(medicoveterinarioDTO2);
    }
}
