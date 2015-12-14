package br.ifpr.sisplan.controller.tree;

import javax.swing.tree.TreeNode;

import org.richfaces.component.UITree;
import org.richfaces.component.html.HtmlTreeNode;
import org.richfaces.component.state.TreeState;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeExpandedListener;
import org.richfaces.model.TreeRowKey;

import br.ifpr.sisplan.controller.bean.PDIControllerBean;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfoIface;
import br.ifpr.sisplan.model.table.parent.NameNode;
import br.ufrn.arq.web.jsf.AbstractController;

public abstract class TreeNodeGeneric extends AbstractController implements TreeNode, TreeNodeInfoIface, NodeExpandedListener {
	private static final long serialVersionUID = 7504710891476763636L;
	protected TreeNodeGeneric parentNode;
	protected NameNode nameNode;
	protected boolean opened = false;
	protected int order;
	
	public TreeNodeGeneric(TreeNodeGeneric parentNode, NameNode nameNode, int order) {
		this.parentNode = parentNode;
		this.nameNode = nameNode;
		this.order = order;
	}
	
	public boolean isOpened() {return opened;}
	public void setOpened(boolean opened) {this.opened = opened;}
	
	public void processExpansion(NodeExpandedEvent arg0) {
		PDIControllerBean pdiController = (PDIControllerBean)this.getMBean("pdiControllerBean");
		HtmlTreeNode htmlNode = (HtmlTreeNode)arg0.getSource();
		UITree uitree = htmlNode.getUITree();
		if(((TreeState)uitree.getComponentState()).isExpanded((TreeRowKey)uitree.getRowKey()))
			pdiController.addExpandedNode(this.getRowKey());
		else
			pdiController.removeExpandedNode(this.getRowKey());
    }
	
	public String getRowKey() {
		if(this.parentNode == null)
			return ""+this.order;
		else
			return this.parentNode.getRowKey() + ":"+ this.order;
	}
/*	
	public boolean equals(TreeNodeGeneric o) {
		return this.getName().equals(o.getName());
	}*/

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
