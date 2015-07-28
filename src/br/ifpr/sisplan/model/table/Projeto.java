package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.util.NameNode;

public class Projeto extends NameNode implements Serializable {
	private static final long serialVersionUID = 8291960368270505551L;
	private String descricao;
	private Data data;
	
	public Projeto() {
		this.setType("projeto");
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
}