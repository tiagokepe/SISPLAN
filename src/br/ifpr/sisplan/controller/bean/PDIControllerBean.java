package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeSelectedEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ifpr.sisplan.controller.ifaces.TreeNodeDetails;
import br.ifpr.sisplan.controller.tree.PDITreeNode;
import br.ifpr.sisplan.controller.tree.UnidadeTreeNode;
import br.ifpr.sisplan.model.dao.PDIDao;
import br.ifpr.sisplan.model.table.PDI;
import br.ifpr.sisplan.util.ConverterToList;
import br.ufrn.arq.web.jsf.AbstractController;

@Component
@Scope("request")
public class PDIControllerBean extends AbstractController {
	private static final long serialVersionUID = 501774684540273826L;
	private TreeNode currentNodeSelection = null;
	private boolean rendered, renderedHintPanel;

	public PDIControllerBean() {
	}
	
	private List<TreeNode> pdis;
	
	public List<TreeNode> getPdis() {
		this.pdis = new ArrayList<TreeNode>();
		final List<PDI> listPDIs = ConverterToList.convertListMappedToList(getDAO(PDIDao.class).selectAll(), PDI.class);
		
		for(PDI pdi: listPDIs) {
			PDITreeNode pdiTree = new PDITreeNode(pdi);
			pdis.add(pdiTree);
		}
		return pdis;
	}
	
	public void nodeSelected(NodeSelectedEvent event) {
		HtmlTree tree = (HtmlTree)event.getSource();
		this.currentNodeSelection = (TreeNode)tree.getRowData();
		if(this.currentNodeSelection instanceof TreeNodeDetails) {
			this.rendered = true;
			this.renderedHintPanel = false;
			return;
		}
		else if( !(this.currentNodeSelection instanceof UnidadeTreeNode)) {
			this.rendered = false;
			this.renderedHintPanel = true;
			return;
		}
		this.rendered = false;
		this.renderedHintPanel = false;
	}

	public TreeNode getCurrentNodeSelection() {
		return currentNodeSelection;
	}
	
    public void setCurrentNodeSelection(TreeNode currentNode) {
        this.currentNodeSelection = currentNode;
    }
	
    public boolean isRendered() {
    	return this.rendered;
    }
    
    public boolean isRenderedHintPanel() {
    	return this.renderedHintPanel;
    }
}