package br.gov.cmb.common.web.util;

import org.primefaces.context.RequestContext;

public class PrimefacesUtil {

	private static final String SCRIPT_HIDE_MODAL_PATTERN = "PF('%s').hide();";
	private static final String SCRIPT_SHOW_MODAL_PATTERN = "PF('%s').show();";

	public static void executeScript(String script) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute(script);
	}

	public static void abrirModalPF(String widgetVar) {
		String script = String.format(SCRIPT_SHOW_MODAL_PATTERN, widgetVar);
		executeScript(script);
	}

	public static void fecharModalPF(String widgetVar) {
		String script = String.format(SCRIPT_HIDE_MODAL_PATTERN, widgetVar);
		executeScript(script);
	}
}
