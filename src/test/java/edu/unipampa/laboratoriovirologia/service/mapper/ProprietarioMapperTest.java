package edu.unipampa.laboratoriovirologia.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProprietarioMapperTest {

    private ProprietarioMapper proprietarioMapper;

    @BeforeEach
    public void setUp() {
        proprietarioMapper = new ProprietarioMapperImpl();
    }
}
