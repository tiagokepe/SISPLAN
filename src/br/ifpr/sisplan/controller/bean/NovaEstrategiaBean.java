package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import br.ifpr.sisplan.controller.PDIControllerCached;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroAbstract;
import br.ifpr.sisplan.controller.tree.DiretrizTreeNode;
import br.ifpr.sisplan.controller.tree.EixoTreeNode;
import br.ifpr.sisplan.controller.tree.EstrategiaTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEspecificoTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEstrategicoTreeNode;
import br.ifpr.sisplan.model.dao.EstrategiaDao;
import br.ifpr.sisplan.model.table.Estrategia;
import br.ufrn.arq.seguranca.log.Logger;

@Component
@Scope("request")
public class NovaEstrategiaBean extends NovoCadastro<TreeNodeCadastroAbstract> {
	private static final long serialVersionUID = 7471068395949877404L;
/*	private static NovaEstrategiaBean instance;
	
	public static NovaEstrategiaBean getInstance() {
		if(instance == null)
			instance = new NovaEstrategiaBean();
		
		return instance;
	}*/
	
    private List<SelectItem> availableObjEsp; 
    private List<String> selectedObjEsp;
    
    //Tá dando problema com TREEMap, mas tem que ser uma lista ordenada para evitar valores repetidos
	private Map<String, List<ObjetivoEspecificoTreeNode>> mapObjEspecificoTreeNode;
	
	public NovaEstrategiaBean() {
		super();
	}

/*	public String getUnidadeName() {
		return ((ObjetivoEspecificoTreeNode)this.parent).getUnidadeName();
	}*/
	
	public String getObjetivoName() {
		return this.parent.getName();
	}

	public String getObjetivoDesc() {
		return this.parent.getDesc();
	}

	public void save() {
		if(validateFields()) {
			final Estrategia estrategia = getDAO(EstrategiaDao.class).insertEstrategia(this.descricao);
			for(String strObj: this.selectedObjEsp) {
//				ObjetivoEspecificoTreeNode objEsp = ((PDIControllerBean)this.getMBean("pdiControllerBean")).getMapObjEspecificoTreeNode().get(strObj);
				List<ObjetivoEspecificoTreeNode> listEsp = this.mapObjEspecificoTreeNode.get(strObj);
				for(ObjetivoEspecificoTreeNode objEsp: listEsp) {
					try {
						// Try to insert a relationship between ObjetivoEspecifico e Estrategia, even ignoring primary key constraint
						getDAO(EstrategiaDao.class).insertRelationshipObjetivoEstrategia(objEsp.getMyID(), estrategia.getId());
					}
					catch(DuplicateKeyException e) {
						Logger.warn("SISPLAN - DuplicateKeyException, violation of the primary key constraint,"
								+ "i.e., relationship between Objetivo Específico("+estrategia.getId()+") and Estratégia("+objEsp.getMyID()+") already exist.");
					} catch(Exception e) {
						e.printStackTrace();
					}
					EstrategiaTreeNode estTreeNode = new EstrategiaTreeNode(objEsp, estrategia, objEsp.getChildCount());
					objEsp.addTreeNodeChild(estTreeNode);
				}
			}
			this.returnMainPage();
		}
	}
	
	@Override
	protected boolean validateFields() {
		boolean ret = true; 
		if(this.descricao.isEmpty()) {
			addMensagemErro("Descrição está vazia, ela deve ser preenchida.");
			ret = false;
		}
		if(this.unidadeName.isEmpty() || this.unidadeName.equals(SELECIONE_UNIDADE)
				|| this.unidadeName.equals(PDIControllerCached.getInstance().getUnidadeAll())) {
			addMensagemErro("Unidade não pode ser vazia.");
			ret = false;
		}
		if(this.selectedObjEsp == null || this.selectedObjEsp.isEmpty()) {
			addMensagemErro("Nenhum Objetivo Específico foi selecionado.");
			ret = false;
		}
			
		return ret;
	}

	public List<SelectItem> getAvailableObjEsp() {
		return availableObjEsp;
	}

	public void setAvailableObjEsp(List<SelectItem> availableObjEsp) {
		this.availableObjEsp = availableObjEsp;
	}

	public List<String> getSelectedObjEsp() {
		return selectedObjEsp;
	}

	public void setSelectedObjEsp(List<String> selectedObjEsp) {
		this.selectedObjEsp = selectedObjEsp;
	}
	
	//TODO Atualizar MAPA conforme a unidade selecionada 
	@Override
	protected void initInternalStructure() {
		this.availableObjEsp = new ArrayList<SelectItem>();
		this.mapObjEspecificoTreeNode = new LinkedHashMap<String, List<ObjetivoEspecificoTreeNode>>();
		for(EixoTreeNode eixo: ((PDIControllerBean)this.getMBean("pdiControllerBean")).getCurrentPDI().getEixosTree())
			for(DiretrizTreeNode dir: eixo.getDiretrizesTree())
				for(ObjetivoEstrategicoTreeNode objEst: dir.getObjetivosTree())
					for(ObjetivoEspecificoTreeNode objEsp: objEst.getAllObjetivos()) {
						List<ObjetivoEspecificoTreeNode> listObj = this.mapObjEspecificoTreeNode.get(objEsp.getDesc());
						if(listObj == null) {
							listObj = new ArrayList<ObjetivoEspecificoTreeNode>();
							this.mapObjEspecificoTreeNode.put(objEsp.getDesc(), listObj);
							this.availableObjEsp.add(new SelectItem(objEsp.getDesc()));
						}
						listObj.add(objEsp);
					}
					
/*		for(Map.Entry<String, ObjetivoEspecificoTreeNode> entry: ((PDIControllerBean)this.getMBean("pdiControllerBean")).getMapObjEspecificoTreeNode().entrySet()) {
			this.availableObjEsp.add(new SelectItem(entry.getKey()));
		}*/
	}
	
	@Override
	public void unidadeSelectedListener(ValueChangeEvent e) {
 		this.unidadeName = (String)e.getNewValue();
		this.availableObjEsp.clear();
		String unidadeAll = PDIControllerCached.getInstance().getUnidadeAll().getName();
/*		for(Map.Entry<String, ObjetivoEspecificoTreeNode> entry: ((PDIControllerBean)this.getMBean("pdiControllerBean")).getMapObjEspecificoTreeNode().entrySet()) {
			if(entry.getValue().getUnidadeName().equals(this.unidadeName) || this.unidadeName.equals(unidadeAll))
				this.availableObjEsp.add(new SelectItem(entry.getKey()));
		}*/
		for(Map.Entry<String, List<ObjetivoEspecificoTreeNode>> entry: this.mapObjEspecificoTreeNode.entrySet()) {
			ObjetivoEspecificoTreeNode obj = entry.getValue().get(0);
			if(obj == null)
				continue;
			if(obj.getUnidadeName().equals(this.unidadeName) || this.unidadeName.equals(unidadeAll))
				this.availableObjEsp.add(new SelectItem(entry.getKey()));
		}
		if(this.selectedObjEsp != null)
			this.selectedObjEsp.clear();
	}
}