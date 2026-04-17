package br.gov.cmb.common.rest.mapper;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import br.gov.cmb.common.rest.response.ValidacaoResponse;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	private static final String MESSAGE_TEMPLATE_DEFAULT = "javax.validation";
	private static final String SEPARADOR = ".";
	private static final String FORMATO_MENSAGEM = "%s %s";

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		ValidacaoResponse validacaoResponse = new ValidacaoResponse();
		for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
			adicionarMensagemErro(validacaoResponse, constraintViolation);
		}

		return Response.status(Status.BAD_REQUEST).entity(validacaoResponse).build();
	}

	private void adicionarMensagemErro(ValidacaoResponse validacaoResponse, ConstraintViolation<?> constraintViolation) {
		String mensagem = constraintViolation.getMessage();
		if (!existeMensagemCustomizada(constraintViolation)) {
			mensagem = obterMensagemPadrao(constraintViolation);
		}
		validacaoResponse.adicionarErro(mensagem);
	}

	private boolean existeMensagemCustomizada(ConstraintViolation<?> constraintViolation) {
		return !constraintViolation.getMessageTemplate().contains(MESSAGE_TEMPLATE_DEFAULT);
	}

	private String obterMensagemPadrao(ConstraintViolation<?> constraintViolation) {
		String campo = obterCampo(constraintViolation.getPropertyPath().toString());
		return String.format(FORMATO_MENSAGEM, campo, constraintViolation.getMessage());
	}

	private String obterCampo(String path) {
		Splitter splitter = Splitter.on(SEPARADOR).trimResults();
		return Iterables.getLast(splitter.split(path));
	}

}
