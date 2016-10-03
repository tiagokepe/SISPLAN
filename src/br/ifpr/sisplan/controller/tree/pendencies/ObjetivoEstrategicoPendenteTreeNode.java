package br.ifpr.sisplan.controller.tree.pendencies;

import br.ifpr.sisplan.controller.tree.PendenteTreeNode;
import br.ifpr.sisplan.model.table.ObjetivoEstrategico;

public class ObjetivoEstrategicoPendenteTreeNode extends PendenteTreeNode  {
	
	public ObjetivoEstrategicoPendenteTreeNode(ObjetivoEstrategico objetivo) {
		super(objetivo);
	}

	@Override
	public String getName() {
		return "Objetivo Estratégico " + this.descNode.getId();
	}

}
