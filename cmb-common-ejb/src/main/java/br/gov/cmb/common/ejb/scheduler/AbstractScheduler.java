package br.gov.cmb.common.ejb.scheduler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

public abstract class AbstractScheduler {

	@Resource
	private TimerService timerService;
	
	@PostConstruct
	public void init() {
		cancelarTimerService();
		TimerConfig timerConfig = criarTimerConfig();
		ScheduleExpression expression = criarScheduleExpression();
		timerService.createCalendarTimer(expression, timerConfig);
	}
	
	@Timeout
	public void timeout() {
		execute();
	}
	
	public ScheduleExpression criarScheduleExpression() {
		ScheduleExpression expression = new ScheduleExpression();
		expression.dayOfWeek(getDiaSemana());
		expression.dayOfMonth(getDiaDoMes());
		expression.month(getMes());
		
		expression.hour(getHora());
		expression.minute(getMinutos());
		return expression;
	}

	public TimerConfig criarTimerConfig() {
		TimerConfig timerConfig = new TimerConfig();
		timerConfig.setInfo(getNome());
		timerConfig.setPersistent(false);
		return timerConfig;
	}

	public void cancelarTimerService() {
		if (timerService.getTimers() != null) {
			for (Timer timer : timerService.getTimers()) {
				if (timer.getInfo().equals(getNome())) {
					timer.cancel();
				}
			}
		}
	}

	public abstract String getHora();

	public abstract String getMinutos();

	public abstract String getDiaSemana();
	
	public abstract String getNome();
	
	public abstract String getDiaDoMes();
	
	public abstract String getMes();
	
	public abstract void execute();
}
