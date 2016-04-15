package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ProgressStatus;
import br.ifpr.sisplan.controller.bean.PDIControllerBean;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroAbstract;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroIface;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfoIface;
import br.ifpr.sisplan.model.dao.EstrategiaDao;
import br.ifpr.sisplan.model.dao.ObjetivoEspecificoDao;
import br.ifpr.sisplan.model.dao.UnidadeDao;
import br.ifpr.sisplan.model.table.Estrategia;
import br.ifpr.sisplan.model.table.ObjetivoEspecifico;
import br.ifpr.sisplan.model.table.Unidade;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class ObjetivoEspecificoTreeNode extends TreeNodeCadastroAbstract implements Comparable<ObjetivoEspecificoTreeNode>  {
	private static final long serialVersionUID = 4568288872051168852L;
	private List<EstrategiaTreeNode> estrategiasTree = new ArrayList<EstrategiaTreeNode>();
	private Unidade unidade;
	private String newDescricao;
	
	public ObjetivoEspecificoTreeNode(TreeNodeGeneric parent, ObjetivoEspecifico objetivo, int order) {
		super(parent, objetivo, order);
		this.setEstrategiasTree();
		this.setUnidade();
	}

	public TreeNode getChildAt(int paramInt) {
		return this.estrategiasTree.get(paramInt);
	}

	public int getChildCount() {
		return this.estrategiasTree.size();
	}

	public TreeNode getParent() {
		return this.parentNode;
	}

	public int getIndex(TreeNode paramTreeNode) {
		return this.estrategiasTree.indexOf(paramTreeNode);
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public boolean isLeaf() {
		return this.estrategiasTree.isEmpty();
	}

	public Enumeration children() {
		return Iterators.asEnumeration(this.estrategiasTree.iterator());
	}
	
	@Override
	public String toString() {
		return this.descriptionNode.getName(); 
	}

	public List<EstrategiaTreeNode> getEstrategiasTree() {
		return estrategiasTree;
	}

	private void setEstrategiasTree() {
		final List<Estrategia> estrategias = ConverterToList.convertListMappedToList(getDAO(EstrategiaDao.class).selectEstrategiaByObjetivo(this.descriptionNode.getId()), Estrategia.class);
		int order=0;
		for(Estrategia e: estrategias) {
			final EstrategiaTreeNode estrategiaTree = new EstrategiaTreeNode(this, e, order++);
			this.estrategiasTree.add(estrategiaTree);
		}
 	}

	public String getType() {
		return this.descriptionNode.getType();
	}
	
	public String getName() {
		return "Objetivo Especifico "+ (this.order+1);
	}

	public String getDescricao() {
		return this.descriptionNode.getDescricao();
	}
	
	public void setDescricao(String descricao) {
		this.newDescricao = descricao;
	}

	public String getCadastroURL() {
		//((NovaEstrategiaBean)this.getMBean("novaEstrategiaBean")).setTreeNodeParent(this);
		return "/SISPLAN/portal/nova_estrategia.jsf";
	}

	public String getCadastroTitle() {
		return "Cadastrar Estratégia";
	}

	public int getMyID() {
		return this.descriptionNode.getId();
	}

	public void addTreeNodeChild(TreeNodeGeneric child) {
		this.estrategiasTree.add((EstrategiaTreeNode)child);
	}
	
	private void setUnidade() {
		this.unidade = getDAO(UnidadeDao.class).selectUnidadeByObjEspecifico(this.getMyID());
	}
	
	public Unidade getUnidade() {
		return this.unidade;
	}
	
	public String getUnidadeName() {
		return this.unidade.getName();
	}

	public int compareTo(ObjetivoEspecificoTreeNode other) {
		return Integer.compare(this.getMyID(), other.getMyID());
	}
	
	public int getUnidadeID() {
		return this.unidade.getId();
	}
	
	public void deleteFromDB() {
		// Removing estrategia from data base
		this.getDAO(ObjetivoEspecificoDao.class).deleteObjEspecifico(this.descriptionNode.getId());
		for(EstrategiaTreeNode estrategia: this.estrategiasTree)
			estrategia.deleteFromDB();
	}

	public void delete() {
		System.out.println("Objetivo Específico delete...");
		this.deleteFromDB();
		
		// Removing objetivo especifico references from java objects
		for(EixoTreeNode eixo: ((PDIControllerBean)this.getMBean("pdiControllerBean")).getCurrentPDI().getEixosTree())
			for(DiretrizTreeNode dir: eixo.getDiretrizesTree())
				for(ObjetivoEstrategicoTreeNode objEst: dir.getObjetivosTree()) {
					boolean foundObjEsp = false;
					Iterator<ObjetivoEspecificoTreeNode> it = objEst.getFilteredObjetivos().iterator();
					ObjetivoEspecificoTreeNode nextObjEsp;
					while(it.hasNext()) {
						nextObjEsp = it.next();
						// Se projeto encontrado, então decrementa a ordem dos projetos posteriores
						if(foundObjEsp)
							nextObjEsp.decreaseOrder();
						if(nextObjEsp.getMyID() == this.getMyID()) {
							//Check if objetivo especifico has no relationship with other objetivo estratégico
							for(EstrategiaTreeNode estrategia: nextObjEsp.getEstrategiasTree()) {
								//Delete objetivo especifico with no link with objetivo estratégico
								if(!estrategia.hasLinkObjEspecifico())
									estrategia.deleteFromDB();
							}
							((PDIControllerBean)this.getMBean("pdiControllerBean")).removeExpandedNode(nextObjEsp.getRowKey());
							it.remove();
							objEst.getAllObjetivos().remove(nextObjEsp);
							foundObjEsp = true;
						}
						System.out.println(nextObjEsp.getMyID() +  " == " + this.getMyID());
					}
					System.out.println("--------------------------------------------");
				}
		
		((PDIControllerBean)this.getMBean("pdiControllerBean")).setCurrentNodeSelection(null);
		
	}

	public boolean isRenderedDescricao() {
		return true;
	}

	public boolean isRenderedUnidade() {
		return true;
	}

	public boolean isRenderedProjetoOrEtapa() {
		return false;
	}

	public void removeTreeNodeChild(TreeNodeGeneric child) {
		// TODO Auto-generated method stub
	}

	public String getAlterarURL() {
		return "/SISPLAN/portal/alterar_descricao.jsf";
	}

	public void save() {
		boolean alreadyUpdateDB = false;
		for(EixoTreeNode eixo: ((PDIControllerBean)this.getMBean("pdiControllerBean")).getCurrentPDI().getEixosTree())
			for(DiretrizTreeNode dir: eixo.getDiretrizesTree())
				for(ObjetivoEstrategicoTreeNode objEst: dir.getObjetivosTree()) 
					for(ObjetivoEspecificoTreeNode objEsp: objEst.getFilteredObjetivos()) {
						if(objEsp.getMyID() == this.getMyID()) {
							if(alreadyUpdateDB) {
								objEsp.getDescriptionNode().setDescricao(newDescricao);
							} else {
								LogHistory.getInstance().log((TreeNodeInfoIface) this,
										(TreeNodeCadastroIface) this, "Descrição",
										objEsp.getDescriptionNode().getDescricao(),
										newDescricao);
								objEsp.getDescriptionNode().setDescricao(newDescricao);
								this.getDAO(ObjetivoEspecificoDao.class).updateObjetivo(objEsp.getDescriptionNode());
								alreadyUpdateDB = true;

							}
						}
					}
		this.returnMainPage();
	}

	public void cancel() {
		this.redirect("/portal/index.jsf");		
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