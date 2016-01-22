package br.ifpr.sisplan.model.table;

import br.ifpr.sisplan.model.table.parent.NameNode;

public class Responsavel extends NameNode {
	private int id_unidade;
	private String name;
	
	public Responsavel() {
		this.setType("Responsavel");
	}

	public int getId_unidade() {
		return id_unidade;
	}

	public void setId_unidade(int id_unidade) {
		this.id_unidade = id_unidade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}