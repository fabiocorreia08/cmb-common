package br.gov.cmb.common.rest.response;

import java.util.ArrayList;
import java.util.List;

public class ValidacaoResponse {

	private List<String> erros = new ArrayList<>();
	
	public void adicionarErro(String erro) {
		this.erros.add(erro);
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}
}
