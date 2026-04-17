package br.gov.cmb.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.MutableDateTime;
import org.joda.time.Period;
import org.joda.time.Years;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import br.gov.cmb.common.exception.runtime.CMBParseException;

public final class DataUtils {

	public static final String FORMATO_HORA_HHMM = "HH:mm";
	public static final String FORMATO_DATA_DDMMYYYY = "dd/MM/yyyy";
	public static final String FORMATO_HORA_DDMMYYYY_HHMM = "dd/MM/yyyy HH:mm";
	public static final String FORMATO_DATA_YYYYMMDD = "yyyyMMdd";

	public static Date adicionarDias(Date data, int numeroDias) {
		DateTime dateTime = new DateTime(data);
		return dateTime.plusDays(numeroDias).toDate();
	}

	public static Date obterDataAtualComHoraZerada() {
		return obterDataComHoraZerada(new Date());
	}

	public static Date obterDataComHoraZerada(Date data) {
		return new DateTime(data).withTimeAtStartOfDay().toDate();
	}

	public static Date obterDataComHoraFinal(Date data) {
		return new DateTime(data).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate();
	}

	public static Date obterDataAtual() {
		return new DateTime().toDate();
	}

	public static Date subtrairDiasUteis(Date data, int numeroDias) {
		DateTime dataSubtraida = subtrairDias(data, numeroDias);
		if (dataSubtraida.getDayOfWeek() == DateTimeConstants.SUNDAY) {
			dataSubtraida = dataSubtraida.minusDays(2);
		} else if (dataSubtraida.getDayOfWeek() == DateTimeConstants.SATURDAY) {
			dataSubtraida = dataSubtraida.minusDays(1);
		}
		return dataSubtraida.toDate();
	}

	public static DateTime subtrairDias(Date data, int numeroDias) {
		DateTime dataSubtraida = new DateTime(data).withTimeAtStartOfDay();
		return dataSubtraida.minusDays(numeroDias);
	}

	public static Date montarDataHorarioLimite(Date data, String horarioParametro) {
		MutableDateTime dateTime = new MutableDateTime(data);
		String[] arrayHora = horarioParametro.split(":");
		dateTime.setHourOfDay(Integer.parseInt(arrayHora[0]));
		dateTime.setMinuteOfHour(Integer.parseInt(arrayHora[1]));
		dateTime.setSecondOfMinute(Integer.parseInt(arrayHora[1]));

		return dateTime.toDate();
	}

	public static Date montaDataHorarioDiaDaSemanaLimite(Date data, int diaDaSemana, String dataParametro) {
		MutableDateTime dateTime = new MutableDateTime(data);
		dateTime.setDayOfWeek(diaDaSemana);
		return montarDataHorarioLimite(dateTime.toDate(), dataParametro);
	}

	public static Date retornarDiaDaSemana(Date dataAtual, int diaDaSemana) {
		MutableDateTime mutableDateTime = new MutableDateTime(dataAtual);
		mutableDateTime.setDayOfWeek(diaDaSemana);
		return mutableDateTime.toDate();
	}

	public static List<Date> retornarDiasIntervalo(Date dataInicio, Date dataFim) {
		List<Date> intervalo = new ArrayList<Date>();
		while (dataInicio.compareTo(dataFim) <= 0) {
			intervalo.add(dataInicio);
			dataInicio = adicionarDias(dataInicio, 1);
		}
		intervalo.add(dataFim);

		return intervalo;
	}

	public static int diferencaDiasEntreDatas(Date dataInicio, Date dataFim) {
		return Days.daysBetween(new DateTime(dataInicio), new DateTime(dataFim)).getDays();
	}	

	public static String retornaDiaPorExtenso(Date data) {
		DateTime dateTime = new DateTime(data);
		return dateTime.dayOfWeek().getAsText();
	}

	public static String formatarHoraHHmm(Date horario) {
		return formatar(horario, FORMATO_HORA_HHMM);
	}

	public static String formatarddMMyyyy(Date data) {
		return formatar(data, FORMATO_DATA_DDMMYYYY);
	}

	public static String formataryyyyMMdd(Date data) {
		return formatar(data, FORMATO_DATA_YYYYMMDD);
	}

	public static String formataryyyyMMddHmm(Date data) {
		return formatar(data, FORMATO_HORA_DDMMYYYY_HHMM);
	}

	public static String formatar(Date data, String pattern) {
		SimpleDateFormat formatador = new SimpleDateFormat(pattern);
		return formatador.format(data);
	}

	public static Date parseyyyyMMdd(String data) {
		return parse(data, FORMATO_DATA_YYYYMMDD);
	}

	public static Date parse(String data, String pattern) {
		try {
			SimpleDateFormat formatador = new SimpleDateFormat(pattern);
			return formatador.parse(data);
		} catch (ParseException e) {
			throw new CMBParseException(e);
		}
	}

	public static int calcularIdade(Date data) {
		return Years.yearsBetween(new DateTime(data), new DateTime(new Date())).getYears();
	}

	public static String formatarDiferencaEntreDataAtualAnosMeses(Date data) {
		return formatarDiferencaEntreDataAnosMeses(new Date(), data);
	}

	public static String formatarDiferencaEntreDataAnosMeses(Date dataInicio, Date dataFim) {
		Period periodo = new Period(new DateTime(dataFim), new DateTime(dataInicio));

		PeriodFormatter pf = new PeriodFormatterBuilder().appendYears().appendSuffix(" ano ", " anos ").appendMonths().appendSuffix(" mês ", " meses ").toFormatter();

		return pf.print(periodo);
	}

	public static String formatarDiferencaEntreDataAtualEmAnos(Date data) {
		return formatarDiferencaEntreDataEmAnos(new Date(), data);
	}

	public static String formatarDiferencaEntreDataEmAnos(Date dataInicio, Date dataFim) {
		Period periodo = new Period(new DateTime(dataFim), new DateTime(dataInicio));

		PeriodFormatter pf = new PeriodFormatterBuilder().appendYears().appendSuffix(" ano ", " anos ").toFormatter();

		return pf.print(periodo);
	}

	public static int getAnoAtual() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public static boolean dataEstaDentroDoPeriodo(Date data, Date inicioPeriodo, Date fimPeriodo, boolean inicioInclusive, boolean fimInclusive){
		DateTime dataTime = new DateTime(data);
		DateTime inicioPeriodoTime = new DateTime(inicioPeriodo);
		DateTime fimPeriodoTime = new DateTime(fimPeriodo);
		Interval intervalo = new Interval(inicioPeriodoTime, fimPeriodoTime);
		boolean contem = intervalo.contains(dataTime);
		if(!inicioInclusive){
			contem = contem && !intervalo.getStart().isEqual(dataTime);
		}
		if(fimInclusive){
			contem = contem || intervalo.getEnd().isEqual(dataTime);
		}
		return contem;
	}
	
	public static boolean periodosColidem(Date inicioPeriodo1, Date fimPeriodo1, Date inicioPeriodo2, Date fimPeriodo2,
			boolean inclusive) {
		Interval intervalo1 = new Interval(new DateTime(inicioPeriodo1), new DateTime(fimPeriodo1));
		Interval intervalo2 = new Interval(new DateTime(inicioPeriodo2), new DateTime(fimPeriodo2));
		if (inclusive) {
			return intervalo1.overlaps(intervalo2) || intervalo1.abuts(intervalo2);
		}
		return intervalo1.overlaps(intervalo2);
	}

}
