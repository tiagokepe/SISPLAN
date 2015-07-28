package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.util.NameNode;

public class Diretriz extends NameNode implements Serializable {
	private static final long serialVersionUID = 3844595706031437005L;

	public Diretriz() {
		this.setType("diretriz");
	}
}
