package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.util.NameNode;

public class Unidade extends NameNode implements Serializable {
	private static final long serialVersionUID = -8530969036750687780L;
	
	public Unidade() {
		this.setType("unidade");
	}

}
