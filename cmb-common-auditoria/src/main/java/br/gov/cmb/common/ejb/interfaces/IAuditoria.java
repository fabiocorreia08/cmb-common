package br.gov.cmb.common.ejb.interfaces;

import java.io.Serializable;

public interface IAuditoria extends Serializable{

	void setLogin(String login);

	void setMatricula(String matricula);

}
