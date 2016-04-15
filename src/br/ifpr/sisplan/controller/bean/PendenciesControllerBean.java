package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map; 
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.tree.TreeNode;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ifpr.sisplan.controller.PDIControllerCached;
import br.ifpr.sisplan.controller.tree.DiretrizPendenteTreeNode;
import br.ifpr.sisplan.controller.tree.DiretrizTreeNode;
import br.ifpr.sisplan.controller.tree.EixoTreeNode;
import br.ifpr.sisplan.controller.tree.EstrategiaTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEspecificoTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEstrategicoTreeNode;
import br.ifpr.sisplan.controller.tree.UnidadePendenteTreeNode;
import br.ifpr.sisplan.model.table.Diretriz;
import br.ifpr.sisplan.model.table.Unidade;
import br.ufrn.arq.web.jsf.AbstractController;

@Component
@Scope("request")
public class PendenciesControllerBean extends AbstractController {
	private List<TreeNode> pendenciasTree;
	
	public PendenciesControllerBean() {
		this.buildPendenciasTree();
	}

	public List<TreeNode> getPendenciasTree() {
		return pendenciasTree;
	}

	public void buildPendenciasTree() {
		this.pendenciasTree = new ArrayList<TreeNode>();
		
		SortedMap<Diretriz, TreeSet<Unidade>> mapDiretrizUnidades = new TreeMap<Diretriz, TreeSet<Unidade>>();
		boolean hasProject = false;
		for(EixoTreeNode eixo: ((PDIControllerBean)this.getMBean("pdiControllerBean")).getCurrentPDI().getEixosTree())
			for(DiretrizTreeNode dir: eixo.getDiretrizesTree()) {
				TreeSet<Unidade> unidadesOk = new TreeSet<Unidade>();
				for(ObjetivoEstrategicoTreeNode objEst: dir.getObjetivosTree()) {
					for(ObjetivoEspecificoTreeNode objEsp: objEst.getAllObjetivos()) {
						for(EstrategiaTreeNode est: objEsp.getEstrategiasTree())
							if(est.getProjetosTree().size() > 0)
								hasProject = true;
						
						if(hasProject) {
							unidadesOk.add(objEsp.getUnidade());
							hasProject = false;
						}
					}
				}
				TreeSet<Unidade> pendingUnidades = new TreeSet<Unidade>(PDIControllerCached.getInstance().getListUnidades());
				if(!unidadesOk.isEmpty())
					pendingUnidades.removeAll(unidadesOk);
				mapDiretrizUnidades.put((Diretriz)dir.getDescriptionNode(), pendingUnidades);
			}
		
		List<Unidade> listUnidades;
		SortedMap<Unidade, TreeSet<Diretriz>> mapUnidadeDiretrizes = new TreeMap<Unidade, TreeSet<Diretriz>>();
		for(Map.Entry<Diretriz, TreeSet<Unidade>> entry: mapDiretrizUnidades.entrySet()) {
			for(Unidade u: entry.getValue()) {
				TreeSet<Diretriz> diretrizes = mapUnidadeDiretrizes.get(u);
				if(diretrizes == null) {
					diretrizes = new TreeSet<Diretriz>();
					mapUnidadeDiretrizes.put(u, diretrizes);
				}
				diretrizes.add(entry.getKey());
			}
		}
		
		//Building Unidade Tree...
		for(SortedMap.Entry<Unidade, TreeSet<Diretriz>> entry: mapUnidadeDiretrizes.entrySet()) {
			List<DiretrizPendenteTreeNode> diretrizes = new ArrayList<DiretrizPendenteTreeNode>();
			for(Diretriz dir: entry.getValue()) {
				DiretrizPendenteTreeNode diretrizNode = new DiretrizPendenteTreeNode(dir);
				diretrizes.add(diretrizNode);
			}
			UnidadePendenteTreeNode unidadeNode = new UnidadePendenteTreeNode(entry.getKey(), diretrizes);
			pendenciasTree.add(unidadeNode);
		}
	}
	
	public String getPendenciasURL() {
		return "/SISPLAN/portal/pendencias_unidade.jsf";
	}
	
	public void gotToPendencias() {
		this.redirect("/portal/pendencias_unidade.jsf");
	}

}
