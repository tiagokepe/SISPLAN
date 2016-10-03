package br.ifpr.sisplan.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.PDIControllerCached;
import br.ifpr.sisplan.controller.ProgressStatus;
import br.ifpr.sisplan.controller.bean.PDIControllerBean;
import br.ifpr.sisplan.model.dao.ObjetivoEstrategicoDao;
import br.ifpr.sisplan.model.table.Diretriz;
import br.ifpr.sisplan.model.table.ObjetivoEstrategico;
import br.ifpr.sisplan.util.ConverterToList;

import com.google.common.collect.Iterators;

public class DiretrizTreeNode extends TreeNodeGeneric {
	private static final long serialVersionUID = 8418909430752199490L;

	public final static String DIR_LEGENDA_GREEN="Todas as unidades cadastraram"
			+ "	projeto(s) para esta Diretriz.";
	public final static String DIR_LEGENDA_ORANGE="Uma ou mais unidade cadastraram"
			+ "	projeto(s) para esta Diretriz, porém existe unidade que ainda não cadastrou.";
	public final static String DIR_LEGENDA_RED="Nenhuma unidade cadastrou projeto(s) para esta Diretriz.";

	private List<ObjetivoEstrategicoTreeNode> objetivosTree = new ArrayList<ObjetivoEstrategicoTreeNode>();
	
	public DiretrizTreeNode(TreeNodeGeneric eixoParent, Diretriz myDiretriz, int order) {
		super(eixoParent, myDiretriz, order);
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
		return this.parentNode;
	}

	public boolean isLeaf() {
		return this.objetivosTree.isEmpty();
	}
	
	public void setObjetivosTree() {
		final List<ObjetivoEstrategico> objetivos = ConverterToList.convertListMappedToList(getDAO(ObjetivoEstrategicoDao.class).
													selectObjetivosByDiretriz(this.descriptionNode.getId()), ObjetivoEstrategico.class);
		int i=0;
		for(ObjetivoEstrategico o: objetivos) {
			final ObjetivoEstrategicoTreeNode objetivoTree = new ObjetivoEstrategicoTreeNode(this, o, i++);
			this.objetivosTree.add(objetivoTree);
		}
	}
	
	public List<ObjetivoEstrategicoTreeNode> getObjetivosTree() {
		if(this.objetivosTree != null && this.objetivosTree.isEmpty())
			this.setObjetivosTree();
		return objetivosTree;
	}
	
	public String getType() {
		return "D"+this.getMyID()+"."+this.descriptionNode.getType();
	}
	
	public String getName() {
		return "Diretriz Organizacional " + this.getMyID();
	}
	
	public String getDescricao() {
		return this.descriptionNode.getDescricao();
	}

	public int getMyID() {
		return this.descriptionNode.getId();
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
		return false;
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
	
	public ProgressStatus getProgressStatus() {
		String unidadeSelected = ((PDIControllerBean)getMBean("pdiControllerBean")).getUnidadeSelectedName();
		if(PDIControllerCached.getInstance().equalsToUnidadeAll(unidadeSelected))
			return this.getStatusForAllUnidades();
		else
			return this.getStatusUnidadeFiltered();
	}

	private ProgressStatus getStatusUnidadeFiltered() {
		for(ObjetivoEstrategicoTreeNode objEstrategico: this.objetivosTree)
			for(ObjetivoEspecificoTreeNode objEspecifico: objEstrategico.getFilteredObjetivos())
				for(EstrategiaTreeNode estrategia: objEspecifico.getEstrategiasTree())
					if(estrategia.getProjetosTree().size() > 0)
						//If the current user isn't a planning manager, so a unique project is enough for green status
							return ProgressStatus.Green;
		return ProgressStatus.Red;
	}
	
	private ProgressStatus getStatusForAllUnidades() {
		HashSet<String> unidadeSet = new HashSet<String>();
		
		for(ObjetivoEstrategicoTreeNode objEstrategico: this.objetivosTree)
			for(ObjetivoEspecificoTreeNode objEspecifico: objEstrategico.getFilteredObjetivos())
				for(EstrategiaTreeNode estrategia: objEspecifico.getEstrategiasTree())
					if(estrategia.getProjetosTree().size() > 0)
						unidadeSet.add(objEspecifico.getUnidadeName());
		
		if(unidadeSet.size() > 0 && unidadeSet.size() == PDIControllerCached.getInstance().getListUnidades().size())
			return ProgressStatus.Green;
		else if(unidadeSet.size() > 0)
			return ProgressStatus.Orange;
			
		return ProgressStatus.Red;
	}
	
	@Override
	public String getStatusStyleClass() {
		return ProgressStatus.Default.getStyleClass();
	}
	
	@Override
	public boolean isShowProgressStatus() {
		return false;
	}
	
	public String getImgStatus() {
		return this.getProgressStatus().getIconPath();
	}

	public String getLegenda() {
		ProgressStatus status = this.getProgressStatus();
		String unidadeName = ((PDIControllerBean)getMBean("pdiControllerBean")).getUnidadeSelectedName();
		if(PDIControllerCached.getInstance().equalsToUnidadeAll(unidadeName) )
			return status.getDiretrizLegenda();
		else
			if(status == ProgressStatus.Green)
				return "A unidade: "+unidadeName+" cadastrou um ou mais projetos para esta diretriz";
			else
				return "A unidade: "+unidadeName+" não cadastrou nenhum projeto para esta diretriz";
	}
}