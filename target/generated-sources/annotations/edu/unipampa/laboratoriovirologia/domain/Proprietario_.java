package edu.unipampa.laboratoriovirologia.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Proprietario.class)
public abstract class Proprietario_ {

	public static volatile SingularAttribute<Proprietario, String> telefone;
	public static volatile SingularAttribute<Proprietario, String> nome;
	public static volatile SingularAttribute<Proprietario, Propriedade> propriedade;
	public static volatile SingularAttribute<Proprietario, Long> id;
	public static volatile SingularAttribute<Proprietario, String> email;
	public static volatile SingularAttribute<Proprietario, Boolean> enviarLaudo;

	public static final String TELEFONE = "telefone";
	public static final String NOME = "nome";
	public static final String PROPRIEDADE = "propriedade";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String ENVIAR_LAUDO = "enviarLaudo";

}

