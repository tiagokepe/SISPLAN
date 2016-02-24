package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ProgressStatus;
import br.ifpr.sisplan.model.dao.ObjetivoEspecificoDao;
import br.ifpr.sisplan.model.table.ObjetivoEspecifico;
import br.ifpr.sisplan.model.table.Unidade;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class UnidadeTreeNode extends TreeNodeGeneric {
	private static final long serialVersionUID = -7309785687849179855L;
	private List<ObjetivoEspecificoTreeNode> objetivosTree = new ArrayList<ObjetivoEspecificoTreeNode>();
	
	public UnidadeTreeNode(TreeNodeGeneric objParent, Unidade unidade, int order) {
		super(objParent, unidade, order);
		this.setObjetivosTree();
	}
	
	public void setObjetivosTree() {
		final List<ObjetivoEspecifico> objetivos = ConverterToList.convertListMappedToList(getDAO(ObjetivoEspecificoDao.class).
													selectObjetivosByUnidadeObjEstrategico(this.descriptionNode.getId(), this.parentNode.getMyID()), ObjetivoEspecifico.class);
		int order=0;
		
		for(ObjetivoEspecifico o: objetivos) {
			final ObjetivoEspecificoTreeNode objetivoTree = new ObjetivoEspecificoTreeNode(this, o, ++order);
			this.objetivosTree.add(objetivoTree);
		}
	}

	public Enumeration<ObjetivoEspecificoTreeNode> children() {
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
	
	@Override
	public String toString() {
		return this.descriptionNode.toString();
	}
	
	public String getType() {
		return this.descriptionNode.getType();
	}
	
	public String getName() {
		return this.descriptionNode.getName();
	}

	public String getDescricao() {
		return "";
	}
	
	public int getMyID() {
		return this.descriptionNode.getId();
	}

	public boolean isRenderedDescricao() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRenderedUnidade() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRenderedCadastrar() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRenderedAlterar() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRenderedExcluir() {
		// TODO Auto-generated method stub
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
}