package br.ifpr.sisplan.controller;

import br.ifpr.sisplan.controller.tree.DiretrizTreeNode;
import br.ifpr.sisplan.controller.tree.ProjetoTreeNode;

public enum ProgressStatus {
	Blue("blueStatus", "/img/icons/blue-smile.png", "", ProjetoTreeNode.PROJ_LEGENDA_BLUE),
	Green("greenStatus", "/img/icons/green-smile.png", DiretrizTreeNode.DIR_LEGENDA_GREEN, ProjetoTreeNode.PROJ_LEGENDA_GREEN),
	Red("redStatus", "/img/icons/red-smile.png", DiretrizTreeNode.DIR_LEGENDA_RED, ProjetoTreeNode.PROJ_LEGENDA_RED), 
	Orange("orangeStatus", "/img/icons/orange-smile.png", DiretrizTreeNode.DIR_LEGENDA_ORANGE, ProjetoTreeNode.PROJ_LEGENDA_ORANGE),
	Default("rich-panel-header", "","","");

	private String styleClass;
	private String iconPath;
	private String diretrizLegenda;
	private String projetoLegenda;
	ProgressStatus(String style, String icon, String dirLegenda, String projLegenda) {
		styleClass = style;
		iconPath = icon;
		diretrizLegenda = dirLegenda;
		projetoLegenda = projLegenda;
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
}