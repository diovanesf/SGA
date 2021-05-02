package edu.unipampa.laboratoriovirologia.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Propriedade.class)
public abstract class Propriedade_ {

	public static volatile SingularAttribute<Propriedade, String> tipoCriacao;
	public static volatile SingularAttribute<Propriedade, Endereco> endereco;
	public static volatile SingularAttribute<Propriedade, Proprietario> proprietario;
	public static volatile SingularAttribute<Propriedade, Long> id;
	public static volatile SingularAttribute<Propriedade, String> tipoPropriedade;

	public static final String TIPO_CRIACAO = "tipoCriacao";
	public static final String ENDERECO = "endereco";
	public static final String PROPRIETARIO = "proprietario";
	public static final String ID = "id";
	public static final String TIPO_PROPRIEDADE = "tipoPropriedade";

}

