package br.ifpr.sisplan.controller;

import br.ifpr.sisplan.controller.tree.DiretrizTreeNode;
import br.ifpr.sisplan.controller.tree.EtapaTreeNode;
import br.ifpr.sisplan.controller.tree.ProjetoTreeNode;

public enum ProgressStatus {
	Blue("blueStatus", "/img/icons/blue-smile.png", "", ProjetoTreeNode.PROJ_LEGENDA_BLUE, EtapaTreeNode.ETAPA_LEGENDA_BLUE),
	Green("greenStatus", "/img/icons/green-smile.png", DiretrizTreeNode.DIR_LEGENDA_GREEN, ProjetoTreeNode.PROJ_LEGENDA_GREEN, EtapaTreeNode.ETAPA_LEGENDA_GREEN),
	Red("redStatus", "/img/icons/red-smile.png", DiretrizTreeNode.DIR_LEGENDA_RED, ProjetoTreeNode.PROJ_LEGENDA_RED, EtapaTreeNode.ETAPA_LEGENDA_RED), 
	Orange("orangeStatus", "/img/icons/orange-smile.png", DiretrizTreeNode.DIR_LEGENDA_ORANGE, ProjetoTreeNode.PROJ_LEGENDA_ORANGE),
	Default("rich-panel-header", "","",""),
	PLAN_Blue("blueStatus", "/img/icons/plan_blue.png", "", ProjetoTreeNode.PROJ_LEGENDA_BLUE, EtapaTreeNode.ETAPA_LEGENDA_BLUE),
	PLAN_Green("greenStatus", "/img/icons/plan/green.png", DiretrizTreeNode.DIR_LEGENDA_GREEN, ProjetoTreeNode.PROJ_LEGENDA_GREEN, EtapaTreeNode.ETAPA_LEGENDA_GREEN),
	PLAN_Red("redStatus", "/img/icons/plan/red.png", DiretrizTreeNode.DIR_LEGENDA_RED, ProjetoTreeNode.PROJ_LEGENDA_RED, EtapaTreeNode.ETAPA_LEGENDA_RED), 
	PLAN_Yellow("orangeStatus", "/img/icons/plan/yellow.png", DiretrizTreeNode.DIR_LEGENDA_ORANGE, ProjetoTreeNode.PROJ_LEGENDA_ORANGE),
	EXEC_Blue("blueStatus", "/img/icons/do/blue.png", "", ProjetoTreeNode.PROJ_LEGENDA_BLUE, EtapaTreeNode.ETAPA_LEGENDA_BLUE),
	EXEC_Green("greenStatus", "/img/icons/do/green.png", DiretrizTreeNode.DIR_LEGENDA_GREEN, ProjetoTreeNode.PROJ_LEGENDA_GREEN, EtapaTreeNode.ETAPA_LEGENDA_GREEN),
	EXEC_Red("redStatus", "/img/icons/do/red.png", DiretrizTreeNode.DIR_LEGENDA_RED, ProjetoTreeNode.PROJ_LEGENDA_RED, EtapaTreeNode.ETAPA_LEGENDA_RED), 
	EXEC_Yellow("orangeStatus", "/img/icons/do/yellow.png", DiretrizTreeNode.DIR_LEGENDA_ORANGE, ProjetoTreeNode.PROJ_LEGENDA_ORANGE);

	private String styleClass;
	private String iconPath;
	private String diretrizLegenda;
	private String projetoLegenda;
	private String etapaLegenda;
	ProgressStatus(String style, String icon, String dirLegenda, String projLegenda) {
		styleClass = style;
		iconPath = icon;
		diretrizLegenda = dirLegenda;
		projetoLegenda = projLegenda;
	}
	
	ProgressStatus(String style, String icon, String dirLegenda, String projLegenda, String etapa_legenda) {
		styleClass = style;
		iconPath = icon;
		diretrizLegenda = dirLegenda;
		projetoLegenda = projLegenda;
		etapaLegenda = etapa_legenda;
	}
	
	public String getStyleClass() {
		return styleClass;
	}

	public String getIconPath() {
		return iconPath;
	}

	public String getDiretrizLegenda() {
		return diretrizLegenda;
	}

	public String getProjetoLegenda() {
		return projetoLegenda;
	}
	
	public String getEtapaLegenda() {
		return etapaLegenda;
	}
}