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
    date = "2021-05-02T16:20:33-0300",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1200.v20200916-0645, environment: Java 11.0.11 (Ubuntu)"
)
@Component
public class PropriedadeMapperImpl implements PropriedadeMapper {

    @Autowired
    private ProprietarioMapper proprietarioMapper;
    @Autowired
    private EnderecoMapper enderecoMapper;

    @Override
    public void partialUpdate(Propriedade arg0, PropriedadeDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getEndereco() != null ) {
            arg0.setEndereco( enderecoMapper.toEntity( arg1.getEndereco() ) );
        }
        if ( arg1.getId() != null ) {
            arg0.setId( arg1.getId() );
        }
        if ( arg1.getProprietario() != null ) {
            arg0.setProprietario( proprietarioMapper.toEntity( arg1.getProprietario() ) );
        }
        if ( arg1.getTipoCriacao() != null ) {
            arg0.tipoCriacao( arg1.getTipoCriacao() );
        }
        if ( arg1.getTipoPropriedade() != null ) {
            arg0.tipoPropriedade( arg1.getTipoPropriedade() );
        }
    }

    @Override
    public List<PropriedadeDTO> toDto(List<Propriedade> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<PropriedadeDTO> list = new ArrayList<PropriedadeDTO>( arg0.size() );
        for ( Propriedade propriedade : arg0 ) {
            list.add( toDto( propriedade ) );
        }

        return list;
    }

    @Override
    public Propriedade toEntity(PropriedadeDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Propriedade propriedade = new Propriedade();

        propriedade.setEndereco( enderecoMapper.toEntity( arg0.getEndereco() ) );
        propriedade.setId( arg0.getId() );
        propriedade.setProprietario( proprietarioMapper.toEntity( arg0.getProprietario() ) );
        propriedade.tipoCriacao( arg0.getTipoCriacao() );
        propriedade.tipoPropriedade( arg0.getTipoPropriedade() );

        return propriedade;
    }

    @Override
    public List<Propriedade> toEntity(List<PropriedadeDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Propriedade> list = new ArrayList<Propriedade>( arg0.size() );
        for ( PropriedadeDTO propriedadeDTO : arg0 ) {
            list.add( toEntity( propriedadeDTO ) );
        }

        return list;
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
        propriedadeDTO.setTipoCriacao( s.getTipoCriacao() );
        propriedadeDTO.setTipoPropriedade( s.getTipoPropriedade() );

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
