package br.ifpr.sisplan.controller.tree;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.bean.NovaEtapaBean;
import br.ifpr.sisplan.controller.bean.PDIControllerBean;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroIface;
import br.ifpr.sisplan.controller.ifaces.TreeNodeDetailsIface;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.EtapaDao;
import br.ifpr.sisplan.model.dao.ProjetoDao;
import br.ifpr.sisplan.model.dao.ResponsavelDao;
import br.ifpr.sisplan.model.table.Etapa;
import br.ifpr.sisplan.model.table.Projeto;
import br.ifpr.sisplan.model.table.Responsavel;
import br.ifpr.sisplan.model.table.parent.DateDescriptionNode;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class ProjetoTreeNode extends TreeNodeCallBack implements TreeNodeCadastroIface {
	private static final long serialVersionUID = -7787388029320598005L;
	
	private List<EtapaTreeNode> etapasTree = new ArrayList<EtapaTreeNode>();
	private Map<Method, Object> mapOfUpdateCallBack = new HashMap<Method, Object>();
	
	private Responsavel responsavel;

	
	public ProjetoTreeNode(TreeNodeGeneric parent, DateDescriptionNode projeto, int order) {
		super(parent, projeto, order);
		this.setEtapasTree();
	}
	
	public TreeNode getChildAt(int paramInt) {
		return etapasTree.get(paramInt);
	}

	public int getChildCount() {
		return this.etapasTree.size();
	}

	public TreeNode getParent() {
		return this.parentNode;
	}

	public int getIndex(TreeNode paramTreeNode) {
		return this.etapasTree.indexOf(paramTreeNode);
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public boolean isLeaf() {
		return this.etapasTree.isEmpty();
	}

	public Enumeration<EtapaTreeNode> children() {
		return Iterators.asEnumeration(this.etapasTree.iterator());
	}
	
	public List<EtapaTreeNode> getEtapasTree() {
		return etapasTree;
	}

	public void setEtapasTree() {
		final List<Etapa> etapas = ConverterToList.convertListMappedToList(getDAO(EtapaDao.class).selectEtapaByProject(this.dataNode.getId()), Etapa.class);
		int i=0;
		for(Etapa e: etapas) {
			this.setDataEtapa(e);
			final EtapaTreeNode etapaTree = new EtapaTreeNode(this, e, i++);
			this.etapasTree.add(etapaTree);
		}
	}
	
	private void setDataEtapa(Etapa e) {
		e.setData(getDAO(DataDao.class).selectDataByEtapa(e.getId()));
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
	
	public boolean isProjectNode() {
		return true;
	}

	public String getHeaderDetails() {
		return "Informações de Projeto";
	}
	
	public void redirectToIndex() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/SISPLAN/portal/index.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void valueChangeListener(ValueChangeEvent e) {
		String id_value = (String)e.getComponent().getAttributes().get("id");
		String newValue = e.getNewValue().toString();
		String methodName = id_value.replace("id_", "").replace("_", "");
		
		try {
			this.mapOfUpdateCallBack.put(TreeNodeDetailsIface.class.getMethod(methodName, String.class),
										newValue);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public int getMyID() {
		return this.nameNode.getId();
	}

	public String getDesc() {
		return this.getDescricao();
	}

	public String getCadastroURL() {
		((NovaEtapaBean)this.getMBean("novaEtapaBean")).setTreeNodeParent(this);
		return "/SISPLAN/portal/nova_etapa.jsf";
	}

	public String getCadastroTitle() {
		return "Cadastrar Etapa";
	}

	public void addTreeNodeChild(TreeNodeGeneric child) {
		this.etapasTree.add((EtapaTreeNode)child);		
	}
	
	public String getResponsavelName() {
		if(responsavel == null) {
			this.responsavel = ResponsavelDao.getInstance().selectResponsavel(((Projeto)this.dataNode).getIdResponsavel());
		}
		return this.responsavel.getName();
	}
	
	public boolean hasLinkEstrategia() {
		return this.getDAO(ProjetoDao.class).countEstrategiaLinks(this.getMyID()) > 0 ? true: false; 
	}
	
	public void deleteProjetoFromDB() {
		// Removing projeto from data base
		this.getDAO(ProjetoDao.class).deleteProjeto(this.nameNode.getId());
		this.getDAO(DataDao.class).deleteData(this.dataNode.getData());
		for(EtapaTreeNode etapa: this.etapasTree)
			etapa.deleteEtapaFromDB();
	}

	public void delete() {
		System.out.println("PROJETO delete...");
		this.deleteProjetoFromDB();
		
		// Removing projeto references from java objects
		for(EixoTreeNode eixo: ((PDIControllerBean)this.getMBean("pdiControllerBean")).getCurrentPDI().getEixosTree())
			for(DiretrizTreeNode dir: eixo.getDiretrizesTree())
				for(ObjetivoEstrategicoTreeNode objEst: dir.getObjetivosTree())
					for(ObjetivoEspecificoTreeNode objEsp: objEst.getFilteredObjetivos())
						for(EstrategiaTreeNode est: objEsp.getEstrategiasTree()) {
								boolean foundProj = false;
								Iterator<ProjetoTreeNode> it = est.getProjetosTree().iterator();
								ProjetoTreeNode nextProj;
								while(it.hasNext()) {
									nextProj = it.next();
									// Se projeto encontrado, então decrementa a ordem dos projetos posteriores
									if(foundProj)
										nextProj.decreaseOrder();
									if(nextProj.getMyID() == this.getMyID()) {
										//Deleting associated etapas from DB
										for(EtapaTreeNode etapa: nextProj.getEtapasTree())
											etapa.deleteEtapaFromDB();
										((PDIControllerBean)this.getMBean("pdiControllerBean")).removeExpandedNode(nextProj.getRowKey());
										it.remove();
										foundProj = true;
									}
									System.out.println(nextProj.getMyID() +  " == " + this.getMyID());
								}
								System.out.println("--------------------------------------------");
							}
		
		((PDIControllerBean)this.getMBean("pdiControllerBean")).setCurrentNodeSelection(null);
		System.out.println("PROJETO deletou...");
	}
	
	public boolean isRenderedDescricao() {
		return true;
	}

	public boolean isRenderedUnidade() {
		return false;
	}

	public boolean isRenderedCadastrar() {
		return true;
	}

	public boolean isRenderedAlterar() {
		return true;
	}

	public boolean isRenderedExcluir() {
		return true;
	}
	
	public boolean isRenderedProjetoOrEtapa() {
		return true;
	}

	public void removeTreeNodeChild(TreeNodeGeneric child) {
		this.etapasTree.remove(child);
	}
}