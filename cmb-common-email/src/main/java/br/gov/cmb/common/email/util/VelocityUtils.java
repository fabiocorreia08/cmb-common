package br.gov.cmb.common.email.util;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public final class VelocityUtils {

	private VelocityUtils() {

	}

	public static String parseTemplate(String template, Map<String, Object> parametros) {
		VelocityContext context = new VelocityContext(parametros);
		StringWriter swOut = new StringWriter();
		
		Properties p = new Properties(); 
		p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");

		Velocity.init(p);
		
		Velocity.evaluate(context, swOut, "template", template);
		return swOut.toString();
	}

}
