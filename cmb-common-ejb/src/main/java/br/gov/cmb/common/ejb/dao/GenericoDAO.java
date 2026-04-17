package br.gov.cmb.common.ejb.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.gov.cmb.common.ejb.vo.ModeloVO;
import br.gov.cmb.common.exception.runtime.DAOException;
import br.gov.cmb.common.util.CollectionUtils;

public abstract class GenericoDAO<T extends Serializable, P> implements Serializable {

	private static final long serialVersionUID = -5875872916961003629L;

	protected static final String SELECT_ALL = "from %s o";
	protected static final String SELECT_ALL_ORDER_BY_ASC = "from %s o order by %s asc";
	protected static final String SELECT_ALL_ORDER_BY_DESC = "from %s o order by %s desc";
	protected static final String SELECT_ALL_POR_CAMPO = "from %s o where %s = :valor";

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<T> buscar() {
		String jpql = String.format(SELECT_ALL, getTipoClasse().getSimpleName());
		return getEntityManager().createQuery(jpql).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> buscarOrdenadoAsc(String campo) {
		String jpql = String.format(SELECT_ALL_ORDER_BY_ASC, getTipoClasse().getSimpleName(), campo);
		return getEntityManager().createQuery(jpql).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> buscarOrdenadoDescPor(String campo) {
		String jpql = String.format(SELECT_ALL_ORDER_BY_DESC, getTipoClasse().getSimpleName(), campo);
		return getEntityManager().createQuery(jpql).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> buscarResultadosPor(String campo, Object valor) {
		return criarQueryBuscarPor(campo, valor).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public T buscarPor(String campo, Object valor) {
		return (T) criarQueryBuscarPor(campo, valor).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
    public T buscaSeguraPor(String campo, Object valor) {
	    try {
	        return (T) criarQueryBuscarPor(campo, valor).getSingleResult();
	    } catch (NoResultException nre) {
	        return null;
	    }
    }

	private Query criarQueryBuscarPor(String campo, Object valor) {
		String jpql = String.format(SELECT_ALL_POR_CAMPO, getTipoClasse().getSimpleName(), campo);
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("valor", valor);
		return query;
	}
	
	@SuppressWarnings("unchecked")
	public T buscar(final P id) {
		return (T) getEntityManager().find(getTipoClasse(), id);
	}

	public T buscarDetached(final P id) {
		T entity = buscar(id);
		getEntityManager().detach(entity);
		return entity;
	}

	public T salvar(final T t) {
		try {
			getEntityManager().persist(t);
			return t;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public T atualizar(final T t) {
		try {
			return getEntityManager().merge(t);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void remover(T entity) {
		try {
			if (!getEntityManager().contains(entity)) {
				entity = getEntityManager().merge(entity);
			}

			getEntityManager().remove(entity);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void remover(final P id) {
		try {
			getEntityManager().remove(getEntityManager().getReference(getTipoClasse(), id));
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	public void refresh(T entity) {
		getEntityManager().refresh(entity);
	}

	protected void adicionarParametros(Query consulta, Map<String, Object> parametros) {
		if (parametros != null) {
			for (Map.Entry<String, Object> entry : parametros.entrySet()) {
				consulta.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}

	protected void adicionarParametros(Query consulta, Object[] parametros) {
		if (parametros != null) {
			for (int i = 1; i <= parametros.length; i++) {
				consulta.setParameter(i, parametros[i - 1]);
			}
		}
	}

	protected List<T> buscar(StringBuilder jpql, Map<String, Object> parametros) {
		Query query = criarConsulta(jpql);

		adicionarParametros(query, parametros);

		return buscar(query);
	}

	@SuppressWarnings("unchecked")
	protected List<T> buscar(StringBuilder jpql, Object... parametros) {
		return (List<T>) buscar(jpql, getTipoClasse(), parametros);
	}

	@SuppressWarnings("unchecked")
	protected <E> List<E> buscar(StringBuilder jpql, Class<E> retorno, Object... parametros) {
		Query query = adicionarParametrosNaQuery(jpql, parametros);
		return (List<E>) buscar(query, retorno);
	}

	@SuppressWarnings("unchecked")
	protected List<T> buscar(StringBuilder jpql, ModeloVO modeloVO) {
		return (List<T>) buscar(jpql, modeloVO, getTipoClasse());
	}

	@SuppressWarnings("unchecked")
	protected <E> List<E> buscar(StringBuilder jpql, ModeloVO modeloVO, Class<E> retorno) {
		Query query = criarConsulta(jpql);
		adicionarParametros(query, modeloVO.getParametros());
		return (List<E>) buscar(query, retorno);
	}

	@SuppressWarnings("unchecked")
	protected T buscarUmResultado(StringBuilder jpql, Object... parametros) {
		return (T) buscarUmResultado(jpql, getTipoClasse(), parametros);
	}

	protected <E> E buscarUmResultado(StringBuilder jpql, Class<E> retorno, Object... parametros) {
		Query query = adicionarParametrosNaQuery(jpql, parametros);
		return buscarUmResultado(query, retorno);
	}
	
	protected T buscaSeguraUmResultado(StringBuilder jpql, Object... parametros) {
	    try {
	        return this.buscarUmResultado(jpql, parametros);
	    } catch (NoResultException nre) {
	        return null;
	    }
	}
	
	protected <E> E buscaSeguraUmResultado(StringBuilder jpql, Class<E> retorno, Object... parametros) {
	    try {
            return this.buscarUmResultado(jpql, retorno, parametros);
        } catch (NoResultException nre) {
            return null;
        }
	}

	private Query adicionarParametrosNaQuery(StringBuilder jpql, Object... parametros) {
		Query query = criarConsulta(jpql);
		adicionarParametros(query, parametros);
		return query;

	}

	protected Query criarConsulta(StringBuilder jpql) {
		try {
			return getEntityManager().createQuery(jpql.toString());
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected List<T> buscar(Query query) {
		return (List<T>) buscar(query, getTipoClasse());
	}

	@SuppressWarnings("unchecked")
	protected <E> E buscar(Query query, Class<E> retorno) {
		try {
			return (E) query.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected T buscarUmResultado(Query query) {
		return (T) buscarUmResultado(query, getTipoClasse());
	}

	@SuppressWarnings("unchecked")
	protected <E> E buscarUmResultado(Query query, Class<E> retorno) {
		try {
			return (E) query.getSingleResult();
		} catch (NoResultException | NonUniqueResultException e) {
			throw e;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	protected boolean contemRegistro(StringBuilder jpql, Object... parametros) {
		return CollectionUtils.isNotEmpty(buscar(jpql, parametros));
	}

	protected <E> boolean contemRegistro(StringBuilder jpql, Class<E> retorno, Object... parametros) {
		return CollectionUtils.isNotEmpty(buscar(jpql, retorno, parametros));
	}

	protected boolean contemRegistro(StringBuilder jpql, Map<String, Object> parametros) {
		return CollectionUtils.isNotEmpty(buscar(jpql, parametros));
	}

	protected Class<?> getTipoClasse() {
		return (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
