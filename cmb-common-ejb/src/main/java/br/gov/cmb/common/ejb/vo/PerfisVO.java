package br.gov.cmb.common.ejb.vo;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="perfis")
@XmlAccessorType (XmlAccessType.FIELD)
public class PerfisVO {
	
	@XmlElement(name = "perfil")
	private List<PerfilVO> perfis;

	public List<PerfilVO> getPerfis() {
		return perfis;
	}
	
	public PerfilVO obterPerfil(Integer id) {
		for (PerfilVO perfil : perfis) {
			if(perfil.getId().equals(id)){
				return perfil;
			}
		}
		
		return null;
	}
	
}
