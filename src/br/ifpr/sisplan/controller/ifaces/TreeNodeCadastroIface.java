package br.ifpr.sisplan.controller.ifaces;

import br.ifpr.sisplan.controller.tree.TreeNodeGeneric;

public interface TreeNodeCadastroIface {
	public String getCadastroURL();
	public String getCadastroTitle();
	public void addTreeNodeChild(TreeNodeGeneric child);

}
