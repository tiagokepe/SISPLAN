package br.ifpr.sisplan.controller;

public enum ProgressStatus {
	Blue("blueStatus"), Green("greenStatus"), Red("redStatus"), Orange("orangeStatus"), Default("rich-panel-header");
	
	private String styleClass;
	ProgressStatus(String style) {
		styleClass = style;
	}
	
	public String getStyleClass() {
		return styleClass;
	}

}
