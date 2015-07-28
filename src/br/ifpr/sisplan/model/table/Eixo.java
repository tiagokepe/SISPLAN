package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.util.NameNode;

public class Eixo extends NameNode implements Serializable {
	
	private static final long serialVersionUID = -8694250556405860271L;

	public Eixo() {
		this.setType("eixo");
	}

}
