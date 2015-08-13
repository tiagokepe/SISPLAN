package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.bean.SisplanUser;
import br.ifpr.sisplan.controller.ifaces.TreeNodeHint;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfo;
import br.ifpr.sisplan.model.table.ObjetivoEstrategico;
import br.ifpr.sisplan.model.table.Unidade;
import br.ufrn.arq.web.jsf.AbstractController;

import com.google.common.collect.Iterators;

public class ObjetivoEstrategicoTreeNode extends AbstractController implements TreeNode, TreeNodeInfo, TreeNodeHint {
	private static final long serialVersionUID = -4321723297704261633L;

	private DiretrizTreeNode diretrizParent;
	private ObjetivoEstrategico myObjetivo;
	private List<UnidadeTreeNode> unidadesTree = new ArrayList<UnidadeTreeNode>();
	
	public ObjetivoEstrategicoTreeNode(DiretrizTreeNode diretrizParent, ObjetivoEstrategico myObjetivo) {
		this.diretrizParent = diretrizParent;
		this.myObjetivo = myObjetivo;
		try {
			this.setUnidadesTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setUnidadesTree() throws Exception {
		final List<Unidade> unidades = ((SisplanUser)this.getMBean("sisplanUser")).getUnidades();
		for(Unidade u: unidades) {
			final UnidadeTreeNode unidadeTree = new UnidadeTreeNode(this, u);
			this.unidadesTree.add(unidadeTree);
		}
		
		/* Check if the user is a planning manager or a campus manager*/
/*		if(Permission.PLANNING_MANAGER.checkPermission(this.getUsuarioLogado().getPapeis())) {
			final List<Unidade> unidades = ConverterToList.convertListMappedToList(getDAO(UnidadeDao.class).
													selectUnidadesByObjetivoEstrategico(this.myObjetivo.getId()), Unidade.class);
			for(Unidade u: unidades) {
				final UnidadeTreeNode unidadeTree = new UnidadeTreeNode(this, u);
				this.unidadesTree.add(unidadeTree);
			}
			
		} else if (Permission.CAMPUS_MANAGER.checkPermission(this.getUsuarioLogado().getPapeis())) {
			final int id_unidade = this.getUsuarioLogado().getUnidade().getId();
			final List<Unidade> unidades = ConverterToList.convertListMappedToList(getDAO(UnidadeDao.class).selectUnidade(id_unidade), Unidade.class);
			if(unidades.isEmpty())
				throw new Exception("The Unidade="+this.getUsuarioLogado().getUnidade().getNome()+" is not registered.");
			
			//It checks if Unidade implements the Objetivo 
			if(getDAO(UnidadeDao.class).checkIfObjetivoIsBoundToUnidade(this.myObjetivo.getId(), unidades.get(0).getId())) {
				final UnidadeTreeNode unidadeTree = new UnidadeTreeNode(this, unidades.get(0));
				this.unidadesTree.add(unidadeTree);
			}
		}*/
	}
	
	public List<UnidadeTreeNode> getUnidadesTree() throws Exception {
		if(!this.unidadesTree.isEmpty()) {
			this.unidadesTree.clear();
		}
		this.setUnidadesTree();
		return this.unidadesTree;
	}

	
	@SuppressWarnings("unchecked")
	public Enumeration<UnidadeTreeNode> children() {
		return Iterators.asEnumeration(unidadesTree.iterator());
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public TreeNode getChildAt(int arg0) {
		return (TreeNode)unidadesTree.get(arg0);
	}

	public int getChildCount() {
		return unidadesTree.size();
	}

	public int getIndex(TreeNode arg0) {
		return unidadesTree.indexOf(arg0);
	}

	public TreeNode getParent() {
		return diretrizParent;
	}

	public boolean isLeaf() {
		return this.unidadesTree.isEmpty();
	}
	
	public ObjetivoEstrategico getMyObjetivo() {
		return myObjetivo;
	}

	@Override
	public String toString() {
		return myObjetivo.toString();
	}
	
	public String getType() {
		return this.myObjetivo.getType();
	}
	
	public String getName() {
		return "Objetico Estratégico " + this.myObjetivo.getId();
	}

	public String getHint() {
		return this.myObjetivo.getName();
	}
}