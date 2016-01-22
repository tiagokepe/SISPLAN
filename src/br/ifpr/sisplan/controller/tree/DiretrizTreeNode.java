package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.model.dao.ObjetivoEstrategicoDao;
import br.ifpr.sisplan.model.table.Diretriz;
import br.ifpr.sisplan.model.table.ObjetivoEstrategico;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class DiretrizTreeNode extends TreeNodeGeneric {
	private static final long serialVersionUID = 8418909430752199490L;
	private List<ObjetivoEstrategicoTreeNode> objetivosTree = new ArrayList<ObjetivoEstrategicoTreeNode>();
	
	public DiretrizTreeNode(TreeNodeGeneric eixoParent, Diretriz myDiretriz, int order) {
		super(eixoParent, myDiretriz, order);
		this.setObjetivosTree();
	}

	public Enumeration<ObjetivoEstrategicoTreeNode> children() {
		return Iterators.asEnumeration(objetivosTree.iterator());
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public TreeNode getChildAt(int arg0) {
		return objetivosTree.get(arg0);
	}

	public int getChildCount() {
		return objetivosTree.size();
	}

	public int getIndex(TreeNode arg0) {
		return objetivosTree.indexOf(arg0);
	}

	public TreeNode getParent() {
		return this.parentNode;
	}

	public boolean isLeaf() {
		return this.objetivosTree.isEmpty();
	}
	
	public void setObjetivosTree() {
		final List<ObjetivoEstrategico> objetivos = ConverterToList.convertListMappedToList(getDAO(ObjetivoEstrategicoDao.class).
													selectObjetivosByDiretriz(this.descriptionNode.getId()), ObjetivoEstrategico.class);
		int i=0;
		for(ObjetivoEstrategico o: objetivos) {
			final ObjetivoEstrategicoTreeNode objetivoTree = new ObjetivoEstrategicoTreeNode(this, o, i++);
			this.objetivosTree.add(objetivoTree);
		}
	}
	
	public List<ObjetivoEstrategicoTreeNode> getObjetivosTree() {
/*		if(!this.objetivosTree.isEmpty()) {
			this.objetivosTree.clear();
		}
		this.setObjetivosTree();*/
		if(this.objetivosTree != null && this.objetivosTree.isEmpty())
			this.setObjetivosTree();
		return objetivosTree;
	}
	
	public String getType() {
		return "D"+this.getMyID()+"."+this.descriptionNode.getType();
	}
	
	public String getName() {
		return "Diretriz Organizacional " + this.getMyID();
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

	public boolean isRenderedProjetoOrEtapa() {
		return false;
	}
}
