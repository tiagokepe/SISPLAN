package br.ifpr.sisplan.model.table;

import java.io.Serializable;

import br.ifpr.sisplan.model.table.parent.NameNode;

public class PDI extends NameNode implements Serializable {
	private static final long serialVersionUID = -1790778840519353771L;
	private String epoch;
	
	public PDI() {
		this.setType("pdi");
	}
	
	public String getEpoch() {
		return epoch;
	}
	public void setEpoch(String epoch) {
		this.epoch = epoch;
	}
}