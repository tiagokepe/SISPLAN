package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.model.table.parent.DescriptionNode;

public class Unidade extends DescriptionNode implements Serializable, Comparable<Unidade> {
	private static final long serialVersionUID = -8530969036750687780L;
	
	public Unidade() {
		this.setType("unidade");
	}
	
	public Unidade(String unidadeName) {
		this.name = unidadeName;
	}
	
/*	public String getName() {
		return name;
	}*/

	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(Unidade o) {
		return this.getName().compareTo(o.getName());
	}
	
	@Override
	public boolean equals(Object other) {
		return this.name.equals(((Unidade)other).getName());
	}

}
