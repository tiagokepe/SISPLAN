package br.ifpr.sisplan.controller;

public class PlanningPeriodController {
	private static PlanningPeriodController instance;

	public static PlanningPeriodController getInstance() {
		if(instance == null)
			instance = new PlanningPeriodController();
		return instance;
	}
	
	private PlanningPeriodController() {
	}
	
	public boolean isPlanningPeriod() {
		return false;
	}
}
