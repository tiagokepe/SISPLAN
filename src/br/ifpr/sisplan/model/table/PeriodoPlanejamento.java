package br.ifpr.sisplan.model.table;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.ifpr.sisplan.util.DateUtil;

public class PeriodoPlanejamento {
	private int id;
	private Date dataInicio, dataFim;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public String getStrDataInicio() {
		return new SimpleDateFormat(DateUtil.DefaultDateFormat).format(this.dataInicio);
	}
	public String getStrDataFim() {
		return new SimpleDateFormat(DateUtil.DefaultDateFormat).format(this.dataFim);
	}
}
