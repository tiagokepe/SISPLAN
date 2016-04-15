package br.ifpr.sisplan.controller.tree;

import java.math.BigDecimal;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import org.richfaces.component.UITree;
import org.richfaces.component.html.HtmlTreeNode;
import org.richfaces.component.state.TreeState;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeExpandedListener;
import org.richfaces.model.TreeRowKey;

import br.ifpr.sisplan.controller.bean.PDIControllerBean;
import br.ifpr.sisplan.controller.bean.PeriodoPlanControllerBean;
import br.ifpr.sisplan.controller.bean.SisplanUser;
import br.ifpr.sisplan.controller.ifaces.RenderedJSFComponentsIface;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfoIface;
import br.ifpr.sisplan.model.table.parent.DescriptionNode;
import br.ufrn.arq.web.jsf.AbstractController;

public abstract class TreeNodeGeneric extends AbstractController implements TreeNode, TreeNodeInfoIface, NodeExpandedListener, RenderedJSFComponentsIface {
	private static final long serialVersionUID = 7504710891476763636L;
	protected TreeNodeGeneric parentNode;
	protected DescriptionNode descriptionNode;
	protected boolean opened = false;
	protected int order;
	
	public TreeNodeGeneric(TreeNodeGeneric parentNode, DescriptionNode descNode, int order) {
		this.parentNode = parentNode;
		this.descriptionNode = descNode;
		this.order = order;
	}
	
	public abstract String getStatusStyleClass();
	public abstract boolean isShowProgressStatus();
	
	public DescriptionNode getDescriptionNode() {
		return descriptionNode;
	}

	public boolean isOpened() {return opened;}
	public void setOpened(boolean opened) {this.opened = opened;}
	
	public BigDecimal getCustoPrevisto() {
		if(this instanceof EtapaTreeNode)
			return this.descriptionNode.getCustoPrevisto();
			
		Enumeration<TreeNodeGeneric> children = this.children();
		BigDecimal sum = new BigDecimal(0);
		BigDecimal newCusto;
		while(children.hasMoreElements()) {
			newCusto = children.nextElement().getCustoPrevisto();
			if(newCusto != null)
				sum = sum.add(newCusto);
		}
				
		return sum;
	}
	
	public BigDecimal getCustoEfetivo() {
		if(this instanceof EtapaTreeNode)
			return this.descriptionNode.getCustoEfetivo();
			
		Enumeration<TreeNodeGeneric> children = this.children();
		BigDecimal sum = new BigDecimal(0);
		BigDecimal newCusto;
		while(children.hasMoreElements()) {
			newCusto = children.nextElement().getCustoEfetivo();
			if(newCusto != null)
				sum = sum.add(newCusto);
		}
				
		return sum;
	}
	
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
	
	public void decreaseOrder() {
		this.order--;
	}
	
	protected boolean isPeriodoPlanAtivo() {
		return ((PeriodoPlanControllerBean)getMBean("periodoPlanControllerBean")).isPeriodoPlanAtivo();
	}
	
	protected boolean isPlanningManager() {
		return ((SisplanUser)getMBean("sisplanUser")).isPlanningManager();
	}
	
	protected boolean isResponsvelProjetoEtapa() {
		return ((SisplanUser)getMBean("sisplanUser")).isResponsavelProjetoEtapa();
	}
	
	public boolean isRenderedCadastrar() {
		if(this.isPlanningManager())
			return true;
		if(this.isPeriodoPlanAtivo() && !this.isResponsvelProjetoEtapa())
			return true;
		return false;
	}
	public boolean isRenderedAlterar() {
		if(this.isPlanningManager())
			return true;
		if(this.isPeriodoPlanAtivo() && !this.isResponsvelProjetoEtapa())
			return true;
		return false;		
	}
	public boolean isRenderedExcluir() {
		if(this.isPeriodoPlanAtivo() && !this.isResponsvelProjetoEtapa())
			return true;
		return false;
	}
	public boolean isRenderedCancelar() {
		return !this.isPeriodoPlanAtivo() && !this.isResponsvelProjetoEtapa();
	}
}
