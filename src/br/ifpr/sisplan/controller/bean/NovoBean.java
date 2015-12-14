package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import br.ifpr.sisplan.controller.tree.DiretrizTreeNode;
import br.ifpr.sisplan.controller.tree.EixoTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEstrategicoTreeNode;
import br.ifpr.sisplan.controller.tree.PDITreeNode;
import br.ifpr.sisplan.controller.tree.UnidadeTreeNode;
import br.ufrn.arq.web.jsf.AbstractController;

public class NovoBean extends AbstractController {
	private static final long serialVersionUID = -1400038438422892956L;
	String selectedUnidade;
	String nome;
	//protected SortedMap<Unidade, List<ObjetivoEspecifico>> mapUnidadeObjetivos;
	List<ObjetivoEstrategicoTreeNode> listObjEstrategicoTreeNode;
	List<UnidadeTreeNode> listUnidadeTreeNode;
	
	public NovoBean() {
		//this.fillMap();
		this.fillListObjEstrategicos();
		this.fillListUnidade();
	}
/*	
	private void fillMap() {
		this.mapUnidadeObjetivos = new TreeMap<Unidade, List<ObjetivoEspecifico>>();
		final List<Unidade> unidades = ((SisplanUser)this.getMBean("sisplanUser")).getUnidades();
		for(Unidade u: unidades) {
			List<ObjetivoEspecifico> listObj = ConverterToList.convertListMappedToList(
								getDAO(ObjetivoEspecificoDao.class).selectObjetivosByUnidade(u.getId()), ObjetivoEspecifico.class);
			this.mapUnidadeObjetivos.put(u, listObj);
		}
	}*/
	
	private void fillListObjEstrategicos() {
		this.listObjEstrategicoTreeNode = new ArrayList<ObjetivoEstrategicoTreeNode>();
		final PDITreeNode pdiTreeNode = ((PDIControllerBean)this.getMBean("pdiControllerBean")).getCurrentPDI();
		for(EixoTreeNode eixo: pdiTreeNode.getEixosTree())
			for(DiretrizTreeNode diretriz: eixo.getDiretrizesTree())
				this.listObjEstrategicoTreeNode.addAll(diretriz.getObjetivosTree());
	}
	
	private void fillListUnidade() {
		this.listUnidadeTreeNode = new ArrayList<UnidadeTreeNode>();
		try {
			//this.listUnidadeTreeNode.addAll(this.listObjEstrategicoTreeNode.get(0).getUnidadesTree());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<SelectItem> getUnidades() {
		final List<SelectItem> listUnidades = new ArrayList<SelectItem>(); 
/*		for(Unidade u: this.mapUnidadeObjetivos.keySet()) {
			SelectItem item = new SelectItem(u.getName(), u.getName());
			listUnidades.add(item);
		}*/
		for(UnidadeTreeNode u: this.listUnidadeTreeNode) {
			SelectItem item = new SelectItem(u.getName(), u.getName());
			listUnidades.add(item);
		}
		return listUnidades;
	}
	public String getSelectedUnidade() {
		return selectedUnidade;
	}
	public void setSelectedUnidade(String unidade) {
		this.selectedUnidade = unidade;
	}
	public void newSelectedUnidade(ValueChangeEvent e) {
		this.selectedUnidade = (String) e.getNewValue();
	}
	
/*	public List<SelectItem> getObjetivos() {
		final List<SelectItem> listObj = new ArrayList<SelectItem>();
		for(ObjetivoEspecifico obj: this.getListObj(this.selectedUnidade)) {
			SelectItem item = new SelectItem(obj.getName(), obj.getName());
			listObj.add(item);
		}
		return listObj;
	}*/
	
/*	private List<ObjetivoEspecifico> getListObj(String unidade) {
		for(Map.Entry<Unidade, List<ObjetivoEspecifico>> entry: this.mapUnidadeObjetivos.entrySet()) {
			if(entry.getKey().getName().equals(unidade))
				return entry.getValue();
		}
		return new ArrayList<ObjetivoEspecifico>();
	}*/
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
