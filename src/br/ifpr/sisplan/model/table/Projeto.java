package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.model.table.parent.DateDescriptionNode;

public class Projeto extends DateDescriptionNode implements Serializable {
	private static final long serialVersionUID = 8291960368270505551L;
	
	public Projeto() {
		this.setType("projeto");
	}
}