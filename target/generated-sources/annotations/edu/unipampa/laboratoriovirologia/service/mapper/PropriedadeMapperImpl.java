package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.Propriedade;
import edu.unipampa.laboratoriovirologia.service.dto.PropriedadeDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-11T16:50:18-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Ubuntu)"
)
@Component
public class PropriedadeMapperImpl implements PropriedadeMapper {

    @Autowired
    private ProprietarioMapper proprietarioMapper;
    @Autowired
    private EnderecoMapper enderecoMapper;

    @Override
    public Propriedade toEntity(PropriedadeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Propriedade propriedade = new Propriedade();

        propriedade.id( dto.getId() );
        propriedade.setTipoPropriedade( dto.getTipoPropriedade() );
        propriedade.setTipoCriacao( dto.getTipoCriacao() );
        propriedade.setProprietario( proprietarioMapper.toEntity( dto.getProprietario() ) );
        propriedade.setEndereco( enderecoMapper.toEntity( dto.getEndereco() ) );

        return propriedade;
    }

    @Override
    public List<Propriedade> toEntity(List<PropriedadeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Propriedade> list = new ArrayList<Propriedade>( dtoList.size() );
        for ( PropriedadeDTO propriedadeDTO : dtoList ) {
            list.add( toEntity( propriedadeDTO ) );
        }

        return list;
    }

    @Override
    public List<PropriedadeDTO> toDto(List<Propriedade> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PropriedadeDTO> list = new ArrayList<PropriedadeDTO>( entityList.size() );
        for ( Propriedade propriedade : entityList ) {
            list.add( toDto( propriedade ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Propriedade entity, PropriedadeDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getTipoPropriedade() != null ) {
            entity.setTipoPropriedade( dto.getTipoPropriedade() );
        }
        if ( dto.getTipoCriacao() != null ) {
            entity.setTipoCriacao( dto.getTipoCriacao() );
        }
        if ( dto.getProprietario() != null ) {
            entity.setProprietario( proprietarioMapper.toEntity( dto.getProprietario() ) );
        }
        if ( dto.getEndereco() != null ) {
            entity.setEndereco( enderecoMapper.toEntity( dto.getEndereco() ) );
        }
    }

    @Override
    public PropriedadeDTO toDto(Propriedade s) {
        if ( s == null ) {
            return null;
        }

        PropriedadeDTO propriedadeDTO = new PropriedadeDTO();

        propriedadeDTO.setProprietario( proprietarioMapper.toDtoNome( s.getProprietario() ) );
        propriedadeDTO.setEndereco( enderecoMapper.toDtoEndereco( s.getEndereco() ) );
        propriedadeDTO.setId( s.getId() );
        propriedadeDTO.setTipoPropriedade( s.getTipoPropriedade() );
        propriedadeDTO.setTipoCriacao( s.getTipoCriacao() );

        return propriedadeDTO;
    }

    @Override
    public PropriedadeDTO toDtoTipoPropriedade(Propriedade propriedade) {
        if ( propriedade == null ) {
            return null;
        }

        PropriedadeDTO propriedadeDTO = new PropriedadeDTO();

        propriedadeDTO.setId( propriedade.getId() );
        propriedadeDTO.setTipoPropriedade( propriedade.getTipoPropriedade() );

        return propriedadeDTO;
    }
}
