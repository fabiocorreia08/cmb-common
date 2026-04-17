package br.gov.cmb.common.ejb.infra;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.gov.cmb.common.ejb.vo.UsuarioVO;
import br.gov.cmb.common.util.ReflectionUtils;

@Named
@RequestScoped
public class UsuarioManager implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioVO usuario;

	
	public UsuarioVO getUsuario() {
		if(!isLogado()){
			return null;
		}
		return ReflectionUtils.clonar(usuario.getClass(), usuario);
	}
	
	public void registrar(UsuarioVO usuario){
		if(!this.usuario.isLogado()){
			this.usuario = usuario;
		}
	}
	
	public boolean isLogado() {
		return this.usuario.isLogado();
	}
}
