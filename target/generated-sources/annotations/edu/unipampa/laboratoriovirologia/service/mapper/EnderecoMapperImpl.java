package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.Endereco;
import edu.unipampa.laboratoriovirologia.service.dto.EnderecoDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-11T12:24:05-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Ubuntu)"
)
@Component
public class EnderecoMapperImpl implements EnderecoMapper {

    @Override
    public Endereco toEntity(EnderecoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Endereco endereco = new Endereco();

        endereco.id( dto.getId() );
        endereco.setEndereco( dto.getEndereco() );
        endereco.setCep( dto.getCep() );
        endereco.setCidade( dto.getCidade() );
        endereco.setEstado( dto.getEstado() );
        endereco.setCoordenadasGps( dto.getCoordenadasGps() );

        return endereco;
    }

    @Override
    public EnderecoDTO toDto(Endereco entity) {
        if ( entity == null ) {
            return null;
        }

        EnderecoDTO enderecoDTO = new EnderecoDTO();

        enderecoDTO.setId( entity.getId() );
        enderecoDTO.setEndereco( entity.getEndereco() );
        enderecoDTO.setCep( entity.getCep() );
        enderecoDTO.setCidade( entity.getCidade() );
        enderecoDTO.setEstado( entity.getEstado() );
        enderecoDTO.setCoordenadasGps( entity.getCoordenadasGps() );

        return enderecoDTO;
    }

    @Override
    public List<Endereco> toEntity(List<EnderecoDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Endereco> list = new ArrayList<Endereco>( dtoList.size() );
        for ( EnderecoDTO enderecoDTO : dtoList ) {
            list.add( toEntity( enderecoDTO ) );
        }

        return list;
    }

    @Override
    public List<EnderecoDTO> toDto(List<Endereco> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<EnderecoDTO> list = new ArrayList<EnderecoDTO>( entityList.size() );
        for ( Endereco endereco : entityList ) {
            list.add( toDto( endereco ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Endereco entity, EnderecoDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getEndereco() != null ) {
            entity.setEndereco( dto.getEndereco() );
        }
        if ( dto.getCep() != null ) {
            entity.setCep( dto.getCep() );
        }
        if ( dto.getCidade() != null ) {
            entity.setCidade( dto.getCidade() );
        }
        if ( dto.getEstado() != null ) {
            entity.setEstado( dto.getEstado() );
        }
        if ( dto.getCoordenadasGps() != null ) {
            entity.setCoordenadasGps( dto.getCoordenadasGps() );
        }
    }

    @Override
    public EnderecoDTO toDtoEndereco(Endereco endereco) {
        if ( endereco == null ) {
            return null;
        }

        EnderecoDTO enderecoDTO = new EnderecoDTO();

        enderecoDTO.setId( endereco.getId() );
        enderecoDTO.setEndereco( endereco.getEndereco() );
        enderecoDTO.setCidade( endereco.getCidade() );

        return enderecoDTO;
    }
}
