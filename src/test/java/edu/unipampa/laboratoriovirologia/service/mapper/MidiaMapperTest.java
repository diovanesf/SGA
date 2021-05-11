package edu.unipampa.laboratoriovirologia.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MidiaMapperTest {

    private MidiaMapper midiaMapper;

    @BeforeEach
    public void setUp() {
        midiaMapper = new MidiaMapperImpl();
    }
}
