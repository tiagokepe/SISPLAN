package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.model.dao.EixoDao;
import br.ifpr.sisplan.model.table.Eixo;
import br.ifpr.sisplan.model.table.PDI;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class PDITreeNode extends TreeNodeGeneric {
	private static final long serialVersionUID = -1835441558926237938L;

	private List<EixoTreeNode> eixosTree = new ArrayList<EixoTreeNode>();

	public PDITreeNode(PDI pdi) {
		super(null, pdi);
		this.setEixosTree();
	}
	
	public Enumeration<EixoTreeNode> children() {
		return Iterators.asEnumeration(eixosTree.iterator());
	}
	public boolean getAllowsChildren() {
		return true;
	}
	public TreeNode getChildAt(int childIndex) {
		return eixosTree.get(childIndex);
	}
	public int getChildCount() {
		return eixosTree.size();
	}
	public int getIndex(TreeNode node) {
		return eixosTree.indexOf(node);
	}
	public TreeNode getParent() {
		return null;
	}
	public boolean isLeaf() {
		return eixosTree.isEmpty();
	}

	public void setEixosTree() {
		final List<Eixo> eixos = ConverterToList.convertListMappedToList(getDAO(EixoDao.class).selectEixosByPDI(this.nameNode.getId()), Eixo.class);
		for(Eixo e: eixos) {
			final EixoTreeNode eixoTree = new EixoTreeNode(this, e);
			this.eixosTree.add(eixoTree);
		}
	}
	
	public List<EixoTreeNode> getEixosTree() {
		if(!this.eixosTree.isEmpty()) {
			this.eixosTree.clear();
		}
		this.setEixosTree();
		return eixosTree;
	}

	@Override
	public String toString() {
		return this.nameNode.toString();
	}
	
	public String getType() {
		return this.nameNode.getType();
	}
	
	public String getName() {
		return this.nameNode.getName();
	}
	
	public String getDesc() {
		return "PDI Hint!!!!";
	}

	public int getMyID() {
		return this.nameNode.getId();
	}
	
}