package br.gov.cmb.common.ejb.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import br.gov.cmb.common.ejb.anotacao.ParametroNomeado;
import br.gov.cmb.common.exception.runtime.CMBReflectionException;
import br.gov.cmb.common.util.ReflectionUtils;

public abstract class ModeloVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String PERCENTUAL = "%";

	public boolean contemValor(String nomeDoCampo) {
		Object valor = ReflectionUtils.recuperarValorCampo(this, nomeDoCampo);

		if (valor instanceof String) {
			return !Strings.isNullOrEmpty(valor.toString());
		}

		return valor != null;
	}

	public boolean contemValor() {
		for (Field campo : ReflectionUtils.recuperarCamposAnotadosCom(this.getClass(), ParametroNomeado.class)) {
			if (contemValor(campo.getName())) {
				return true;
			}
		}

		return false;
	}

	public Map<String, Object> getParametros() {
		Map<String, Object> parametros = Maps.newHashMap();

		if (this != null) {
			for (Field campo : ReflectionUtils.recuperarCamposAnotadosCom(this.getClass(), ParametroNomeado.class)) {
				ParametroNomeado parametroNomedado = campo.getAnnotation(ParametroNomeado.class);
				String nomeCampo = getNomeDoCampo(parametroNomedado, campo);

				try {
					if (this.contemValor(campo.getName())) {
						parametros.put(nomeCampo, getValorDoCampo(parametroNomedado, campo));
					}
				} catch (IllegalAccessException e) {
					throw new CMBReflectionException(e);
				}
			}
		}

		return parametros;
	}

	private Object getValorDoCampo(ParametroNomeado parametroNomedado, Field campo) throws IllegalAccessException {
		campo.setAccessible(true);
		if (parametroNomedado.like()) {
			return PERCENTUAL + campo.get(this).toString() + PERCENTUAL;
		} else {
			return campo.get(this);
		}
	}

	private String getNomeDoCampo(ParametroNomeado parametroNomedado, Field campo) {
		String nomeCampo = parametroNomedado.value();
		if (Strings.isNullOrEmpty(nomeCampo)) {
			nomeCampo = campo.getName();
		}
		return nomeCampo;
	}

}
