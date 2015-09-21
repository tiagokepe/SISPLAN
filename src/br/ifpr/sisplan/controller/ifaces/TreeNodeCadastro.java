package br.ifpr.sisplan.controller.ifaces;

import br.ifpr.sisplan.controller.tree.TreeNodeGeneric;
import br.ifpr.sisplan.model.table.parent.NameNode;

public abstract class TreeNodeCadastro extends TreeNodeGeneric implements TreeNodeDesc {
	private static final long serialVersionUID = 8160287887269249395L;

	public TreeNodeCadastro(TreeNodeGeneric parentNode, NameNode nameNode) {
		super(parentNode, nameNode);
	}
	
	public abstract String getCadastroURL();
	public abstract String getCadastroTitle();
	public abstract void addTreeNodeChild(TreeNodeGeneric child);
}
