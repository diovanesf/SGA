package edu.unipampa.laboratoriovirologia.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Medicoveterinario.class)
public abstract class Medicoveterinario_ {

	public static volatile SingularAttribute<Medicoveterinario, String> telefone;
	public static volatile SingularAttribute<Medicoveterinario, String> CRMV;
	public static volatile SingularAttribute<Medicoveterinario, String> nome;
	public static volatile SingularAttribute<Medicoveterinario, Long> id;
	public static volatile SingularAttribute<Medicoveterinario, String> email;
	public static volatile SingularAttribute<Medicoveterinario, Boolean> enviarLaudo;

	public static final String TELEFONE = "telefone";
	public static final String C_RM_V = "CRMV";
	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String ENVIAR_LAUDO = "enviarLaudo";

}

