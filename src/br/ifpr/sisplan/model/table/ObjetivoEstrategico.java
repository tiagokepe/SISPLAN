package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.model.table.parent.NameNode;

public class ObjetivoEstrategico extends NameNode implements Serializable {

	private static final long serialVersionUID = 4859571185142033846L;
	
	public ObjetivoEstrategico() {
		this.setType("objetivoEstrategico");
	}

}
