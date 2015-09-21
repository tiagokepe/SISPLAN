package br.ifpr.sisplan.controller.tree;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ifaces.TreeNodeDesc;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfo;
import br.ifpr.sisplan.model.table.parent.NameNode;
import br.ufrn.arq.web.jsf.AbstractController;

public abstract class TreeNodeGeneric extends AbstractController implements TreeNode, TreeNodeInfo, TreeNodeDesc {
	private static final long serialVersionUID = 7504710891476763636L;
	protected TreeNodeGeneric parentNode;
	protected NameNode nameNode;
	
	public TreeNodeGeneric(TreeNodeGeneric parentNode, NameNode nameNode) {
		this.parentNode = parentNode;
		this.nameNode = nameNode;
	}
}
