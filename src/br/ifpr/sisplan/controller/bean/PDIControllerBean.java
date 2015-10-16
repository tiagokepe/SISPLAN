package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.swing.tree.TreeNode;

import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeSelectedEvent;

import br.ifpr.sisplan.controller.ifaces.TreeNodeDetailsIface;
import br.ifpr.sisplan.controller.tree.EstrategiaTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEspecificoTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEstrategicoTreeNode;
import br.ifpr.sisplan.controller.tree.PDITreeNode;
import br.ifpr.sisplan.model.dao.PDIDao;
import br.ifpr.sisplan.model.table.PDI;
import br.ifpr.sisplan.model.table.Unidade;
import br.ifpr.sisplan.util.ConverterToList;
import br.ufrn.arq.web.jsf.AbstractController;

public class PDIControllerBean extends AbstractController {
	private static final long serialVersionUID = 501774684540273826L;
	private TreeNode currentNodeSelection = null;
	private boolean rendered, renderedDescPanel, renderedCadastroPanel, objEspecificoPanel;
	private Unidade unidadeSelected;
	private boolean changedUnidade = false;
	private List<TreeNode> pdisTree;
	private PDITreeNode currentPDI;
	List<SelectItem> listUnidades;

	public PDIControllerBean() {
		System.out.println("******PDIControllerBean()");
		this.unidadeSelected = PDIControllerCached.getInstance().getUnidadeAll();
		this.setListUnidades();
	}
	
    public PDITreeNode getCurrentPDI() {
    	if(this.currentPDI == null)
    		this.buildPdisTree();
		return currentPDI;
	}
    
    public void buildPdisTree() {
		final List<PDI> listPDIs = ConverterToList.convertListMappedToList(getDAO(PDIDao.class).selectAll(), PDI.class);
		this.pdisTree = new ArrayList<TreeNode>();
		for(PDI pdi: listPDIs) {
			PDITreeNode pdiTree = new PDITreeNode(pdi);
			pdisTree.add(pdiTree);
		}
		if(pdisTree.size() > 0)
			this.setCurrentPDI((PDITreeNode)pdisTree.get(pdisTree.size()-1));
    }
	
	public List<TreeNode> getPdisTree() {
		if(this.changedUnidade || this.pdisTree == null) {
			this.buildPdisTree();
			this.changedUnidade = false;
		}
		return this.pdisTree;
	}
	
	public void setCurrentPDI(PDITreeNode currentPDI) {
		this.currentPDI = currentPDI;
	}
	
	public void nodeSelected(NodeSelectedEvent event) {
		this.rendered = false;
		this.renderedDescPanel = false;
		this.renderedCadastroPanel = false;
		this.objEspecificoPanel = false;
		
		HtmlTree tree = (HtmlTree)event.getSource();
		this.currentNodeSelection = ((TreeNode)tree.getRowData());
		if(this.currentNodeSelection instanceof TreeNodeDetailsIface) {
			this.rendered = true;
			return;
		}
		else if( !(this.currentNodeSelection instanceof ObjetivoEstrategicoTreeNode ||
				   this.currentNodeSelection instanceof ObjetivoEspecificoTreeNode ||
				   this.currentNodeSelection instanceof EstrategiaTreeNode)) {
			this.renderedDescPanel = true;
			return;
		} else if(this.currentNodeSelection instanceof ObjetivoEspecificoTreeNode) {
			this.objEspecificoPanel = true;
			return;
		}
		
		this.renderedCadastroPanel = true;
	}

	public TreeNode getCurrentNodeSelection() {
		return currentNodeSelection;
	}
	
    public void setCurrentNodeSelection(TreeNode currentNode) {
        this.currentNodeSelection = currentNode;
    }
	
/*    public PDITreeNode getCurrentPDI() {
    	return PDIControllerCached.getInstance().getCurrentPDI();
	}*/

	public boolean isRendered() {
    	return this.rendered;
    }
    
    public boolean isRenderedDescPanel() {
    	return this.renderedDescPanel;
    }
    
    public boolean isrenderedCadastroPanel() {
    	return this.renderedCadastroPanel;
    }
    
    public boolean isObjEspecificoPanel() {
    	return this.objEspecificoPanel;
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

	public Unidade getUnidadeSelected() {
		return unidadeSelected;
	}

	public void setUnidadeSelected(Unidade unidadeSelected) {
		this.unidadeSelected = unidadeSelected;
	}
	
	public Unidade getUnidadeAll() {
		return PDIControllerCached.getInstance().getUnidadeAll();
	}
	
	public List<SelectItem> getListUnidades() {
/*		List<SelectItem> unidades = new ArrayList<SelectItem>();
		for(Unidade u: ((SisplanUser)this.getMBean("sisplanUser")).getUnidades())
			unidades.add(new SelectItem(u.toString(), u.toString()));
		return unidades;*/
		return this.listUnidades;
	}
	
	private void setListUnidades() {
		this.listUnidades = new ArrayList<SelectItem>();
		this.listUnidades.add(new SelectItem(getUnidadeAll().toString(), getUnidadeAll().toString()));
		for(Unidade u: ((SisplanUser)this.getMBean("sisplanUser")).getUnidades())
			this.listUnidades.add(new SelectItem(u.toString(), u.toString()));
	}

	public void unidadeSelectedListener(ValueChangeEvent e) {
		String unidadeName = (String)e.getNewValue();
		/* Checks if unidadeSelected was changed.*/
		if(!this.unidadeSelected.getName().equals(unidadeName)) {
			this.changedUnidade = true;
			this.unidadeSelected = PDIControllerCached.getInstance().getUnidade(unidadeName);
			if(unidadeName.equals(this.getUnidadeAll().getName()))
				this.unidadeSelected = this.getUnidadeAll();
			return;
		}
		this.changedUnidade = false;
		System.out.println("AAA");
	}
}