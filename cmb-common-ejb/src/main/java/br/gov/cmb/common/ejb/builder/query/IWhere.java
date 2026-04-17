package br.gov.cmb.common.ejb.builder.query;

public interface IWhere extends IJPQLBuilder{
	
	IWhere or(String clausula);
	IWhere and(String clausula);
	IWhere and(String clausula,  boolean clasulaDinamica);
	IWhere or(String clausula,  boolean clasulaDinamica);
	
	IOrder order(String... campos);
}
