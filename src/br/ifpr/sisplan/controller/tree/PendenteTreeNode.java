package br.ifpr.sisplan.controller.tree;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.model.table.parent.DescriptionNode;

public abstract class PendenteTreeNode implements TreeNode {
	protected DescriptionNode descNode;

	public PendenteTreeNode(DescriptionNode node) {
		this.descNode = node;
	}
	
	public abstract String getName();

}
