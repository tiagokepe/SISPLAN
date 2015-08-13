package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ifaces.TreeNodeHint;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfo;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.ProjetoDao;
import br.ifpr.sisplan.model.table.Estrategia;
import br.ifpr.sisplan.model.table.Projeto;
import br.ifpr.sisplan.util.ConverterToList;
import br.ufrn.arq.web.jsf.AbstractController;

import com.google.common.collect.Iterators;

public class EstrategiaTreeNode extends AbstractController implements TreeNode, TreeNodeInfo, TreeNodeHint {
	private static final long serialVersionUID = 3708717812208996777L;
	private ObjetivoEspecificoTreeNode parentObjetivo;
	private Estrategia myEstrategia;
	private List<ProjetoTreeNode> projetosTree = new ArrayList<ProjetoTreeNode>();
	private int order;
	
	public EstrategiaTreeNode(ObjetivoEspecificoTreeNode parent, Estrategia estrategia, int order) {
		this.parentObjetivo = parent;
		this.myEstrategia = estrategia;
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
		return this.parentObjetivo;
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
		final List<Projeto> projetos = ConverterToList.convertListMappedToList(getDAO(ProjetoDao.class).selectProjetosByEstrategia(myEstrategia.getId()), Projeto.class);
		for(Projeto p: projetos) {
			this.setDataProjeto(p);
			final ProjetoTreeNode projetoTree = new ProjetoTreeNode(this, p);
			this.projetosTree.add(projetoTree);
		}
		this.projetosTree = projetosTree;
	}
	
	private void setDataProjeto(Projeto p) {
		p.setData(getDAO(DataDao.class).selectDataByProjeto(p.getId()));
	}

	@Override
	public String toString() {
		return myEstrategia.getName();
	}
	
	public String getType() {
		return this.myEstrategia.getType();
	}
	
	public String getName() {
		return this.myEstrategia.getName();
	}

	public String getHint() {
		return "Estratégia "+this.order;
	}
}