package edu.unipampa.laboratoriovirologia.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Amostra.class)
public abstract class Amostra_ {

	public static volatile SingularAttribute<Amostra, String> materialRecebido;
	public static volatile SingularAttribute<Amostra, String> tipo;
	public static volatile SingularAttribute<Amostra, String> situacao;
	public static volatile SingularAttribute<Amostra, String> pricipalSuspeita;
	public static volatile SingularAttribute<Amostra, Integer> numeroAnimais;
	public static volatile SetAttribute<Amostra, Subamostra> subamostras;
	public static volatile SingularAttribute<Amostra, Medicoveterinario> medicoveterinario;
	public static volatile SingularAttribute<Amostra, String> acometidos;
	public static volatile SingularAttribute<Amostra, String> protocolo;
	public static volatile SingularAttribute<Amostra, String> especie;
	public static volatile SingularAttribute<Amostra, BigDecimal> valorTotal;
	public static volatile SingularAttribute<Amostra, Long> id;
	public static volatile SingularAttribute<Amostra, String> acondicionamento;
	public static volatile SingularAttribute<Amostra, String> tipoMedVet;
	public static volatile SetAttribute<Amostra, Midia> midias;
	public static volatile SingularAttribute<Amostra, Vacina> vacina;
	public static volatile SingularAttribute<Amostra, Integer> numeroAmostras;
	public static volatile SingularAttribute<Amostra, Propriedade> propriedade;
	public static volatile SetAttribute<Amostra, User> users;
	public static volatile SetAttribute<Amostra, Exame> exames;
	public static volatile SingularAttribute<Amostra, String> condicaoMaterial;
	public static volatile SingularAttribute<Amostra, String> formaEnvio;
	public static volatile SingularAttribute<Amostra, LocalDate> dataInicial;
	public static volatile SingularAttribute<Amostra, String> tipoPagamento;
	public static volatile SingularAttribute<Amostra, String> status;

	public static final String MATERIAL_RECEBIDO = "materialRecebido";
	public static final String TIPO = "tipo";
	public static final String SITUACAO = "situacao";
	public static final String PRICIPAL_SUSPEITA = "pricipalSuspeita";
	public static final String NUMERO_ANIMAIS = "numeroAnimais";
	public static final String SUBAMOSTRAS = "subamostras";
	public static final String MEDICOVETERINARIO = "medicoveterinario";
	public static final String ACOMETIDOS = "acometidos";
	public static final String PROTOCOLO = "protocolo";
	public static final String ESPECIE = "especie";
	public static final String VALOR_TOTAL = "valorTotal";
	public static final String ID = "id";
	public static final String ACONDICIONAMENTO = "acondicionamento";
	public static final String TIPO_MED_VET = "tipoMedVet";
	public static final String MIDIAS = "midias";
	public static final String VACINA = "vacina";
	public static final String NUMERO_AMOSTRAS = "numeroAmostras";
	public static final String PROPRIEDADE = "propriedade";
	public static final String USERS = "users";
	public static final String EXAMES = "exames";
	public static final String CONDICAO_MATERIAL = "condicaoMaterial";
	public static final String FORMA_ENVIO = "formaEnvio";
	public static final String DATA_INICIAL = "dataInicial";
	public static final String TIPO_PAGAMENTO = "tipoPagamento";
	public static final String STATUS = "status";

}

