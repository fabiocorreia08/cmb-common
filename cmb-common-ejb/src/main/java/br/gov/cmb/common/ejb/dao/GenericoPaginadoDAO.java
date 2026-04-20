package br.gov.cmb.common.ejb.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import br.gov.cmb.common.ejb.paginacao.Pagina;
import br.gov.cmb.common.exception.runtime.DAOException;


public abstract class GenericoPaginadoDAO<T extends Serializable, P> extends
        GenericoDAO<T, P> {

    private static final long serialVersionUID = 1L;
    
	private static final String FETCH = "FETCH";
    private static final String SELECT = "SELECT";
    private static final String FROM = "FROM";
    private static final String COUNT = "SELECT count(1) ";
    private static final String DISTINCT = "DISTINCT";
    private static final String SELECT_COUNT = "SELECT COUNT ( %s) ";
    private static final String GROUP_BY = "GROUP BY";
    private static final String ORDER_BY = "ORDER BY";
    private static final String ORDER_BY_CLAUSE = "ORDER BY %s %s";

    public Pagina<T> buscar(Pagina<T> pagina) {
        String jpql = String.format(SELECT_ALL, getTipoClasse().getSimpleName());
        return buscar(pagina, new StringBuilder(jpql));
    }

    protected Pagina<T> buscar(Pagina<T> pagina, StringBuilder jpql) {
        return buscar(pagina, jpql, pagina.getParametros(), null);
    }

    protected Pagina<T> buscar(Pagina<T> pagina, StringBuilder jpql, String countField) {
        return buscar(pagina, jpql, pagina.getParametros(), countField);
    }

    protected Pagina<T> buscar(Pagina<T> pagina, StringBuilder jpql, Map<String, Object> parametros, String countField) {
        Query consultaTotalDeRegistro = criarConsultaTotalDeRegistro(jpql, countField);
        Query consultaPrincipal = criarConsultaComOrdenacao(pagina, jpql);

        pagina.setTotalDeRegistros(buscarTotalRegistro(consultaTotalDeRegistro, parametros, countField).intValue());
        pagina.setRegistros(buscarRegistrosPaginado(consultaPrincipal, pagina, parametros));
        return pagina;
    }

    protected Query criarConsultaComOrdenacao(Pagina<T> pagina, StringBuilder jpql) {
        if (pagina.possuiOrdenacao()) {
            removerOrderBy(jpql);
            String ordenacao = String.format(ORDER_BY_CLAUSE, pagina.getCampoOrdenacao(), pagina.getOrdem());
            jpql.append(ordenacao);
        }

        return criarConsulta(jpql);
    }

    protected void removerOrderBy(StringBuilder jpql) {
        if (jpql.toString().toUpperCase().contains(ORDER_BY)) {
            jpql.delete(jpql.toString().toUpperCase().lastIndexOf(ORDER_BY), jpql.length());
        }
    }

    private Long buscarTotalRegistro(Query consultaTotalDeRegistro, Map<String, Object> parametros, String countField) {
        consultaTotalDeRegistro.setMaxResults(1);
        adicionarParametros(consultaTotalDeRegistro, parametros);
        return (Long) consultaTotalDeRegistro.getSingleResult();
    }

    private List<T> buscarRegistrosPaginado(Query consultaPrincipal, Pagina<T> pagina, Map<String, Object> parametros) {
        consultaPrincipal
                .setMaxResults(pagina.getTamanho())
                .setFirstResult(pagina.getPrimeiroRegistro());

        adicionarParametros(consultaPrincipal, parametros);

        return buscar(consultaPrincipal);
    }

    private Query criarConsultaTotalDeRegistro(StringBuilder query, String countField) {
        StringBuilder jpql = new StringBuilder(query);
        removerOrderBy(jpql);
        if (jpql.toString().toUpperCase().contains(GROUP_BY)) {
            throw new DAOException("Não pode usar GROUP BY");
        }
        jpql = adicionarCount(jpql, countField);
        jpql = removerFetchs(jpql);
        return criarConsulta(jpql);
    }

    private StringBuilder removerFetchs(StringBuilder jpql) {
        return new StringBuilder(jpql.toString().replace(FETCH, ""));
    }

    private StringBuilder adicionarCount(StringBuilder jpql, String countField) {
        if (jpql.toString().toUpperCase().contains(DISTINCT)) {
            String retorno = jpql.substring(getLastIndexDistinct(jpql), getIndexFrom(jpql));
            jpql.replace(getIndexSelect(jpql), getIndexFrom(jpql), SELECT_COUNT);
            jpql = new StringBuilder(String.format(jpql.toString(), retorno));
        } else {
            jpql.replace(getIndexSelect(jpql), getIndexFrom(jpql), COUNT);
        }
        
        if (countField != null) {
            jpql.replace(getIndexSelect(jpql), getIndexFrom(jpql), SELECT_COUNT);
            jpql = new StringBuilder(String.format(jpql.toString(), countField));
        } 
        
        return jpql;
    }

    private int getLastIndexDistinct(StringBuilder jpql) {
        return jpql.toString().toUpperCase().lastIndexOf(DISTINCT);
    }

    private int getIndexFrom(StringBuilder jpql) {
        return jpql.toString().toUpperCase().indexOf(FROM);
    }

    private int getIndexSelect(StringBuilder jpql) {
        int indexDoSelect = jpql.toString().toUpperCase().indexOf(SELECT);

        if (indexDoSelect < 0) {
            indexDoSelect = 0;
        }

        return indexDoSelect;
    }

}
