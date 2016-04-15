package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.model.table.Unidade;

import com.google.common.collect.Iterators;

public class UnidadePendenteTreeNode extends PendenteTreeNode {
	private List<DiretrizPendenteTreeNode> diretrizesTree = new ArrayList<DiretrizPendenteTreeNode>();
	
	public UnidadePendenteTreeNode(Unidade u, List<DiretrizPendenteTreeNode> diretrizes) {
		super(u);
		this.diretrizesTree = diretrizes;
	}
	
	public TreeNode getChildAt(int childIndex) {
		return this.diretrizesTree.get(childIndex);
	}

	public int getChildCount() {
		return this.diretrizesTree.size();
	}

	public TreeNode getParent() {
		return null;
	}

	public int getIndex(TreeNode node) {
		return this.diretrizesTree.indexOf(node);
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public boolean isLeaf() {
		return false;
	}

	public Enumeration children() {
		return Iterators.asEnumeration(this.diretrizesTree.iterator());
	}

	@Override
	public String getName() {
		return this.descNode.getName();
	}
	

}
