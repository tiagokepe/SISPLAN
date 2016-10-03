package br.ifpr.sisplan.controller;

import br.ifpr.sisplan.model.table.Unidade;

public class UnidadeStatus {
	private Unidade unidade;
	private ProgressStatus status;
	
	public UnidadeStatus(Unidade u) {
		this.unidade = u;
	}

	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	public ProgressStatus getStatus() {
		return status;
	}
	public void setStatus(ProgressStatus status) {
		this.status = status;
	}
}