package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.bean.NovoProjetoBean;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroAbstract;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.ProjetoDao;
import br.ifpr.sisplan.model.table.Estrategia;
import br.ifpr.sisplan.model.table.Projeto;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class EstrategiaTreeNode extends TreeNodeCadastroAbstract {
	private static final long serialVersionUID = 3708717812208996777L;
	private List<ProjetoTreeNode> projetosTree = new ArrayList<ProjetoTreeNode>();
	private int order;
	
	public EstrategiaTreeNode(TreeNodeGeneric parent, Estrategia estrategia, int order) {
		super(parent, estrategia, order);
		this.order = order;
		this.setProjetosTree();
	}
	
	public TreeNode getChildAt(int paramInt) {
		return this.projetosTree.get(paramInt);
	}

	public int getChildCount() {
		return this.projetosTree.size();
	}

	public TreeNode getParent() {
		return this.parentNode;
	}

	public int getIndex(TreeNode paramTreeNode) {
		return this.projetosTree.indexOf(paramTreeNode);
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public boolean isLeaf() {
		return this.projetosTree.isEmpty();
	}

	public Enumeration children() {
		return Iterators.asEnumeration(this.projetosTree.iterator());
	}
	
	public void setProjetosTree() {
		final List<Projeto> projetos = ConverterToList.convertListMappedToList(getDAO(ProjetoDao.class).selectProjetosByEstrategia(this.nameNode.getId()), Projeto.class);
		int i=0;
		for(Projeto p: projetos) {
			this.setDataProjeto(p);
			final ProjetoTreeNode projetoTree = new ProjetoTreeNode(this, p, i++);
			this.projetosTree.add(projetoTree);
		}
		this.projetosTree = projetosTree;
	}
	
	private void setDataProjeto(Projeto p) {
		p.setData(getDAO(DataDao.class).selectDataByProjeto(p.getId()));
	}

	@Override
	public String toString() {
		return this.nameNode.getName();
	}
	
	public String getType() {
		return this.nameNode.getType();
	}
	
	public String getName() {
		return "Estratégia "+(this.order+1);
	}

	public String getDesc() {
		return this.nameNode.getName();
	}

	public int getMyID() {
		return this.nameNode.getId();
	}

	public String getCadastroURL() {
		//((NovoProjetoBean)this.getMBean("novoProjetoBean")).setTreeNodeParent(this);
		return "/SISPLAN/portal/novo_projeto.jsf";
	}

	public String getCadastroTitle() {
		return "Cadastrar Projeto";
	}

	public void addTreeNodeChild(TreeNodeGeneric child) {
		this.projetosTree.add((ProjetoTreeNode)child);		
	}
}