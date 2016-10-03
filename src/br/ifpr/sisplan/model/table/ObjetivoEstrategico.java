package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.model.table.parent.DescriptionNode;

public class ObjetivoEstrategico extends DescriptionNode implements Serializable, Comparable<ObjetivoEstrategico> {

	private static final long serialVersionUID = 4859571185142033846L;
	
	public ObjetivoEstrategico() {
		this.setType("objetivoEstrategico");
	}
	
	public int compareTo(ObjetivoEstrategico o) {
		if(this.getId() == o.getId())
			return 0;
		return this.getId() < o.getId()? -1: 1;
	}

}
