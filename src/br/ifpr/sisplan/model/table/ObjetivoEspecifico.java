package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.model.table.parent.DescriptionNode;

public class ObjetivoEspecifico extends DescriptionNode implements Serializable {
	private static final long serialVersionUID = 4640817509642335970L;
	private boolean ativo;
	
	public ObjetivoEspecifico() {
		this.setType("objetivoEspecifico");
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}