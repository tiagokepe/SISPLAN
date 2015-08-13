package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ifaces.TreeNodeHint;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfo;
import br.ifpr.sisplan.model.dao.ObjetivoEstrategicoDao;
import br.ifpr.sisplan.model.table.Diretriz;
import br.ifpr.sisplan.model.table.ObjetivoEstrategico;
import br.ifpr.sisplan.util.ConverterToList;
import br.ufrn.arq.web.jsf.AbstractController;

import com.google.common.collect.Iterators;

public class DiretrizTreeNode extends AbstractController implements TreeNode, TreeNodeInfo, TreeNodeHint {
	private static final long serialVersionUID = 8418909430752199490L;
	private EixoTreeNode eixoParent;
	private Diretriz myDiretriz;
	private List<ObjetivoEstrategicoTreeNode> objetivosTree = new ArrayList<ObjetivoEstrategicoTreeNode>();
	
	public DiretrizTreeNode(EixoTreeNode eixoParent, Diretriz myDiretriz) {
		this.eixoParent = eixoParent;
		this.myDiretriz = myDiretriz;
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
		return eixoParent;
	}

	public boolean isLeaf() {
		return this.objetivosTree.isEmpty();
	}
	
	public void setObjetivosTree() {
		final List<ObjetivoEstrategico> objetivos = ConverterToList.convertListMappedToList(getDAO(ObjetivoEstrategicoDao.class).
													selectObjetivosByDiretriz(myDiretriz.getId()), ObjetivoEstrategico.class);
		for(ObjetivoEstrategico o: objetivos) {
			final ObjetivoEstrategicoTreeNode objetivoTree = new ObjetivoEstrategicoTreeNode(this, o);
			this.objetivosTree.add(objetivoTree);
		}
	}
	
	public List<ObjetivoEstrategicoTreeNode> getObjetivosTree() {
		if(!this.objetivosTree.isEmpty()) {
			this.objetivosTree.clear();
		}
		this.setObjetivosTree();
		return objetivosTree;
	}
	
	public String getType() {
		return this.myDiretriz.getType();
	}
	
	public String getName() {
		return "Diretriz Organizacional " + this.myDiretriz.getId();
	}
	
	public String getHint() {
		return this.myDiretriz.getName();
	}
}
