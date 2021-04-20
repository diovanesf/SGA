package edu.unipampa.laboratoriovirologia.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Propriedade.class)
public abstract class Propriedade_ {

	public static volatile SingularAttribute<Propriedade, String> observacoes;
	public static volatile SingularAttribute<Propriedade, String> tipoCriacao;
	public static volatile SingularAttribute<Propriedade, Endereco> endereco;
	public static volatile SingularAttribute<Propriedade, String> pricipalSuspeita;
	public static volatile SingularAttribute<Propriedade, Integer> numeroAnimais;
	public static volatile SingularAttribute<Propriedade, Long> id;
	public static volatile SingularAttribute<Propriedade, String> tipoPropriedade;
	public static volatile SingularAttribute<Propriedade, String> acometidos;

	public static final String OBSERVACOES = "observacoes";
	public static final String TIPO_CRIACAO = "tipoCriacao";
	public static final String ENDERECO = "endereco";
	public static final String PRICIPAL_SUSPEITA = "pricipalSuspeita";
	public static final String NUMERO_ANIMAIS = "numeroAnimais";
	public static final String ID = "id";
	public static final String TIPO_PROPRIEDADE = "tipoPropriedade";
	public static final String ACOMETIDOS = "acometidos";

}

