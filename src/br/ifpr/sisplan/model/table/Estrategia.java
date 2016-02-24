package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.model.table.parent.DescriptionNode;

public class Estrategia extends DescriptionNode implements Serializable {
	private static final long serialVersionUID = -958931312133384263L;
	private boolean ativo;

	public Estrategia() {
		this.setType("estrategia");
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
