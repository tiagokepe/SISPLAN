package br.ifpr.sisplan.controller.ifaces;

import br.ifpr.sisplan.controller.tree.TreeNodeGeneric;
import br.ifpr.sisplan.model.table.parent.NameNode;

public abstract class TreeNodeCadastroAbstract extends TreeNodeGeneric implements TreeNodeCadastroIface {
	private static final long serialVersionUID = 8160287887269249395L;

	public TreeNodeCadastroAbstract(TreeNodeGeneric parentNode, NameNode nameNode, int order) {
		super(parentNode, nameNode, order);
	}

/*	public boolean equals(TreeNodeGeneric o) {
		return this.parentNode.getName().equals(o.getName());
	}*/

/*	public boolean equals(ObjetivoEstrategicoTreeNode o) {
		return this.parentNode.getName().equals(o.getName());
	}*/

}
