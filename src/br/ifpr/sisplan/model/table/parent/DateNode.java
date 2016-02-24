package br.ifpr.sisplan.model.table.parent;

import java.math.BigDecimal;

import br.ifpr.sisplan.model.table.Data;

public class DateNode extends DescriptionNode {
	protected Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}