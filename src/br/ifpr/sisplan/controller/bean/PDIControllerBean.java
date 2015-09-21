package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeSelectedEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ifpr.sisplan.controller.ifaces.TreeNodeDetails;
import br.ifpr.sisplan.controller.tree.EstrategiaTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEspecificoTreeNode;
import br.ifpr.sisplan.controller.tree.PDITreeNode;
import br.ifpr.sisplan.controller.tree.UnidadeTreeNode;
import br.ifpr.sisplan.model.dao.PDIDao;
import br.ifpr.sisplan.model.table.PDI;
import br.ifpr.sisplan.util.ConverterToList;
import br.ufrn.arq.web.jsf.AbstractController;

@Component
@Scope("session")
public class PDIControllerBean extends AbstractController {
	private static final long serialVersionUID = 501774684540273826L;
	private TreeNode currentNodeSelection = null;
	private boolean rendered, renderedDescPanel, renderedCadastroPanel;
	private PDITreeNode currentPDI; 
	private List<TreeNode> pdis = new ArrayList<TreeNode>();

	public PDIControllerBean() {
	}
	
	public List<TreeNode> getPdis() {
		if(this.pdis.isEmpty()) {
			final List<PDI> listPDIs = ConverterToList.convertListMappedToList(getDAO(PDIDao.class).selectAll(), PDI.class);
			
			for(PDI pdi: listPDIs) {
				PDITreeNode pdiTree = new PDITreeNode(pdi);
				pdis.add(pdiTree);
			}
			if(pdis.size() > 0)
				this.setCurrentPDI((PDITreeNode)pdis.get(pdis.size()-1));
		}
		return this.pdis;
	}
	
	public void nodeSelected(NodeSelectedEvent event) {
		HtmlTree tree = (HtmlTree)event.getSource();
		this.currentNodeSelection = (TreeNode)tree.getRowData();
		if(this.currentNodeSelection instanceof TreeNodeDetails) {
			this.rendered = true;
			this.renderedDescPanel = false;
			this.renderedCadastroPanel = false;
			return;
		}
		else if( !(this.currentNodeSelection instanceof UnidadeTreeNode || this.currentNodeSelection instanceof ObjetivoEspecificoTreeNode || this.currentNodeSelection instanceof EstrategiaTreeNode)) {
			this.rendered = false;
			this.renderedDescPanel = true;
			this.renderedCadastroPanel= false;
			return;
		}
		
		this.rendered = false;
		this.renderedDescPanel = false;
		this.renderedCadastroPanel = true;
	}

	public TreeNode getCurrentNodeSelection() {
		return currentNodeSelection;
	}
	
    public void setCurrentNodeSelection(TreeNode currentNode) {
        this.currentNodeSelection = currentNode;
    }
	
    public PDITreeNode getCurrentPDI() {
    	if(this.currentPDI == null)
    		this.getPdis();
		return currentPDI;
	}

	public void setCurrentPDI(PDITreeNode currentPDI) {
		this.currentPDI = currentPDI;
	}

	public boolean isRendered() {
    	return this.rendered;
    }
    
    public boolean isRenderedDescPanel() {
    	return this.renderedDescPanel;
    }
    
    public boolean isrenderedCadastroPanel() {
    	return this.renderedCadastroPanel;
    }

	public void goToNovoObjetivo() {
		this.redirect("/portal/novo_objetivo.jsf");
	}

	public void goToNovoProjeto() {
		this.redirect("/portal/novo_projeto.jsf");
	}
	
	public void returnMainPage() {
		this.redirect("/portal/index.jsf");
	}
}