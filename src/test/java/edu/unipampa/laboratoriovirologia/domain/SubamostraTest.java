package edu.unipampa.laboratoriovirologia.domain;

import static org.assertj.core.api.Assertions.assertThat;

import edu.unipampa.laboratoriovirologia.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubamostraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subamostra.class);
        Subamostra subamostra1 = new Subamostra();
        subamostra1.setId(1L);
        Subamostra subamostra2 = new Subamostra();
        subamostra2.setId(subamostra1.getId());
        assertThat(subamostra1).isEqualTo(subamostra2);
        subamostra2.setId(2L);
        assertThat(subamostra1).isNotEqualTo(subamostra2);
        subamostra1.setId(null);
        assertThat(subamostra1).isNotEqualTo(subamostra2);
    }
}
