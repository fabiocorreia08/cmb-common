package br.gov.cmb.common.ejb.builder.query;

public interface IQuery extends IJPQLBuilder{
	
	ISelect select(String... campos);

}
