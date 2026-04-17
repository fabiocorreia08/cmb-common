package br.gov.cmb.common.ejb.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="perfil")
@XmlAccessorType (XmlAccessType.FIELD)
public class PerfilVO {
	
	@XmlAttribute
	private Integer id;
	
	@XmlAttribute
	private String nome;
	
	@XmlAttribute
	private String descricao;

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

}
