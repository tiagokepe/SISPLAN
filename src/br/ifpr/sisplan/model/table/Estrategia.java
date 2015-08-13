package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.model.table.parent.NameNode;

public class Estrategia extends NameNode implements Serializable {
	private static final long serialVersionUID = -958931312133384263L;
	int id_objetivo;

	public Estrategia() {
		this.setType("estrategia");
	}
	
	public int getId_objetivo() {
		return id_objetivo;
	}

	public void setId_objetivo(int id_objetivo) {
		this.id_objetivo = id_objetivo;
	}

}
