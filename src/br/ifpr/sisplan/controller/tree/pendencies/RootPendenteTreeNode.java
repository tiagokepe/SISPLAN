package br.ifpr.sisplan.controller.tree.pendencies;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.PDIControllerCached;
import br.ifpr.sisplan.controller.tree.PendenteTreeNode;
import br.ifpr.sisplan.model.table.ObjetivoEstrategico;
import br.ifpr.sisplan.model.table.parent.DescriptionNode;

import com.google.common.collect.Iterators;

public class RootPendenteTreeNode extends PendenteTreeNode {
	private List<PendenteTreeNode> kidsTree = new ArrayList<PendenteTreeNode>();
	private int currentValue;
	private int maxValue;
	
	public RootPendenteTreeNode(DescriptionNode d, List<PendenteTreeNode> kids) {
		super(d);
		this.kidsTree = kids;
		maxValue = PDIControllerCached.getInstance().getListUnidades().size();
		currentValue = maxValue - kidsTree.size();
	}
	
    public int getCurrentValue() {
        return currentValue;
    }
    
	@Override
	public int getMaxValue() {
		return maxValue;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public TreeNode getChildAt(int childIndex) {
		return this.kidsTree.get(childIndex);
	}

	public int getChildCount() {
		return this.kidsTree.size();
	}

	public TreeNode getParent() {
		return null;
	}

	public int getIndex(TreeNode node) {
		return this.kidsTree.indexOf(node);
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public boolean isLeaf() {
		return false;
	}

	public Enumeration children() {
		return Iterators.asEnumeration(this.kidsTree.iterator());
	}

	@Override
	public String getName() {
		if(this.descNode instanceof ObjetivoEstrategico)
			return "Objetivo Estratégico " + this.descNode.getId();
		return this.descNode.getName();
	}
	

}
