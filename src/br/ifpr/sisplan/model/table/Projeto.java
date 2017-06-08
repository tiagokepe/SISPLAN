package br.ifpr.sisplan.model.table;

import java.io.Serializable;
import java.util.Date;

import br.ifpr.sisplan.model.table.parent.DateNode;

public class Projeto extends DateNode implements Serializable {
	private static final long serialVersionUID = 8291960368270505551L;
	private int idResponsavel;
	private boolean ativo;
	private boolean sentEmail;
	private Date firstEmail;
	
	public Projeto() {
		this.setType("projeto");
	}

	public int getIdResponsavel() {
		return idResponsavel;
	}

	public void setIdResponsavel(int id_responsavel) {
		this.idResponsavel = id_responsavel;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
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
}