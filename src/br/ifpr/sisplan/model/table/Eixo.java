package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.model.table.parent.DescriptionNode;

public class Eixo extends DescriptionNode implements Serializable {
	
	private static final long serialVersionUID = -8694250556405860271L;

	public Eixo() {
		this.setType("eixo");
	}

}
