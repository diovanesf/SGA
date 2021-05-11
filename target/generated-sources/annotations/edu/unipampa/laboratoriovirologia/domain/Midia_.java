package edu.unipampa.laboratoriovirologia.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Midia.class)
public abstract class Midia_ {

	public static volatile SingularAttribute<Midia, byte[]> file;
	public static volatile SingularAttribute<Midia, String> nome;
	public static volatile SingularAttribute<Midia, Long> id;
	public static volatile SingularAttribute<Midia, Amostra> amostra;
	public static volatile SingularAttribute<Midia, String> fileContentType;
	public static volatile SingularAttribute<Midia, String> descricao;

	public static final String FILE = "file";
	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String AMOSTRA = "amostra";
	public static final String FILE_CONTENT_TYPE = "fileContentType";
	public static final String DESCRICAO = "descricao";

}

