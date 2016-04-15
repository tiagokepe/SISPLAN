package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ProgressStatus;
import br.ifpr.sisplan.controller.bean.PDIControllerBean;
import br.ifpr.sisplan.controller.bean.SisplanUser;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroAbstract;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroIface;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfoIface;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.EstrategiaDao;
import br.ifpr.sisplan.model.dao.ProjetoDao;
import br.ifpr.sisplan.model.table.Estrategia;
import br.ifpr.sisplan.model.table.Projeto;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class EstrategiaTreeNode extends TreeNodeCadastroAbstract {
	private static final long serialVersionUID = 3708717812208996777L;
	private List<ProjetoTreeNode> projetosTree = new ArrayList<ProjetoTreeNode>();
	private int order;
	private String newDescricao;
	
	public EstrategiaTreeNode(TreeNodeGeneric parent, Estrategia estrategia, int order) {
		super(parent, estrategia, order);
		this.order = order;
		this.setProjetosTree();
	}
	
	public TreeNode getChildAt(int paramInt) {
		return this.projetosTree.get(paramInt);
	}

	public int getChildCount() {
		return this.projetosTree.size();
	}

	public TreeNode getParent() {
		return this.parentNode;
	}

	public int getIndex(TreeNode paramTreeNode) {
		return this.projetosTree.indexOf(paramTreeNode);
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public boolean isLeaf() {
		return this.projetosTree.isEmpty();
	}

	public Enumeration children() {
		return Iterators.asEnumeration(this.projetosTree.iterator());
	}
	
	public void setProjetosTree() {
		final List<Projeto> projetos;
		SisplanUser sisplanUser = (SisplanUser)getMBean("sisplanUser");
		if(sisplanUser.isResponsavelProjetoEtapa() )
			projetos = ConverterToList.convertListMappedToList(getDAO(ProjetoDao.class).
										selectProjetosByEstrategiaAndResponsavel(this.descriptionNode.getId(), sisplanUser.getUserID()), Projeto.class);
		else
			projetos = ConverterToList.convertListMappedToList(getDAO(ProjetoDao.class).
										selectProjetosByEstrategia(this.descriptionNode.getId()), Projeto.class);
		
		int i=0;
		for(Projeto p: projetos) {
			this.setDataProjeto(p);
			final ProjetoTreeNode projetoTree = new ProjetoTreeNode(this, p, i++);
			this.projetosTree.add(projetoTree);
		}
		this.projetosTree = projetosTree;
	}
	
	public List<ProjetoTreeNode> getProjetosTree() {
		return projetosTree;
	}

	private void setDataProjeto(Projeto p) {
		p.setData(getDAO(DataDao.class).selectDataByProjeto(p.getId()));
	}

	@Override
	public String toString() {
		return this.descriptionNode.getName();
	}
	
	public String getType() {
		return this.descriptionNode.getType();
	}
	
	public String getName() {
		return "Estratégia "+(this.order+1);
	}

	public String getDescricao() {
		return this.descriptionNode.getDescricao();
	}
	
	public void setDescricao(String descricao) {
		this.newDescricao = descricao;
	}

	public int getMyID() {
		return this.descriptionNode.getId();
	}

	public String getCadastroURL() {
		//((NovoProjetoBean)this.getMBean("novoProjetoBean")).setTreeNodeParent(this);
		return "/SISPLAN/portal/novo_projeto.jsf";
	}

	public String getCadastroTitle() {
		return "Cadastrar Projeto";
	}

	public void addTreeNodeChild(TreeNodeGeneric child) {
		this.projetosTree.add((ProjetoTreeNode)child);		
	}
	
	public boolean hasLinkObjEspecifico() {
		return this.getDAO(EstrategiaDao.class).countObjEspecificoLinks(this.getMyID()) > 0 ? true: false; 
	}
	
	public void deleteFromDB() {
		// Removing estrategia from data base
		this.getDAO(EstrategiaDao.class).deleteEstrategia(this.descriptionNode.getId());
		for(ProjetoTreeNode proj: this.projetosTree)
			proj.deleteFromDB();
	}

	public void delete() {
		System.out.println("Estrategia delete...");
		this.deleteFromDB();
		
		// Removing estrategia references from java objects
		for(EixoTreeNode eixo: ((PDIControllerBean)this.getMBean("pdiControllerBean")).getCurrentPDI().getEixosTree())
			for(DiretrizTreeNode dir: eixo.getDiretrizesTree())
				for(ObjetivoEstrategicoTreeNode objEst: dir.getObjetivosTree())
					for(ObjetivoEspecificoTreeNode objEsp: objEst.getFilteredObjetivos()) {
								boolean foundEstrategia = false;
								Iterator<EstrategiaTreeNode> it = objEsp.getEstrategiasTree().iterator();
								EstrategiaTreeNode nextEstrategia;
								while(it.hasNext()) {
									nextEstrategia = it.next();
									// Se projeto encontrado, então decrementa a ordem dos projetos posteriores
									if(foundEstrategia)
										nextEstrategia.decreaseOrder();
									if(nextEstrategia.getMyID() == this.getMyID()) {
										//Check if projeto has no relationship with other estrategia
										for(ProjetoTreeNode proj: nextEstrategia.getProjetosTree()) {
											//Delete projeto with no link with estrategia
											if(!proj.hasLinkEstrategia())
												proj.deleteFromDB();
										}
										((PDIControllerBean)this.getMBean("pdiControllerBean")).removeExpandedNode(nextEstrategia.getRowKey());
										it.remove();
										foundEstrategia = true;
									}
									System.out.println(nextEstrategia.getMyID() +  " == " + this.getMyID());
								}
								System.out.println("--------------------------------------------");
							}
		
		((PDIControllerBean)this.getMBean("pdiControllerBean")).setCurrentNodeSelection(null);
	}
	

	public boolean isRenderedDescricao() {
		return true;
	}

	public boolean isRenderedUnidade() {
		return false;
	}

	public boolean isRenderedProjetoOrEtapa() {
		return false;
	}

	public void removeTreeNodeChild(TreeNodeGeneric child) {
		this.projetosTree.remove(child);
	}

	public String getAlterarURL() {
		return "/SISPLAN/portal/alterar_descricao.jsf";
	}

	public void save() {
		boolean alreadyUpdatedDB = false;

		for(EixoTreeNode eixo: ((PDIControllerBean)this.getMBean("pdiControllerBean")).getCurrentPDI().getEixosTree())
			for(DiretrizTreeNode dir: eixo.getDiretrizesTree())
				for(ObjetivoEstrategicoTreeNode objEst: dir.getObjetivosTree()) 
					for(ObjetivoEspecificoTreeNode objEsp: objEst.getFilteredObjetivos())
						for(EstrategiaTreeNode estrategia: objEsp.getEstrategiasTree())
							if(this.getMyID() == estrategia.getMyID()) {
								if(alreadyUpdatedDB) { 
									estrategia.getDescriptionNode().setDescricao(newDescricao);
								} else {
									LogHistory.getInstance().log((TreeNodeInfoIface) this,
											(TreeNodeCadastroIface) this, "Descrição",
											estrategia.getDescriptionNode().getDescricao(),
											newDescricao);
									estrategia.getDescriptionNode().setDescricao(newDescricao);
									this.getDAO(EstrategiaDao.class).updateEstrategia(estrategia.getDescriptionNode());
									alreadyUpdatedDB = true;
								}
							}
		this.returnMainPage();
	}

	public void cancel() {
		this.redirect("/portal/index.jsf");		
	}

	public String getUnidadeName() {
		return ((TreeNodeCadastroIface)this.parentNode).getUnidadeName();
	}
	
	@Override
	public String getStatusStyleClass() {
		return ProgressStatus.Default.getStyleClass();
	}

	@Override
	public boolean isShowProgressStatus() {
		// TODO Auto-generated method stub
		return false;
	}
}