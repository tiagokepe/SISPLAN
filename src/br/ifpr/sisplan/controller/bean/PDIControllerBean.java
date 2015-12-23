package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.swing.tree.TreeNode;

import org.richfaces.component.UITree;
import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeSelectedEvent;

import br.ifpr.sisplan.controller.PDIControllerCached;
import br.ifpr.sisplan.controller.tree.DiretrizTreeNode;
import br.ifpr.sisplan.controller.tree.EixoTreeNode;
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
	private boolean renderPanel = false;
	private Unidade unidadeSelected;
/*	private boolean changedUnidade = false;*/
	private PDITreeNode currentPDI;
	private List<SelectItem> listUnidades;
	private SortedSet<String> setExpandedNode = new TreeSet<String>();
	private List<TreeNode> pdisTreeAll;
/*	private List<TreeNode> pdisTreeFiltered;*/
	
	public PDIControllerBean() {
		SisplanUser sisplanUser = (SisplanUser)this.getMBean("sisplanUser");
		if(sisplanUser.isPlanningManager())
			this.unidadeSelected = PDIControllerCached.getInstance().getUnidadeAll();
		else
			this.unidadeSelected = sisplanUser.getUnidades().get(0);
		this.setListUnidades();
	}
	
    public PDITreeNode getCurrentPDI() {
    	if(this.currentPDI == null)
    		//Não fazer isso, manter os mesmos objetos apenas atualizar a lista conforme o campus selecionado
    		this.buildPdisTree();
		return currentPDI;
	}
    
    public void buildPdisTree() {
		final List<PDI> listPDIs = ConverterToList.convertListMappedToList(getDAO(PDIDao.class).selectAll(), PDI.class);
		this.pdisTreeAll = new ArrayList<TreeNode>();
		int i=0;
		for(PDI pdi: listPDIs) {
			PDITreeNode pdiTree = new PDITreeNode(pdi, i++);
			pdisTreeAll.add(pdiTree);
		}
		if(pdisTreeAll.size() > 0)
			this.setCurrentPDI((PDITreeNode)pdisTreeAll.get(pdisTreeAll.size()-1));
    }
	
	public List<TreeNode> getPdisTree() {
		if(pdisTreeAll == null)
			this.buildPdisTree();
		return pdisTreeAll;
	}
	
/*	public List<TreeNode> getPdisTreeFilter() {
		return pdisTreeFiltered;
	}

	public void setPdisTreeFilter(List<TreeNode> pdisTreeFilter) {
		this.pdisTreeFiltered = pdisTreeFilter;
	}*/

	//TODO TEM que ver se ao aplicar o filtro não remove elementos da lista de Objetivos Específicos ORIGINAL
/*	private void applyUnidadeFilter() {
		//Clear the last filter
		this.pdisTreeFiltered.clear();
		int order=0;
		for(TreeNode pdiNode: this.pdisTreeAll) {
			if(((PDITreeNode)pdiNode).equals(this.currentPDI)) {
				this.currentPDI = new PDITreeNode(((PDITreeNode)pdiNode).getPDI(), order++);
				this.pdisTreeFiltered.add(this.currentPDI);
			}
			else
				this.pdisTreeFiltered.add(pdiNode);
		}
		//this.pdisTreeFilter.addAll(pdisTreeAll);

		if(!unidadeSelected.equals(PDIControllerCached.getInstance().getUnidadeAll())) {
			int index = this.pdisTreeFiltered.indexOf(this.currentPDI);
			PDITreeNode pdiFiltered = (PDITreeNode)this.pdisTreeFiltered.get(this.pdisTreeFiltered.indexOf(this.currentPDI));
			for(EixoTreeNode e: pdiFiltered.getEixosTree())
				for(DiretrizTreeNode d: e.getDiretrizesTree())
					for(ObjetivoEstrategicoTreeNode o: d.getObjetivosTree()) {
						Iterator<ObjetivoEspecificoTreeNode> it = o.getAllObjetivos().iterator();
						while(it.hasNext()) {
							ObjetivoEspecificoTreeNode obj = it.next();
							//Apply filter, i.e., remove other objetivos which unidade doesn't match to the filter
							if(!obj.getUnidadeName().equals(unidadeSelected.getName()))
								it.remove();
						}
					}
		}
	}*/
	
	private void fireFilter() {
		this.setExpandedNode.clear();
		for(EixoTreeNode e: ((PDITreeNode)this.getCurrentPDI()).getEixosTree())
			for(DiretrizTreeNode d: e.getDiretrizesTree())
				for(ObjetivoEstrategicoTreeNode o: d.getObjetivosTree())
					o.applyUnidadeFilter(this.unidadeSelected.getName());
	}

	public void setCurrentPDI(PDITreeNode currentPDI) {
		this.currentPDI = currentPDI;
	}
	
	public void nodeSelected(NodeSelectedEvent event) {
		this.renderPanel = true;
		
		HtmlTree tree = (HtmlTree)event.getSource();
		System.out.println("HTMLTree - "+tree);
		this.currentNodeSelection = ((TreeNode)tree.getRowData());
	}

	public TreeNode getCurrentNodeSelection() {
		return currentNodeSelection;
	}
	
    public void setCurrentNodeSelection(TreeNode currentNode) {
        this.currentNodeSelection = currentNode;
    }
	
	public boolean isRenderPanel() {
		return renderPanel;
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
		return this.listUnidades;
	}
	
	private void setListUnidades() {
		this.listUnidades = new ArrayList<SelectItem>();
		SisplanUser sisplanUser = (SisplanUser)this.getMBean("sisplanUser");
		if(sisplanUser.isPlanningManager())
			this.listUnidades.add(new SelectItem(getUnidadeAll().toString(), getUnidadeAll().toString()));
		for(Unidade u: sisplanUser.getUnidades())
			this.listUnidades.add(new SelectItem(u.toString(), u.toString()));
	}

	public void unidadeSelectedListener(ValueChangeEvent e) {
		String unidadeName = (String)e.getNewValue();
		/* Checks if unidadeSelected was changed.*/
		if(!this.unidadeSelected.getName().equals(unidadeName) && !unidadeName.isEmpty()) {
//			this.changedUnidade = true;
			this.unidadeSelected = PDIControllerCached.getInstance().getUnidade(unidadeName);
			if(unidadeName.equals(this.getUnidadeAll().getName()))
				this.unidadeSelected = this.getUnidadeAll();
			this.fireFilter();
			return;
		}
//		this.changedUnidade = false;
		System.out.println("AAA");
	}
	
	public String getUnidadeSelectedName() {
		return this.unidadeSelected.getName();
	}
	
	public Boolean adviseNodeOpened(UITree tree) {
		Object rowKey = tree.getRowKey();
		if(this.setExpandedNode.contains(rowKey.toString()))
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}
	
	public synchronized void addExpandedNode(String rowKey) {
		this.setExpandedNode.add(rowKey);
	}
	
	public synchronized void removeExpandedNode(String rowKey) {
		this.setExpandedNode.remove(rowKey);
	}
}