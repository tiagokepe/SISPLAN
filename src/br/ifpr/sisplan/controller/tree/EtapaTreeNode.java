package br.ifpr.sisplan.controller.tree;

import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.bean.PDIControllerBean;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroIface;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.EtapaDao;
import br.ifpr.sisplan.model.dao.ResponsavelDao;
import br.ifpr.sisplan.model.table.Etapa;
import br.ifpr.sisplan.model.table.Responsavel;

public class EtapaTreeNode extends TreeNodeCallBack implements TreeNodeCadastroIface {
	private static final long serialVersionUID = -9205942028545960131L;
	private Responsavel responsavel;
	
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
		return this.dataNode.getName();
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

	public String getDesc() {
		return this.getDescricao();
	}

	public String getResponsavelName() {
		if(responsavel == null) {
			this.responsavel = ResponsavelDao.getInstance().selectResponsavel(((Etapa)this.dataNode).getIdResponsavel());
		}
		return this.responsavel.getName();
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
	
	public void deleteEtapaFromDB() {
		// Removing etapa from data base
		this.getDAO(EtapaDao.class).deleteEtapa(this.dataNode.getId());
		this.getDAO(DataDao.class).deleteData(this.dataNode.getData());
	}

	public void delete() {
		System.out.println("ETAPA delete...");
		this.deleteEtapaFromDB();
		
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

	public boolean isRenderedExcluir() {
		return true;
	}
	
	public boolean isRenderedProjetoOrEtapa() {
		return true;
	}

	public void removeTreeNodeChild(TreeNodeGeneric child) {
		// TODO Auto-generated method stub
		
	}
}