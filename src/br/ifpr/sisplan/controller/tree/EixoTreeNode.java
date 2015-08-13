package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ifaces.TreeNodeHint;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfo;
import br.ifpr.sisplan.model.dao.DiretrizDao;
import br.ifpr.sisplan.model.table.Diretriz;
import br.ifpr.sisplan.model.table.Eixo;
import br.ifpr.sisplan.util.ConverterToList;
import br.ufrn.arq.web.jsf.AbstractController;

import com.google.common.collect.Iterators;

public class EixoTreeNode extends AbstractController implements TreeNode, TreeNodeInfo, TreeNodeHint {
	private static final long serialVersionUID = 4712680632403538439L;
	private List<DiretrizTreeNode> diretrizesTree = new ArrayList<DiretrizTreeNode>();
	private PDITreeNode pdiParent;

	private Eixo myEixo;
	
	public EixoTreeNode(PDITreeNode pdiParent, Eixo eixo) {
		this.pdiParent = pdiParent;
		this.myEixo = eixo;
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
		return pdiParent;
	}
	public boolean isLeaf() {
		return diretrizesTree.isEmpty();
	}

	public void setDiretrizesTree() {
		final List<Diretriz> diretrizes = ConverterToList.convertListMappedToList(getDAO(DiretrizDao.class).selectDiretrizesByEixo(myEixo.getId()), Diretriz.class);
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
		return myEixo.toString();
	}
	
	public String getType() {
		return this.myEixo.getType();
	}
	
	public String getName() {
		return "Eixo Estratégico "+this.myEixo.getId();
	}

	public String getHint() {
		return this.myEixo.getName();
	}

}