package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.Medicoveterinario;
import edu.unipampa.laboratoriovirologia.service.dto.MedicoveterinarioDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-20T00:47:10-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (Ubuntu)"
)
@Component
public class MedicoveterinarioMapperImpl implements MedicoveterinarioMapper {

    @Override
    public Medicoveterinario toEntity(MedicoveterinarioDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Medicoveterinario medicoveterinario = new Medicoveterinario();

        medicoveterinario.id( dto.getId() );
        medicoveterinario.setNome( dto.getNome() );
        medicoveterinario.setTelefone( dto.getTelefone() );
        medicoveterinario.setEmail( dto.getEmail() );
        medicoveterinario.setCRMV( dto.getCRMV() );
        medicoveterinario.setEnviarLaudo( dto.getEnviarLaudo() );

        return medicoveterinario;
    }

    @Override
    public MedicoveterinarioDTO toDto(Medicoveterinario entity) {
        if ( entity == null ) {
            return null;
        }

        MedicoveterinarioDTO medicoveterinarioDTO = new MedicoveterinarioDTO();

        medicoveterinarioDTO.setId( entity.getId() );
        medicoveterinarioDTO.setNome( entity.getNome() );
        medicoveterinarioDTO.setTelefone( entity.getTelefone() );
        medicoveterinarioDTO.setEmail( entity.getEmail() );
        medicoveterinarioDTO.setCRMV( entity.getCRMV() );
        medicoveterinarioDTO.setEnviarLaudo( entity.getEnviarLaudo() );

        return medicoveterinarioDTO;
    }

    @Override
    public List<Medicoveterinario> toEntity(List<MedicoveterinarioDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Medicoveterinario> list = new ArrayList<Medicoveterinario>( dtoList.size() );
        for ( MedicoveterinarioDTO medicoveterinarioDTO : dtoList ) {
            list.add( toEntity( medicoveterinarioDTO ) );
        }

        return list;
    }

    @Override
    public List<MedicoveterinarioDTO> toDto(List<Medicoveterinario> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MedicoveterinarioDTO> list = new ArrayList<MedicoveterinarioDTO>( entityList.size() );
        for ( Medicoveterinario medicoveterinario : entityList ) {
            list.add( toDto( medicoveterinario ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Medicoveterinario entity, MedicoveterinarioDTO dto) {
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
        if ( dto.getCRMV() != null ) {
            entity.setCRMV( dto.getCRMV() );
        }
        if ( dto.getEnviarLaudo() != null ) {
            entity.setEnviarLaudo( dto.getEnviarLaudo() );
        }
    }

    @Override
    public MedicoveterinarioDTO toDtoNome(Medicoveterinario medicoveterinario) {
        if ( medicoveterinario == null ) {
            return null;
        }

        MedicoveterinarioDTO medicoveterinarioDTO = new MedicoveterinarioDTO();

        medicoveterinarioDTO.setId( medicoveterinario.getId() );
        medicoveterinarioDTO.setNome( medicoveterinario.getNome() );

        return medicoveterinarioDTO;
    }
}