package br.ifpr.sisplan.controller.ifaces;

import br.ifpr.sisplan.controller.tree.TreeNodeGeneric;
import br.ifpr.sisplan.model.table.parent.DescriptionNode;

public abstract class TreeNodeCadastroAbstract extends TreeNodeGeneric implements TreeNodeCadastroIface {
	private static final long serialVersionUID = 8160287887269249395L;

	public TreeNodeCadastroAbstract(TreeNodeGeneric parentNode, DescriptionNode descNode, int order) {
		super(parentNode, descNode, order);
	}

	public void returnMainPage() {
		this.redirect("/portal/index.jsf");
	}
}
