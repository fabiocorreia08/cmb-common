package br.gov.cmb.common.security.rest.client;

import java.io.Serializable;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.jboss.logging.Logger;

import br.gov.cmb.common.ejb.vo.UsuarioVO;
import br.gov.cmb.common.exception.runtime.CMBSecurityException;
import br.gov.cmb.common.security.rest.request.UsuarioRequest;
import br.gov.cmb.common.security.soap.Args;
import br.gov.cmb.common.security.soap.CPALocator;
import br.gov.cmb.common.security.soap.CPAPortType;
import br.gov.cmb.common.util.JsonUtils;
import br.gov.cmb.common.util.PropertiesUtils;

public class UsuarioClient implements Serializable {
	private static final Logger logger = Logger.getLogger(UsuarioClient.class);

	private static final long serialVersionUID = 1L;
	private static final String CPA_SERVER_URL_PROPERTY = "cpa.server.url";

	public UsuarioVO autenticar(UsuarioRequest usuarioRequest) {
		try {
			String resultado = getResponseLogin(usuarioRequest);
			logger.infof("[CPA] - RESPONSE [%s]", resultado);
			return JsonUtils.parse(resultado, UsuarioVO.class);
		} catch (ServiceException | RemoteException e) {
			throw new CMBSecurityException(e);
		}
	}

	public String getResponseLogin(UsuarioRequest usuarioRequest) throws RemoteException, ServiceException {
		logger.infof("[CPA] - REQUEST [ IdSistema=%d, Login=%s, Senha=**** ]", usuarioRequest.getIdSistema().intValue(), usuarioRequest.getLogin());
		Args args = new Args(usuarioRequest.getIdSistema().intValue(), usuarioRequest.getLogin(), usuarioRequest.getSenha());
		return getCPAPortType().login(args);
	}

	private CPAPortType getCPAPortType() throws ServiceException {
		CPALocator locator = new CPALocator();
		String cpaServerUrl = PropertiesUtils.getProperty(CPA_SERVER_URL_PROPERTY);
		logger.infof("[CPA] - URL [ %s ]", cpaServerUrl);
		locator.setCPAPortEndpointAddress(cpaServerUrl);
		return locator.getCPAPort();
	}

}
