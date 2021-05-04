package edu.unipampa.laboratoriovirologia.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubamostraMapperTest {

    private SubamostraMapper subamostraMapper;

    @BeforeEach
    public void setUp() {
        subamostraMapper = new SubamostraMapperImpl();
    }
}
