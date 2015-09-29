package br.ifpr.sisplan.controller.ifaces;

public interface TreeNodeDetailsIface {
	public String getDescricao();
	public void setDescricao(String desc);
	public boolean isRenderedDescription();
	public boolean isRenderedCadastrar();
	public String getHeaderDetails();
	public String getDataInicioPrevista();
	public void setDataInicioPrevista(String strData); 
	public String getDataInicioEfetiva();
	public void setDataInicioEfetiva(String strData);
	public String getDataFimPrevista();
	public void setDataFimPrevista(String strData);
	public String getDataFimEfetiva();
	public void setDataFimEfetiva(String strData);
}
