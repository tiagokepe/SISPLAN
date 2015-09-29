package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.bean.NovoObjetivoBean;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroAbstract;
import br.ifpr.sisplan.model.dao.ObjetivoEspecificoDao;
import br.ifpr.sisplan.model.table.ObjetivoEspecifico;
import br.ifpr.sisplan.model.table.Unidade;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class UnidadeTreeNode extends TreeNodeCadastroAbstract {
	private static final long serialVersionUID = -7309785687849179855L;
	private List<ObjetivoEspecificoTreeNode> objetivosTree = new ArrayList<ObjetivoEspecificoTreeNode>();
	
	public UnidadeTreeNode(TreeNodeGeneric objParent, Unidade unidade) {
		super(objParent, unidade);
		this.setObjetivosTree();
	}
	
	public void setObjetivosTree() {
		final List<ObjetivoEspecifico> objetivos = ConverterToList.convertListMappedToList(getDAO(ObjetivoEspecificoDao.class).
													selectObjetivosByUnidadeObjEstrategico(this.nameNode.getId(), this.parentNode.getMyID()), ObjetivoEspecifico.class);
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
		return this.nameNode.toString();
	}
	
	public String getType() {
		return this.nameNode.getType();
	}
	
	public String getName() {
		return this.nameNode.getName();
	}

	public String getDesc() {
		return "";
	}
	
	public int getMyID() {
		return this.nameNode.getId();
	}
	
	public void addTreeNodeChild(TreeNodeGeneric child) {
		this.objetivosTree.add((ObjetivoEspecificoTreeNode)child);
	}

	public String getCadastroURL() {
		((NovoObjetivoBean)this.getMBean("novoObjetivoBean")).setTreeNodeParent(this);
		return "/SISPLAN/portal/novo_objetivo.jsf";
	}

	public String getCadastroTitle() {
		return "Cadastrar Objetivo Específico";
	}
}