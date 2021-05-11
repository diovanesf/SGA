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
    date = "2021-05-11T12:24:05-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Ubuntu)"
)
@Component
public class MidiaMapperImpl implements MidiaMapper {

    @Autowired
    private AmostraMapper amostraMapper;

    @Override
    public Midia toEntity(MidiaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Midia midia = new Midia();

        midia.id( dto.getId() );
        midia.setNome( dto.getNome() );
        midia.setDescricao( dto.getDescricao() );
        byte[] file = dto.getFile();
        if ( file != null ) {
            midia.setFile( Arrays.copyOf( file, file.length ) );
        }
        midia.setFileContentType( dto.getFileContentType() );
        midia.setAmostra( amostraMapper.toEntity( dto.getAmostra() ) );

        return midia;
    }

    @Override
    public List<Midia> toEntity(List<MidiaDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Midia> list = new ArrayList<Midia>( dtoList.size() );
        for ( MidiaDTO midiaDTO : dtoList ) {
            list.add( toEntity( midiaDTO ) );
        }

        return list;
    }

    @Override
    public List<MidiaDTO> toDto(List<Midia> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MidiaDTO> list = new ArrayList<MidiaDTO>( entityList.size() );
        for ( Midia midia : entityList ) {
            list.add( toDto( midia ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Midia entity, MidiaDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getNome() != null ) {
            entity.setNome( dto.getNome() );
        }
        if ( dto.getDescricao() != null ) {
            entity.setDescricao( dto.getDescricao() );
        }
        byte[] file = dto.getFile();
        if ( file != null ) {
            entity.setFile( Arrays.copyOf( file, file.length ) );
        }
        if ( dto.getFileContentType() != null ) {
            entity.setFileContentType( dto.getFileContentType() );
        }
        if ( dto.getAmostra() != null ) {
            entity.setAmostra( amostraMapper.toEntity( dto.getAmostra() ) );
        }
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
