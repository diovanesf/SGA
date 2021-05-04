package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.Proprietario;
import edu.unipampa.laboratoriovirologia.service.dto.ProprietarioDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-03T21:07:03-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Ubuntu)"
)
@Component
public class ProprietarioMapperImpl implements ProprietarioMapper {

    @Override
    public Proprietario toEntity(ProprietarioDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Proprietario proprietario = new Proprietario();

        proprietario.id( dto.getId() );
        proprietario.setNome( dto.getNome() );
        proprietario.setTelefone( dto.getTelefone() );
        proprietario.setEmail( dto.getEmail() );
        proprietario.setEnviarLaudo( dto.getEnviarLaudo() );

        return proprietario;
    }

    @Override
    public ProprietarioDTO toDto(Proprietario entity) {
        if ( entity == null ) {
            return null;
        }

        ProprietarioDTO proprietarioDTO = new ProprietarioDTO();

        proprietarioDTO.setId( entity.getId() );
        proprietarioDTO.setNome( entity.getNome() );
        proprietarioDTO.setTelefone( entity.getTelefone() );
        proprietarioDTO.setEmail( entity.getEmail() );
        proprietarioDTO.setEnviarLaudo( entity.getEnviarLaudo() );

        return proprietarioDTO;
    }

    @Override
    public List<Proprietario> toEntity(List<ProprietarioDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Proprietario> list = new ArrayList<Proprietario>( dtoList.size() );
        for ( ProprietarioDTO proprietarioDTO : dtoList ) {
            list.add( toEntity( proprietarioDTO ) );
        }

        return list;
    }

    @Override
    public List<ProprietarioDTO> toDto(List<Proprietario> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProprietarioDTO> list = new ArrayList<ProprietarioDTO>( entityList.size() );
        for ( Proprietario proprietario : entityList ) {
            list.add( toDto( proprietario ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Proprietario entity, ProprietarioDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getNome() != null ) {
            entity.setNome( dto.getNome() );
        }
        if ( dto.getTelefone() != null ) {
            entity.setTelefone( dto.getTelefone() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getEnviarLaudo() != null ) {
            entity.setEnviarLaudo( dto.getEnviarLaudo() );
        }
    }

    @Override
    public ProprietarioDTO toDtoNome(Proprietario proprietario) {
        if ( proprietario == null ) {
            return null;
        }

        ProprietarioDTO proprietarioDTO = new ProprietarioDTO();

        proprietarioDTO.setId( proprietario.getId() );
        proprietarioDTO.setNome( proprietario.getNome() );

        return proprietarioDTO;
    }
}
