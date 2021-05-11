package edu.unipampa.laboratoriovirologia.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PropriedadeMapperTest {

    private PropriedadeMapper propriedadeMapper;

    @BeforeEach
    public void setUp() {
        propriedadeMapper = new PropriedadeMapperImpl();
    }
}
