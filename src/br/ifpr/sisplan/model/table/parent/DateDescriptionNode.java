package br.ifpr.sisplan.model.table.parent;

import br.ifpr.sisplan.model.table.Data;

public class DateDescriptionNode extends NameNode {
	protected String descricao;
	protected Data data;
	
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
