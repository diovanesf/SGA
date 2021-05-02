package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.Midia;
import edu.unipampa.laboratoriovirologia.service.dto.MidiaDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-02T17:06:31-0300",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1200.v20200916-0645, environment: Java 11.0.11 (Ubuntu)"
)
@Component
public class MidiaMapperImpl implements MidiaMapper {

    @Autowired
    private AmostraMapper amostraMapper;

    @Override
    public void partialUpdate(Midia arg0, MidiaDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getId() != null ) {
            arg0.id( arg1.getId() );
        }
        if ( arg1.getNome() != null ) {
            arg0.setNome( arg1.getNome() );
        }
        if ( arg1.getDescricao() != null ) {
            arg0.setDescricao( arg1.getDescricao() );
        }
        byte[] file = arg1.getFile();
        if ( file != null ) {
            arg0.setFile( Arrays.copyOf( file, file.length ) );
        }
        if ( arg1.getFileContentType() != null ) {
            arg0.setFileContentType( arg1.getFileContentType() );
        }
        if ( arg1.getAmostra() != null ) {
            arg0.setAmostra( amostraMapper.toEntity( arg1.getAmostra() ) );
        }
    }

    @Override
    public List<MidiaDTO> toDto(List<Midia> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<MidiaDTO> list = new ArrayList<MidiaDTO>( arg0.size() );
        for ( Midia midia : arg0 ) {
            list.add( toDto( midia ) );
        }

        return list;
    }

    @Override
    public Midia toEntity(MidiaDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Midia midia = new Midia();

        midia.id( arg0.getId() );
        midia.setNome( arg0.getNome() );
        midia.setDescricao( arg0.getDescricao() );
        byte[] file = arg0.getFile();
        if ( file != null ) {
            midia.setFile( Arrays.copyOf( file, file.length ) );
        }
        midia.setFileContentType( arg0.getFileContentType() );
        midia.setAmostra( amostraMapper.toEntity( arg0.getAmostra() ) );

        return midia;
    }

    @Override
    public List<Midia> toEntity(List<MidiaDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Midia> list = new ArrayList<Midia>( arg0.size() );
        for ( MidiaDTO midiaDTO : arg0 ) {
            list.add( toEntity( midiaDTO ) );
        }

        return list;
    }

    @Override
    public MidiaDTO toDto(Midia s) {
        if ( s == null ) {
            return null;
        }

        MidiaDTO midiaDTO = new MidiaDTO();

        midiaDTO.setAmostra( amostraMapper.toDtoProtocolo( s.getAmostra() ) );
        midiaDTO.setId( s.getId() );
        midiaDTO.setNome( s.getNome() );
        midiaDTO.setDescricao( s.getDescricao() );
        byte[] file = s.getFile();
        if ( file != null ) {
            midiaDTO.setFile( Arrays.copyOf( file, file.length ) );
        }
        midiaDTO.setFileContentType( s.getFileContentType() );

        return midiaDTO;
    }
}
