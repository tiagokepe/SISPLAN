package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.bean.NovaEstrategiaBean;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastro;
import br.ifpr.sisplan.model.dao.EstrategiaDao;
import br.ifpr.sisplan.model.table.Estrategia;
import br.ifpr.sisplan.model.table.ObjetivoEspecifico;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class ObjetivoEspecificoTreeNode extends TreeNodeCadastro {
	private static final long serialVersionUID = 4568288872051168852L;
	private List<EstrategiaTreeNode> estrategiasTree = new ArrayList<EstrategiaTreeNode>();
	private int order;
	
	public ObjetivoEspecificoTreeNode(TreeNodeGeneric parent, ObjetivoEspecifico objetivo, int order) {
		super(parent, objetivo);
		this.order = order;
		this.setEstrategiasTree();
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
			final EstrategiaTreeNode estrategiaTree = new EstrategiaTreeNode(this, e, ++order);
			this.estrategiasTree.add(estrategiaTree);
		}
 	}

	public String getType() {
		return this.nameNode.getType();
	}
	
	public String getName() {
		return "Objetivo Especifico "+this.order;
	}

	public String getDesc() {
		return this.nameNode.getName();
	}

	public String getCadastroURL() {
		((NovaEstrategiaBean)this.getMBean("novaEstrategiaBean")).setTreeNodeParent(this);
		return "/SISPLAN/portal/nova_estrategia.jsf";
	}

	public String getCadastroTitle() {
		return "Cadastrar Estratégia";
	}

	public int getMyID() {
		return this.nameNode.getId();
	}

	@Override
	public void addTreeNodeChild(TreeNodeGeneric child) {
		this.estrategiasTree.add((EstrategiaTreeNode)child);
	}
}
