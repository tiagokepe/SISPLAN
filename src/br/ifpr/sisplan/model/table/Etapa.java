package br.ifpr.sisplan.model.table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.ifpr.sisplan.model.table.parent.DateNode;

public class Etapa extends DateNode implements Serializable {
	private static final long serialVersionUID = -7711507910916895636L;
	private int id_projeto;
	private int idResponsavel;
	private BigDecimal custoPrevisto;
	private BigDecimal custoEfetivo;
	private String descricao;
	private boolean sentEmail;
	private Date firstEmail;
	
	public Etapa() {
		this.setType("etapa");
	}

	public int getId_projeto() {
		return id_projeto;
	}

	public void setId_projeto(int id_projeto) {
		this.id_projeto = id_projeto;
	}
	
	public int getIdResponsavel() {
		return idResponsavel;
	}

	public void setIdResponsavel(int id_responsavel) {
		this.idResponsavel = id_responsavel;
	}
	
	public BigDecimal getCustoPrevisto() {
		return custoPrevisto;
	}

	public void setCustoPrevisto(BigDecimal custoPrevisto) {
		this.custoPrevisto = custoPrevisto;
	}

	public BigDecimal getCustoEfetivo() {
		return custoEfetivo;
	}

	public void setCustoEfetivo(BigDecimal custoEfetivo) {
		this.custoEfetivo = custoEfetivo;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isSentEmail() {
		return sentEmail;
	}

	public void setSentEmail(boolean sent_email) {
		this.sentEmail = sent_email;
	}

	public Date getFirstEmail() {
		return firstEmail;
	}

	public void setFirstEmail(Date firstEmail) {
		this.firstEmail = firstEmail;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	public String getName() {
		return this.getDescricao();
	}
}