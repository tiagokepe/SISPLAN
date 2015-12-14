package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.model.table.parent.DateDescriptionNode;

public class Etapa extends DateDescriptionNode implements Serializable {
	private static final long serialVersionUID = -7711507910916895636L;
	private int id_projeto;
	private int idResponsavel;
	
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
	
	@Override
	public String toString() {
		return this.descricao;
	}
	
	@Override
	public String getName() {
		return this.descricao;
	}
}