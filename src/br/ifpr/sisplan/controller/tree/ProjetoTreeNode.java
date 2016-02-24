package br.ifpr.sisplan.controller.tree;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.swing.tree.TreeNode;

import org.joda.time.DateTime;
import org.joda.time.Days;

import br.ifpr.sisplan.controller.ProgressStatus;
import br.ifpr.sisplan.controller.bean.NovaEtapaBean;
import br.ifpr.sisplan.controller.bean.PDIControllerBean;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroAbstract;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroIface;
import br.ifpr.sisplan.controller.ifaces.TreeNodeDetailsIface;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.EtapaDao;
import br.ifpr.sisplan.model.dao.ProjetoDao;
import br.ifpr.sisplan.model.dao.ResponsavelDao;
import br.ifpr.sisplan.model.table.Etapa;
import br.ifpr.sisplan.model.table.Projeto;
import br.ifpr.sisplan.model.table.Responsavel;
import br.ifpr.sisplan.model.table.parent.DateNode;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class ProjetoTreeNode extends TreeNodeCallBack implements TreeNodeCadastroIface {
	private static final long serialVersionUID = -7787388029320598005L;
	
	private List<EtapaTreeNode> etapasTree = new ArrayList<EtapaTreeNode>();
	private Map<Method, Object> mapOfUpdateCallBack = new HashMap<Method, Object>();
	
	private Responsavel responsavel;

	
	public ProjetoTreeNode(TreeNodeGeneric parent, DateNode projeto, int order) {
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
		return this.descriptionNode.getId();
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
	
	public void deleteFromDB() {
		// Removing projeto from data base
		this.getDAO(ProjetoDao.class).deleteProjeto(this.descriptionNode.getId());
		this.getDAO(DataDao.class).deleteData(this.dataNode.getData());
		for(EtapaTreeNode etapa: this.etapasTree)
			etapa.deleteFromDB();
	}

	public void delete() {
		System.out.println("PROJETO delete...");
		this.deleteFromDB();
		
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
											etapa.deleteFromDB();
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

	public boolean isRenderedProjetoOrEtapa() {
		return true;
	}

	public void removeTreeNodeChild(TreeNodeGeneric child) {
		this.etapasTree.remove(child);
	}
	
	public void save() {
		super.save();
	}

	public void cancel() {
		super.cancel();
	}

	public String getAlterarURL() {
		return "/SISPLAN/portal/alterar_projeto_etapa.jsf";
	}

	public String getUnidadeName() {
		return ((TreeNodeCadastroIface)this.parentNode).getUnidadeName();
	}

	@Override
	public String getStatusStyleClass() {
		DateTime dtFimPrev = new DateTime(this.getDataFimPrevista());
		DateTime dtToday = new DateTime(new Date());
		int days = Days.daysBetween(dtToday, dtFimPrev).getDays();
		if(days >= 0)
			return ProgressStatus.Blue.getStyleClass();
		else 
			if(this.getDataFimEfetiva() == null)
				return ProgressStatus.Red.getStyleClass();
			else {
				DateTime dtFimEfe = new DateTime(this.getDataFimEfetiva());
				days = Days.daysBetween(dtFimPrev, dtFimEfe).getDays();
				if(days <= 0)
					return ProgressStatus.Green.getStyleClass();
				else
					return ProgressStatus.Orange.getStyleClass();
				
			}
	}
}