package br.gov.cmb.common.rest.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.gov.cmb.common.exception.ValidacaoException;
import br.gov.cmb.common.rest.response.ValidacaoResponse;

@Provider
public class ValidacaoExceptionMapper implements ExceptionMapper<ValidacaoException> {

	private static final int STATUS_UNPROCESSABLE_ENTITY = 422;

	@Override
	public Response toResponse(ValidacaoException exception) {
		ValidacaoResponse validacaoResponse = new ValidacaoResponse();
		for (ValidacaoException validacaoException : exception.getErrosDeValidacao()) {
			validacaoResponse.adicionarErro(validacaoException.getMessage());
		}
		return Response.status(STATUS_UNPROCESSABLE_ENTITY).entity(validacaoResponse).build();
	}

}
