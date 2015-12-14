package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.swing.tree.TreeNode;

import org.richfaces.component.UITree;
import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeSelectedEvent;

import br.ifpr.sisplan.controller.PDIControllerCached;
import br.ifpr.sisplan.controller.ifaces.TreeNodeDetailsIface;
import br.ifpr.sisplan.controller.tree.DiretrizTreeNode;
import br.ifpr.sisplan.controller.tree.EixoTreeNode;
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
	private PDITreeNode currentPDI;
	private List<SelectItem> listUnidades;
	private SortedSet<String> setExpandedNode = new TreeSet<String>();
//	private Map<String, ObjetivoEstrategicoTreeNode> mapObjEstrategicoTreeNode;
//	private SortedMap<String, ObjetivoEspecificoTreeNode> mapObjEspecificoTreeNode;
	private List<TreeNode> pdisTreeAll;
	private List<TreeNode> pdisTreeFilter;
	
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
/*		if(this.pdisTreeFilter == null) {
			this.buildPdisTree();
			this.pdisTreeFilter = new ArrayList<TreeNode>();
			this.pdisTreeFilter.addAll(pdisTreeAll);
			this.changedUnidade = false;
		}
		else if(this.changedUnidade) {
			this.changedUnidade = false;
		}
		return this.pdisTreeFilter;*/
	}
	
	public List<TreeNode> getPdisTreeFilter() {
		return pdisTreeFilter;
	}

	public void setPdisTreeFilter(List<TreeNode> pdisTreeFilter) {
		this.pdisTreeFilter = pdisTreeFilter;
	}

	//TODO TEM que ver se ao aplicar o filtro não remove elementos da lista de Objetivos Específicos ORIGINAL
	private void applyUnidadeFilter() {
		//Clear the last filter
		this.pdisTreeFilter.clear();
		int order=0;
		for(TreeNode pdiNode: this.pdisTreeAll) {
			if(((PDITreeNode)pdiNode).equals(this.currentPDI)) {
				this.currentPDI = new PDITreeNode(((PDITreeNode)pdiNode).getPDI(), order++);
				this.pdisTreeFilter.add(this.currentPDI);
			}
			else
				this.pdisTreeFilter.add(pdiNode);
		}
		//this.pdisTreeFilter.addAll(pdisTreeAll);

		if(!unidadeSelected.equals(PDIControllerCached.getInstance().getUnidadeAll())) {
			int index = this.pdisTreeFilter.indexOf(this.currentPDI);
			PDITreeNode pdiFiltered = (PDITreeNode)this.pdisTreeFilter.get(this.pdisTreeFilter.indexOf(this.currentPDI));
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
	}
	
	private void fireFilter() {
		for(EixoTreeNode e: ((PDITreeNode)this.getCurrentPDI()).getEixosTree())
			for(DiretrizTreeNode d: e.getDiretrizesTree())
				for(ObjetivoEstrategicoTreeNode o: d.getObjetivosTree())
					o.applyUnidadeFilter(this.unidadeSelected.getName());
	}
/*	private void excludeObjetivos() {
		for(EixoTreeNode eixo: this.getCurrentPDI().getEixosTree())
			for(DiretrizTreeNode dir: eixo.getDiretrizesTree())
				for(ObjetivoEstrategicoTreeNode objEst: dir.getObjetivosTree())
					for(ObjetivoEspecificoTreeNode objEsp: objEst.getObjetivosTree()) {
						if(!objEsp.getUnidadeName().equals(this.unidadeSelected.getName())) {
							String unidadeName = objEsp.getUnidadeName();
							List<ObjetivoEspecificoTreeNode> listObj = this.mapUnidadeObjetivoEspecificoExcluded.get(unidadeName);
							if(listObj == null)
								listObj = new ArrayList<ObjetOivoEspecificoTreeNode>();
							listObj.add(objEsp);
							this.mapUnidadeObjetivoEspecificoExcluded.put(unidadeName, listObj);
						}
					}
	}*/
	
	public void setCurrentPDI(PDITreeNode currentPDI) {
		this.currentPDI = currentPDI;
	}
	
	public void nodeSelected(NodeSelectedEvent event) {
		//((TreeNodeGeneric)event.getSource()).setOpened(true);
		
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
			this.changedUnidade = true;
			this.unidadeSelected = PDIControllerCached.getInstance().getUnidade(unidadeName);
			if(unidadeName.equals(this.getUnidadeAll().getName()))
				this.unidadeSelected = this.getUnidadeAll();
			this.fireFilter();
			return;
		}
		this.changedUnidade = false;
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
/*		TreeState state = (TreeState)tree.getComponentState();
	    if (state.isExpanded(key)) {
	        System.out.println(rowKey + " - expanded");
	        return Boolean.TRUE;
	    }
	    else {
	        System.out.println(rowKey + " - collapsed");
	        return Boolean.FALSE;
	    }*/
	}
	
	public synchronized void addExpandedNode(String rowKey) {
		this.setExpandedNode.add(rowKey);
	}
	
	public synchronized void removeExpandedNode(String rowKey) {
		this.setExpandedNode.remove(rowKey);
	}
	
/*	public Map<String, ObjetivoEstrategicoTreeNode> getMapObjEstrategicoTreeNode() {
		if(mapObjEstrategicoTreeNode == null)
			this.setMapObjEstrategicoTreeNode();
		return mapObjEstrategicoTreeNode;
	}

	private void setMapObjEstrategicoTreeNode() {
		this.mapObjEstrategicoTreeNode = new LinkedHashMap<String, ObjetivoEstrategicoTreeNode>();
		for(EixoTreeNode eixo: this.getCurrentPDI().getEixosTree())
			for(DiretrizTreeNode dir: eixo.getDiretrizesTree())
				for(ObjetivoEstrategicoTreeNode obj: dir.getObjetivosTree())
					this.mapObjEstrategicoTreeNode.put(obj.getDesc(), obj);
					
	}*/
	
/*	public Map<String, ObjetivoEspecificoTreeNode> getMapObjEspecificoTreeNode() {
		if(mapObjEspecificoTreeNode == null)
			this.setMapObjEspecificoTreeNode();
		return this.mapObjEspecificoTreeNode;
	}
	
	private void setMapObjEspecificoTreeNode() {
		this.mapObjEspecificoTreeNode = new TreeMap<String, ObjetivoEspecificoTreeNode>();
		for(Map.Entry<String, ObjetivoEstrategicoTreeNode> entry: this.getMapObjEstrategicoTreeNode().entrySet())
			for(ObjetivoEspecificoTreeNode objEsp: entry.getValue().getAllObjetivos())
				this.mapObjEspecificoTreeNode.put(objEsp.getDesc(), objEsp);
					
	}*/
}