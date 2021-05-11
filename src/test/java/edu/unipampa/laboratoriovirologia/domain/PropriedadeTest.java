package edu.unipampa.laboratoriovirologia.domain;

import static org.assertj.core.api.Assertions.assertThat;

import edu.unipampa.laboratoriovirologia.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PropriedadeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Propriedade.class);
        Propriedade propriedade1 = new Propriedade();
        propriedade1.setId(1L);
        Propriedade propriedade2 = new Propriedade();
        propriedade2.setId(propriedade1.getId());
        assertThat(propriedade1).isEqualTo(propriedade2);
        propriedade2.setId(2L);
        assertThat(propriedade1).isNotEqualTo(propriedade2);
        propriedade1.setId(null);
        assertThat(propriedade1).isNotEqualTo(propriedade2);
    }
}
