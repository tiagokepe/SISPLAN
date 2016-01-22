package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.model.table.parent.DescriptionNode;

public class Estrategia extends DescriptionNode implements Serializable {
	private static final long serialVersionUID = -958931312133384263L;

	public Estrategia() {
		this.setType("estrategia");
	}
}
