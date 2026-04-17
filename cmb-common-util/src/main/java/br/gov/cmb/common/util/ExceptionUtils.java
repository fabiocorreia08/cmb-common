package br.gov.cmb.common.util;

import java.util.ArrayList;
import java.util.List;

public final class ExceptionUtils {

	private ExceptionUtils() {

	}

	public static List<String> getExceptionMessageChain(Throwable throwable) {
		List<String> result = new ArrayList<>();
		while (throwable != null) {
			result.add(throwable.getMessage());
			throwable = throwable.getCause();
		}
		return result;
	}
}
