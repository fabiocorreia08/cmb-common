package br.gov.cmb.common.ejb.builder.query;


public interface ISelect extends IJPQLBuilder{
	
	IFrom from(Class<?> entidade, String alias);

}
