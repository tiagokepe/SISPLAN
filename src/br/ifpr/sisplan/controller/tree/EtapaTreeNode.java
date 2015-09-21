package br.ifpr.sisplan.controller.tree;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ifaces.TreeNodeDetails;
import br.ifpr.sisplan.model.table.Etapa;

public class EtapaTreeNode extends TreeNodeCallBack implements TreeNodeDetails {
	private static final long serialVersionUID = -9205942028545960131L;
	
	public EtapaTreeNode(TreeNodeGeneric parent, Etapa etapa) {
		super(parent, etapa);
	}
	
	public TreeNode getChildAt(int paramInt) {
		return null;
	}

	public int getChildCount() {
		return 0;
	}

	public TreeNode getParent() {
		return this.parentNode;
	}

	public int getIndex(TreeNode paramTreeNode) {
		return 0;
	}

	public boolean getAllowsChildren() {
		return false;
	}

	public boolean isLeaf() {
		return true;
	}

	public Enumeration<TreeNode> children() {
        return new Enumeration<TreeNode>() {
            public boolean hasMoreElements() {
                return false;
            }
 
            public TreeNode nextElement() {
                return null;
            }
        };
	}
	
	@Override
	public String toString() {
		return this.dataNode.toString();
	}

	public String getType() {
		return this.dataNode.getType();
	}

	public String getName() {
		return this.dataNode.getName();
	}
	
	public boolean isRenderedDescription() {
		return false;
	}
	
	public String getHeaderDetails() {
		return "Informações de Etapa";
	}

	public int getMyID() {
		return this.getDataNode().getId();
	}

	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}
}