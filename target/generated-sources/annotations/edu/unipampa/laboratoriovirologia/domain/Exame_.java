package edu.unipampa.laboratoriovirologia.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Exame.class)
public abstract class Exame_ {

	public static volatile SingularAttribute<Exame, String> observacoes;
	public static volatile SingularAttribute<Exame, String> tipo;
	public static volatile SingularAttribute<Exame, String> preenchimentoEspelho;
	public static volatile SingularAttribute<Exame, String> resultado;
	public static volatile SingularAttribute<Exame, Subamostra> subamostra;
	public static volatile SingularAttribute<Exame, BigDecimal> valor;
	public static volatile SingularAttribute<Exame, LocalDate> dataLeitura;
	public static volatile SingularAttribute<Exame, String> nome;
	public static volatile SingularAttribute<Exame, Long> id;
	public static volatile SingularAttribute<Exame, LocalDate> dataTeste;
	public static volatile SingularAttribute<Exame, Amostra> amostra;

	public static final String OBSERVACOES = "observacoes";
	public static final String TIPO = "tipo";
	public static final String PREENCHIMENTO_ESPELHO = "preenchimentoEspelho";
	public static final String RESULTADO = "resultado";
	public static final String SUBAMOSTRA = "subamostra";
	public static final String VALOR = "valor";
	public static final String DATA_LEITURA = "dataLeitura";
	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String DATA_TESTE = "dataTeste";
	public static final String AMOSTRA = "amostra";

}

