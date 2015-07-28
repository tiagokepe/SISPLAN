package br.ifpr.sisplan.controller.tree;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ifaces.TreeNodeActions;
import br.ifpr.sisplan.controller.ifaces.TreeNodeDetails;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfo;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.EtapaDao;
import br.ifpr.sisplan.model.table.Etapa;
import br.ifpr.sisplan.model.table.Projeto;
import br.ifpr.sisplan.util.ConverterToList;
import br.ifpr.sisplan.util.NameNode;
import br.ufrn.arq.web.jsf.AbstractController;

import com.google.common.collect.Iterators;

public class ProjetoTreeNode extends AbstractController implements TreeNode, TreeNodeInfo, TreeNodeDetails, TreeNodeActions {
	private static final long serialVersionUID = -7787388029320598005L;
	private EstrategiaTreeNode parentEstrategia;
	private Projeto myProjeto;
	private List<EtapaTreeNode> etapasTree = new ArrayList<EtapaTreeNode>();
	
	public ProjetoTreeNode(EstrategiaTreeNode parent, Projeto projeto) {
		this.parentEstrategia = parent;
		this.myProjeto = projeto;
		this.setEtapasTree();
	}
	
	public TreeNode getChildAt(int paramInt) {
		return etapasTree.get(paramInt);
	}

	public int getChildCount() {
		return this.etapasTree.size();
	}

	public TreeNode getParent() {
		return this.parentEstrategia;
	}

	public int getIndex(TreeNode paramTreeNode) {
		return this.etapasTree.indexOf(paramTreeNode);
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public boolean isLeaf() {
		return this.etapasTree.isEmpty();
	}

	public Enumeration children() {
		return Iterators.asEnumeration(this.etapasTree.iterator());
	}
	
	public List<EtapaTreeNode> getEtapasTree() {
		return etapasTree;
	}

	public void setEtapasTree() {
		final List<Etapa> etapas = ConverterToList.convertListMappedToList(getDAO(EtapaDao.class).selectEtapaByProject(this.myProjeto.getId()), Etapa.class);

		for(Etapa e: etapas) {
			this.setDataEtapa(e);
			final EtapaTreeNode etapaTree = new EtapaTreeNode(this, e);
			this.etapasTree.add(etapaTree);
		}
	}
	
	private void setDataEtapa(Etapa e) {
		e.setData(getDAO(DataDao.class).selectDataByEtapa(e.getId()));
	}

	@Override
	public String toString() {
		return myProjeto.toString();
	}

	public String getType() {
		return this.myProjeto.getType();
	}

	public String getName() {
		return this.myProjeto.getName();
	}
	
	public String getHint() {
		return "Projeto Hint!";
	}

	public String getDescricao() {
		return this.myProjeto.getDescricao();
	}
	
	public boolean isRenderedDescription() {
		return true;
	}

	public String getHeaderDetails() {
		return "Informações de Projeto";
	}

	public String getDataInicioPrevista() {
		Date dt = this.myProjeto.getData().getDataInicioPrevista();
		return dt != null? new SimpleDateFormat(NameNode.DefaultDateFormat).format(dt): "";
	}

	public String getDataInicioEfetiva() {
		Date dt = this.myProjeto.getData().getDataInicioEfetiva();
		return dt != null? new SimpleDateFormat(NameNode.DefaultDateFormat).format(dt): "";
	}

	public String getDataFimPrevista() {
		Date dt = this.myProjeto.getData().getDataFimPrevista();
		return dt != null? new SimpleDateFormat(NameNode.DefaultDateFormat).format(dt): "";
	}

	public String getDataFimEfetiva() {
		Date dt = this.myProjeto.getData().getDataFimEfetiva();
		return dt != null? new SimpleDateFormat(NameNode.DefaultDateFormat).format(dt): "";
	}

	public void changeDate() {
		System.out.println(this.getClass().getName());
		return;
	}
}