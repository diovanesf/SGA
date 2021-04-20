package edu.unipampa.laboratoriovirologia.service.mapper;

import edu.unipampa.laboratoriovirologia.domain.Amostra;
import edu.unipampa.laboratoriovirologia.domain.User;
import edu.unipampa.laboratoriovirologia.service.dto.AmostraDTO;
import edu.unipampa.laboratoriovirologia.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-20T03:17:37-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (Ubuntu)"
)
@Component
public class AmostraMapperImpl implements AmostraMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PropriedadeMapper propriedadeMapper;
    @Autowired
    private MedicoveterinarioMapper medicoveterinarioMapper;

    @Override
    public List<Amostra> toEntity(List<AmostraDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Amostra> list = new ArrayList<Amostra>( dtoList.size() );
        for ( AmostraDTO amostraDTO : dtoList ) {
            list.add( toEntity( amostraDTO ) );
        }

        return list;
    }

    @Override
    public List<AmostraDTO> toDto(List<Amostra> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AmostraDTO> list = new ArrayList<AmostraDTO>( entityList.size() );
        for ( Amostra amostra : entityList ) {
            list.add( toDto( amostra ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Amostra entity, AmostraDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getProtocolo() != null ) {
            entity.setProtocolo( dto.getProtocolo() );
        }
        if ( dto.getFormaEnvio() != null ) {
            entity.setFormaEnvio( dto.getFormaEnvio() );
        }
        if ( dto.getNumeroAmostras() != null ) {
            entity.setNumeroAmostras( dto.getNumeroAmostras() );
        }
        if ( dto.getEspecie() != null ) {
            entity.setEspecie( dto.getEspecie() );
        }
        if ( dto.getDataInicial() != null ) {
            entity.setDataInicial( dto.getDataInicial() );
        }
        if ( dto.getDataFinal() != null ) {
            entity.setDataFinal( dto.getDataFinal() );
        }
        if ( dto.getMaterialRecebido() != null ) {
            entity.setMaterialRecebido( dto.getMaterialRecebido() );
        }
        if ( dto.getAcondicionamento() != null ) {
            entity.setAcondicionamento( dto.getAcondicionamento() );
        }
        if ( dto.getCondicaoMaterial() != null ) {
            entity.setCondicaoMaterial( dto.getCondicaoMaterial() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getTipoMedVet() != null ) {
            entity.setTipoMedVet( dto.getTipoMedVet() );
        }
        if ( dto.getValorTotal() != null ) {
            entity.setValorTotal( dto.getValorTotal() );
        }
        if ( dto.getTipoPagamento() != null ) {
            entity.setTipoPagamento( dto.getTipoPagamento() );
        }
        if ( dto.getSituacao() != null ) {
            entity.setSituacao( dto.getSituacao() );
        }
        if ( entity.getUsers() != null ) {
            Set<User> set = userDTOSetToUserSet( dto.getUsers() );
            if ( set != null ) {
                entity.getUsers().clear();
                entity.getUsers().addAll( set );
            }
        }
        else {
            Set<User> set = userDTOSetToUserSet( dto.getUsers() );
            if ( set != null ) {
                entity.setUsers( set );
            }
        }
        if ( dto.getPropriedade() != null ) {
            entity.setPropriedade( propriedadeMapper.toEntity( dto.getPropriedade() ) );
        }
        if ( dto.getMedicoveterinario() != null ) {
            entity.setMedicoveterinario( medicoveterinarioMapper.toEntity( dto.getMedicoveterinario() ) );
        }
    }

    @Override
    public AmostraDTO toDto(Amostra s) {
        if ( s == null ) {
            return null;
        }

        AmostraDTO amostraDTO = new AmostraDTO();

        amostraDTO.setUsers( userMapper.toDtoLoginSet( s.getUsers() ) );
        amostraDTO.setPropriedade( propriedadeMapper.toDtoTipoPropriedade( s.getPropriedade() ) );
        amostraDTO.setMedicoveterinario( medicoveterinarioMapper.toDtoNome( s.getMedicoveterinario() ) );
        amostraDTO.setId( s.getId() );
        amostraDTO.setProtocolo( s.getProtocolo() );
        amostraDTO.setFormaEnvio( s.getFormaEnvio() );
        amostraDTO.setNumeroAmostras( s.getNumeroAmostras() );
        amostraDTO.setEspecie( s.getEspecie() );
        amostraDTO.setDataInicial( s.getDataInicial() );
        amostraDTO.setDataFinal( s.getDataFinal() );
        amostraDTO.setMaterialRecebido( s.getMaterialRecebido() );
        amostraDTO.setAcondicionamento( s.getAcondicionamento() );
        amostraDTO.setCondicaoMaterial( s.getCondicaoMaterial() );
        amostraDTO.setStatus( s.getStatus() );
        amostraDTO.setTipoMedVet( s.getTipoMedVet() );
        amostraDTO.setValorTotal( s.getValorTotal() );
        amostraDTO.setTipoPagamento( s.getTipoPagamento() );
        amostraDTO.setSituacao( s.getSituacao() );

        return amostraDTO;
    }

    @Override
    public Amostra toEntity(AmostraDTO amostraDTO) {
        if ( amostraDTO == null ) {
            return null;
        }

        Amostra amostra = new Amostra();

        amostra.id( amostraDTO.getId() );
        amostra.setProtocolo( amostraDTO.getProtocolo() );
        amostra.setFormaEnvio( amostraDTO.getFormaEnvio() );
        amostra.setNumeroAmostras( amostraDTO.getNumeroAmostras() );
        amostra.setEspecie( amostraDTO.getEspecie() );
        amostra.setDataInicial( amostraDTO.getDataInicial() );
        amostra.setDataFinal( amostraDTO.getDataFinal() );
        amostra.setMaterialRecebido( amostraDTO.getMaterialRecebido() );
        amostra.setAcondicionamento( amostraDTO.getAcondicionamento() );
        amostra.setCondicaoMaterial( amostraDTO.getCondicaoMaterial() );
        amostra.setStatus( amostraDTO.getStatus() );
        amostra.setTipoMedVet( amostraDTO.getTipoMedVet() );
        amostra.setValorTotal( amostraDTO.getValorTotal() );
        amostra.setTipoPagamento( amostraDTO.getTipoPagamento() );
        amostra.setSituacao( amostraDTO.getSituacao() );
        amostra.setUsers( userDTOSetToUserSet( amostraDTO.getUsers() ) );
        amostra.setPropriedade( propriedadeMapper.toEntity( amostraDTO.getPropriedade() ) );
        amostra.setMedicoveterinario( medicoveterinarioMapper.toEntity( amostraDTO.getMedicoveterinario() ) );

        return amostra;
    }

    @Override
    public AmostraDTO toDtoProtocolo(Amostra amostra) {
        if ( amostra == null ) {
            return null;
        }

        AmostraDTO amostraDTO = new AmostraDTO();

        amostraDTO.setId( amostra.getId() );
        amostraDTO.setProtocolo( amostra.getProtocolo() );

        return amostraDTO;
    }

    protected User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.getId() );
        user.setLogin( userDTO.getLogin() );

        return user;
    }

    protected Set<User> userDTOSetToUserSet(Set<UserDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<User> set1 = new HashSet<User>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( UserDTO userDTO : set ) {
            set1.add( userDTOToUser( userDTO ) );
        }

        return set1;
    }
}
