package br.ifpr.sisplan.controller;

import java.util.Collection;

import br.ufrn.comum.dominio.Papel;

public enum Permission {
	PLANNING_MANAGER(21), CAMPUS_MANAGER(22), RESPONSALVEL_PROJETO_ETAPA(23);
	
	private int idPapel;
	
	Permission(int papel) {
		this.idPapel = papel;
	}
	
	public int getIdPapel() {
		return this.idPapel;
	}
	
	public boolean checkPermission(Collection<Papel> papeis) {
		for(Papel p: papeis) {
			if(p.getId() == this.idPapel)
				return true;
		}
		return false;
	}
}
