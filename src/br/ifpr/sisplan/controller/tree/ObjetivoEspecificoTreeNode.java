package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroAbstract;
import br.ifpr.sisplan.model.dao.EstrategiaDao;
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
		return this.nameNode.getName();
	}

	public List<EstrategiaTreeNode> getEstrategiasTree() {
		return estrategiasTree;
	}

	private void setEstrategiasTree() {
		final List<Estrategia> estrategias = ConverterToList.convertListMappedToList(getDAO(EstrategiaDao.class).selectEstrategiaByObjetivo(this.nameNode.getId()), Estrategia.class);
		int order=0;
		for(Estrategia e: estrategias) {
			final EstrategiaTreeNode estrategiaTree = new EstrategiaTreeNode(this, e, order++);
			this.estrategiasTree.add(estrategiaTree);
		}
 	}

	public String getType() {
		return this.nameNode.getType();
	}
	
	public String getName() {
		return "Objetivo Especifico "+ (this.order+1);
	}

	public String getDesc() {
		return this.nameNode.getName();
	}

	public String getCadastroURL() {
		//((NovaEstrategiaBean)this.getMBean("novaEstrategiaBean")).setTreeNodeParent(this);
		return "/SISPLAN/portal/nova_estrategia.jsf";
	}

	public String getCadastroTitle() {
		return "Cadastrar Estratégia";
	}

	public int getMyID() {
		return this.nameNode.getId();
	}

	public void addTreeNodeChild(TreeNodeGeneric child) {
		this.estrategiasTree.add((EstrategiaTreeNode)child);
	}
	
	private void setUnidade() {
		this.unidade = getDAO(UnidadeDao.class).selectUnidadeByObjEspecifico(this.getMyID());
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

	public void delete() {
		// TODO Auto-generated method stub
		
	}

	public boolean isRenderedDescricao() {
		return true;
	}

	public boolean isRenderedUnidade() {
		return true;
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
		return false;
	}

	public void removeTreeNodeChild(TreeNodeGeneric child) {
		// TODO Auto-generated method stub
		
	}
}