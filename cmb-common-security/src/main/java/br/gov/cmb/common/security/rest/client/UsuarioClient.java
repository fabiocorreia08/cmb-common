package br.gov.cmb.common.security.rest.client;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import br.gov.cmb.common.ejb.vo.UsuarioVO;
import br.gov.cmb.common.exception.runtime.CMBSecurityException;
import br.gov.cmb.common.security.rest.request.UsuarioRequest;
import br.gov.cmb.common.soap.client.CpaSoapClient;
import br.gov.cmb.common.util.JsonUtils;

@RequestScoped
public class UsuarioClient implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(UsuarioClient.class);

    @Inject
    private CpaSoapClient cpaSoapClient;

    /**
     * Mantido exatamente igual ao original.
     * Todos os sistemas continuam chamando este método.
     */
    public UsuarioVO autenticar(UsuarioRequest usuarioRequest) {
        try {
            logger.infof("[CPA] - REQUEST [ IdSistema=%d, Login=%s, Senha=**** ]",
                    usuarioRequest.getIdSistema().intValue(),
                    usuarioRequest.getLogin());

            String json = cpaSoapClient.login(
                    usuarioRequest.getIdSistema().intValue(),
                    usuarioRequest.getLogin(),
                    usuarioRequest.getSenha()
            );

            logger.infof("[CPA] - RESPONSE [%s]", json);

            return JsonUtils.parse(json, UsuarioVO.class);

        } catch (Exception e) {
            throw new CMBSecurityException(e);
        }
    }
}