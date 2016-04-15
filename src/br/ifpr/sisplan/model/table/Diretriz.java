package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.model.table.parent.DescriptionNode;

public class Diretriz extends DescriptionNode implements Serializable, Comparable<Diretriz> {
	private static final long serialVersionUID = 3844595706031437005L;

	public Diretriz() {
		this.setType("diretriz");
	}

	public int compareTo(Diretriz o) {
		if(this.getId() == o.getId())
			return 0;
		return this.getId() < o.getId()? -1: 1;
	}
}
