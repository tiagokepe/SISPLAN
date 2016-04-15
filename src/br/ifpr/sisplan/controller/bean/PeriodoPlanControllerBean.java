package br.ifpr.sisplan.controller.bean;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ifpr.sisplan.controller.PDIControllerCached;
import br.ifpr.sisplan.model.dao.PeriodoPlanejamentoDao;
import br.ifpr.sisplan.model.table.PeriodoPlanejamento;
import br.ifpr.sisplan.model.table.Unidade;
import br.ufrn.arq.web.jsf.AbstractController;

@Component
@Scope("session")
public class PeriodoPlanControllerBean extends AbstractController {
	private enum MenuValues {
		SHOW("Mostrar"), HIDE("Esconder"), NONE("Cadastrar");
		
		public String value; 
		MenuValues(String value) {
			this.value = value;
		}
	}
	
	private PeriodoPlanejamento periodoPlan;
	private int barCurrentValue;
	private String valueMenuShowHide = MenuValues.NONE.value;;
	private boolean showPeriodoPlan;
	private boolean periodoPlanAtivo;
	
	public PeriodoPlanControllerBean() {
		this.setPeriodoPlan();
		this.setPeriodoPlanAtivo();
		this.setShowPeriodoPlan();
	}

	public void setPeriodoPlan() {
		final int id_pdi = ((PDIControllerBean)getMBean("pdiControllerBean")).getCurrentPDI().getMyID();
		this.periodoPlan = this.getDAO(PeriodoPlanejamentoDao.class).selectPeriodoPlanByPDI(id_pdi);
		
		Unidade unidadeSelected = ((PDIControllerBean)getMBean("pdiControllerBean")).getUnidadeSelected();
		//Check if was selected a specific Unidade
		if(!PDIControllerCached.getInstance().equalsToUnidadeAll(unidadeSelected.getName()))
			this.setPeriodoPlanByUnidade(unidadeSelected);
	}
	
	private void setPeriodoPlanByUnidade(Unidade unidade) {
		final PeriodoPlanejamento periodoPlanUnidade = this.getDAO(PeriodoPlanejamentoDao.class).selectPeriodoPlanByUnidade(unidade.getId());
		if(periodoPlanUnidade != null) {
			DateTime dtFimGeral = new DateTime(this.periodoPlan.getDataFim());
			DateTime dtFimUnidade = new DateTime(periodoPlanUnidade.getDataFim());
			
			int diffDays = Days.daysBetween(new DateTime(dtFimGeral), new DateTime(dtFimUnidade)).getDays();
			if(diffDays > 0)
				this.periodoPlan = periodoPlanUnidade;
		}
	}
	
	public void setPeriodoPlan(PeriodoPlanejamento periodoPlan) {
		this.periodoPlan = periodoPlan;
		this.setPeriodoPlanAtivo();
		this.setShowPeriodoPlan();
	}
	
	public PeriodoPlanejamento getPeriodoPlan() {
		return periodoPlan;
	}
	
	public int getBarCurrentValue() {
		DateTime dtIni = new DateTime(this.periodoPlan.getDataInicio());
		DateTime dtFim = new DateTime(this.periodoPlan.getDataFim());
		int totalDays = Days.daysBetween(dtIni, dtFim).getDays() + 1;
		int remainingDays = Days.daysBetween(new DateTime(new Date()), dtFim).getDays() + 1;
		
		if(remainingDays <= 0)
			this.barCurrentValue = 100;
		else
			this.barCurrentValue = (totalDays - remainingDays) * (100/totalDays);
			
		return barCurrentValue;
	}

	public String getValueMenuShowHide() {
		return valueMenuShowHide;
	}
	
/*	public void setValueMenuShowHide() {
		if(this.periodoPlan == null) {
			this.valueMenuShowHide = MenuValues.NONE.value;
			return;
		}
		
		if(this.valueMenuShowHide.equals(MenuValues.NONE.value) || this.valueMenuShowHide.equals(MenuValues.))
		
		if(this.valueMenuShowHide.equals(MenuValues.SHOW.value) || )
			this.valueMenuShowHide = MenuValues.HIDE.value;
		else
			this.valueMenuShowHide = MenuValues.SHOW.value;

	}*/
	
	public boolean isShowPeriodoPlan() {
		return showPeriodoPlan;
	}

	public void setShowPeriodoPlan() {
/*		if(!this.periodoPlanAtivo) {
			this.valueMenuShowHide = MenuValues.NONE.value;
			this.showPeriodoPlan = false;
			return;
		}*/
		
		if(this.valueMenuShowHide.equals(MenuValues.NONE.value)) {
			this.valueMenuShowHide = MenuValues.SHOW.value;
			this.showPeriodoPlan = false;
		} else if(this.valueMenuShowHide.equals(MenuValues.SHOW.value)) {
			this.valueMenuShowHide = MenuValues.HIDE.value;
			this.showPeriodoPlan = true;
		} else {
			this.valueMenuShowHide = MenuValues.SHOW.value;
			this.showPeriodoPlan = false;
		}
	}
	
	public void goToNovoPeriodoPlan() {
		this.redirect("/portal/novo_periodo_plan.jsf");
	}

	public boolean isPeriodoPlanAtivo() {
		return periodoPlanAtivo;
	}

	private void setPeriodoPlanAtivo() {
		if(this.periodoPlan == null) {
			this.periodoPlanAtivo = false;
			return;
		}
		final SisplanUser sisplanUser = (SisplanUser)getMBean("sisplanUser");
		if(sisplanUser.isPlanningManager()) {
			this.periodoPlanAtivo = true;
			return;
		}
			
		// Case user isn't planning manager, so checks the planning period. 
		DateTime dtFim = new DateTime(this.periodoPlan.getDataFim());
		int remainingDays = Days.daysBetween(new DateTime(new Date()), dtFim).getDays() + 1;
		
		if(remainingDays <= 0)
			this.periodoPlanAtivo = false;
		else
			this.periodoPlanAtivo = true;

	}

}
