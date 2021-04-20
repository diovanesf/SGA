package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.Proprietario;
import edu.unipampa.laboratoriovirologia.service.dto.ProprietarioDTO;
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
public class ProprietarioMapperImpl implements ProprietarioMapper {

    @Autowired
    private EnderecoMapper enderecoMapper;
    @Autowired
    private PropriedadeMapper propriedadeMapper;

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
        proprietario.setEndereco( enderecoMapper.toEntity( dto.getEndereco() ) );
        proprietario.setPropriedade( propriedadeMapper.toEntity( dto.getPropriedade() ) );

        return proprietario;
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
        if ( dto.getEndereco() != null ) {
            entity.setEndereco( enderecoMapper.toEntity( dto.getEndereco() ) );
        }
        if ( dto.getPropriedade() != null ) {
            entity.setPropriedade( propriedadeMapper.toEntity( dto.getPropriedade() ) );
        }
    }

    @Override
    public ProprietarioDTO toDto(Proprietario s) {
        if ( s == null ) {
            return null;
        }

        ProprietarioDTO proprietarioDTO = new ProprietarioDTO();

        proprietarioDTO.setEndereco( enderecoMapper.toDtoEndereco( s.getEndereco() ) );
        proprietarioDTO.setPropriedade( propriedadeMapper.toDtoTipoPropriedade( s.getPropriedade() ) );
        proprietarioDTO.setId( s.getId() );
        proprietarioDTO.setNome( s.getNome() );
        proprietarioDTO.setTelefone( s.getTelefone() );
        proprietarioDTO.setEmail( s.getEmail() );
        proprietarioDTO.setEnviarLaudo( s.getEnviarLaudo() );

        return proprietarioDTO;
    }
}
