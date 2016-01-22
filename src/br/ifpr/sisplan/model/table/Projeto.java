package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.model.table.parent.DateNode;

public class Projeto extends DateNode implements Serializable {
	private static final long serialVersionUID = 8291960368270505551L;
	private int idResponsavel;
	
	public Projeto() {
		this.setType("projeto");
	}

	public int getIdResponsavel() {
		return idResponsavel;
	}

	public void setIdResponsavel(int id_responsavel) {
		this.idResponsavel = id_responsavel;
	}
}