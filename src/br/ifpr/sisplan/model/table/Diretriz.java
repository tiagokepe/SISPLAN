package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.model.table.parent.DescriptionNode;

public class Diretriz extends DescriptionNode implements Serializable {
	private static final long serialVersionUID = 3844595706031437005L;

	public Diretriz() {
		this.setType("diretriz");
	}
}
