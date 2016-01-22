package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.ifpr.sisplan.controller.PDIControllerCached;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroAbstract;
import br.ifpr.sisplan.controller.tree.DiretrizTreeNode;
import br.ifpr.sisplan.controller.tree.EixoTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEspecificoTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEstrategicoTreeNode;
import br.ifpr.sisplan.model.dao.ObjetivoEspecificoDao;
import br.ifpr.sisplan.model.table.ObjetivoEspecifico;

public class NovoObjetivoBean extends NovoCadastro<TreeNodeCadastroAbstract> implements Validator {
	private static final long serialVersionUID = -4222480778726435646L;
	private static NovoObjetivoBean instance;

    private List<SelectItem> availableObjEst; // +getter (no setter necessary)
    private List<String> selectedObjEst; // +getter +setter
    
	private Map<String, ObjetivoEstrategicoTreeNode> mapObjEstrategicoTreeNode;
    
	public NovoObjetivoBean() {
		super();
	}
	
	public static NovoObjetivoBean getInstance() {
		if(instance == null)
			instance = new NovoObjetivoBean();
		
		return instance;
	}
	
	public String getObjetivoEstrategicoName() {
		return this.parent.getName();
	}
	
	public String getObjetivoEstrategicoDesc() {
		return this.parent.getDescricao();
	}

	public void save() {
		if(this.validateFields()) {
			final ObjetivoEspecifico objEspecifico = getDAO(ObjetivoEspecificoDao.class).insertObj(this.descricao);
			
			for(String strObj: this.selectedObjEst) {
				ObjetivoEstrategicoTreeNode objEstr = this.mapObjEstrategicoTreeNode.get(strObj);
				getDAO(ObjetivoEspecificoDao.class).insertUnidadeObjetivos(this.getUnidadeSelected().getId(), objEstr.getMyID(), objEspecifico.getId());
				
				ObjetivoEspecificoTreeNode objEsp = new ObjetivoEspecificoTreeNode(objEstr, objEspecifico, objEstr.getChildCount());
				objEstr.addTreeNodeChild(objEsp);
	
			}
			this.returnMainPage();
		}
	}

	public String getDefaultUnidadeName() {
		if(listUnidades.size() == 1)
			return listUnidades.get(0).getLabel();
		return "Selecione Unidade";
	}

	public List<String> getSelectedObjEst() {
		return selectedObjEst;
	}

	public void setSelectedObjEst(List<String> selectedObjEst) {
		this.selectedObjEst = selectedObjEst;
	}

	public List<SelectItem> getAvailableObjEst() {
		return availableObjEst;
	}
	
	public void setAvailableObjEst(List<SelectItem> availableObjEst) {
		this.availableObjEst = availableObjEst;
	}

	public void validate(FacesContext fc, UIComponent component, Object value)
			throws ValidatorException {
		
		System.out.println("NovoObjetivoBean Validator-----");
	}

	@Override
	protected void initInternalStructure() {
		this.mapObjEstrategicoTreeNode = new LinkedHashMap<String, ObjetivoEstrategicoTreeNode>();
		this.availableObjEst = new ArrayList<SelectItem>();
		for(EixoTreeNode eixo: ((PDIControllerBean)this.getMBean("pdiControllerBean")).getCurrentPDI().getEixosTree())
			for(DiretrizTreeNode dir: eixo.getDiretrizesTree())
				for(ObjetivoEstrategicoTreeNode obj: dir.getObjetivosTree()) {
					this.mapObjEstrategicoTreeNode.put(obj.getDescricao(), obj);
					this.availableObjEst.add(new SelectItem(obj.getDescricao()));	
				}
	}

	@Override
	protected boolean validateFields() {
		boolean ret = true; 
		if(this.name.isEmpty()) {
			addMensagemErro("Descrição está vazia, ela deve ser preenchida.");
			ret = false;
		}
		if(this.unidadeName.isEmpty() || this.unidadeName.equals(SELECIONE_UNIDADE)
				|| this.unidadeName.equals(PDIControllerCached.getInstance().getUnidadeAll())) {
			addMensagemErro("Unidade não pode ser vazia.");
			ret = false;
		}
		if(!(this.selectedObjEst != null && !this.selectedObjEst.isEmpty())) {
			addMensagemErro("Nenhum Objetivo Específico foi selecionado.");
			ret = false;
		}
			
		return ret;
	}
	
}