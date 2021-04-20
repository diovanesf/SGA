package edu.unipampa.laboratoriovirologia.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Endereco.class)
public abstract class Endereco_ {

	public static volatile SingularAttribute<Endereco, String> cidade;
	public static volatile SingularAttribute<Endereco, String> estado;
	public static volatile SingularAttribute<Endereco, String> endereco;
	public static volatile SingularAttribute<Endereco, String> coordenadasGps;
	public static volatile SingularAttribute<Endereco, Long> id;
	public static volatile SingularAttribute<Endereco, String> cep;

	public static final String CIDADE = "cidade";
	public static final String ESTADO = "estado";
	public static final String ENDERECO = "endereco";
	public static final String COORDENADAS_GPS = "coordenadasGps";
	public static final String ID = "id";
	public static final String CEP = "cep";

}

