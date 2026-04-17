package br.gov.cmb.common.ejb.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SimpleQueryBuilder {
	private EntityManager entityManager;
	private StringBuilder jpqlQuery;
	private Map<String, Object> params;
	
	private SimpleQueryBuilder(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.params = new HashMap<>();
		this.jpqlQuery = new StringBuilder();
	}
	
	public static SimpleQueryBuilder getInstance(EntityManager entityManager) {
		return new SimpleQueryBuilder(entityManager);
	}
	
	public SimpleQueryBuilder addJpql(String jpql) {
		this.jpqlQuery.append(jpql);
		
		return this;
	}
	
	public SimpleQueryBuilder addJpqlIfNotNULL(String jpql, String paramName, Object value) {
		if (value != null) {
			this.jpqlQuery.append(jpql);
			params.put(paramName, value);
		}
		
		return this;
	}	
	
	public SimpleQueryBuilder addJpqlIfNotNULL(String jpql, Object value) {
		if (value != null) {
			this.jpqlQuery.append(jpql);
		}
		
		return this;
	}	
	
	public Query createQuery() {
		Query query = entityManager.createQuery(jpqlQuery.toString());
		
		for (Entry<String, Object> elem : params.entrySet()) {
			query.setParameter(elem.getKey(), elem.getValue());
		}
		
		return query;
	}
}
