package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ifaces.TreeNodeHint;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfo;
import br.ifpr.sisplan.model.dao.EstrategiaDao;
import br.ifpr.sisplan.model.table.Estrategia;
import br.ifpr.sisplan.model.table.ObjetivoEspecifico;
import br.ifpr.sisplan.util.ConverterToList;
import br.ufrn.arq.web.jsf.AbstractController;

import com.google.common.collect.Iterators;

public class ObjetivoEspecificoTreeNode extends AbstractController implements TreeNode, TreeNodeInfo, TreeNodeHint {
	private static final long serialVersionUID = 4568288872051168852L;
	private UnidadeTreeNode unidadeParent;
	private ObjetivoEspecifico myObjetivo;
	private List<EstrategiaTreeNode> estrategiasTree = new ArrayList<EstrategiaTreeNode>();
	private int order;
	
	public ObjetivoEspecificoTreeNode(UnidadeTreeNode parent, ObjetivoEspecifico objetivo, int order) {
		this.unidadeParent = parent;
		this.myObjetivo  = objetivo;
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
		return this.unidadeParent;
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
		return myObjetivo.getName();
	}

	public List<EstrategiaTreeNode> getEstrategiasTree() {
		return estrategiasTree;
	}

	private void setEstrategiasTree() {
		final List<Estrategia> estrategias = ConverterToList.convertListMappedToList(getDAO(EstrategiaDao.class).selectEstrategiaByObjetivo(myObjetivo.getId()), Estrategia.class);
		int order=0;
		for(Estrategia e: estrategias) {
			final EstrategiaTreeNode estrategiaTree = new EstrategiaTreeNode(this, e, ++order);
			this.estrategiasTree.add(estrategiaTree);
		}
 	}

	public String getType() {
		return this.myObjetivo.getType();
	}
	
	public String getName() {
		return this.myObjetivo.getName();
	}

	public String getHint() {
		return "Objetivo Especifico "+this.order;
	}
}
