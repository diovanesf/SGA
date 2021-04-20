package edu.unipampa.laboratoriovirologia.domain;

import static org.assertj.core.api.Assertions.assertThat;

import edu.unipampa.laboratoriovirologia.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedicoveterinarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medicoveterinario.class);
        Medicoveterinario medicoveterinario1 = new Medicoveterinario();
        medicoveterinario1.setId(1L);
        Medicoveterinario medicoveterinario2 = new Medicoveterinario();
        medicoveterinario2.setId(medicoveterinario1.getId());
        assertThat(medicoveterinario1).isEqualTo(medicoveterinario2);
        medicoveterinario2.setId(2L);
        assertThat(medicoveterinario1).isNotEqualTo(medicoveterinario2);
        medicoveterinario1.setId(null);
        assertThat(medicoveterinario1).isNotEqualTo(medicoveterinario2);
    }
}
