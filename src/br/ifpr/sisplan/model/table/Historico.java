package br.ifpr.sisplan.model.table;

import java.io.Serializable;
import java.util.Date;

import br.ifpr.sisplan.util.DateUtil;

public class Historico implements Serializable {
	private static final long serialVersionUID = -4778519711504541763L;

	private Date timeStamp;
	private String responsavel;
	private String tipoComponente;
	private String detalhe;
	private String unidade;
	private String campoAlterado;
	private String de;
	private String para;
	
	public String getTimeStamp() {
		return DateUtil.timeStampToString(timeStamp);
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public String getTipoComponente() {
		return tipoComponente;
	}
	public void setTipoComponente(String tipoComponente) {
		this.tipoComponente = tipoComponente;
	}
	public String getDetalhe() {
		return detalhe;
	}
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public String getCampoAlterado() {
		return campoAlterado;
	}
	public void setCampoAlterado(String campoAlterado) {
		this.campoAlterado = campoAlterado;
	}
	public String getDe() {
		return de;
	}
	public void setDe(String de) {
		this.de = de;
	}
	public String getPara() {
		return para;
	}
	public void setPara(String para) {
		this.para = para;
	}
}
