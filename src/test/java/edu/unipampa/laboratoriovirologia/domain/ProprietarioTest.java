package edu.unipampa.laboratoriovirologia.domain;

import static org.assertj.core.api.Assertions.assertThat;

import edu.unipampa.laboratoriovirologia.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProprietarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proprietario.class);
        Proprietario proprietario1 = new Proprietario();
        proprietario1.setId(1L);
        Proprietario proprietario2 = new Proprietario();
        proprietario2.setId(proprietario1.getId());
        assertThat(proprietario1).isEqualTo(proprietario2);
        proprietario2.setId(2L);
        assertThat(proprietario1).isNotEqualTo(proprietario2);
        proprietario1.setId(null);
        assertThat(proprietario1).isNotEqualTo(proprietario2);
    }
}
