package edu.unipampa.laboratoriovirologia.domain.enumeration;

public enum TipoVirus {
    BVDV("BVDV"),
    IBR("IBR"),
    EHV("EHV"),
    EAV("EAV"),
    LEB("LEB"),
    CDV("CDV"),
    AIE("AIE"),
    FCM("FCM"),
    BOHV_5("BOHV_5"),
    ORFV("ORFV"),
    FIV_FELV("FIV_FELV"),
    RABV("RABV"),
    INFLUENZA_EQUINA("INFLUENZA_EQUINA"),
    CPV("CPV"),
    BRSV("BRSV"),
    CORONAVIRUS("CORONAVIRUS"),
    ROTAVIRUS("ROTAVIRUS");

    private final String valor;

    TipoVirus(String valorOpcao){
        valor = valorOpcao;
    }
    public String getValor(){
        return valor;
    }
}
