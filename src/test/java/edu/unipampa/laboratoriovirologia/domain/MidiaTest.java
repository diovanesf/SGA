package edu.unipampa.laboratoriovirologia.domain;

import static org.assertj.core.api.Assertions.assertThat;

import edu.unipampa.laboratoriovirologia.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MidiaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Midia.class);
        Midia midia1 = new Midia();
        midia1.setId(1L);
        Midia midia2 = new Midia();
        midia2.setId(midia1.getId());
        assertThat(midia1).isEqualTo(midia2);
        midia2.setId(2L);
        assertThat(midia1).isNotEqualTo(midia2);
        midia1.setId(null);
        assertThat(midia1).isNotEqualTo(midia2);
    }
}
