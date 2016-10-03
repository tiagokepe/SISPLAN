package br.ifpr.sisplan.controller.bean.pendencies;

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
import br.ifpr.sisplan.controller.ProgressStatus;
import br.ifpr.sisplan.controller.bean.PDIControllerBean;
import br.ifpr.sisplan.controller.tree.DiretrizTreeNode;
import br.ifpr.sisplan.controller.tree.EixoTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEspecificoTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEstrategicoTreeNode;
import br.ifpr.sisplan.controller.tree.PendenteTreeNode;
import br.ifpr.sisplan.controller.tree.pendencies.RootPendenteTreeNode;
import br.ifpr.sisplan.controller.tree.pendencies.UnidadePendenteTreeNode;
import br.ifpr.sisplan.model.table.ObjetivoEstrategico;
import br.ifpr.sisplan.model.table.Unidade;
import br.ufrn.arq.web.jsf.AbstractController;

@Component
@Scope("request")
public class UnidadePendenciesControllerBean extends AbstractController {
	
	private List<TreeNode> pendenciasTree;
	
	public UnidadePendenciesControllerBean() {
		this.buildPendenciasTree();
	}

	public List<TreeNode> getPendenciasTree() {
		return pendenciasTree;
	}

	public void buildPendenciasTree() {
		this.pendenciasTree = new ArrayList<TreeNode>();
		
		SortedMap<ObjetivoEstrategico, TreeSet<Unidade>> mapObjEstrategicoUnidades = new TreeMap<ObjetivoEstrategico, TreeSet<Unidade>>();
		
		for(EixoTreeNode eixo: ((PDIControllerBean)this.getMBean("pdiControllerBean")).getCurrentPDI().getEixosTree())
			for(DiretrizTreeNode dir: eixo.getDiretrizesTree()) {
				for(ObjetivoEstrategicoTreeNode objEst: dir.getObjetivosTree()) {
					TreeSet<Unidade> unidadesOk = new TreeSet<Unidade>();
					for(ObjetivoEspecificoTreeNode objEsp: objEst.getAllObjetivos()) {
						if(objEsp.getProgressStatus().compareTo(ProgressStatus.PLAN_Green) == 0)
							unidadesOk.add(objEsp.getUnidade());
					}
					TreeSet<Unidade> pendingUnidades = new TreeSet<Unidade>(PDIControllerCached.getInstance().getListUnidades());
					if(!unidadesOk.isEmpty())
						pendingUnidades.removeAll(unidadesOk);
					mapObjEstrategicoUnidades.put((ObjetivoEstrategico)objEst.getDescriptionNode(), pendingUnidades);
				}

			}
		
		List<Unidade> listUnidades;
		SortedMap<Unidade, TreeSet<ObjetivoEstrategico>> mapUnidadeObjs = new TreeMap<Unidade, TreeSet<ObjetivoEstrategico>>();
		for(Map.Entry<ObjetivoEstrategico, TreeSet<Unidade>> entry: mapObjEstrategicoUnidades.entrySet()) {
			for(Unidade u: entry.getValue()) {
				TreeSet<ObjetivoEstrategico> objs = mapUnidadeObjs.get(u);
				if(objs == null) {
					objs = new TreeSet<ObjetivoEstrategico>();
					mapUnidadeObjs.put(u, objs);
				}
				objs.add(entry.getKey());
			}
		}
		
		//Building Unidade Tree...
		for(SortedMap.Entry<ObjetivoEstrategico, TreeSet<Unidade>> entry: mapObjEstrategicoUnidades.entrySet()) {
			List<PendenteTreeNode> unidades = new ArrayList<PendenteTreeNode>();
			for(Unidade u: entry.getValue()) {
				UnidadePendenteTreeNode unidadeNode = new UnidadePendenteTreeNode(u);
				unidades.add(unidadeNode);
			}
			RootPendenteTreeNode objNode = new RootPendenteTreeNode(entry.getKey(), unidades);
			pendenciasTree.add(objNode);
		}
	}
	
	public String getPendenciasURL() {
		return "/SISPLAN/portal/pendencias_unidade.jsf";
	}
	
	public void gotToPendencias() {
		this.redirect("/portal/pendencias_unidade.jsf");
	}
}
