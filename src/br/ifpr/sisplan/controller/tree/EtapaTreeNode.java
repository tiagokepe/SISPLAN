package br.ifpr.sisplan.controller.tree;

import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ProgressStatus;
import br.ifpr.sisplan.controller.bean.PDIControllerBean;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroIface;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.EtapaDao;
import br.ifpr.sisplan.model.dao.ResponsavelDao;
import br.ifpr.sisplan.model.dao.UnidadeDao;
import br.ifpr.sisplan.model.table.Etapa;
import br.ifpr.sisplan.model.table.Unidade;

public class EtapaTreeNode extends TreeNodeCallBack implements TreeNodeCadastroIface {
	private static final long serialVersionUID = -9205942028545960131L;
	
	public EtapaTreeNode(TreeNodeGeneric parent, Etapa etapa, int order) {
		super(parent, etapa, order);
	}
	
	public TreeNode getChildAt(int paramInt) {
		return null;
	}

	public int getChildCount() {
		return 0;
	}

	public TreeNode getParent() {
		return this.parentNode;
	}

	public int getIndex(TreeNode paramTreeNode) {
		return 0;
	}

	public boolean getAllowsChildren() {
		return false;
	}

	public boolean isLeaf() {
		return true;
	}

	public Enumeration<TreeNode> children() {
        return new Enumeration<TreeNode>() {
            public boolean hasMoreElements() {
                return false;
            }
 
            public TreeNode nextElement() {
                return null;
            }
        };
	}
	
	@Override
	public String toString() {
		return this.dataNode.toString();
	}

	public String getType() {
		return this.dataNode.getType();
	}

	public String getName() {
		return "Etapa: " + this.dataNode.getDescricao();
	}
	
	public boolean isProjectNode() {
		return false;
	}
	
	public String getHeaderDetails() {
		return "Informações de Etapa";
	}

	public int getMyID() {
		return this.getDataNode().getId();
	}

	public String getResponsavelName() {
		return this.responsavelName;
	}
	
	protected void setResponsavel() {
		if(responsavel == null) {
			this.responsavel = ResponsavelDao.getInstance().selectResponsavel(((Etapa)this.dataNode).getIdResponsavel());
			responsavelName = responsavel.getName();
		}
	}
	
	protected void setResponsaveis() {
		Unidade unidade = this.getDAO(UnidadeDao.class).selectUnidadeOfResponsavel(((Etapa)dataNode).getIdResponsavel());
		this.responsaveis = ResponsavelDao.getInstance().selectResponsavelByUnidade(unidade.getId());			
	}

	public String getCadastroURL() {
		// TODO It is not need bacause Etapa doesn't register nothing.
		return null;
	}

	public String getCadastroTitle() {
		// TODO It is not need bacause Etapa doesn't register nothing.
		return null;
	}

	public void addTreeNodeChild(TreeNodeGeneric child) {
		// TODO It is not need bacause Etapa doesn't register nothing.
		
	}
	
	public void deleteFromDB() {
		// Removing etapa from data base
		this.getDAO(EtapaDao.class).deleteEtapa(this.dataNode.getId());
		this.getDAO(DataDao.class).deleteData(this.dataNode.getData());
	}

	public void delete() {
		System.out.println("ETAPA delete...");
		this.deleteFromDB();
		
		// Removing etapa references from java objects
		for(EixoTreeNode eixo: ((PDIControllerBean)this.getMBean("pdiControllerBean")).getCurrentPDI().getEixosTree())
			for(DiretrizTreeNode dir: eixo.getDiretrizesTree())
				for(ObjetivoEstrategicoTreeNode objEst: dir.getObjetivosTree())
					for(ObjetivoEspecificoTreeNode objEsp: objEst.getFilteredObjetivos())
						for(EstrategiaTreeNode est: objEsp.getEstrategiasTree())
							for(ProjetoTreeNode proj: est.getProjetosTree()) {
								System.out.println("PROJETO-"+proj.getName()+" - "+proj.getMyID());
								System.out.print("ETAPAS: ");
								for(EtapaTreeNode et: proj.getEtapasTree())
									System.out.print(et.getMyID() + ", ");
								
								boolean foundEtapa = false;
								Iterator<EtapaTreeNode> it = proj.getEtapasTree().iterator();
								EtapaTreeNode nextEtapa;
								while(it.hasNext()) {
									nextEtapa = it.next();
									// Se etapa encontrada, então decrementa a ordem das etapas posteriores
									if(foundEtapa)
										nextEtapa.decreaseOrder();
									if(nextEtapa.getMyID() == this.getMyID()) {
										((PDIControllerBean)this.getMBean("pdiControllerBean")).removeExpandedNode(nextEtapa.getRowKey());
										it.remove();
										foundEtapa = true;
									}
									System.out.println(nextEtapa.getMyID() +  " == " + this.getMyID());
								}
								System.out.println("--------------------------------------------");
							}
					
		//((PDIControllerBean)this.getMBean("pdiControllerBean")).setCurrentNodeSelection(null);
		System.out.println("ETAPA deletou...");
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
		return true;
	}

	public boolean isRenderedProjetoOrEtapa() {
		return true;
	}

	public void removeTreeNodeChild(TreeNodeGeneric child) {
		// TODO Auto-generated method stub
	}
	
	public void save() {
		super.save();
	}

	public void cancel() {
		super.cancel();
	}

	public String getAlterarURL() {
		return "/SISPLAN/portal/alterar_projeto_etapa.jsf";
	}
	
	public String getUnidadeName() {
		return ((TreeNodeCadastroIface)this.parentNode).getUnidadeName();
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
	
	@Override
	protected void setIdResponsavel() {
		((Etapa)this.dataNode).setIdResponsavel(this.responsavel.getId());
	}

	@Override
	protected void updateDBResponsavel() {
		this.getDAO(EtapaDao.class).updateResponsavel(this.dataNode.getId(), this.responsavel.getId());		
	}
	
}