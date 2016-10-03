package br.ifpr.sisplan.controller.tree;

import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.tree.TreeNode;

import org.joda.time.DateTime;
import org.joda.time.Days;

import br.ifpr.sisplan.controller.ProgressStatus;
import br.ifpr.sisplan.controller.bean.PDIControllerBean;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroIface;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.EtapaDao;
import br.ifpr.sisplan.model.dao.ResponsavelDao;
import br.ifpr.sisplan.model.dao.UnidadeDao;
import br.ifpr.sisplan.model.table.Etapa;
import br.ifpr.sisplan.model.table.Unidade;
import br.ifpr.sisplan.util.DateUtil;

public class EtapaTreeNode extends TreeNodeCallBack implements TreeNodeCadastroIface {
	private static final long serialVersionUID = -9205942028545960131L;
	public final static String ETAPA_LEGENDA_BLUE="Etapa em andamento.";
	public final static String ETAPA_LEGENDA_GREEN="Etapa concluída.";
	public final static String ETAPA_LEGENDA_RED="Etapa em atraso.";
	
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
	protected void setIdResponsavel() {
		((Etapa)this.dataNode).setIdResponsavel(this.responsavel.getId());
	}

	@Override
	protected void updateDBResponsavel() {
		this.getDAO(EtapaDao.class).updateResponsavel(this.dataNode.getId(), this.responsavel.getId());		
	}

	public boolean validateDate() {
		DateTime dtIniPrevProjeto = new DateTime(((ProjetoTreeNode)this.parentNode).getDataNode().getData().getDataInicioPrevista());
		DateTime dtIniPrevEtapa = new DateTime(this.dataNode.getData().getDataInicioPrevista());
		int days = Days.daysBetween(dtIniPrevProjeto, dtIniPrevEtapa).getDays();
		if(days < 0) {
			addMensagemErro("Data início prevista da etapa (" + DateUtil.dateToString(dtIniPrevEtapa.toDate())
					+ ") é anterior a data de início do projeto (" + DateUtil.dateToString(dtIniPrevProjeto.toDate()) + ").");
			return false;
		}
		
		DateTime dtFimPrevProjeto = new DateTime(((ProjetoTreeNode)this.parentNode).getDataNode().getData().getDataFimPrevista());
		DateTime dtFimPrevEtapa = new DateTime(this.dataNode.getData().getDataFimPrevista());
		
		days = Days.daysBetween(dtFimPrevEtapa, dtFimPrevProjeto).getDays();
		if(days < 0) {
			addMensagemErro("Data fim prevista da etapa (" + DateUtil.dateToString(dtFimPrevEtapa.toDate())
					+ ") é posterior a data fim prevista do projeto (" + DateUtil.dateToString(dtFimPrevProjeto.toDate()) + ").");
			return false;
		}
		return true;
	}
	
	@Override
	public String getStatusStyleClass() {
		return this.getProgressStatus().getStyleClass();
	}

	@Override
	public boolean isShowProgressStatus() {
		return true;
	}
	
	public String getLegenda() {
		return this.getProgressStatus().getEtapaLegenda();
	}
	
	public ProgressStatus getProgressStatus() {
		if(getDataFimEfetiva() != null)
			return ProgressStatus.EXEC_Green;
		DateTime dtFimPrev = new DateTime(this.getDataFimPrevista());
		DateTime dtToday = new DateTime(new Date());
		int days = Days.daysBetween(dtToday, dtFimPrev).getDays();
		if(days < 0)
			return ProgressStatus.EXEC_Red;
		else
			return ProgressStatus.EXEC_Blue;
	}

	@Override
	public boolean isEnabledObservacao() {
		return true;
	}
}