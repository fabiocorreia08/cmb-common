package br.gov.cmb.common.ejb.validacao;

import br.gov.cmb.common.exception.ValidacaoException;

public abstract class AbstractValidador implements Validador {

	public void validarTodos(Object entidade, String... validacoes) throws ValidacaoException {
		ExecutorDeValidacao.executar(this, entidade, validacoes);
	}
}
