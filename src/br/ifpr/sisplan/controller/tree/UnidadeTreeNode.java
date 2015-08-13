package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ifaces.TreeNodeHint;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfo;
import br.ifpr.sisplan.model.dao.ObjetivoEspecificoDao;
import br.ifpr.sisplan.model.table.ObjetivoEspecifico;
import br.ifpr.sisplan.model.table.Unidade;
import br.ifpr.sisplan.util.ConverterToList;
import br.ufrn.arq.web.jsf.AbstractController;

import com.google.common.collect.Iterators;

public class UnidadeTreeNode extends AbstractController implements TreeNode, TreeNodeInfo, TreeNodeHint {
	private static final long serialVersionUID = -7309785687849179855L;
	private ObjetivoEstrategicoTreeNode objParent;
	private Unidade myUnidade;
	private List<ObjetivoEspecificoTreeNode> objetivosTree = new ArrayList<ObjetivoEspecificoTreeNode>();
	
	public UnidadeTreeNode(ObjetivoEstrategicoTreeNode objParent, Unidade unidade) {
		this.objParent = objParent;
		this.myUnidade = unidade;
		this.setObjetivosTree();
	}
	
	public void setObjetivosTree() {
		final List<ObjetivoEspecifico> objetivos = ConverterToList.convertListMappedToList(getDAO(ObjetivoEspecificoDao.class).
													selectObjetivosByUnidade(this.myUnidade.getId(), this.objParent.getMyObjetivo().getId()), ObjetivoEspecifico.class);
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
		return this.objParent;
	}

	public boolean isLeaf() {
		return this.objetivosTree.isEmpty();
	}
	
	@Override
	public String toString() {
		return myUnidade.toString();
	}
	
	public String getType() {
		return this.myUnidade.getType();
	}
	
	public String getName() {
		return this.myUnidade.getName();
	}

	public String getHint() {
		return "Unidade Hint!";
	}
}