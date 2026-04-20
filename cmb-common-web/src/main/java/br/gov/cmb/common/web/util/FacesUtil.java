package br.gov.cmb.common.web.util;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.base.Strings;

/**
 * Classe utilitária para desenvolvimento JSF
 */
public final class FacesUtil {

	private FacesUtil() {

	}

	public static String getRequestParameter(String name) {
		return (String) getExternalContext().getRequestParameterMap().get(name);
	}

	@SuppressWarnings("removal")
	public static Long getRequestParameterAsLong(String name) {
		String id = FacesUtil.getRequestParameter(name);
		if (!Strings.isNullOrEmpty(id)) {
			return new Long(id);
		}
		return null;
	}

	public static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	public static Map<String, Object> getSessionMap() {
		return getExternalContext().getSessionMap();
	}

	public static void addSessionMap(String key, Object value) {
		getSessionMap().put(key, value);
	}

	public static void removeSessionMap(String key) {
		getSessionMap().remove(key);
	}

	public static Object getValueSessionMap(String key) {
		return getSessionMap().get(key);
	}

	public static ServletContext getServletContext() {
		return (ServletContext) getExternalContext().getContext();
	}

	public static HttpServletRequest getServletRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}

	public static HttpServletResponse getServletResponse() {
		return (HttpServletResponse) getExternalContext().getResponse();
	}

	public static HttpSession getHttpSession() {
		return (HttpSession) FacesUtil.getExternalContext().getSession(false);
	}

	public static FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}

	public static void redirect(String retorno) throws IOException {
		getExternalContext().redirect(retorno);
	}

	public static String gerarUrl(String url) {
		return getContextName().concat(url);
	}

	public static String getContextName() {
		return getServletRequest().getContextPath();
	}

	public static Flash getFlash() {
		return FacesContext.getCurrentInstance().getExternalContext().getFlash();
	}

	public static Object getFlashAttributeAndRemove(String key) {
		Object object = getFlash().get(key);
		getFlash().remove(key);
		return object;
	}

	public static Flash clearFlash() {
		return FacesContext.getCurrentInstance().getExternalContext().getFlash();
	}

}
