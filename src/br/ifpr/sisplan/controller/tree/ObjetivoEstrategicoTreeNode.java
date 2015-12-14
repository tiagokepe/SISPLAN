package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.PDIControllerCached;
import br.ifpr.sisplan.controller.bean.NovoObjetivoBean;
import br.ifpr.sisplan.controller.bean.PDIControllerBean;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroAbstract;
import br.ifpr.sisplan.model.dao.ObjetivoEspecificoDao;
import br.ifpr.sisplan.model.table.ObjetivoEspecifico;
import br.ifpr.sisplan.model.table.ObjetivoEstrategico;
import br.ifpr.sisplan.model.table.Unidade;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class ObjetivoEstrategicoTreeNode extends TreeNodeCadastroAbstract {
	private static final long serialVersionUID = -4321723297704261633L;
	private List<ObjetivoEspecificoTreeNode> allObjetivos = new ArrayList<ObjetivoEspecificoTreeNode>();
	private List<ObjetivoEspecificoTreeNode> filteredObjetivos = new ArrayList<ObjetivoEspecificoTreeNode>();
	private String unidadeSelected;
	
	public ObjetivoEstrategicoTreeNode(TreeNodeGeneric diretrizParent, ObjetivoEstrategico myObjetivo, int order) {
		super(diretrizParent, myObjetivo, order);
		try {
			this.setAllObjetivos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setAllObjetivos() {
		Unidade unidadeSelected = ((PDIControllerBean)this.getMBean("pdiControllerBean")).getUnidadeSelected();
		List<ObjetivoEspecifico> objetivos = null;
/*		if(unidadeSelected == PDIControllerCached.getInstance().getUnidadeAll())
			objetivos = ConverterToList.convertListMappedToList(getDAO(ObjetivoEspecificoDao.class).
							selectObjetivosEspByEstrategico(this.nameNode.getId()), ObjetivoEspecifico.class);
		else
			objetivos = ConverterToList.convertListMappedToList(getDAO(ObjetivoEspecificoDao.class).
					selectObjetivosByUnidadeObjEstrategico(unidadeSelected.getId(), this.getMyID()), ObjetivoEspecifico.class);*/
		objetivos = ConverterToList.convertListMappedToList(getDAO(ObjetivoEspecificoDao.class).
				selectObjetivosEspByEstrategico(this.nameNode.getId()), ObjetivoEspecifico.class);
		int order=0;
		
		for(ObjetivoEspecifico o: objetivos) {
			final ObjetivoEspecificoTreeNode objetivoTree = new ObjetivoEspecificoTreeNode(this, o, order++);
			this.allObjetivos.add(objetivoTree);
		}
		this.filteredObjetivos.addAll(this.allObjetivos);
	}

	public List<ObjetivoEspecificoTreeNode> getAllObjetivos() {
		if(this.allObjetivos.isEmpty())
			this.setAllObjetivos();
		return this.allObjetivos;
	}

	
	@SuppressWarnings("unchecked")
	public Enumeration<ObjetivoEspecificoTreeNode> children() {
		return Iterators.asEnumeration(this.filteredObjetivos.iterator());
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public TreeNode getChildAt(int arg0) {
		return (TreeNode)filteredObjetivos.get(arg0);
	}

	public int getChildCount() {
		return filteredObjetivos.size();
	}

	public int getIndex(TreeNode arg0) {
		return filteredObjetivos.indexOf(arg0);
	}

	public TreeNode getParent() {
		return this.parentNode;
	}

	public boolean isLeaf() {
		return this.filteredObjetivos.isEmpty();
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
		return "O"+this.getMyID()+"."+this.nameNode.getName();
	}
	
	public int getMyID() {
		return this.nameNode.getId();
	}

	public void addTreeNodeChild(TreeNodeGeneric child) {
		this.allObjetivos.add((ObjetivoEspecificoTreeNode)child);
		if(child.getName().equals(this.unidadeSelected))
			this.filteredObjetivos.add((ObjetivoEspecificoTreeNode)child);
	}

	public String getCadastroURL() {
		((NovoObjetivoBean)this.getMBean("novoObjetivoBean")).setTreeNodeParent(this);
		return "/SISPLAN/portal/novo_objetivo.jsf";
	}

	public String getCadastroTitle() {
		return "Cadastrar Objetivo Específico";
	}

	public void setUnidadeSelected(String unidadeSelected) {
		this.unidadeSelected = unidadeSelected;
	}
	
	public void applyUnidadeFilter(String unidadeSelected) {
		this.setUnidadeSelected(unidadeSelected);
		this.filteredObjetivos.clear();
		if(unidadeSelected.equals(PDIControllerCached.getInstance().getUnidadeAll().getName())) {
			int order = 0;
			for(ObjetivoEspecificoTreeNode objEsp: this.allObjetivos) {
				objEsp.setOrder(order++);
				this.filteredObjetivos.add(objEsp);
			}
//			this.filteredObjetivos.addAll(this.allObjetivos);
		}
		else {
			int order = 0;
			for(ObjetivoEspecificoTreeNode objEsp: this.allObjetivos) {
				if(objEsp.getUnidadeName().equals(unidadeSelected)) {
					objEsp.setOrder(order++);
					this.filteredObjetivos.add(objEsp);
				}
			}
		}
	}
	
/*	public boolean equals(ObjetivoEstrategicoTreeNode o) {
		return this.getName().equals(o.getName());
	}*/
}