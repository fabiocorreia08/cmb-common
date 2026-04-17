package br.gov.cmb.common.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.gov.cmb.common.ejb.infra.UsuarioManager;
import br.gov.cmb.common.ejb.vo.UsuarioVO;
import br.gov.cmb.common.exception.ValidacaoException;
import br.gov.cmb.common.web.util.MessagesUtils;

/**
 * Essa classe possui apenas métodos comuns a dois ou mais controllers
 */
public abstract class AbstractController implements Serializable {

    private static final long serialVersionUID = 6581780917004125758L;
    
    @Inject
    private UsuarioManager usuarioManager;
    
    public UsuarioVO getUsuarioLogado() {
        return usuarioManager.getUsuario();
     }
    
    public boolean isLogado() {
        return usuarioManager.isLogado();
    }
    
    public boolean contemParametroRequest(String parametro){
    	return !recuperarParametroRequest(parametro).isEmpty();
    }
    
    public String recuperarParametroRequest(String parametro){
    	String valor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parametro);
    	return valor == null ? "" : valor;
    }
    
    protected void construirMensagemDeErro(List<ValidacaoException> erros) {
        for (ValidacaoException erro : erros) {
            MessagesUtils.exibirMensagemErro(erro.getMessage());
        }
    }

    
}