package br.gov.cmb.common.ejb.builder.query;

import br.gov.cmb.common.ejb.builder.JPQLBuilder.EOperadorLogico;
import br.gov.cmb.common.ejb.vo.ModeloVO;

public class Clausula {
	
	private static final String VAZIO = "";
	private static final String ABRE_COLCHETE = "[";
	private static final String FECHA_COLCHETE = "]";
	private static final String DOIS_PONTOS = ":";
	
	private String clausula;
	private boolean opcional;
	private ModeloVO modeloVO;
	private EOperadorLogico opedarorLogico;
	private int indexMenorQue;
	private int indexMaiorQue;

	public Clausula(String clausula, boolean opcional, ModeloVO modeloVO, EOperadorLogico opedarorLogico) {
		this.clausula = clausula;
		this.opcional = opcional;
		this.modeloVO = modeloVO;
		this.opedarorLogico = opedarorLogico;
		indexMenorQue = clausula.lastIndexOf(ABRE_COLCHETE);
		indexMaiorQue = clausula.lastIndexOf(FECHA_COLCHETE);
	}

	public String getClausula() {
		return contemMaiorEMenor() ? clausula.replace(ABRE_COLCHETE, DOIS_PONTOS).replace(FECHA_COLCHETE, VAZIO) : clausula;
	}

	public boolean contemValor() {
		return !opcional || contemValorNoVO() || modeloVO == null;
	}

	private boolean contemValorNoVO() {
		if(contemMaiorEMenor()){
			String nomeDoCampo = (String) clausula.subSequence(indexMenorQue+1, indexMaiorQue);
			if(modeloVO.contemValor(nomeDoCampo)){
				return true;
			}
		}
		
		return false;
	}

	private boolean contemMaiorEMenor() {
		return indexMenorQue > 0 && indexMaiorQue > 0;
	}

	public String getOpedarorLogico() {
		return opedarorLogico.toString();
	}

	public boolean isOpcional() {
		return opcional;
	}
	
	

}
