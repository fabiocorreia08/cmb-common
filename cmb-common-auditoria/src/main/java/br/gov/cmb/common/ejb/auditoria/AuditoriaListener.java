package br.gov.cmb.common.ejb.auditoria;

import org.hibernate.envers.RevisionListener;

import br.gov.cmb.common.ejb.infra.UsuarioManager;
import br.gov.cmb.common.ejb.interfaces.IAuditoria;
import br.gov.cmb.common.ejb.vo.UsuarioVO;
import br.gov.cmb.common.util.CDIUtils;

public class AuditoriaListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		UsuarioManager usuarioManager = CDIUtils.recuperarBean(UsuarioManager.class);
		UsuarioVO usuario = usuarioManager.getUsuario();
		
		IAuditoria auditoria = (IAuditoria) revisionEntity;
		
		if(usuario != null){
			auditoria.setLogin(usuario.getLogin());
			auditoria.setMatricula(usuario.getMatricula());
		}

	}

}
