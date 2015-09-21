package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.model.dao.DiretrizDao;
import br.ifpr.sisplan.model.table.Diretriz;
import br.ifpr.sisplan.model.table.Eixo;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class EixoTreeNode extends TreeNodeGeneric {
	private static final long serialVersionUID = 4712680632403538439L;
	private List<DiretrizTreeNode> diretrizesTree = new ArrayList<DiretrizTreeNode>();

	public EixoTreeNode(TreeNodeGeneric pdiParent, Eixo eixo) {
		super(pdiParent, eixo);
		this.setDiretrizesTree();
	}
	
	public Enumeration<DiretrizTreeNode> children() {
		return Iterators.asEnumeration(diretrizesTree.iterator());
	}
	public boolean getAllowsChildren() {
		return true;
	}
	public TreeNode getChildAt(int childIndex) {
		return diretrizesTree.get(childIndex);
	}
	public int getChildCount() {
		return diretrizesTree.size();
	}
	public int getIndex(TreeNode node) {
		return diretrizesTree.indexOf(node);
	}
	public TreeNode getParent() {
		return this.parentNode;
	}
	public boolean isLeaf() {
		return diretrizesTree.isEmpty();
	}

	public void setDiretrizesTree() {
		final List<Diretriz> diretrizes = ConverterToList.convertListMappedToList(getDAO(DiretrizDao.class).selectDiretrizesByEixo(this.nameNode.getId()), Diretriz.class);
		for(Diretriz d: diretrizes) {
			final DiretrizTreeNode diretrizTree = new DiretrizTreeNode(this, d);
			this.diretrizesTree.add(diretrizTree);
		}
	}
	
	public List<DiretrizTreeNode> getDiretrizesTree() {
		if(!this.diretrizesTree.isEmpty()) {
			this.diretrizesTree.clear();
		}
		this.setDiretrizesTree();
		return diretrizesTree;
	}

	@Override
	public String toString() {
		return this.nameNode.toString();
	}
	
	public String getType() {
		return this.nameNode.getType();
	}
	
	public String getName() {
		return "Eixo Estratégico "+this.nameNode.getId();
	}

	public String getDesc() {
		return this.nameNode.getName();
	}

	public int getMyID() {
		return this.nameNode.getId();
	}

}