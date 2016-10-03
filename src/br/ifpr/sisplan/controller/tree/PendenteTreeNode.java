package br.ifpr.sisplan.controller.tree;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.model.table.parent.DescriptionNode;

public abstract class PendenteTreeNode implements TreeNode {
	protected DescriptionNode descNode;

	public PendenteTreeNode(DescriptionNode node) {
		this.descNode = node;
	}
	
	public abstract String getName();
	
	public int getMaxValue() {
		return -1;
	}
	
	public boolean isEnabled() {
		return false;
	}
	
	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getChildCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public TreeNode getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getIndex(TreeNode node) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return false;
	}

	public Enumeration children() {
        return new Enumeration<TreeNode>() {
            public boolean hasMoreElements() {
                return false;
            }
 
            public TreeNode nextElement() {
                return null;
            }
        };
	}

}
