package br.gov.cmb.common.ejb.builder;

import java.util.ArrayList;
import java.util.List;

import br.gov.cmb.common.ejb.builder.query.Clausula;
import br.gov.cmb.common.ejb.builder.query.IFrom;
import br.gov.cmb.common.ejb.builder.query.IOrder;
import br.gov.cmb.common.ejb.builder.query.IQuery;
import br.gov.cmb.common.ejb.builder.query.ISelect;
import br.gov.cmb.common.ejb.builder.query.IWhere;
import br.gov.cmb.common.ejb.vo.ModeloVO;
import br.gov.cmb.common.util.CollectionUtils;




public class JPQLBuilder implements IQuery, ISelect, IFrom, IOrder, IWhere {
	
	private static final String SELECT = "SELECT";
	private static final String ESPACO = " ";
	private static final String VIRGULA = ",";
	private static final String FROM = "FROM";
	private static final String INNER_JOIN = "INNER JOIN";
	private static final String INNER_JOIN_FETCH = "INNER JOIN FETCH";
	private static final String LEFT_JOIN = "LEFT JOIN";
	private static final String LEFT_JOIN_FETCH = "LEFT JOIN FETCH";
	private static final String WHERE = "WHERE";
	private static final String ORDER_BY = "ORDER BY";
	private static final String DESC = "DESC";
	
	private StringBuilder consulta;
	private StringBuilder ordenacao;
	private List<Clausula> clausulas = new ArrayList<Clausula>();
	private ModeloVO modeloVO;
	private boolean contemClausula;
	
	public enum EOperadorLogico{
		AND, OR
	}
	
	private JPQLBuilder() {}
	
	public static IQuery getInstance() {
		return new JPQLBuilder();
	}
	
	public StringBuilder builder() {
		StringBuilder consulta = new StringBuilder(this.consulta);
		
		if(CollectionUtils.isNotEmpty(clausulas) && (contemValorNoVO() || contemClausulaObrigatoria())){
			adicionarComEspaco(WHERE, consulta);
			for (Clausula clausula : clausulas) {
				if(clausula.contemValor()){
					if(contemClausula){
						adicionarComEspaco(clausula.getOpedarorLogico(), consulta);
					}
					
					adicionarComEspaco(clausula.getClausula(), consulta);
					contemClausula = true;
				}
			}
		}
		
		if(this.ordenacao != null){
			adicionarComEspaco(this.ordenacao.toString(), consulta);
		}
		
		return consulta;
	}
	
	private boolean contemClausulaObrigatoria() {
		for (Clausula clausula : clausulas) {
			if(!clausula.isOpcional()) {
				return true;
			}
		}
		return false;
	}

	private boolean contemValorNoVO() {
		return this.modeloVO == null || this.modeloVO.contemValor();
	}

	public ISelect select(String... campos) {
		this.consulta = new StringBuilder();
		this.adicionarComEspaco(SELECT);
		adicionarCampos(consulta, campos);
		
		return this;
	}

	private void adicionarCampos(StringBuilder consulta, String... campos) {
		for (int i = 0; i < campos.length; i++) {
			adicionarComEspaco(campos[i], consulta );
			adicionarVirgula(i, campos.length, consulta);
		}
	}

	@Override
	public IOrder order(String... campos) {
		ordenacao = new StringBuilder();
		adicionarComEspaco(ORDER_BY, ordenacao);
		adicionarCampos(ordenacao, campos);
		
		return this;
	}

	@Override
	public IFrom innerJoin(String relacionamento, String alias) {
		return adicionarRelacionamento(relacionamento, alias, INNER_JOIN);
	}

	@Override
	public IFrom innerJoinFetch(String relacionamento, String alias) {
		return adicionarRelacionamento(relacionamento, alias, INNER_JOIN_FETCH);
	}

	@Override
	public IFrom leftJoin(String relacionamento, String alias) {
		return adicionarRelacionamento(relacionamento, alias, LEFT_JOIN);
	}

	@Override
	public IFrom leftJoinFetch(String relacionamento, String alias) {
		return adicionarRelacionamento(relacionamento, alias, LEFT_JOIN_FETCH);
	}

	@Override
	public IWhere where(ModeloVO modeloVO, String clausula) {
		return where(modeloVO, clausula, true);
	}
	
	@Override
	public IWhere where(ModeloVO modeloVO, String clausula, boolean opcional) {
		this.modeloVO = modeloVO;
		adicionarClasula(clausula, opcional, null);
		return this;
	}

	private void adicionarClasula(String clausula, boolean opcional, EOperadorLogico opedarorLogico) {
		this.clausulas.add(new Clausula(clausula, opcional, modeloVO, opedarorLogico));
	}
	
	@Override
	public IFrom from(Class<?> entidade, String alias) {
		adicionarComEspaco(FROM);
		adicionarComEspaco(entidade.getSimpleName());
		adicionarComEspaco(alias);
		
		return this;
	}

	@Override
	public IOrder desc() {
		adicionarComEspaco(DESC, ordenacao);
		return this;
	}

	@Override
	public IWhere or(String clausula) {
		return or(clausula, true);
	}
	
	@Override
	public IWhere or(String clausula, boolean clasulaDinamica) {
		adicionarClasula(clausula, clasulaDinamica, EOperadorLogico.OR);
		return this;
	}

	@Override
	public IWhere and(String clausula) {
		return and(clausula, true);
	}

	@Override
	public IWhere and(String clausula,  boolean clasulaDinamica) {
		adicionarClasula(clausula, clasulaDinamica, EOperadorLogico.AND);
		return this;
	}
	
	private IFrom adicionarRelacionamento(String relacionamento, String alias, String join) {
		adicionarComEspaco(join);
		adicionarComEspaco(relacionamento);
		adicionarComEspaco(alias);
		return this;
	}
	
	private void adicionarComEspaco(String str) {
		adicionarComEspaco(str, consulta);
	}
	
	private void adicionarComEspaco(String str, StringBuilder consulta) {
		adicionarEspaco(consulta);
		consulta.append(str);
	}
	
	private void adicionarEspaco(StringBuilder consulta) {
		consulta.append(ESPACO);
	}
	
	private void adicionarVirgula(int item, int totalDeItem, StringBuilder consulta) {
		int proximoItem = item + 1;
		if(proximoItem < totalDeItem){
			consulta.append(VIRGULA);
		}
	}

	@Override
	public IWhere where(String clausula) {
		return where(null, clausula, false);
	}
	
}
