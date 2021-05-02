package edu.unipampa.laboratoriovirologia.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VacinaMapperTest {

    private VacinaMapper vacinaMapper;

    @BeforeEach
    public void setUp() {
        vacinaMapper = new VacinaMapperImpl();
    }
}
