package br.ifpr.sisplan.model.table;

import java.io.Serializable;
import java.util.Date;

import br.ifpr.sisplan.util.NameNode;

public class Data extends NameNode implements Serializable {
	private Date dataInicioPrevista, dataInicioEfetiva, dataFimPrevista, dataFimEfetiva;
	public Data() {
		this.setType("data");
	}
	
	public Date getDataInicioPrevista() {
		return dataInicioPrevista;
	}
	public void setDataInicioPrevista(Date dataInicioPrevista) {
		this.dataInicioPrevista = dataInicioPrevista;
	}
	public Date getDataInicioEfetiva() {
		return dataInicioEfetiva;
	}
	public void setDataInicioEfetiva(Date dataInicioEfetiva) {
		this.dataInicioEfetiva = dataInicioEfetiva;
	}
	public Date getDataFimPrevista() {
		return dataFimPrevista;
	}
	public void setDataFimPrevista(Date dataFimPrevista) {
		this.dataFimPrevista = dataFimPrevista;
	}
	public Date getDataFimEfetiva() {
		return dataFimEfetiva;
	}
	public void setDataFimEfetiva(Date dataFimEfetiva) {
		this.dataFimEfetiva = dataFimEfetiva;
	}
}
