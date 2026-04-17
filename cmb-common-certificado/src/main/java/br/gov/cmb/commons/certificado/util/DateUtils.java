package br.gov.cmb.commons.certificado.util;

import java.util.Date;


public class DateUtils {
	private static final long MILLIS = 1000l;
	private static final int SESSENTA = 60;
	

	public static long millisHours(int hours) {
		return (hours * SESSENTA * SESSENTA * MILLIS);
	}
	
	public static Date minusHours(Date date, int hours) {
		return new Date(date.getTime() - millisHours(hours)); 
	}
	
	public static Date minusHours(int hours) {
		Date now = new Date();
		
		return minusHours(now, hours);
	}
	
	public static Date plusHours(Date date, int hours) {
		return new Date(date.getTime() + millisHours(hours)); 
	}
}
