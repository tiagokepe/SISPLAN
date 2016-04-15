package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ProgressStatus;
import br.ifpr.sisplan.model.dao.DiretrizDao;
import br.ifpr.sisplan.model.table.Diretriz;
import br.ifpr.sisplan.model.table.Eixo;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class EixoTreeNode extends TreeNodeGeneric {
	private static final long serialVersionUID = 4712680632403538439L;
	private List<DiretrizTreeNode> diretrizesTree = new ArrayList<DiretrizTreeNode>();

	public EixoTreeNode(TreeNodeGeneric pdiParent, Eixo eixo, int order) {
		super(pdiParent, eixo, order);
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
		final List<Diretriz> diretrizes = ConverterToList.convertListMappedToList(getDAO(DiretrizDao.class).selectDiretrizesByEixo(this.descriptionNode.getId()), Diretriz.class);
		int i=0;
		for(Diretriz d: diretrizes) {
			final DiretrizTreeNode diretrizTree = new DiretrizTreeNode(this, d, i++);
			this.diretrizesTree.add(diretrizTree);
		}
	}
	
	public List<DiretrizTreeNode> getDiretrizesTree() {
/*		if(!this.diretrizesTree.isEmpty()) {
			this.diretrizesTree.clear();
		}
		this.setDiretrizesTree();*/
		if(this.diretrizesTree != null && this.diretrizesTree.isEmpty())
			this.setDiretrizesTree();

		return diretrizesTree;
	}

	@Override
	public String toString() {
		return this.descriptionNode.toString();
	}
	
	public String getType() {
		return this.descriptionNode.getType();
	}
	
	public String getName() {
		return "Eixo Estratégico "+this.descriptionNode.getId();
	}

	public String getDescricao() {
		return this.descriptionNode.getDescricao();
	}

	public int getMyID() {
		return this.descriptionNode.getId();
	}

	public boolean isRenderedDescricao() {
		return true;
	}

	public boolean isRenderedUnidade() {
		return false;
	}

	public boolean isRenderedCadastrar() {
		return false;
	}

	public boolean isRenderedAlterar() {
		return false;
	}

	public boolean isRenderedExcluir() {
		return false;
	}
	
	public boolean isRenderedCancelar() {
		return false;
	}
	
	public boolean isRenderedProjetoOrEtapa() {
		return false;
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