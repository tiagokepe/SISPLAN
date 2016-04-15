package br.ifpr.sisplan.controller.ifaces;

import java.math.BigDecimal;
import java.util.Date;

public interface TreeNodeCallBackIface {
	public void setNameCallBack(String name);
	public void setDescricaoCallBack(String desc);
	public void setDataInicioPrevistaCallBack(Date newDate);
	public void setDataInicioEfetivaCallBack(Date newDate);
	public void setDataFimPrevistaCallBack(Date newDate);
	public void setDataFimEfetivaCallBack(Date newDate);
	public void setCustoPrevistoCallBack(BigDecimal newCusto);
	public void setCustoEfetivoCallBack(BigDecimal newCusto);
	public void setResponsaveNamelCallBack(String newName);
}
