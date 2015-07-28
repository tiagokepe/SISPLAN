package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.util.NameNode;

public class Etapa extends NameNode implements Serializable {
	private static final long serialVersionUID = -7711507910916895636L;
	private String descricao;
	private int id_projeto;
	private Data data;
	
	public Etapa() {
		this.setType("etapa");
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public int getId_projeto() {
		return id_projeto;
	}

	public void setId_projeto(int id_projeto) {
		this.id_projeto = id_projeto;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
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