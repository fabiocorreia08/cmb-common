package br.gov.cmb.common.email.util;

import java.text.ParseException;
import java.util.Date;

import org.quartz.CronExpression;

public final class CronUtils {

	private CronUtils() {

	}

	public static Date recuperarProximaData(String cron) throws ParseException {
		CronExpression cronExpression = new CronExpression(cron);
		return cronExpression.getTimeAfter(new Date());

	}

}
