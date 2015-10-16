package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.bean.NovoObjetivoBean;
import br.ifpr.sisplan.controller.bean.PDIControllerBean;
import br.ifpr.sisplan.controller.bean.PDIControllerCached;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroAbstract;
import br.ifpr.sisplan.model.dao.ObjetivoEspecificoDao;
import br.ifpr.sisplan.model.table.ObjetivoEspecifico;
import br.ifpr.sisplan.model.table.ObjetivoEstrategico;
import br.ifpr.sisplan.model.table.Unidade;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class ObjetivoEstrategicoTreeNode extends TreeNodeCadastroAbstract {
	private static final long serialVersionUID = -4321723297704261633L;
	private List<ObjetivoEspecificoTreeNode> objetivosEspecificoTree = new ArrayList<ObjetivoEspecificoTreeNode>();
	
	public ObjetivoEstrategicoTreeNode(TreeNodeGeneric diretrizParent, ObjetivoEstrategico myObjetivo) {
		super(diretrizParent, myObjetivo);
		try {
			this.setObjetivosTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setObjetivosTree() {
		Unidade unidadeSelected = ((PDIControllerBean)this.getMBean("pdiControllerBean")).getUnidadeSelected();
		List<ObjetivoEspecifico> objetivos = null;
		if(unidadeSelected == PDIControllerCached.getInstance().getUnidadeAll())
			objetivos = ConverterToList.convertListMappedToList(getDAO(ObjetivoEspecificoDao.class).
							selectObjetivosEspByEstrategico(this.nameNode.getId()), ObjetivoEspecifico.class);
		else
			objetivos = ConverterToList.convertListMappedToList(getDAO(ObjetivoEspecificoDao.class).
					selectObjetivosByUnidadeObjEstrategico(unidadeSelected.getId(), this.getMyID()), ObjetivoEspecifico.class);;
		int order=0;
		
		for(ObjetivoEspecifico o: objetivos) {
			final ObjetivoEspecificoTreeNode objetivoTree = new ObjetivoEspecificoTreeNode(this, o, ++order);
			this.objetivosEspecificoTree.add(objetivoTree);
		}
	}

/*	private void setUnidadesTree() throws Exception {
		final List<Unidade> unidades = ((SisplanUser)this.getMBean("sisplanUser")).getUnidades();
		for(Unidade u: unidades) {
			final UnidadeTreeNode unidadeTree = new UnidadeTreeNode(this, u);
			this.unidadesTree.add(unidadeTree);
		}

	}*/
	
	public List<ObjetivoEspecificoTreeNode> getUnidadesTree() throws Exception {
/*		if(!this.unidadesTree.isEmpty()) {
			this.unidadesTree.clear();
		}
		this.setUnidadesTree();*/
		if(this.objetivosEspecificoTree.isEmpty())
			this.setObjetivosTree();
		return this.objetivosEspecificoTree;
	}

	
	@SuppressWarnings("unchecked")
	public Enumeration<ObjetivoEspecificoTreeNode> children() {
		return Iterators.asEnumeration(objetivosEspecificoTree.iterator());
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public TreeNode getChildAt(int arg0) {
		return (TreeNode)objetivosEspecificoTree.get(arg0);
	}

	public int getChildCount() {
		return objetivosEspecificoTree.size();
	}

	public int getIndex(TreeNode arg0) {
		return objetivosEspecificoTree.indexOf(arg0);
	}

	public TreeNode getParent() {
		return this.parentNode;
	}

	public boolean isLeaf() {
		return this.objetivosEspecificoTree.isEmpty();
	}

	@Override
	public String toString() {
		return this.nameNode.toString();
	}
	
	public String getType() {
		return this.nameNode.getType();
	}
	
	public String getName() {
		return "Objetivo Estratégico " + this.getMyID();
	}

	public String getDesc() {
		return this.nameNode.getName();
	}
	
	public int getMyID() {
		return this.nameNode.getId();
	}

	public void addTreeNodeChild(TreeNodeGeneric child) {
		this.objetivosEspecificoTree.add((ObjetivoEspecificoTreeNode)child);
	}

	public String getCadastroURL() {
		((NovoObjetivoBean)this.getMBean("novoObjetivoBean")).setTreeNodeParent(this);
		return "/SISPLAN/portal/novo_objetivo.jsf";
	}

	public String getCadastroTitle() {
		return "Cadastrar Objetivo Específico";
	}
}