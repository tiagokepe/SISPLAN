package br.ifpr.sisplan.model.table.parent;

import java.math.BigDecimal;

public class DescriptionNode extends NameNode {
	protected String descricao;
	protected BigDecimal custoPrevisto;
	protected BigDecimal custoEfetivo;
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public BigDecimal getCustoPrevisto() {
		return custoPrevisto;
	}

	public void setCustoPrevisto(BigDecimal custoPrevisto) {
		this.custoPrevisto = custoPrevisto;
	}

	public BigDecimal getCustoEfetivo() {
		return custoEfetivo;
	}

	public void setCustoEfetivo(BigDecimal custoEfetivo) {
		this.custoEfetivo = custoEfetivo;
	}
}