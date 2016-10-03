package br.ifpr.sisplan.controller.tree.pendencies;

import br.ifpr.sisplan.controller.tree.PendenteTreeNode;
import br.ifpr.sisplan.model.table.Unidade;

public class UnidadePendenteTreeNode extends PendenteTreeNode {
	public UnidadePendenteTreeNode(Unidade unidade) {
		super(unidade);
	}

	@Override
	public String getName() {
		return this.descNode.getName();
	}
}
