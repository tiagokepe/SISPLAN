package br.ifpr.sisplan.controller.ifaces;

import java.util.Date;

public interface TreeNodeCallBackIface {
	public void setDescricaoCallBack(String desc);
	public void setDataInicioPrevistaCallBack(Date newDate);
	public void setDataInicioEfetivaCallBack(Date newDate);
	public void setDataFimPrevistaCallBack(Date newDate);
	public void setDataFimEfetivaCallBack(Date newDate);
}
