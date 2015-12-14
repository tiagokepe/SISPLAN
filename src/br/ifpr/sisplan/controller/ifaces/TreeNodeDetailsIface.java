package br.ifpr.sisplan.controller.ifaces;

public interface TreeNodeDetailsIface {
	public String getDescricao();
	public void setDescricao(String desc);
	public boolean isProjectNode();
	public boolean isRenderedCadastrar();
	public String getHeaderDetails();
	public String getStrDataInicioPrevista();
	public void setDataInicioPrevista(String strData); 
	public String getStrDataInicioEfetiva();
	public void setDataInicioEfetiva(String strData);
	public String getStrDataFimPrevista();
	public void setDataFimPrevista(String strData);
	public String getStrDataFimEfetiva();
	public void setDataFimEfetiva(String strData);
}
