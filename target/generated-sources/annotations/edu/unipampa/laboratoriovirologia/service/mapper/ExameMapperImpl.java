package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.Exame;
import edu.unipampa.laboratoriovirologia.service.dto.ExameDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-20T00:47:10-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (Ubuntu)"
)
@Component
public class ExameMapperImpl implements ExameMapper {

    @Autowired
    private AmostraMapper amostraMapper;

    @Override
    public Exame toEntity(ExameDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Exame exame = new Exame();

        exame.id( dto.getId() );
        exame.setNome( dto.getNome() );
        exame.setTipo( dto.getTipo() );
        exame.setResultado( dto.getResultado() );
        exame.setDataTeste( dto.getDataTeste() );
        exame.setDataLeitura( dto.getDataLeitura() );
        exame.setPreenchimentoEspelho( dto.getPreenchimentoEspelho() );
        exame.setObservacoes( dto.getObservacoes() );
        exame.setValor( dto.getValor() );
        exame.setAmostra( amostraMapper.toEntity( dto.getAmostra() ) );

        return exame;
    }

    @Override
    public List<Exame> toEntity(List<ExameDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Exame> list = new ArrayList<Exame>( dtoList.size() );
        for ( ExameDTO exameDTO : dtoList ) {
            list.add( toEntity( exameDTO ) );
        }

        return list;
    }

    @Override
    public List<ExameDTO> toDto(List<Exame> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ExameDTO> list = new ArrayList<ExameDTO>( entityList.size() );
        for ( Exame exame : entityList ) {
            list.add( toDto( exame ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Exame entity, ExameDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getNome() != null ) {
            entity.setNome( dto.getNome() );
        }
        if ( dto.getTipo() != null ) {
            entity.setTipo( dto.getTipo() );
        }
        if ( dto.getResultado() != null ) {
            entity.setResultado( dto.getResultado() );
        }
        if ( dto.getDataTeste() != null ) {
            entity.setDataTeste( dto.getDataTeste() );
        }
        if ( dto.getDataLeitura() != null ) {
            entity.setDataLeitura( dto.getDataLeitura() );
        }
        if ( dto.getPreenchimentoEspelho() != null ) {
            entity.setPreenchimentoEspelho( dto.getPreenchimentoEspelho() );
        }
        if ( dto.getObservacoes() != null ) {
            entity.setObservacoes( dto.getObservacoes() );
        }
        if ( dto.getValor() != null ) {
            entity.setValor( dto.getValor() );
        }
        if ( dto.getAmostra() != null ) {
            entity.setAmostra( amostraMapper.toEntity( dto.getAmostra() ) );
        }
    }

    @Override
    public ExameDTO toDto(Exame s) {
        if ( s == null ) {
            return null;
        }

        ExameDTO exameDTO = new ExameDTO();

        exameDTO.setAmostra( amostraMapper.toDtoProtocolo( s.getAmostra() ) );
        exameDTO.setId( s.getId() );
        exameDTO.setNome( s.getNome() );
        exameDTO.setTipo( s.getTipo() );
        exameDTO.setResultado( s.getResultado() );
        exameDTO.setDataTeste( s.getDataTeste() );
        exameDTO.setDataLeitura( s.getDataLeitura() );
        exameDTO.setPreenchimentoEspelho( s.getPreenchimentoEspelho() );
        exameDTO.setObservacoes( s.getObservacoes() );
        exameDTO.setValor( s.getValor() );

        return exameDTO;
    }
}
