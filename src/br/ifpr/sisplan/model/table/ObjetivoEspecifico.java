package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.util.NameNode;

public class ObjetivoEspecifico extends NameNode implements Serializable {
	private static final long serialVersionUID = 4640817509642335970L;

	public ObjetivoEspecifico() {
		this.setType("objetivoEspecifico");
	}
}