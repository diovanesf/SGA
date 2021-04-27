package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.Endereco;
import edu.unipampa.laboratoriovirologia.service.dto.EnderecoDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-27T04:41:14-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (Ubuntu)"
)
@Component
public class EnderecoMapperImpl implements EnderecoMapper {

    @Override
    public Endereco toEntity(EnderecoDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Endereco endereco = new Endereco();

        endereco.id( arg0.getId() );
        endereco.setEndereco( arg0.getEndereco() );
        endereco.setCep( arg0.getCep() );
        endereco.setCidade( arg0.getCidade() );
        endereco.setEstado( arg0.getEstado() );
        endereco.setCoordenadasGps( arg0.getCoordenadasGps() );

        return endereco;
    }

    @Override
    public EnderecoDTO toDto(Endereco arg0) {
        if ( arg0 == null ) {
            return null;
        }

        EnderecoDTO enderecoDTO = new EnderecoDTO();

        enderecoDTO.setId( arg0.getId() );
        enderecoDTO.setEndereco( arg0.getEndereco() );
        enderecoDTO.setCep( arg0.getCep() );
        enderecoDTO.setCidade( arg0.getCidade() );
        enderecoDTO.setEstado( arg0.getEstado() );
        enderecoDTO.setCoordenadasGps( arg0.getCoordenadasGps() );

        return enderecoDTO;
    }

    @Override
    public List<Endereco> toEntity(List<EnderecoDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Endereco> list = new ArrayList<Endereco>( arg0.size() );
        for ( EnderecoDTO enderecoDTO : arg0 ) {
            list.add( toEntity( enderecoDTO ) );
        }

        return list;
    }

    @Override
    public List<EnderecoDTO> toDto(List<Endereco> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<EnderecoDTO> list = new ArrayList<EnderecoDTO>( arg0.size() );
        for ( Endereco endereco : arg0 ) {
            list.add( toDto( endereco ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Endereco arg0, EnderecoDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getId() != null ) {
            arg0.id( arg1.getId() );
        }
        if ( arg1.getEndereco() != null ) {
            arg0.setEndereco( arg1.getEndereco() );
        }
        if ( arg1.getCep() != null ) {
            arg0.setCep( arg1.getCep() );
        }
        if ( arg1.getCidade() != null ) {
            arg0.setCidade( arg1.getCidade() );
        }
        if ( arg1.getEstado() != null ) {
            arg0.setEstado( arg1.getEstado() );
        }
        if ( arg1.getCoordenadasGps() != null ) {
            arg0.setCoordenadasGps( arg1.getCoordenadasGps() );
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
