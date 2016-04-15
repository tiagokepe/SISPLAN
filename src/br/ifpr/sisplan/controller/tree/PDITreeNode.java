package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ProgressStatus;
import br.ifpr.sisplan.controller.bean.SisplanUser;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroAbstract;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroIface;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfoIface;
import br.ifpr.sisplan.model.dao.EixoDao;
import br.ifpr.sisplan.model.dao.PDIDao;
import br.ifpr.sisplan.model.table.Eixo;
import br.ifpr.sisplan.model.table.PDI;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class PDITreeNode extends TreeNodeCadastroAbstract {
	private static final long serialVersionUID = -1835441558926237938L;

	private List<EixoTreeNode> eixosTree = new ArrayList<EixoTreeNode>();
	private String newDescricao;

	public PDITreeNode(PDI pdi, int order) {
		super(null, pdi, order);
		this.setEixosTree();
	}
	
	public Enumeration<EixoTreeNode> children() {
		return Iterators.asEnumeration(eixosTree.iterator());
	}
	public boolean getAllowsChildren() {
		return true;
	}
	public TreeNode getChildAt(int childIndex) {
		return eixosTree.get(childIndex);
	}
	public int getChildCount() {
		return eixosTree.size();
	}
	public int getIndex(TreeNode node) {
		return eixosTree.indexOf(node);
	}
	public TreeNode getParent() {
		return null;
	}
	public boolean isLeaf() {
		return eixosTree.isEmpty();
	}

	public void setEixosTree() {
		final List<Eixo> eixos = ConverterToList.convertListMappedToList(getDAO(EixoDao.class).selectEixosByPDI(this.descriptionNode.getId()), Eixo.class);
		int i=0;
		for(Eixo e: eixos) {
			final EixoTreeNode eixoTree = new EixoTreeNode(this, e, i++);
			this.eixosTree.add(eixoTree);
		}
	}
	
	public List<EixoTreeNode> getEixosTree() {
/*		if(!this.eixosTree.isEmpty()) {
			this.eixosTree.clear();
		}
		this.setEixosTree();*/
		if(this.eixosTree != null && this.eixosTree.isEmpty())
			this.setEixosTree();
		return eixosTree;
	}

	@Override
	public String toString() {
		return this.descriptionNode.toString();
	}
	
	public String getType() {
		return this.descriptionNode.getType();
	}
	
	public String getName() {
		return this.descriptionNode.getDescricao();
	}
	
	public String getDescricao() {
		return this.descriptionNode.getDescricao();
	}

	public int getMyID() {
		return this.descriptionNode.getId();
	}

	public PDI getPDI() {
		return (PDI)this.descriptionNode;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof PDITreeNode) {
			return (this.getMyID() == ((PDITreeNode)o).getMyID()) ? true : false;
		}
		return false;
	}

	public boolean isRenderedDescricao() {
		return true;
	}

	public boolean isRenderedUnidade() {
		return false;
	}

	public boolean isRenderedCadastrar() {
		return false;
	}

	public boolean isRenderedAlterar() {
		return ((SisplanUser)getMBean("sisplanUser")).isPlanningManager();
	}

	public boolean isRenderedExcluir() {
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

	@Override
	public boolean isShowProgressStatus() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getCadastroURL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCadastroTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAlterarURL() {
		return "/SISPLAN/portal/alterar_descricao.jsf";
	}

	public void addTreeNodeChild(TreeNodeGeneric child) {
		// TODO Auto-generated method stub
		
	}

	public void removeTreeNodeChild(TreeNodeGeneric child) {
		// TODO Auto-generated method stub
		
	}

	public void delete() {
		// TODO Auto-generated method stub
		
	}

	public void deleteFromDB() {
		// TODO Auto-generated method stub
		
	}
	
	public void setDescricao(String descricao) {
		this.newDescricao = descricao;
	}

	public void save() {
		this.descriptionNode.setDescricao(newDescricao);
		LogHistory.getInstance().log((TreeNodeInfoIface) this,
				(TreeNodeCadastroIface) this, "Descrição",
				this.descriptionNode.getDescricao(),
				newDescricao);
		this.getDAO(PDIDao.class).updatePDI(this.getDescriptionNode());

		this.returnMainPage();		
	}

	public void cancel() {
		this.returnMainPage();		
	}

	public String getUnidadeName() {
		// TODO Auto-generated method stub
		return null;
	}
}