package br.ifpr.sisplan.controller.tree.pendencies;

import br.ifpr.sisplan.controller.tree.PendenteTreeNode;
import br.ifpr.sisplan.model.table.Diretriz;

public class DiretrizPendenteTreeNode extends PendenteTreeNode {
	
	public DiretrizPendenteTreeNode(Diretriz diretriz) {
		super(diretriz);
	}

	@Override
	public String getName() {
		return "Diretriz Organizacional " + this.descNode.getId();
	}

}
