package br.gov.cmb.common.ejb.validacao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Strings;

import br.gov.cmb.common.ejb.anotacao.RegraDeValidacao;
import br.gov.cmb.common.exception.ValidacaoException;
import br.gov.cmb.common.exception.runtime.CMBReflectionException;
import br.gov.cmb.common.util.CollectionUtils;
import br.gov.cmb.common.util.ReflectionUtils;

public final class ExecutorDeValidacao {

    @SuppressWarnings("unused")
	private static final String FORAM_ENCONTRADOS_ERROS = "Foram encontrados erros";

    private ExecutorDeValidacao() {
    }

    /**
     * @deprecated Implementar a classe AbstractValidador e chamar o metodo validar
     * @param classeValidator
     * @param entidade
     * @param validacoes
     * @throws ValidacaoException
     */
    @Deprecated
    public static <T extends Validador>void executar(Class<T> classeValidator, Object entidade, String... validacoes) throws ValidacaoException {
        T validador = ReflectionUtils.instanciar(classeValidator);
        executarValidacao(validador, entidade,  validacoes);
    }
    
    public static <T extends Validador>void executar(T validador, Object entidade, String... validacoes) throws ValidacaoException {
    	executarValidacao(validador, entidade, validacoes);
    }

	private static <T extends Validador> void executarValidacao(T validador, Object entidade, String... validacoes) throws ValidacaoException {
		List<ValidacaoException> erros = new ArrayList<ValidacaoException>();
		for (Method metodo : ReflectionUtils.recuperarMetodosAnotadosCom(validador.getClass(), RegraDeValidacao.class)) {
            if (validacoes.length == 0 || contemDiscriminador(metodo, validacoes)) {
                invocarValidacao(validador, metodo, entidade, erros);
            }
        }

        if (CollectionUtils.isNotEmpty(erros)) {
            ValidacaoException validationException = new ValidacaoException();
            validationException.adicionarErrosEncontrados(erros);
            throw validationException;
        }
	}

    private static boolean contemDiscriminador(Method metodo, String[] validacoes) {
        RegraDeValidacao validacao = metodo.getAnnotation(RegraDeValidacao.class);

        if (!Strings.isNullOrEmpty(validacao.value())) {
            return Arrays.asList(validacoes).contains(validacao.value());
        }

        return false;
    }

    private static <T extends Validador>void invocarValidacao(T validador, Method metodo, Object entidade, List<ValidacaoException> erros) {
        try {
            metodo.invoke(validador, entidade);
        } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {

            if (e.getCause() instanceof ValidacaoException) {
                erros.add((ValidacaoException) e.getCause());
            }
            else {
                throw new CMBReflectionException(e);
            }
        }
    }



}
